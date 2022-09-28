package AnalizadorLexico;

public class AnalizadorLexico {
    String buffer; //va guardando lo que va pasando por el automata
    Integer idToken; //id token obtenido del buffer

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
}
