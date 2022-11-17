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


public class AnalizadorSintactico {

    private AnalizadorLexico analizadorLexico;
    private Parser parser;
    private ArrayList<String> erroresSintacticos;
    private TablaSimbolos tablaSimbolos;
    private ArrayList<String> analisisSintactico;
    private String tipo;
    private Nodo raiz;

    private ArrayList<Integer> variables; // estructura que se utiliza para ir guardando en la gramatica los id's a medida que se encuentran




    public AnalizadorSintactico(AnalizadorLexico l, Parser p){
        analizadorLexico = l;
        parser = p;
        erroresSintacticos = new ArrayList<String>();
        analisisSintactico = new ArrayList<String>();
        tablaSimbolos = l.getTablaSimbolos();
        tipo = "";
        raiz = null;
        variables =  new ArrayList<Integer>();



    }

    public void addListaVariables(int refTS){
        variables.add(refTS);
    }

    public void setUso(String uso, int indice){
       tablaSimbolos.getEntrada(indice).setUso(uso);
    }

    public void setTipoEnIndex(String tipo, int indice){
        tablaSimbolos.getEntrada(indice).setTipo(tipo);
    }

    public void completarConTipos(String tipo){

       for(int idVariable : variables){
           tablaSimbolos.getEntrada(idVariable).setTipo(tipo);
       }
       vaciarListaVariables();
    }

    public void vaciarListaVariables(){
        if (!variables.isEmpty()){
            variables.clear();
        }
    }

