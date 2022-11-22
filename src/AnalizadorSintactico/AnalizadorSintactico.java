package AnalizadorSintactico;
import AnalizadorLexico.*;
import ArbolSintactico.GenerarCodigo;
import ArbolSintactico.Nodo;
import ArbolSintactico.NodoBinario;
import ArbolSintactico.NodoHijo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    public AnalizadorSintactico(AnalizadorLexico l, Parser p){
        analizadorLexico = l;
        parser = p;
        erroresSintacticos = new ArrayList<String>();
        analisisSintactico = new ArrayList<String>();
        tablaSimbolos = l.getTablaSimbolos();
        tipo = "";
        raiz = null;
        variables =  new ArrayList<Integer>();
        arbolesFunciones = new HashMap<>();

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

            if (n.getHijoIzquierdo()!= null && n.getHijoIzquierdo().getLexema().equals("lista_ctes")) {

                n = n.getHijoIzquierdo().getHijoIzquierdo();
                agregarNuevoNodo(n, nuevo);
                return new ParserVal(n);
            }

            n = nuevo;
            return new ParserVal(n);
        }

        if (nuevo.getLexema().equals("declarativa")) {
            if (nuevo.getHijoIzquierdo() != null && nuevo.getHijoIzquierdo().getLexema().equals("lista_ctes")) {
                nuevo = nuevo.getHijoIzquierdo().getHijoIzquierdo();
            } else {
                return new ParserVal(n); // las declarativas que no tienen que ver con lista-ctes no se agregan al arbol.
            }

        }

        agregarNuevoNodo(n, nuevo);
        return new ParserVal(n);
    }

    public String tipoResultante(Nodo izq, Nodo der){
       // System.out.println("nodo izq " + izq.toString());
       // System.out.println("nodo der " + der.toString());
        if (izq.getTipo() != null && izq.getTipo() != null )
            if(izq.getTipo().equals(der.getTipo())){
                System.out.println("---------------- LOS TIPOS SON IGUALESS " + izq.getTipo());
                return izq.getTipo();
            }
        this.addAnalisis("ERROR DE TIPOS (Línea " + AnalizadorLexico.LINEA + " )" );
        return null;
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
            System.out.println("EL NODO IZQ ES " + hijoIzq.toString());
            System.out.println("EL NODO DER ES " + hijoDer.toString()); // TODO POR QUE??? EL IZQUIERD0 NO?
            Nodo i = new NodoBinario(hijoIzq.obj, hijoDer.obj, identificador);
            i.setTipo( tipoResultante( (Nodo)hijoIzq.obj, (Nodo)hijoDer.obj));
            System.out.println("EL NODO RESULTANTE ES " + i.toString());
            return i;
        }
    }

    public Nodo crearNodoControl(String identificador, ParserVal hijo){
        if (hijo == null){
            Nodo i = new NodoHijo(null, identificador);
            return i;
        }
        Nodo i = new NodoHijo(hijo.obj, identificador);
        return i;
    }

    public Nodo crearNodoFunc(int indice, ParserVal hijo){
        String lexema = this.tablaSimbolos.getEntrada(indice).getLexema();
        if (hijo == null) {
            Nodo i = new NodoHijo(null, lexema, indice);
            return i;
        } else {
            Nodo i = new NodoHijo(hijo.obj, lexema, indice);
            return i;
        }
    }

    public void agregarNuevoNodo(Nodo n, Nodo nuevo){
        if (n.getHijoDerecho() == null){
            n.setHijoDerecho(nuevo);
        } else {
            agregarNuevoNodo(n.getHijoDerecho(),nuevo);
        }
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

    public void imprimirTablaSimbolos() {
        System.out.println();
        if (this.tablaSimbolos.isEmpty())
            System.out.println("|----- TABLA DE SIMBOLOS VACIA ------|");
        else tablaSimbolos.imprimir();

    }

    public void imprimirErroresSintacticos() {

        Path path = Paths.get("salida_archivo/ejecucion_reciente.txt");
        String contenido = "";
        try {
            if (this.erroresSintacticos.isEmpty()){
                contenido = "\n" + "\n" + "\n" + "\n" +  "|--- Sin errores SINTACTICOS ---| (OK)";
                Files.write(path, contenido.getBytes(), StandardOpenOption.APPEND);
            }else{
                if (!(this.erroresSintacticos.isEmpty())){ // Si hay errores sintacticos
                    contenido = "\n" + "\n" + "\n" + "| ERRORES SINTACTICOS: ---| (!)" + "\n" + "\n" + "\n";
                    Files.write(path, contenido.getBytes(), StandardOpenOption.APPEND);
                    for (int i = 0; i < this.erroresSintacticos.size(); i++){
                        contenido = this.erroresSintacticos.get(i);
                        Files.write(path, contenido.getBytes(), StandardOpenOption.APPEND);
                        Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imprimirAnalisisSintactico() {

        System.out.println("   🔎  ANALISIS SINTÁCTICO    ");

        if (!this.analisisSintactico.isEmpty())
            for (String dato : this.analisisSintactico)
                System.out.println(dato);
        else
            System.out.println("Análisis sintáctico vacío.");
        System.out.println();
    }

    public void imprimirAnalisisLexico() {
        System.out.println();
        System.out.println("|                       ANÁLIZADOR LÉXICO                  |");
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



    // -- Otros
    public void verificarRangoEnteroLargo(int indice) {

        String lexema = this.tablaSimbolos.getEntrada(indice).getLexema();
        Long numero = Long.parseLong(lexema);

        if(numero == 2147483648L) {
            this.tablaSimbolos.eliminarEntrada(indice);    // Se elimina la entrada de la tabla de símbolos.
            this.addErrorSintactico("SyntaxError FUERA DE RANGO (Línea " + this.analizadorLexico.LINEA + "): CONST LONG");
        }
    }





    // -- Analizador Sintactico START

    public void start() {
        System.out.println("________________________________________________");
        parser.activarAmbito();
        parser.setLexico(this.analizadorLexico);
        parser.setSintactico(this);
        System.out.println();

        if (parser.yyparse() == 0) {

            System.out.println(" \n \n ✅ EJECUCION DEL PARSER FINALIZADA \n \n  ");
            imprimirAnalisisLexico();
            System.out.println("\n \n 🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧 \n ");
            imprimirAnalisisSintactico();
            imprimirTablaSimbolos();
        }
        else
            System.out.println(" \n \n ❌ EL PARSER NO PUDO TERMINAR \n \n ");

        analizadorLexico.imprimirErrores();

        this.imprimirErroresSintacticos();
        analizadorLexico.setPosArchivo(0);
        analizadorLexico.setBuffer("");

        System.out.println(" ");

        System.out.println("🌳 ARBOL 🌳 ");
        imprimirArbol(this.raiz,0);
        imprimirArbolesFuncion();
        GenerarCodigo g = new GenerarCodigo(analizadorLexico);
        g.generacionDeCodigo(this.raiz);
    }


}




