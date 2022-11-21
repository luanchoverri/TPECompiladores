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
    private Integer refTablaSimbolos;

    public NodoHijo(Object nodoHijo, String lexema) {
        this.nodoHijo = (Nodo) nodoHijo;
        this.refTablaSimbolos = null;
        super.lexema = lexema;
    }

    public NodoHijo(Object nodoHijo, String lexema, Integer refTablaSimbolos) {
        this.nodoHijo = (Nodo) nodoHijo;
        this.refTablaSimbolos = refTablaSimbolos;
        super.lexema = lexema;
    }

    public Integer getRefTablaSimbolos() {
        return refTablaSimbolos;
    }

    public void setRefTablaSimbolos(Integer refTablaSimbolos) {
        this.refTablaSimbolos = refTablaSimbolos;
    }

    @Override
    public Nodo getHijoIzquierdo() {
        return this.nodoHijo;
    }

    @Override
    public Nodo getHijoDerecho() {
        return null;
    }

    @Override
    public void setHijoDerecho(Nodo derecho) {

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

    public boolean hijosSonHoja() {
        if (this.nodoHijo.esHoja())
            return true;
        return false;
    }

    public void descolgarHijos() {
        this.nodoHijo = null;
    }

}