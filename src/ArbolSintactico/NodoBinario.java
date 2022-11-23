package ArbolSintactico;

public class NodoBinario extends Nodo {

	private Nodo hijoIzquierdo;
	private Nodo hijoDerecho;

	public NodoBinario(Object izquierda, Object derecha, String lexema) {
		this.hijoIzquierdo = (Nodo) izquierda;
		this.hijoDerecho = (Nodo) derecha;
		super.lexema = lexema;
	}

	@Override
	public Nodo getHijoIzquierdo() {
		return this.hijoIzquierdo;
	}

	@Override
	public Nodo getHijoDerecho() {
		return this.hijoDerecho;
	}

	@Override
	public boolean esHoja() {
		if (this.hijoIzquierdo == null && this.hijoDerecho == null)
			return true;
		return false;
	}

	public void setHijos(Nodo izquierda, Nodo derecha) {
		this.hijoIzquierdo = izquierda;
		this.hijoDerecho = derecha;
	}

	public void setHijoDerecho(Nodo hijoDerecho) {
		this.hijoDerecho = hijoDerecho;
	}

	public boolean hijosSonHoja() {
		if (esHoja())
			return false;
		if (this.hijoIzquierdo == null && this.hijoDerecho != null) {
			if (this.hijoDerecho.esHoja()) {
				return true;
			}
		}
		if (this.hijoDerecho == null && this.hijoIzquierdo != null) {
			if (this.hijoIzquierdo.esHoja()) {
				return true;
			}
		}
		if (this.hijoIzquierdo != null && this.hijoDerecho != null && this.hijoIzquierdo.esHoja() && this.hijoDerecho.esHoja())
			return true;
		return false;
	}

	public void descolgarHijos() {
		this.hijoDerecho = null;
		this.hijoIzquierdo = null;
	}

}