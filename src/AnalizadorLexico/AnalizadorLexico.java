package AnalizadorLexico;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import AnalizadorLexico.AccionesSemanticas.AccionSemantica;
import AnalizadorLexico.AccionesSemanticas.AccionSemanticaCompuesta;
import AnalizadorLexico.AccionesSemanticas.AccionesSimples.*;
import AnalizadorLexico.AccionesSemanticas.MatrizAccionesSemanticas;

public class AnalizadorLexico {

    // -- CONSTANTES -- //

    public static final int ESTADOS = 18;       // Cantidad de estados del autómata.
    public static final int SIMBOLOS = 23;      // Cantidad de símbolos aceptados por el compilador.
    public static int LINEA = 1;

    // -- VARIABLES -- //

    private String archivo;
    private String buffer; // va guardando lo que va pasando por el automata
    private int tokenActual;
    private int estadoActual;


    private int refTablaSimbolos;
    private int posArchivo;
    private boolean codigoLeido;
    private TablaSimbolos tablaSimbolos;
    private ArrayList<Token> tokensReconocidos; // Va procesando el codigo y acá guarda los tokens reconocidos
    private ArrayList<String> errores; // ArrayList para almacenar los errores encontrados junto con el numero de linea (Implementar?) !!!!!!!!!!!!!!

    // -- MATRICES -- //
    private final MatrizAccionesSemanticas matrizAccionesSemanticas;  // Matriz de acciones semánticas.
    private final MatrizEstados matrizEstados;

    // -- Constructor --

