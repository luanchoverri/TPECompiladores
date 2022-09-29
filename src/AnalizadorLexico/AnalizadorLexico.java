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
    private ArrayList <Atributo> tokensReconocidos; // Va procesando el codigo y acá guarda los tokens reconocidos
    private ArrayList <String> errores; // ArrayList para almacenar los errores encontrados junto con el numero de linea (Implementar?) !!!!!!!!!!!!!!
    
    // -- MATRICES -- // 
    private MatrizAccionesSemanticas matrizAccionesSemanticas;  // Matriz de acciones semánticas.
    private MatrizEstados matrizEstados;
    
    // -- Constructor --
    
    public AnalizadorLexico(String archivoEntrada){
        this.archivoEntrada = archivoEntrada;
        this.matrizAccionesSemanticas = new MatrizAccionesSemanticas();
        this.matrizEstados = new MatrizEstados();


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