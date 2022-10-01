 package AnalizadorLexico;
 
 import java.awt.*;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;

 import AnalizadorLexico.AccionesSemanticas.AccionSemantica;
 import AnalizadorLexico.AccionesSemanticas.AccionSemanticaCompuesta;
 import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
 import AnalizadorLexico.AccionesSemanticas.AccionesSimples.*;
 import AnalizadorLexico.AccionesSemanticas.MatrizAccionesSemanticas;

 public class AnalizadorLexico {
    
    // -- CONSTANTES -- //

    public static final int ESTADOS = 18;       // Cantidad de estados del autómata.
    public static final int SIMBOLOS = 23;      // Cantidad de símbolos aceptados por el compilador.
    
    // -- VARIABLES -- // 
    
    private String buffer; // va guardando lo que va pasando por el automata
    private Integer idToken; // id token obtenido del buffer
    private String archivoEntrada; // Variable para la lectura del archivo txt
    private TablaSimbolos tablaSimbolos;
    private ArrayList <Atributo> tokensReconocidos; // Va procesando el codigo y acá guarda los tokens reconocidos
    private ArrayList <String> errores; // ArrayList para almacenar los errores encontrados junto con el numero de linea (Implementar?) !!!!!!!!!!!!!!
    
    // -- MATRICES -- // 
    private MatrizAccionesSemanticas matrizAccionesSemanticas;  // Matriz de acciones semánticas.
    private MatrizEstados matrizEstados;
    
    // -- Constructor --
    
    public AnalizadorLexico(String archivoEntrada){
        this.archivoEntrada = archivoEntrada;
        this.matrizAccionesSemanticas = new MatrizAccionesSemanticas(ESTADOS, SIMBOLOS);
        this.matrizEstados = new MatrizEstados();

        // -- Acciones semanticas SIMPLES --

        // AS1 -> Inicializa el buffer
        AccionSemantica AS1 = new InicializarBuffer(this);

        // AS2 -> Agrega char leido al String (Buffer)
        AccionSemantica AS2 = new AgregarChar(this);

        // AS3 -> Devuelve el ID del Token de los simbolos (operadores aritmeticos, comparadores, etc)
        AccionSemantica AS3 = new ObtenerIdToken(this, this.getTablaSimbolos());

        // AS5 -> "Devuelve" al archivo el ultimo char leido
        AccionSemantica AS5 = new DevolverUltimoLeido(this); //TODO implementar

        // AS6 -> Controla si es palabra reservada
        AccionSemantica AS6 = new ChequearPalabraReservada(this, this.getTablaSimbolos());

        // AS7 -> Controla el rango de los enteros. Si esta en rango, lo agrega a la tabla de simbolos, sino, devuelve error (catch)
        AccionSemantica AS7 = new RangoEntero(this, this.getTablaSimbolos());

        // AS8 -> Controla el rango de los flotantes. Si esta en rango, lo agrega a la tabla de simbolos, sino, devuelve error (catch)
        AccionSemantica AS8 = new RangoFlotante(this, this.getTablaSimbolos());

        // AS9 -> Elimina el ultimo caracter del buffer
        AccionSemantica AS9 = new EliminarCaracterBuffer(this);

        // AS17 -> Obtiene el id token de Cadena y registra el lexema en la tabla de simbolos
        AccionSemantica AS17 = new AgregarCadena(this, this.getTablaSimbolos());


        // -- Acciones semanticas COMPUESTAS --

        // AS10 -> Inicializa el buffer y agrega el string leido al buffer
        AccionSemantica AS10 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS1,AS2)), this);

        // AS11 -> devulve al archivo y chequea rango flotante
        AccionSemantica AS11 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS5,AS8)), this);

        // AS12 -> devulve al archivo y chequea rango entero
        AccionSemantica AS12 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS5,AS7)), this);

        // AS13 -> Devuelve al archivo y obtiene el id
        AccionSemantica AS13 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS5,AS3)), this);

        // AS14 -> Agrega al buffer y obtiene el id
        AccionSemantica AS14 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS2,AS3)), this);

        //AS15 -> Inicializa el buffer, agrega caracter y obtiene el id
        AccionSemantica AS15 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS10,AS3)), this);

        // AS16 -> Devuelve al archivo y chequea palabra reservada/identificador
        AccionSemantica AS16 = new AccionSemanticaCompuesta(new ArrayList<>(List.of(AS5,AS6)), this);


        AccionSemantica [][] matrizSemantica = {
                /*            L       d     .     _     F     <     >     =     +     -     *     /     '    \n     ;     :     ,     (     )     {     }   otro    !  Bl,Tab  $
                /*0*/        {AS10, AS10, AS10, null, AS10, AS10, AS10, null, AS15, AS15, AS15, AS15, AS1 , null, AS15, null, AS15, AS15, AS15, AS15, AS15, null, null, null, null },
                /*1*/        {null, AS2 , null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null },
                /*2*/        {AS12, AS2 , AS2 , AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, AS12, null},
                /*3*/        {AS11, AS2 , AS11, AS11, AS2 , AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, null },
                /*4*/        {null, AS2 , null, null, null, null, null, null, AS2 , AS2 , null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                /*5*/        {null, AS2 , null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null },
                /*6*/        {AS11, AS2 , AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, AS11, null},
                /*7*/        {AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS14, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, null },
                /*8*/        {AS13, AS13, AS13, AS13, AS13, null, AS13, AS14, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, null },
                /*9*/        {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                /*10*/       {AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS13, AS14, AS13, AS13, AS13, AS13, AS13, AS13, AS14, AS13, null},
                /*11*/       {AS2 , AS2 , AS16, AS2 , AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, AS16, null },
                /*12*/       {AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS17, null, AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , null },
                /*13*/       {AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , null, AS9 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , null },
                /*14*/       {AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS17, null, AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , AS2 , null },

        };
        this.matrizAccionesSemanticas.setMatrizAccionSemantica(matrizSemantica);
    }




    
    
    // -- Metodos --
    public String getBuffer() {
        return buffer;
    }
    
    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }
    
    public Integer getIdToken() {
        return idToken;
    }
    
    public void setIdToken(Integer idToken) {
        this.idToken = idToken;
        
    }
    
    public TablaSimbolos getTablaSimbolos(){
        return this.tablaSimbolos;
    }
    
    public boolean isCodigoLeido() {
        return false;
    }
    
    public void procesarYylex() {
    }
    
    public ArrayList<Atributo> getListaTokens() {
        return this.tokensReconocidos;
    }
    
    public void imprimirErrores() {
    }
    
    public void imprimirTablaSimbolos() {
    }
    
    
}