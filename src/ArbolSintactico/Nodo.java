package ArbolSintactico;

public abstract class Nodo {

	protected String lexema;
	private String tipo;

	public String getLexema () {
		return this.lexema;
	}

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getTipo () {
		return this.tipo;
	}

	public void setTipo (String tipo) {
		this.tipo = tipo;
	}

	public void addLexema(String lexemaNuevo) {
		this.lexema = lexemaNuevo;
	}

	public abstract boolean esHoja();

    public abstract Nodo getHijoIzquierdo ();

    public abstract Nodo getHijoDerecho ();

}