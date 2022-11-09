package ArbolSintactico;

public class NodoHijo extends Nodo {

    /*
     * Para la sentencia ELSE solo necesitamos 2 parametros, ya que el caso mas
     * simple solo tendremos el cuerpo de sentencias, si tuvieramos otro IF se llama
     * a NodoBinario
     *
     * Entonces para ELSE:
     * -> en el nodo tendriamos el cuerpo (bloque de sentencias)
     * -> y el lexema en este caso seria "ELSE"
     *
     *
     */

    private Nodo nodoHijo;

    public NodoHijo(Object nodoHijo, String lexema) {
        this.nodoHijo = (Nodo) nodoHijo;
        super.lexema = lexema;
    }

    @Override
    public Nodo getHijoIzquierdo() {
        return null;
    }

    @Override
    public Nodo getHijoDerecho() {
        return this.nodoHijo;
    }

    @Override
    public boolean esHoja() {
        if (this.nodoHijo == null)
            return true;
        return false;
    }

    public void setHijo(Nodo nodoHijo) {
        this.nodoHijo = nodoHijo;
    }

}