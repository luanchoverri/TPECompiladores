package AnalizadorLexico;

public class Atributo {

    // Posee los tokens (id del token, etc)

    private int idToken; // Id del token (Numero de identificacion)
    private String lexema; // Significado del Token
    private int nroLinea;
    private String tipo;
    

    
    // -- Constructor --

    public Atributo(String lexema, int idToken) {
        this.lexema = lexema;
        this.idToken = idToken;
    }
    public Atributo(int idToken, String lexema, int nroLinea, String tipo){
        this.idToken = idToken;
        this.lexema = lexema;
        this.nroLinea = nroLinea;
        this.tipo = tipo;
    }

    // -- MÉTODOS -- // 

    // Getters 

    public int getIdToken() {
        return this.idToken;
    }

    public String getLexema() {
        return this.lexema;
    }

    // Setters

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }    

    public void setIdToken(int idToken) {
        this.idToken = idToken;
    }

    @Override
    public String toString() {
        return " | TOKEN " + "id=" + idToken + " " + lexema + "  nroLinea=" + nroLinea + " tipo= " + tipo + "  |" ;
    }
    public String getTipoToken() {
        return null;
    }
}