    public AnalizadorLexico(String archivo) {
        this.archivo = archivo + '$';
        this.matrizAccionesSemanticas = new MatrizAccionesSemanticas(ESTADOS, SIMBOLOS);
        this.matrizEstados = new MatrizEstados();
        this.tablaSimbolos = new TablaSimbolos();
        this.errores = new ArrayList<String>();
        this.tokensReconocidos = new ArrayList<Token>();
        this.refTablaSimbolos = -1;
        this.codigoLeido = false;


        // -- Acciones semanticas SIMPLES --

        // AS1 -> Inicializa el buffer
        AccionSemantica AS1 = new InicializarBuffer(this);

        // AS2 -> Agrega char leido al String (Buffer)
        AccionSemantica AS2 = new AgregarChar(this);

        // AS3 -> Devuelve el ID del Token de los simbolos (operadores aritmeticos, comparadores, etc)
        AccionSemantica AS3 = new ObtenerIdToken(this);

        // AS5 -> "Devuelve" al archivo el ultimo char leido
        AccionSemantica AS5 = new DevolverUltimoLeido(this); //TODO implementar

        // AS6 -> Controla si es palabra reservada
        AccionSemantica AS6 = new ChequearPalabraReservada(this);

        // AS7 -> Controla el rango de los enteros. Si esta en rango, lo agrega a la tabla de simbolos, sino, devuelve error (catch)
        AccionSemantica AS7 = new RangoEntero(this);

        // AS8 -> Controla el rango de los flotantes. Si esta en rango, lo agrega a la tabla de simbolos, sino, devuelve error (catch)
        AccionSemantica AS8 = new RangoFlotante(this);

        // AS9 -> Elimina el ultimo caracter del buffer
        AccionSemantica AS9 = new EliminarCaracterBuffer(this);

        // AS17 -> Obtiene el id token de Cadena y registra el lexema en la tabla de simbolos
        AccionSemantica AS17 = new AgregarCadena(this);


        // -- Acciones semanticas COMPUESTAS --

        // AS10 -> Inicializa el buffer y agrega el string leido al buffer
        AccionSemantica AS10 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS1, AS2)), this);

        // AS11 -> devulve al archivo y chequea rango flotante
        AccionSemantica AS11 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS5, AS8)), this);

        // AS12 -> devulve al archivo y chequea rango entero
        AccionSemantica AS12 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS5, AS7)), this);

        // AS13 -> Devuelve al archivo y obtiene el id
        AccionSemantica AS13 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS5, AS3)), this);

        // AS14 -> Agrega al buffer y obtiene el id
        AccionSemantica AS14 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS2, AS3)), this);

        //AS15 -> Inicializa el buffer, agrega caracter y obtiene el id
        AccionSemantica AS15 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS10, AS3)), this);

        // AS16 -> Devuelve al archivo y chequea palabra reservada/identificador
        AccionSemantica AS16 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS5, AS6)), this);



        AccionSemantica[][] matrizSemantica = { // Revisar los fin de archivo ($) en cada estado del automata por si se debe invocar una accion semantica
                /*            L       d     .     _     F     <     >     =     +     -     *     /     '    \n     ;     :     ,     (     )     {     }   otro   !   Bl,Tab  $
                /*0*/        {AS10, AS10, AS10, AS10, AS10, AS10, AS10, AS10, AS15, AS15, AS15, AS15,  AS1, null, AS15, AS15, AS15, AS15, AS15, AS15, AS15, null, null, null, null},
                /*1*/        {null,  AS2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                /*2*/        {AS12,  AS2,  AS2, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, null},
                /*3*/        {AS11,  AS2, AS11, AS11,  AS2, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, null},
                /*4*/        {null,  AS2, null, null, null, null, null, null,  AS2,  AS2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                /*5*/        {null,  AS2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                /*6*/        {AS11,  AS2, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, null},
                /*7*/        {AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS14, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, null},
                /*8*/        {AS13, AS13, AS13, AS13, AS13, AS1, AS13, AS14, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, null},
                /*9*/        {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                /*10*/       {AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS14, AS13, AS13, AS13, AS13, AS13, AS13, AS14, AS13, null},
                /*11*/       { AS2,  AS2, AS16,  AS2, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16},
                /*12*/       { AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2, AS17, null,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2, null},
                /*13*/       { AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2, null,  AS9,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2, null},
                /*14*/       { AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2, AS17, null,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2,  AS2, null},

        };
        this.matrizAccionesSemanticas.setMatrizAccionSemantica(matrizSemantica);
    }

    public ArrayList<String> getErroresLexicos(){
        return errores;
    }

    // -- Metodos --
    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public void setPosArchivo(int pos) {
        posArchivo = pos;
    }


    public int getIdToken(String lexema) {
        return tablaSimbolos.getIdToken(lexema);
    }

    public String getArchivo() {
        return archivo;
    }

    public void setTokenActual(int idToken) {
        this.tokenActual = idToken;

    }
    public int getRefTablaSimbolos() {
        return refTablaSimbolos;
    }

    public void setRefTablaSimbolos(int refTablaSimbolos) {
        this.refTablaSimbolos = refTablaSimbolos;
    }

    public TablaSimbolos getTablaSimbolos() {
        return this.tablaSimbolos;
    }

    public boolean isCodigoLeido() {
        return this.codigoLeido;
    }

    public void agregarRegistro(String lexema, int idToken) {
        this.tablaSimbolos.agregarRegistro(lexema, idToken, this.LINEA);
        this.refTablaSimbolos = this.tablaSimbolos.size() - 1;
    }

    public int procesarYylex() {

        this.tokenActual = -1;
        this.estadoActual = 0;
        this.buffer = "";
        this.refTablaSimbolos = -1;
        char caracter;
        int columnaCaracter = -1;
        AccionSemantica accion;


        //Se itera mientras estemos en una posicion valida del archivo y no hayamos consigo un token con valor valido
        // token = -1 quiere decir que no hay token reconocido
        // token = 0 significa que no hay token sino que se está consumiendo caracteres de por ejemplo una comentario o una cadena mal cerrada
        while (this.posArchivo < this.archivo.length() && this.tokenActual != 0 && this.tokenActual == -1) {
            caracter = this.archivo.charAt(this.posArchivo); // consigue el caracter
            columnaCaracter = matrizEstados.getColCaracter(caracter); // consigue a que columna corresponde va con ese caracter

            // trae la accion semantica en (estadoActual, nroColumna del simbolo) al inicio es con estado actual en cero
            accion = this.matrizAccionesSemanticas.get(this.estadoActual, columnaCaracter);

            // si hay una accion semantica valida entonces la ejecuta
            if (accion != null)
                accion.ejecutar(this.buffer, caracter);

            this.posArchivo += 1; // avanzamos a la posicion del siguiente caracter

            // SI EL CARACTER ES FIN DE ARCHIVO
            if (caracter == '$') {
                if (esFinDeArchivo()) // actualiza las variables y corta
                    break;
                else {
                    // Si no cerró la cadena y venía el EoF
                    if (this.posArchivo == this.archivo.length())
                        if (this.estadoActual == 12) {
                            this.addErrorLexico("ERROR LÉXICO (Línea " + this.LINEA + "): cadena o comentario mal cerrados");
                            this.tokenActual = 0;
                            this.posArchivo = 0;
                            this.LINEA = 1;
                            this.codigoLeido = true;
                            break;
                        }
                }
            }

            // SI EL CARACTER ES UN SALTO DE LINEA
            if (caracter == '\n' && estadoActual!=2 && estadoActual!=3 && estadoActual!=6 && estadoActual!=11)
                LINEA++;

            // CONSIGO EL SIGUIENTE ESTADO
            int estadoSiguiente = this.matrizEstados.get(this.estadoActual, columnaCaracter);
            estadoActual = estadoSiguiente;


            // SI EL SIGUIENTE ESTADO ES INVALIDO
            if (this.estadoActual == -1)
                this.sincronizarAnalisis(caracter); // TRATO EL ERROR



        } // VUELVE AL WHILE


        //SALE DEL WHILE Y PREGUNTA SI HAY UN TOKEN RECONOCIDO ( != -1) Y QUE NO SEA DE LEER UNA CADENA O COMENTARIO (!= 0)
        if (this.tokenActual != 0 && this.tokenActual != -1) {
            String tipo = tablaSimbolos.getTipoToken(this.tokenActual);
            Token token = new Token(this.tokenActual, this.buffer, this.LINEA, tipo);



            if (tipo == "IDENTIFICADOR" || tipo == "PALABRA RESERVADA" || tipo == "CONSTANTE"){
                posArchivo--;
            }
            this.tokensReconocidos.add(token);
        }

        //ACA ESTAMOS EN EL FINAL DEL ARCHIVO ASIQUE CODIGO LEIDO = TRUE PARA CORTAR EL WHILE DESDE EL CONSUMIDOR DE TOKENS
        if (this.posArchivo == this.archivo.length())
            this.codigoLeido = true;


        return this.tokenActual; // retorno del id del token para el analizador sintactico
    }

    private void sincronizarAnalisis(char caracter) {
        String aux = this.buffer;  // Variable para imprimir el error
        this.buffer = "";
        while (this.archivo.charAt(this.posArchivo) != ';' && this.archivo.charAt(this.posArchivo) != '\n'
                                                            && this.archivo.charAt(this.posArchivo) != 9 // DISTINTO DE TABULACION
                                                            && this.archivo.charAt(this.posArchivo) != 32 // DISTINTO DE UN ESTACIO EN BLANCO (REVISAR)
                                                            && this.archivo.charAt(this.posArchivo) != '$' // DISTINTO DE FIN
                                                            && this.posArchivo < this.archivo.length()) {
            aux += this.archivo.charAt(this.posArchivo);
            this.posArchivo++;
        }
        this.estadoActual = 0;
    //    this.addErrorLexico("ERROR LÉXICO (Línea " + this.LINEA + "): \'" + aux + "\' es un token inválido, no reconocido por la matriz de transición de estados.");
    }

    public void addErrorLexico(String error) {
        this.errores.add(error);
    }
    public boolean esFinDeArchivo() {
        if (this.estadoActual == 0) { // si estoy en un estado final
            if (this.posArchivo == (this.archivo.length() - 1)) { // y si llegue al final del archivo entonces actualizo
                this.tokenActual = 0;
                this.posArchivo = 0;
                this.LINEA = 1;
                this.codigoLeido = true;
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    public ArrayList<Token> getListaTokens() {
        return this.tokensReconocidos;
    }


    public void imprimirTablaSimbolos() {
        System.out.println();
        System.out.println("|--- TABLA SIMBOLOS: ---|");
        if (this.tokensReconocidos.isEmpty())
            System.out.println("no hay tokens");
        else {
            for(Token t : tokensReconocidos){
                System.out.println(t.toString());
            }
        }
    }

    public boolean isPalabraReservada(String buffer) {
       return this.tablaSimbolos.isPalabraReservada(buffer);
    }

    public void imprimirErrores(BufferedWriter bw) throws IOException {

            String contenido;

            contenido = "|--- CODIGO FUENTE ---|" + "\n" + "\n" + "\n";
            bw.write(contenido);
            bw.write("\n" + "\n"+ "-----------------------------------------------------------------" + "\n");

            if (this.errores.isEmpty()){
                contenido = "\n" + "\n" + "\n" + "\n" +  "|--- Sin errores LEXICOS ---| (OK)";
                bw.write(contenido);
            }
            else{
                if (!(this.errores.isEmpty())){ // Si hay errores lexicos
                    contenido = "\n" + "\n" + "\n" + "|--- ERRORES LÉXICOS: ---| (!)" + "\n" + "\n" + "\n";
                    bw.write(contenido);
                    for (int i = 0; i < this.errores.size(); i++){
                        contenido = this.errores.get(i);
                        bw.write(contenido);
                    }
                }
            }

    }



}