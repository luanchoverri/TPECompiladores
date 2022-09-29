package AnalizadorLexico.AccionesSemanticas;

public class MatrizAccionesSemanticas {


    // Estado -> Fila ; Simbolo -> Columna (En la matriz de estados)
    private AccionSemantica[][] matriz;

    // Constructor
    public MatrizAccionesSemanticas(int estado, int simbolo) {
        this.matriz = new AccionSemantica[estado][simbolo];
    }

    // -- MÉTODOS -- // 

    // Getter
    public AccionSemantica get(int estado, int simbolo) {
        return matriz[estado][simbolo];
    }

    // Setter -> Setea una matriz completa a la ya existente    
    public void setMatrizAccionSemantica (AccionSemantica[][] matriz) {
        this.matriz = matriz;
    }

    // Setter -> Setea un valor de AS en la matriz
    public void setAccionSemantica (int estado, int simbolo, AccionSemantica accionSemantica) {
        matriz[estado][simbolo] = accionSemantica;
    }
}

    

