 package AnalizadorLexico;
 
 import java.util.ArrayList;
 
 import AnalizadorLexico.AccionesSemanticas.AccionSemantica;
 import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
 import AnalizadorLexico.AccionesSemanticas.MatrizAccionesSemanticas;
 import AnalizadorLexico.AccionesSemanticas.AccionesSimples.AgregarChar;
 import AnalizadorLexico.AccionesSemanticas.AccionesSimples.ChequearPalabraReservada;
 import AnalizadorLexico.AccionesSemanticas.AccionesSimples.InicializarBuffer;
 import AnalizadorLexico.AccionesSemanticas.AccionesSimples.ObtenerIdToken;
 import AnalizadorLexico.AccionesSemanticas.AccionesSimples.RangoEntero;
 import AnalizadorLexico.AccionesSemanticas.AccionesSimples.RangoFlotante;
 
 public class AnalizadorLexico {
    
    // -- CONSTANTES -- // 
    
    public static final int ESTADOS = 18;       // Cantidad de estados del autómata.
    public static final int SIMBOLOS = 23;      // Cantidad de símbolos aceptados por el compilador.
    
    // -- VARIABLES -- // 
    
    private String buffer; // va guardando lo que va pasando por el automata
    private Integer idToken; // id token obtenido del buffer
    private String archivoEntrada; // Variable para la lectura del archivo txt
    private TablaSimbolos tablaSimbolos;
    private ArrayList <String> errores; // ArrayList para almacenar los errores encontrados junto con el numero de linea (Implementar?) !!!!!!!!!!!!!!
    
    // -- MATRICES -- // 
    private MatrizAccionesSemanticas matrizAccionesSemanticas;  // Matriz de acciones semánticas.
    
    
    // -- Constructor --
    
    public AnalizadorLexico(String archivoEntrada){
        this.archivoEntrada = archivoEntrada;
        this.matrizAccionesSemanticas = new MatrizAccionesSemanticas(ESTADOS, SIMBOLOS);
        
        // -- Acciones semanticas SIMPLES --
        
        // AS1 -> Inicializa el buffer
        AccionSemanticaSimple AS1 = new InicializarBuffer(this);
        
        // AS7 -> Agrega char leido al String (Buffer)
        AccionSemanticaSimple AS7 = new AgregarChar(this);
        
        // AS3 -> Devuelve el ID del Token de los simbolos (operadores aritmeticos, comparadores, etc)
        AccionSemanticaSimple AS3 = new ObtenerIdToken(this, this.getTablaSimbolos());
        
        // AS8 -> Controla si es palabra reservada
        AccionSemanticaSimple AS8 = new ChequearPalabraReservada(this, this.getTablaSimbolos());    
        
        // AS10 -> Controla el rango de los enteros. Si esta en rango, lo agrega a la tabla de simbolos, sino, devuelve error (catch)
        AccionSemanticaSimple AS10 = new RangoEntero(this, this.getTablaSimbolos());
        
        // AS11 -> Controla el rango de los flotantes. Si esta en rango, lo agrega a la tabla de simbolos, sino, devuelve error (catch)
        AccionSemanticaSimple AS11 = new RangoFlotante(this, this.getTablaSimbolos());
        
        // -- Acciones semanticas COMPUESTAS -- 
        
        
        // ----- MATRIZ DE ACCIONES SEMÁNTICAS ------ //
        
        AccionSemantica [][] matrizSemantica = {
            /*            L       d     .     _     S     <     >     =     &     |     +     -     *     /     %    \n     ;     :     ,     (     )   otro  Bl,Tab  $                  
            /*0*/        {null ,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null, null,  null,  null,  null,  null,  null,  null, null,  null,  null },
            /*1*/        {null ,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null },
            /*2*/        {null,  null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,  null},
            /*3*/        {null ,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null },
            /*4*/        {null,  null, null, null, null, null, null, null, null, null,  null,  null, null, null, null, null, null, null, null, null, null, null, null,  null},
            /*5*/        {null ,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null },
            /*6*/        {null,  null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,  null},
            /*7*/        {null ,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null },
            /*8*/        {null ,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null },
            /*9*/        {null, null, null, null, null, null, null, null,  null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,  null},
            /*10*/       {null, null, null, null, null, null, null, null, null,  null, null, null, null, null, null, null, null, null, null, null, null, null, null,  null},
            /*11*/       {null ,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null },
            /*12*/       {null ,  null,  null, null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null },
            /*13*/       {null ,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null },
            /*14*/       {null ,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null, null, null,  null,  null,  null,  null,  null,  null,  null,  null },
            /*15*/       {null ,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null, null, null,  null,  null,  null,  null,  null,  null,  null,  null },
            /*16*/       {null, null, null, null, null, null, null, null, null, null,  null, null, null, null, null, null, null, null, null, null, null, null,  null,  null},
            /*17*/       {null ,  null, null,  null,  null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,  null},
            /*18*/       {null, null, null, null, null, null, null,  null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,  null},   
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
        return null;
    }
    
    public void imprimirErrores() {
    }
    
    public void imprimirTablaSimbolos() {
    }
    
    
}