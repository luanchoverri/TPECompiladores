package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Atributo;

public class RangoEntero extends AccionSemanticaSimple {

    public static final long MAXIMO_ENTERO_LARGO = 2147483648L;

    public static final long MINIMO_ENTERO_LARGO = -2147483648L;

    public RangoEntero(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);
    }
    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
       AnalizadorLexico lexico = this.getAnalizadorLexico();
        try {
            if (buffer.equals("0")) // Si es cero, retorna true
                return true;
            long intBuffer = Long.parseLong(buffer);
            if (!(intBuffer <= MAXIMO_ENTERO_LARGO) || !(intBuffer >= MINIMO_ENTERO_LARGO))
                throw new Exception("FUERA DE RANGO"); // genero la excepcion

        } catch (Throwable e){
            lexico.addErrorLexico("ERROR LÉXICO (Línea " + lexico.LINEA + "): la constante i32 con valor -> " + buffer + " está fuera de rango.") ;
            //e.printStackTrace(); // fuera de rango (Quiere decir que no se pudo asignar a la variable)
        }
        int idToken = lexico.getIdToken("cte");
        lexico.setTokenActual(idToken);
        lexico.agregarRegistro(buffer, idToken);
        return true;
    }
}
