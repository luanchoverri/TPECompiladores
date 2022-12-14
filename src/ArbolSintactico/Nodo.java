package ArbolSintactico;

public abstract class Nodo {

	protected String lexema;
	protected int valor;
	private String tipo;


	public int getValor(){
		return this.valor;
	}

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

	public abstract void setHijoDerecho(Nodo derecho);

    public abstract boolean hijosSonHoja();

	public abstract void descolgarHijos();

	@Override
	public String toString() {
		return "Nodo{" +
				"lexema='" + lexema + '\'' +
				", valor=" + valor +
				", tipo='" + tipo + '\'' +
				'}';
	}
}