    public void setRaiz(ParserVal raiz){
        this.raiz = (Nodo) raiz.obj;
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

    public ParserVal modificarHijo(ParserVal arbolSentencias, Nodo nuevo){
        System.out.println("");
        System.out.println("");
        System.out.println("");
        Nodo n = (Nodo) arbolSentencias.obj;
        System.out.println("ARBOL DE SENTENCIAS ANTES DE AGREGAR SENTENCIA");
        imprimirArbol(n,0);
        agregarNuevoNodo(n, nuevo);
        return new ParserVal(n);
    }

    public void agregarNuevoNodo(Nodo n, Nodo nuevo){
        if (n.getHijoDerecho() == null){
            n.setHijoDerecho(nuevo);
        } else {
            agregarNuevoNodo(n.getHijoDerecho(),nuevo);
        }
    }

    public void eliminarEntrada(int indice){this.tablaSimbolos.eliminarEntrada(indice);}
    public String getTipoFromTS(int indice) { return this.tablaSimbolos.getEntrada(indice).getTipo(); }

    public void addErrorSintactico(String nuevo) {
        erroresSintacticos.add(nuevo);
    }

    public void addAnalisis(String nuevo) {
        analisisSintactico.add(nuevo);
    }

    public void clearTipo(){
        this.tipo = null;
    }
    public void setTipoGlobal(String tipo) {
        this.tipo = tipo;
    }
    public String getTipo() { return  this.tipo;}

    public void verificarRangoEnteroLargo(int indice) {

        String lexema = this.tablaSimbolos.getEntrada(indice).getLexema();
        Long numero = Long.parseLong(lexema);

        if (numero == 2147483648L) {
            this.tablaSimbolos.eliminarEntrada(indice);    // Se elimina la entrada de la tabla de símbolos.
            this.addErrorSintactico("ERROR SINTÁCTICO (Línea " + this.analizadorLexico.LINEA + "): la constante LONG está fuera de rango.");
        }
    }
    public Nodo crearHoja(int indice){

        String lexema = this.tablaSimbolos.getEntrada(indice).getLexema();
        Nodo i = new NodoHijo(null, lexema,indice);
        i.setTipo(this.tablaSimbolos.getEntrada(indice).getTipo());
        return i;
    }

    public Nodo crearNodo(String identificador, ParserVal hijoIzq, ParserVal hijoDer){
        if (hijoDer == null){                                                   // TODO POR QUE??? EL IZQUIERD0 NO?
            Nodo i = new NodoBinario(hijoIzq.obj,null,identificador);
            return i;
        } else {
            Nodo i = new NodoBinario(hijoIzq.obj, hijoDer.obj, identificador);
            i.setTipo( tipoResultante( (Nodo)hijoIzq.obj, (Nodo)hijoDer.obj));
            return i;
        }
    }

    public String tipoResultante(Nodo izq, Nodo der){
        System.out.println("nodo izquierdo  " + izq.getLexema() + " ES  " + izq.getTipo());
        System.out.println("nodo derecho  " + der.getLexema() + "  ES  " + der.getTipo());
        if (izq.getTipo() != null && izq.getTipo() != null )
            if(izq.getTipo().equals(der.getTipo())){
                System.out.println("---------------- LOS TIPOS SON IGUALESS " + izq.getTipo());
                return izq.getTipo();
            }
        this.addAnalisis("ERROR DE TIPOS (Línea " + AnalizadorLexico.LINEA + " )" );
        return null;
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

    public void modificarLexema(int indice, String ambito){
        String nuevoLex = getEntradaTablaSimb(indice).getLexema() + ambito;
        getEntradaTablaSimb(indice).setLexema(nuevoLex);
    }
    public Atributo getEntradaTablaSimb(int indice){
        return this.tablaSimbolos.getEntrada(indice);
    }

    public void setNegativoTablaSimb(int indice) {
        String sinSigno = this.tablaSimbolos.getEntrada(indice).getLexema();
        this.tablaSimbolos.getEntrada(indice).setLexema("-" + sinSigno);
    }

    public TablaSimbolos getTS(){ return this.tablaSimbolos;}


    public void imprimirTablaSimbolos() {
        System.out.println();
        if (this.tablaSimbolos.isEmpty())
            System.out.println("|----- TABLA DE SIMBOLOS VACIA ------|");
        else tablaSimbolos.imprimir();

    }

    /**
     * Método para imprimir los errores sintácticos.
     */
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

    /**
     * Método para imprimir el análisis sintáctico.
     */
    public void imprimirAnalisisSintactico() {

        System.out.println("   🔎  ANALISIS SINTÁCTICO    ");

        if (!this.analisisSintactico.isEmpty())
            for (String dato : this.analisisSintactico)
                System.out.println(dato);
        else
            System.out.println("Análisis sintáctico vacío.");
        System.out.println();
    }


    /**
     * Método para imprimir el análisis léxico.
     */
    public void imprimirAnalisisLexico() {
        System.out.println();
        System.out.println("|                       ANÁLIZADOR LÉXICO                  |");
        ArrayList<Atributo> listaTokens = this.analizadorLexico.getListaTokens();

        for (Atributo token : listaTokens) {
            System.out.println("----------------------------------");
            System.out.println("Tipo : " + token.getTipo());
            System.out.println("Lexema: " + token.getLexema());
        }
    }



    public void start() {
        System.out.println("________________________________________________");
        parser.activarAmbito();
        parser.setLexico(this.analizadorLexico);
        parser.setSintactico(this);
        System.out.println();

        if (parser.yyparse() == 0) {

            System.out.println(" \n \n ✅ EJECUCION DEL PARSER FINALIZADA \n \n  ");
        //    imprimirAnalisisLexico();
            System.out.println("\n \n 🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧🟧 \n ");
            imprimirAnalisisSintactico();
            imprimirTablaSimbolos();
        }
        else
            System.out.println(" \n \n ❌ EL PARSER NO PUDO TERMINAR \n \n ");

       // analizadorLexico.imprimirErrores();

        this.imprimirErroresSintacticos();
        analizadorLexico.setPosArchivo(0);
        analizadorLexico.setBuffer("");

        System.out.println(" ");

        System.out.println("🌳 ARBOL 🌳 ");
        imprimirArbol(this.raiz,0);
        GenerarCodigo g = new GenerarCodigo(analizadorLexico);
        g.generacionDeCodigo();
    }


}




