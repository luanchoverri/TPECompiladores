package AnalizadorLexico;

public class Token {

    // Posee los tokens (id del token, etc)

    private int id; // Id del token (Numero de identificacion)
    private String lexema; // Significado del Token
    private int nroLinea;
    private String tipo;
    private String uso;



    // -- Constructor --

    public Token(String lexema, int idToken, int nroLinea) {
        this.lexema = lexema;
        this.id = idToken;
        this.tipo = null;
        this.nroLinea = nroLinea;
        this.uso = null;
    }

    public Token(int idToken, String lexema, int nroLinea, String tipo) {
        this.id = idToken;
        this.lexema = lexema;
        this.nroLinea = nroLinea;
        this.tipo = tipo;
        this.uso = null;
    }


    // -- MÉTODOS -- // 

    // Getters 

    public int getId() {
        return this.id;
    }

    public String getLexema() {
        return this.lexema;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getUso() { return uso; }


    // Setters

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setUso(String uso) {this.uso = uso;}

    public void setTipo(String tipo) {this.tipo = tipo;}

    @Override
    public String toString() {

        String s = String.format("%5s %5s %10s %10s %1s %6s %6s ",
                                "TOKEN", id, lexema, "#linea", nroLinea, tipo, uso);
        return(s) ;
    }

}

