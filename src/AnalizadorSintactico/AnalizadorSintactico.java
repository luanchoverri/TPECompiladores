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
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSpinnerUI;


public class AnalizadorSintactico {

    private AnalizadorLexico analizadorLexico;
    private Parser parser;
    private ArrayList<String> erroresSintacticos;
    private TablaSimbolos tablaSimbolos;
    private ArrayList<String> analisisSintactico;
    private String tipo;
    private Nodo raiz;



    private ArrayList<String> warnings;
    private ArrayList<Integer> variables; // estructura que se utiliza para ir guardando en la gramatica los id's a medida que se encuentran
    public static boolean ASSEMBLER_GENERADO = false;

    private HashMap<String,Nodo> arbolesFunciones;

    private ArrayList<String> cadenas;

    public HashMap<String, Nodo> getArbolesFunciones() {
        return arbolesFunciones;
    }

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
        warnings = new ArrayList<>();

    }

    public ArrayList<String> getWarnings() {
        return warnings;
    }

    public void addWarning(String warning){
        this.warnings.add(warning);
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

    public ParserVal modificarHijo(ParserVal arbolSentencias, Nodo hijoNuevo){
        System.out.println("");
        System.out.println("");
        System.out.println("");

        Nodo arbol = (Nodo) arbolSentencias.obj;

//        System.out.println(" el nodo a modificar es:" );
//        imprimirArbol(arbol, 0);
//        System.out.println(" el hijo es:" );
//        imprimirArbol(hijoNuevo, 0);


        if (esNodoDeclarativo(arbol) && esNodoDeclarativo(hijoNuevo)) {

            Nodo papa = (Nodo)(limpiarDeclarativasArbol(arbol)).obj;
            Nodo hijo = (Nodo)(limpiarDeclarativasArbol(hijoNuevo)).obj;

            if (!(esNodoDeclarativo(papa) && esNodoDeclarativo(hijo)))
                return modificarHijo(new ParserVal(papa), hijo);

            return new ParserVal(hijo);
        }

        else if (esNodoDeclarativo(arbol)){

            Nodo papa = (Nodo)(limpiarDeclarativasArbol(arbol)).obj;

            if(!esNodoDeclarativo(papa)){ // se pudo limpiar
                agregarNuevoNodo(papa, hijoNuevo);

                imprimirArbol(papa, 0);

                return new ParserVal(papa);
            }
            //papa = hijoNuevo;
            return new ParserVal(hijoNuevo);

        }

        else if (esNodoDeclarativo(hijoNuevo)){
           // System.out.println(" ------- ENTRA EN 3 " );
            Nodo hijo = (Nodo)(limpiarDeclarativasArbol(hijoNuevo)).obj;
            if (!esNodoDeclarativo(hijo)) { //se pudo limpiar
                agregarNuevoNodo(arbol, hijo);
                return new ParserVal(arbol);
            }
            return new ParserVal(arbol);


        }
        else { // caso donde son ambos sentencias
          //  System.out.println(" ------- ENTRA EN 4 " );
            agregarNuevoNodo(arbol, hijoNuevo);
            return new ParserVal(arbol);
        }


    }

    public boolean esNodoDeclarativo(Nodo n){
        return n.getLexema().equals("declarativa");
    }




    public ParserVal limpiarDeclarativasArbol(Nodo nodo){

        if (nodo.getLexema().equals("declarativa")){
            if (nodo.getHijoIzquierdo()!= null && (nodo.getHijoIzquierdo().getLexema().equals("lista_ctes") || nodo.getHijoIzquierdo().getLexema().equals("when"))) {
                Nodo limpio = nodo.getHijoIzquierdo(); //TODO se queda con lista-cte y con when, puede quedarse solo con hijo de lista de ctes pero sin when no xq tiene dos hijos
                if(limpio.getLexema().equals("lista_ctes")) {
                    limpio = limpio.getHijoIzquierdo();
                }


                return new ParserVal(limpio);
            }
        }
        return new ParserVal(nodo);
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

//    public Nodo crearHoja(String lexema){
//
//        String lexema = lexema;
//        Nodo i = new NodoHijo(null, lexema,indice);
//        i.setTipo(this.tablaSimbolos.getEntrada(indice).getTipo());
//        return i;
//    }

    public boolean tieneBreak(ParserVal hijo){
        return (((Nodo)hijo.obj).getTieneBreak() != null);
    }

    public void checkBreaks(Nodo izq, Nodo der){

        String breakDer = der.getTieneBreak();
        String breakizq = izq.getTieneBreak();
            if (!(breakDer.equals(breakizq))){
                this.addErrorSintactico("SemanticError. LOS TIPOS NO COINCIDEN -  BREAK IF/ELSE:  (Línea " + AnalizadorLexico.LINEA + " )");
            }
    }


    public Nodo crearNodo(String identificador, ParserVal hijoIzq, ParserVal hijoDer){

        if (hijoDer == null){

            Nodo i = new NodoBinario(hijoIzq.obj,null,identificador);
            if (i.getTieneBreak() != null) {
                i.setTieneBreak(i.getTieneBreak());
            }

            return i;
        }

        Nodo i = new NodoBinario(hijoIzq.obj, hijoDer.obj, identificador);
//            if (!i.getLexema().equals("condicion y operacion for") && !i.getLexema().equals("encabezado for") && !i.getLexema().equals("For") && !i.getLexema().equals("etiqueta") && !i.getLexema().equals("for-etiquetado")){
//                i.setTipo( tipoResultante( identificador, (Nodo)hijoIzq.obj, (Nodo)hijoDer.obj));
//            }

        return i;



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
            System.out.println(" |  TABLA DE SIMBOLOS: VACIA  | ");
        else tablaSimbolos.imprimir();

    }

    public void imprimirErroresSintacticos(BufferedWriter bw) throws IOException {



        if (this.erroresSintacticos.isEmpty()){
            bw.write("\n \n|    ERRORES SINTACTICOS - SEMANTICOS:  NO HAY   | \n \n ");
        }else{
            bw.write("\n \n|    ERRORES SINTACTICOS - SEMANTICOS  | \n \n ");
            for (String dato : this.erroresSintacticos){
                bw.write("*  " + dato + " \n ");
            }
        }
        bw.write("\n ");
    }

//    public void imprimirLista(ArrayList<String> lista) {
//
//        System.out.println(" ");
//
//        if (!this.analisisSintactico.isEmpty())
//            for (String dato : lista)
//                System.out.println(dato);
//        else
//            System.out.println("vacío.");
//        System.out.println();
//    }


    public void imprimirAnalisisSintactico(BufferedWriter bw) throws IOException {



        if (!this.analisisSintactico.isEmpty()) {
            bw.write("\n \n |   ANALISIS SINTACTICO   |   \n \n ");
            for (String dato : this.analisisSintactico) {
                bw.write("*  " + dato + " \n ");
            }
        }
        else
            bw.write(" \n |  ANALISIS SINTACTICO: NO HAY | \n");

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
    public boolean verificarRangoEnteroLargo(int indice) {

        String lexema = this.tablaSimbolos.getEntrada(indice).getLexema();
        Long numero = Long.parseLong(lexema);

        if((numero >=  AnalizadorLexico.MAXIMO_ENTERO_LARGO) || (numero <= AnalizadorLexico.MINIMO_ENTERO_LARGO)) {
            this.addErrorSintactico("(SyntaxError) FUERA DE RANGO: ENTERO LARGO i32 (Línea " + this.analizadorLexico.LINEA + ")");
            return false;
        }
        else return true;
    }

    public double convertFloat(String lexema){
        double floatBuffer = 0f;
        if (lexema.equals("-0.0") || lexema.equals("-0.") || lexema.equals("-.0")){
            lexema = lexema.replace("-", "");
        }
        if (lexema.contains("F")){
            String[] parts = lexema.split("F");
            floatBuffer = (Double.valueOf(parts[0]) * Math.pow(10, Double.valueOf(parts[1])));
        }else{
            if(lexema.startsWith("."))
                lexema = lexema.replace(".", "0.");
            else if(lexema.endsWith("."))
                    lexema = lexema.replace(".", ".0");
            if (lexema.equals("-0.0")){
                lexema = lexema.replace("-", "");
            }

            floatBuffer = Double.parseDouble(lexema);
        }

        return floatBuffer;
    }

    public boolean verificarRangoFlotante(int indice) {

        String lexema = this.tablaSimbolos.getEntrada(indice).getLexema();
        double numero = this.convertFloat(lexema);
        if (lexema.equals("-0.0") || lexema.equals("-0.") || lexema.equals("-.0")){
            lexema = lexema.replace("-", "");
        }
        if (lexema.startsWith("-") ){
            if((numero >=  AnalizadorLexico.MINIMO_FLOAT * -1) || (numero <= AnalizadorLexico.MAXIMO_FLOAT * -1)) {
                this.addErrorSintactico("(SyntaxError) FUERA DE RANGO: FLOTANTE f32 (-) (Línea " + this.analizadorLexico.LINEA + ")");
                return false;
            }
        }else{
            if ((numero != 0.0)){
                if((numero <=  AnalizadorLexico.MINIMO_FLOAT) || (numero >= AnalizadorLexico.MAXIMO_FLOAT)) {
                    this.addErrorSintactico("(SyntaxError) FUERA DE RANGO: FLOTANTE f32 (+) (Línea " + this.analizadorLexico.LINEA + ")");
                    return false;
                }
            }
        }

        return true;
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

    }

    public void analisisParser(String programa, String estadoParser ) {
        try {
            String filename = "ejecucion_reciente" ;
//
//            System.out.println("nombre archivo");
//            Scanner scanner = new Scanner(System.in);
//            filename = scanner.next();

            filename = filename.replace(" ", "_") + ".txt";

            String ruta = "salida_archivo/"+ filename;
            String[] contenido = programa.split("\n");
            int nroLinea = 1;
            File file = new File(ruta);

            // Si el archivo no existe es creado

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

              // Leo el codigo fuente
            for (String linea : contenido){
                bw.write(nroLinea+":    "+linea+"\n");
                nroLinea++;
            }
            bw.write(" \n --------------------------- " + estadoParser  ) ;

            this.analizadorLexico.imprimirErrores(bw);
            this.imprimirErroresSintacticos(bw);
            //this.imprimirAnalisisSintactico(bw);
            this.imprimirTablaSimbolos(bw);

            bw.write("\n ARBOL SINTACTICO  \n ");

            this.imprimirArbol(this.raiz, 0, bw);
            this.imprimirArbolesFuncion(bw);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -- Analizador Sintactico START
//    public void startConsola() {
//        System.out.println("________________________________________________");
//        parser.activarAmbito();
//        parser.setLexico(this.analizadorLexico);
//        parser.setSintactico(this);
//
//        if (parser.yyparse() == 0) {
//
//            System.out.println(" \n \n ✅ EJECUCION DEL PARSER FINALIZADA \n \n  ");
//
//            System.out.println("\n \n 🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧 \n ");
//
//            // imprimirAnalisisLexico();
//
//
//        }
//        else
//            System.out.println(" \n \n ❌ EL PARSER NO PUDO TERMINAR \n \n ");
//
//        //   analizadorLexico.imprimirErrores();
//
//        analizadorLexico.setPosArchivo(0);
//        analizadorLexico.setBuffer("");
//
//        System.out.println(" \n \n 💜 Analisis Sintactico ");
//        imprimirLista(this.analisisSintactico);
//        System.out.println(" \n \n ❤️ Errores Sintacticos y Semanticos ");
//        imprimirLista(this.erroresSintacticos);
//        imprimirTablaSimbolos();
//
//        System.out.println(" ");
//
//        System.out.println("🌳 ARBOL 🌳 ");
//        imprimirArbol(this.raiz,0);
//        imprimirArbolesFuncion();
//        // GenerarCodigo g = new GenerarCodigo(analizadorLexico);
//     //    g.generacionDeCodigo(this.raiz);
//    }

    public void start() {

        parser.activarAmbito();
        parser.setLexico(this.analizadorLexico);
        parser.setSintactico(this);


        String estadoParser ;

        if (parser.yyparse() == 0) {
            estadoParser = ("  EJECUCION DEL PARSER FINALIZADA  \n ") ;
        }
        else
            estadoParser = ("  EL PARSER NO PUDO TERMINAR \n ");


        analizadorLexico.setPosArchivo(0);
        analizadorLexico.setBuffer("");

        this.analisisParser(analizadorLexico.getArchivo(), estadoParser);
        if (this.erroresSintacticos.isEmpty() && this.analizadorLexico.getErroresLexicos().isEmpty()){
            GenerarCodigo g = new GenerarCodigo(analizadorLexico, this);
            g.generacionDeCodigo(this.raiz,this);

            // Si se genero el assembler, agrego al archivo los errores del mismo, si los hubiera
            try{
                String filename = "ejecucion_reciente" ;
                filename = filename.replace(" ", "_") + ".txt";

                String ruta = "salida_archivo/"+ filename;
                File file = new File(ruta);
                FileWriter fw = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(fw);

                g.imprimirErroresAssembler(bw);
                String horaActual = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a").format(LocalDateTime.now());
                bw.write(" \n |||||||||||||||||||||||||||||||||||||||||||||| ") ;
                bw.write(" \n Hora de ejecucion: " + horaActual);
                bw.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Se encontraron errores LEXICOS o SINTACTICOS, por lo tanto no se pudo generar el assembler"+"\n");
        }
        System.out.println("Ejecucion detallada en el archivo ejecucion_reciente.txt en la carpeta salida_archivo");


    }


}




