package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Atributo;

public class RangoEntero extends AccionSemanticaSimple {


    public RangoEntero(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);
    }
    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
       AnalizadorLexico lexico = this.getAnalizadorLexico();

        try {
            int intBuffer = Integer.parseInt(buffer);

        } catch (Exception e){
            lexico.addErrorLexico("ERROR LÉXICO (Línea " + lexico.LINEA + "): la constante i32 " + buffer + " está fuera de rango.") ;
            e.printStackTrace(); // fuera de rango (Quiere decir que no se pudo asignar a la variable)

        }

        int idToken = lexico.getIdToken("i32");
        lexico.setTokenActual(idToken);
        lexico.agregarRegistro(buffer, idToken);

        return true;
    }
}
