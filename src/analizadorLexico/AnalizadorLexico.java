package analizadorLexico;

import java.util.HashMap;
import java.util.Vector;

public class AnalizadorLexico {

    // CONSTANTES
    public static int LINEA = 1;                // Referencia a la línea de código.
    public static final int ESTADOS = 18;       // Cantidad de estados del autómata.
    public static final int SIMBOLOS = 23;      // Cantidad de símbolos aceptados por el compilador.

    public static final int NO_ENCONTRADO = -1; // Indicador de falta de símbolo en la tabla que los almacena.

    // ATRIBUTOS
    private String archivo;         // Código del archivo.
    private String buffer;          // Buffer para leer el token actual.
    private int posArchivo;         // Índice que indica la posición del archivo que se está leyendo.
    private int estadoActual;       // Estado actual del autómata.
    private int tokenActual;        // ID del token que se está procesando actualmente.
    private int refTablaSimbolos;   // Número que indica el índice del token en la tabla de símbolos.
    private boolean codigoLeido;    // Variable que verifica si se terminó de leer el código.

    private TablaSimbolos tablaSimbolos;
    private Vector<Token> listaTokens; // lista de tokens resultantes
    private Vector<String> listaErrores;  // Lista de errores léxicos.
    //Matrices
    private MatrizEstados matrizEstados;
    private MatrizAccionesSemanticas matrizAccionesSemanticas;


    //Contructor
    public AnalizadorLexico(String archivo) {
        this.archivo = archivo + '$';
        this.buffer = "";
        this.posArchivo = 0;
        this.estadoActual = 0;
        this.tokenActual = -1;
        this.refTablaSimbolos = -1;
        this.codigoLeido = false;
        this.tablaSimbolos = new TablaSimbolos();
        this.listaTokens = new Vector<Token>();
        this.listaErrores = new Vector<>();
        this.matrizEstados = new MatrizEstados();
        this.matrizAccionesSemanticas = new MatrizAccionesSemanticas(ESTADOS, SIMBOLOS);
    }

    public void addErrorLexico(String error) {
        this.listaErrores.add(error);
    }

    public void addToken(Token nuevo) {
        this.listaTokens.add(nuevo);
    }

    public boolean esPalabraReservada(String palabra){
        return this.tablaSimbolos.esPalabraReservada(palabra);
    }

    public boolean esIdentificador(String token) {

        if (!Character.isLetter(token.charAt(0))) // que el primer caracter sea una letra
            return false;

        // chequea que adentro solo tenga letras digitos y _
        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);
            if (!(c == '_' || Character.isDigit(c) || (Character.isLetter(c))))
                return false;
        }

        return true;
    }

    public int procesarYylex() {
        this.tokenActual = -1;
        this.estadoActual = 0;
        this.buffer = "";
        this.refTablaSimbolos = -1;
        char caracter;
        int colCaracter = -1;
        AccionSemantica accion;

        while (this.posArchivo < this.archivo.length() && this.tokenActual != 0 && this.tokenActual == -1) {
            caracter = this.archivo.charAt(this.posArchivo);

            colCaracter = matrizEstados.getColCaracter(caracter)
            accion = this.matrizAccionesSemanticas.get(this.estadoActual, colCaracter);

            if (accion != null)
                accion.ejecutar(this.buffer, caracter);

            if (caracter == '\n' && this.estadoActual != 1 && this.estadoActual != 3 && this.estadoActual != 5 && this.estadoActual != 17)
                this.LINEA++;

            if (caracter == '$') {
                if (esFinDeArchivo())
                    break;
                else {
                    // Si no cerró la cadena y venía el EOF
                    if (this.posArchivo == this.archivo.length())
                        if (this.estadoActual == 14) {
                            this.addErrorLexico("ERROR LÉXICO (Línea " + this.LINEA + "): cadena o comentario mal cerrados");
                            this.tokenActual = 0;
                            this.posArchivo = 0;
                            this.LINEA = 1;
                            this.codigoLeido = true;
                            break;
                        }
                }
            }

            this.estadoActual = this.matrizEstados.getEstado(estadoActual, colCaracter);

            if (this.estadoActual == -1)
                this.sincronizarAnalisis(caracter);
        }

        if (this.tokenActual != 0 && this.tokenActual != -1) {
            String tipo = getTipoToken(this.tokenActual);
            Token nuevo = new Token(this.tokenActual, this.buffer, this.LINEA, tipo);
            this.addToken(nuevo);
        }

        if (this.posArchivo == this.archivo.length())
            this.codigoLeido = true;

        return this.tokenActual;
    }
















}
