package ArbolSintactico;

public class NodoBinario extends Nodo {

	/**
	 * Esta clase se implementa para crear los nodos binarios con hijos (izq y der)
	 *
	 * Es necesario, ya que en casos donde se necesite una condicion y un cuerpo
	 *
	 * Por ejemplo, sentencia IF
	 *
	 * Entonces:
	 * -> En el nodo IZQ tendremos la condificion
	 * -> En el nodo DER tendremos el cuerpo (bloque de sentencias)
	 * -> y el lexema en este caso seria "IF"
	 *
	 */

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
		if (this.hijoIzquierdo.esHoja() && this.hijoDerecho.esHoja())
			return true;
		return false;
	}

	public void descolgarHijos() {
		this.hijoDerecho = null;
		this.hijoIzquierdo = null;
	}

}