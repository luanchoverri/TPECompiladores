package AnalizadorLexico;

public class Atributo {

    // Posee los tokens (id del token, etc)
    
    private int idToken; // Id del token (Numero de identificacion)
    private String lexema; // Significado del Token
    
    
    // ... otros atributos
    
    // -- Constructor --

    public Atributo(int idToken) {
        this.idToken = idToken;
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
    
}
