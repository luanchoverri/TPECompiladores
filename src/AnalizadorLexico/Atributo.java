package AnalizadorLexico;

public class Atributo {

    // Posee los tokens (id del token, etc)

    private int idToken; // Id del token (Numero de identificacion)
    private String lexema; // Significado del Token
    private int nroLinea;
    private String tipo;


    // -- Constructor --

    public Atributo(String lexema, int idToken, int nroLinea) {
        this.lexema = lexema;
        this.idToken = idToken;
        this.tipo = null;
        this.nroLinea = nroLinea;
    }

    public Atributo(int idToken, String lexema, int nroLinea, String tipo) {
        this.idToken = idToken;
        this.lexema = lexema;
        this.nroLinea = nroLinea;
        this.tipo = tipo;
    }

    public Atributo(int idToken, String lexema, String tipo) {
        this.idToken = idToken;
        this.lexema = lexema;
        this.tipo = tipo;
    }

    // -- MÉTODOS -- // 

    // Getters 
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getIdToken() {
        return this.idToken;
    }

    public String getLexema() {
        return this.lexema;
    }

    public String getTipo() {
        return this.tipo;
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
        return " | TOKEN " + "id=" + idToken + " " + lexema + "  nroLinea=" + nroLinea + " tipo= " + tipo + "  |";
    }

}

