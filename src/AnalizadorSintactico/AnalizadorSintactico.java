package AnalizadorSintactico;
import AnalizadorLexico.*;
import AnalizadorLexico.AccionesSemanticas.AccionesSimples.RangoEntero;

import java.util.ArrayList;


public class AnalizadorSintactico {

    private AnalizadorLexico analizadorLexico;
    private Parser parser;
    private ArrayList<String> erroresSintacticos;
    private TablaSimbolos tablaSimbolos;
    private ArrayList<String> analisisSintactico;
    private String tipo;


    public AnalizadorSintactico(AnalizadorLexico l, Parser p){
        analizadorLexico = l;
        parser = p;
        erroresSintacticos = new ArrayList<String>();
        analisisSintactico = new ArrayList<String>();
        tablaSimbolos = l.getTablaSimbolos();
        tipo = "";


    }


    public String getTipoFromTS(int indice) { return this.tablaSimbolos.getEntrada(indice).getTipo(); }

    public void addErrorSintactico(String nuevo) {
        erroresSintacticos.add(nuevo);
    }

    public void addAnalisis(String nuevo) {
        analisisSintactico.add(nuevo);
    }

    public void setTipo(String tipo) {
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

    public void setNegativoTablaSimb(int indice) {
        String sinSigno = this.tablaSimbolos.getEntrada(indice).getLexema();
        this.tablaSimbolos.getEntrada(indice).setLexema("-" + sinSigno);
    }


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
        System.out.println();
        System.out.println("|----------ERRORES SINTÁCTICOS-----------|");
        if (this.erroresSintacticos.isEmpty())
            System.out.println("Ejecución sin errores.");
        else {
            for (int i = 0; i < this.erroresSintacticos.size(); i++)
                System.out.println(this.erroresSintacticos.get(i));
        }
    }

    /**
     * Método para imprimir el análisis sintáctico.
     */
    public void imprimirAnalisisSintactico() {
        System.out.println();
        System.out.println("|----------ANALISIS SINTÁCTICO-----------|");

        if (!this.analisisSintactico.isEmpty())
            for (String dato : this.analisisSintactico)
                System.out.println(dato);
        else
            System.out.println("Análisis sintáctico vacío.");
        System.out.println();
    }

    /**
     * Método para imprimir los errores léxicos.
     */
    public void imprimirErroresLexicos() {
        this.analizadorLexico.imprimirErrores();
    }

    /**
     * Método para imprimir el análisis léxico.
     */
    public void imprimirAnalisisLexico() {
        System.out.println();
        System.out.println("|----------ANÁLIZADOR LÉXICO-----------|");
        ArrayList<Atributo> listaTokens = this.analizadorLexico.getListaTokens();

        for (Atributo token : listaTokens) {
            System.out.println("----------------");
            System.out.println("Tipo token: " + token.getTipo());
            System.out.println("Lexema: " + token.getLexema());
        }
    }



    public void start() {
        System.out.println("________________________________________________");
        parser.setLexico(this.analizadorLexico);
        parser.setSintactico(this);
        System.out.println();

        if (parser.yyparse() == 0) {
            System.out.println("Ejecución del Parser finalizada.");
            imprimirAnalisisLexico();
            imprimirAnalisisSintactico();
            imprimirTablaSimbolos();
        }
        else
            System.out.println("Ejecución del Parser no finalizada.");

        analizadorLexico.imprimirErrores();
        this.imprimirErroresSintacticos();
        analizadorLexico.setPosArchivo(0);
        analizadorLexico.setBuffer("");
        System.out.println("________________________________________________");
    }

}




