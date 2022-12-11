package AnalizadorSintactico;
import AnalizadorLexico.*;
import ArbolSintactico.GenerarCodigo;
import ArbolSintactico.Nodo;
import ArbolSintactico.NodoBinario;
import ArbolSintactico.NodoHijo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AnalizadorSintactico {

    private AnalizadorLexico analizadorLexico;
    private Parser parser;
    private ArrayList<String> erroresSintacticos;
    private TablaSimbolos tablaSimbolos;
    private ArrayList<String> analisisSintactico;
    private String tipo;
    private Nodo raiz;
    private ArrayList<Integer> variables; // estructura que se utiliza para ir guardando en la gramatica los id's a medida que se encuentran

    private HashMap<String,Nodo> arbolesFunciones;

    private ArrayList<String> cadenas;

    public AnalizadorSintactico(AnalizadorLexico l, Parser p){
        analizadorLexico = l;
        parser = p;
        erroresSintacticos = new ArrayList<String>();
        analisisSintactico = new ArrayList<String>();
        cadenas = new ArrayList<String>();
        tablaSimbolos = l.getTablaSimbolos();
        tipo = "";
        raiz = null;
        variables =  new ArrayList<Integer>();
        arbolesFunciones = new HashMap<>();

    }

    public ArrayList<String> getCadenas(){return this.cadenas;}
    public void addCadena(int index){
        this.cadenas.add(tablaSimbolos.getEntrada(index).getLexema());
    }

    public void agregarArbolFuncion(ParserVal arbol, String nombreFuncion){
        Nodo n = (Nodo) arbol.obj;
        this.arbolesFunciones.put(nombreFuncion,n);
    }

    public void imprimirArbolesFuncion(){
        for (Map.Entry<String,Nodo> entry : this.arbolesFunciones.entrySet()){
            System.out.println("");
            System.out.println("ARBOL DE LA FUNCION "+entry.getKey());
            imprimirArbol(entry.getValue(),0);
        }
    }

    public void imprimirArbolesFuncion(BufferedWriter bw) throws IOException{
        bw.write(" \n ");
        for (Map.Entry<String,Nodo> entry : this.arbolesFunciones.entrySet()){

            bw.write(" ARBOL DE LA FUNCION "+entry.getKey() + " \n");
            imprimirArbol(entry.getValue(),0, bw);
            bw.write(" \n ");
        }
    }

    // -- Adders
    public void addListaVariables(int refTS){
        variables.add(refTS);
    }
    public void addErrorSintactico(String nuevo) {
        erroresSintacticos.add(nuevo);
    }
    public void addAnalisis(String nuevo) {
        analisisSintactico.add(nuevo);
    }



    // -- Setters
    /** Setea en negativo el valor del Token asociado al indice */
    public void setNegativoTablaSimb(int indice) {
        String sinSigno = this.tablaSimbolos.getEntrada(indice).getLexema();
        this.tablaSimbolos.getEntrada(indice).setLexema("-" + sinSigno);
    }

    /** Setea la raiz del arbol Sintactico */
    public void setRaiz(ParserVal raiz){
        this.raiz = (Nodo) raiz.obj;
    }

    /** Setea el nuevo AMBITO para el Token que corresponde a ese indice en la Tabla de Simbolos */
    public void setLexemaEnIndex(int indice, String ambito){
        String nuevoLex = getEntradaTablaSimb(indice).getLexema() + ambito;
        getEntradaTablaSimb(indice).setLexema(nuevoLex);
    }

    /** Setea el nuevo USO para el Token que corresponde a ese indice en la Tabla de Simbolos */
    public void setUsoEnIndex(String uso, int indice){
        tablaSimbolos.getEntrada(indice).setUso(uso);
    }

    /** Setea el nuevo TIPO para el Token que corresponde a ese indice en la Tabla de Simbolos */
    public void setTipoEnIndex(String tipo, int indice){
        tablaSimbolos.getEntrada(indice).setTipo(tipo);
    }

    /** Set de nuevo tipo para la variable local (Se usa como var comunitaria para cuando no se puede instanciar inmediatamente el tipo debido a la recursion de los estados) */
    public void setTipoGlobal(String tipo) {
        this.tipo = tipo;
    }

    /** Para la lista previamente cargada con los ids de las variables, les setea el tipo dado. */
    public void completarConTipos(String tipo){
       for(int idVariable : variables){
           tablaSimbolos.getEntrada(idVariable).setTipo(tipo);
       }
       vaciarListaVariables();
    }


    public void setUsoParam(String idFun){
        for(int i=0;i<variables.size(); i++){
            Token param = getEntradaTablaSimb(variables.get(i));
            param.setUso(param.getUso()+"@"+idFun+"@"+i);
        }
    }


    // -- Getters

    /** @param indice
     * @return  el Token asociado en la Tabla de Simbolos */
    public Token getEntradaTablaSimb(int indice) {
        return this.tablaSimbolos.getEntrada(indice);
    }

    /** @return  el tipo del Token asociado al indice */
    public String getTipoFromTS(int indice) {
        return this.tablaSimbolos.getEntrada(indice).getTipo(); }

    /** Devuelve el utimo tipo que fue seteado en el Analizador. Se usa como un aux*/
    public String getTipo() { return  this.tipo;}

    /** Devuelve la tabla de simbolos */
    public TablaSimbolos getTS() {
        return this.tablaSimbolos;}



    // -- Creacion del ARBOL SINTACTICO

    public ParserVal modificarHijo(ParserVal arbolSentencias, Nodo nuevo){
        System.out.println("");
        System.out.println("");
        System.out.println("");

        Nodo n = (Nodo) arbolSentencias.obj;
        if (n.getLexema().equals("declarativa")){// en este caso la primera sentencia era declarativa, por lo que aun no se genero un arbol

            if (n.getHijoIzquierdo()!= null && (n.getHijoIzquierdo().getLexema().equals("lista_ctes") || n.getHijoIzquierdo().getLexema().equals("when"))) {

                n = n.getHijoIzquierdo().getHijoIzquierdo();
                agregarNuevoNodo(n, nuevo);
                return new ParserVal(n);
            }

            n = nuevo;
            return new ParserVal(n);
        }

        if (nuevo.getLexema().equals("declarativa")) {
            if (n.getHijoIzquierdo()!= null && (n.getHijoIzquierdo().getLexema().equals("lista_ctes") || n.getHijoIzquierdo().getLexema().equals("when"))) {
                nuevo = nuevo.getHijoIzquierdo().getHijoIzquierdo();
            } else {
                return new ParserVal(n); // las declarativas que no tienen que ver con lista-ctes no se agregan al arbol.
            }

        }

        agregarNuevoNodo(n, nuevo);
        return new ParserVal(n);
    }


    public String tipoResultante(String id, Nodo izq, Nodo der){

        if (izq.getTipo() != null && der.getTipo() != null ) {
        //    System.out.println("EL NODO IZQ ES " + izq.toString());
       //     System.out.println("EL NODO DER ES " + der.toString());
            if(izq.getTipo().equals(der.getTipo())){

                System.out.println("---------------- LOS TIPOS SON IGUALESS " + izq.getTipo());
                return izq.getTipo();
            }
            this.addErrorSintactico("SemanticError. LOS TIPOS NO COINCIDEN - OPERACION: " + id + " (Línea " + AnalizadorLexico.LINEA + " )");
        }
        return null;
    }

    public void imprimirNodos(Nodo izq, Nodo der){
        if (izq != null && der != null ) {
            System.out.println("EL NODO IZQ ES " + izq.toString());
            System.out.println("EL NODO DER ES " + der.toString());
        }else if(izq != null ) {
            System.out.println("EL NODO IZQ ES " + izq.toString());
            System.out.println("EL NODO DER ES  NULL" );
        }
        else
            System.out.println("--" );
    }

    public Nodo crearHoja(int indice){

        String lexema = this.tablaSimbolos.getEntrada(indice).getLexema();
        Nodo i = new NodoHijo(null, lexema,indice);
        i.setTipo(this.tablaSimbolos.getEntrada(indice).getTipo());
        return i;
    }

    public Nodo crearNodo(String identificador, ParserVal hijoIzq, ParserVal hijoDer){


        if (hijoDer == null){

            Nodo i = new NodoBinario(hijoIzq.obj,null,identificador);
            return i;
        } else {

            Nodo i = new NodoBinario(hijoIzq.obj, hijoDer.obj, identificador);
            if (!i.getLexema().equals("condicion y operacion for") && !i.getLexema().equals("encabezado for") && !i.getLexema().equals("For") && !i.getLexema().equals("etiqueta") && !i.getLexema().equals("for-etiquetado")){
                i.setTipo( tipoResultante( identificador, (Nodo)hijoIzq.obj, (Nodo)hijoDer.obj));
            }
            imprimirNodos((Nodo)hijoIzq.obj, (Nodo)hijoDer.obj);
            System.out.println("EL NODO RESULTANTE ES " + i.toString() + " DE TIPO"+ (String)i.getTipo());

            return i;

        }
    }

    public Nodo crearNodoControl(String identificador, ParserVal hijo){
        if (hijo == null){
            Nodo i = new NodoHijo(null, identificador);
            return i;
        }
        Nodo i = new NodoHijo(hijo.obj, identificador);
      //NO  i.setTipo(((Nodo)hijo.obj).getTipo());
        return i;
    }

    public Nodo crearNodoParam(String identificador, ParserVal izq, ParserVal der){
        if (der == null){
            Nodo i = new NodoBinario(izq.obj,null, identificador);
            return i;
        }
        Nodo i = new NodoBinario(izq.obj, der.obj, identificador);
        return i;
    }


    public Nodo crearNodoFunc(int indice, ParserVal hijo){
        String lexema = this.tablaSimbolos.getEntrada(indice).getLexema();

        Nodo i = new NodoHijo(hijo.obj, lexema, indice);
        i.setTipo(this.tablaSimbolos.getEntrada(indice).getTipo());
        return i;

    }

    public Nodo crearNodoFor(String identificador, ParserVal izq, ParserVal der){
        if (der == null){
            Nodo i = new NodoBinario(izq.obj,null, identificador);
            return i;
        }
        Nodo i = new NodoBinario(izq.obj, der.obj, identificador);
        return i;


    }
    public void agregarNuevoNodo(Nodo n, Nodo nuevo){
        if (n.getHijoDerecho() == null){
            n.setHijoDerecho(nuevo);
        } else {
            agregarNuevoNodo(n.getHijoDerecho(),nuevo);
        }
    }

    public int encontrarTag(int refTag, String ambito){
        String lexTag = this.tablaSimbolos.getEntrada(refTag).getLexema();
        return this.tablaSimbolos.existeEntradaContainsTag(lexTag+"@"+ambito);
    }


    // -- Deleters

    public void eliminarEntrada(int indice){this.tablaSimbolos.eliminarEntrada(indice);}

    public void clearTipo(){
        this.tipo = null;
    }
    public void vaciarListaVariables(){
        if (!variables.isEmpty()){
            variables.clear();
        }
    }



    // -- Printers

    public void imprimirTablaSimbolos(BufferedWriter bw) throws IOException {
        tablaSimbolos.imprimir(bw);
    }

    public void imprimirTablaSimbolos() {
        System.out.println();
        if (this.tablaSimbolos.isEmpty())
            System.out.println("|----- TABLA DE SIMBOLOS VACIA ------|");
        else tablaSimbolos.imprimir();

    }

    public void imprimirErroresSintacticos(BufferedWriter bw) throws IOException {

        bw.write("\n \n ---- ERRORES SINTACTICOS Y SEMANTICOS ---  \n \n ");

        if (this.erroresSintacticos.isEmpty()){
            bw.write("\n vacio. \n ");
        }else{
            for (String dato : this.erroresSintacticos){
                bw.write("\n ");
                bw.write(dato);
            }
        }
        bw.write("\n ");
    }

    public void imprimirLista(ArrayList<String> lista) {

        System.out.println(" ");

        if (!this.analisisSintactico.isEmpty())
            for (String dato : lista)
                System.out.println(dato);
        else
            System.out.println("vacío.");
        System.out.println();
    }


    public void imprimirAnalisisSintactico(BufferedWriter bw) throws IOException {

        bw.write("\n \n 🔎  ANALISIS SINTÁCTICO    \n \n ");

        if (!this.analisisSintactico.isEmpty())
            for (String dato : this.analisisSintactico) {
                bw.write("\n ");
                bw.write(dato);
            }
        else
            bw.write(" \n Análisis sintáctico vacío. \n");

        bw.write("\n ");

    }

    public void imprimirAnalisisLexico(BufferedWriter bw) {
        System.out.println();
        System.out.println("|  ANÁLIZADOR LÉXICO  |");
        ArrayList<Token> detectados = this.analizadorLexico.getListaTokens();

        for (Token t : detectados) {
            System.out.println("----------------------------------");
            System.out.println("Tipo : " + t.getTipo());
            System.out.println("Lexema: " + t.getLexema());
        }
    }

    public void imprimirArbol(Nodo nodo, int nivel){
        if (nodo == null) {

            return;
        }
        for (int i=0; i<nivel; i ++){
            System.out.print("   ");
        }
        System.out.println(" └─ " + (nodo.getLexema().toString()) + " "); // mostrar datos del nodo
        nivel++;
        imprimirArbol(nodo.getHijoIzquierdo(),nivel); //recorre subarbol izquierdo
        imprimirArbol(nodo.getHijoDerecho(),nivel); //recorre subarbol derecho
        nivel--;
    }

    public void imprimirArbol(Nodo nodo, int nivel, BufferedWriter bw ) throws IOException {
        if (nodo == null) {
            return;
        }
        for (int i=0; i<nivel; i ++){
            bw.write("   ");

        }
        bw.write("  └─ " + nodo.getLexema().toString() + " \n"); // mostrar datos del nodo
        nivel++;
        imprimirArbol(nodo.getHijoIzquierdo(),nivel, bw); //recorre subarbol izquierdo
        imprimirArbol(nodo.getHijoDerecho(),nivel, bw); //recorre subarbol derecho
        nivel--;
    }



    // -- Otros
    public void verificarRangoEnteroLargo(int indice) {

        String lexema = this.tablaSimbolos.getEntrada(indice).getLexema();
        Long numero = Long.parseLong(lexema);

        if(numero == 2147483648L) {
            this.tablaSimbolos.eliminarEntrada(indice);    // Se elimina la entrada de la tabla de símbolos.
            this.addErrorSintactico("SyntaxError FUERA DE RANGO (Línea " + this.analizadorLexico.LINEA + "): CONST LONG");
        }
    }

    public void checkParametros(String idFun){

            String nombFun = idFun.split("@")[0];
            ArrayList<Token> parametros = tablaSimbolos.obtenerParamPorUso("param"+"@"+nombFun);
            if(parametros.size() != variables.size()  ){
                this.addErrorSintactico("SemanticError. El NUMERO de parametros no corresponde con los funcion invocada (Línea " + this.analizadorLexico.LINEA + ")" );
            }else {
                for (int i = 0; i < variables.size() ; i++) {
                    Token paramInvocado = tablaSimbolos.getEntrada(variables.get(i));
                    if(!parametros.get(i).getTipo().equals(paramInvocado.getTipo())){
                        this.addErrorSintactico("SemanticError. LOS TIPOS DE LOS PARAMETROS FORMALES/REALES NO COICIDEN (Línea " + this.analizadorLexico.LINEA + ")" );
                    }
                }
            }
            vaciarListaVariables();

    }

    public void checkRetorno(ParserVal hijo, String ultimoSetFun){

        if(!((Nodo)hijo.obj).getTipo().equals(ultimoSetFun)){
            this.addErrorSintactico("SemanticError. NO SE RECONOCE EL TIPO DE RETURN (Línea " + this.analizadorLexico.LINEA + ")" );
        }
        clearTipo();
    }

    public void analisisParser(String programa, String estadoParser ) {
        try {
            String ruta = "salida_archivo/ejecucion_reciente.txt";
            String contenido;
            File file = new File(ruta);

            // Si el archivo no existe es creado

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(programa);  // Leo el codigo fuente

            bw.write(estadoParser);
            bw.write(" \n |||||||||||||||||||||||||||||||||||||||||||||| " ) ;

            this.imprimirAnalisisLexico(bw);
            this.analizadorLexico.imprimirErrores(bw);
            this.imprimirAnalisisSintactico(bw);
            this.imprimirErroresSintacticos(bw);
            this.imprimirTablaSimbolos(bw);

            bw.write("\n --- ARBOL ---- \n ");

            this.imprimirArbol(this.raiz, 0, bw);
            this.imprimirArbolesFuncion(bw);

            String horaActual = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a").format(LocalDateTime.now());
            bw.write(" \n |||||||||||||||||||||||||||||||||||||||||||||| ") ;
            bw.write(" \n Hora de ejecucion: " + horaActual);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -- Analizador Sintactico START
    public void startConsola() {
        System.out.println("________________________________________________");
        parser.activarAmbito();
        parser.setLexico(this.analizadorLexico);
        parser.setSintactico(this);

        if (parser.yyparse() == 0) {

            System.out.println(" \n \n ✅ EJECUCION DEL PARSER FINALIZADA \n \n  ");

            System.out.println("\n \n 🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧 \n ");

            // imprimirAnalisisLexico();


        }
        else
            System.out.println(" \n \n ❌ EL PARSER NO PUDO TERMINAR \n \n ");

        //   analizadorLexico.imprimirErrores();

        analizadorLexico.setPosArchivo(0);
        analizadorLexico.setBuffer("");

        System.out.println(" \n \n 💜 Analisis Sintactico ");
        imprimirLista(this.analisisSintactico);
        System.out.println(" \n \n ❤️ Errores Sintacticos y Semanticos ");
        imprimirLista(this.erroresSintacticos);
        imprimirTablaSimbolos();

        System.out.println(" ");

        System.out.println("🌳 ARBOL 🌳 ");
        imprimirArbol(this.raiz,0);
        imprimirArbolesFuncion();
        // GenerarCodigo g = new GenerarCodigo(analizadorLexico);
     //    g.generacionDeCodigo(this.raiz);
    }

    public void start() {

        parser.activarAmbito();
        parser.setLexico(this.analizadorLexico);
        parser.setSintactico(this);


        String estadoParser ;

        if (parser.yyparse() == 0) {
            estadoParser = (" \n \n  EJECUCION DEL PARSER FINALIZADA  \n ") ;
        }
        else
            estadoParser = (" \n \n EL PARSER NO PUDO TERMINAR \n ");


        analizadorLexico.setPosArchivo(0);
        analizadorLexico.setBuffer("");

        this.analisisParser(analizadorLexico.getArchivo(), estadoParser);
        if (this.erroresSintacticos.isEmpty() && this.analizadorLexico.getErroresLexicos().isEmpty()){
            GenerarCodigo g = new GenerarCodigo(analizadorLexico, this);
            g.generarCodigoFunciones(arbolesFunciones);
            g.generacionDeCodigo(this.raiz);
        }else{
            System.out.println("Se encontraron errores LEXICOS o SINTACTICOS, por lo tanto no se pudo generar el assembler"+"\n");
        }


    }


}




