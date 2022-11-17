package AnalizadorLexico;

public class Atributo {

    // Posee los tokens (id del token, etc)

    private int idToken; // Id del token (Numero de identificacion)
    private String lexema; // Significado del Token
    private int nroLinea;
    private String tipo;
    private String uso;



    // -- Constructor --

    public Atributo(String lexema, int idToken, int nroLinea) {
        this.lexema = lexema;
        this.idToken = idToken;
        this.tipo = null;
        this.nroLinea = nroLinea;
        this.uso = null;
    }

    public Atributo(int idToken, String lexema, int nroLinea, String tipo) {
        this.idToken = idToken;
        this.lexema = lexema;
        this.nroLinea = nroLinea;
        this.tipo = tipo;
        this.uso = null;
    }


    // -- MÉTODOS -- // 

    // Getters 

    public int getIdToken() {
        return this.idToken;
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
                                "TOKEN", idToken, lexema, "#linea", nroLinea, tipo, uso);
        return(s) ;
    }

}

