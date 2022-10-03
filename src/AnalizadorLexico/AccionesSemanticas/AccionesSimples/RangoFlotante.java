package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;


public class RangoFlotante extends AccionSemanticaSimple {


    public RangoFlotante(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);

    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        AnalizadorLexico lexico = this.getAnalizadorLexico();

        if (buffer.contains("F") && !buffer.endsWith("F")){
            buffer = buffer.replace("F","e");
            buffer += "f";
        }
        try {
            float floatBuffer = Float.parseFloat(buffer);
            if ((!(1.17549435e-38 < floatBuffer) || !(floatBuffer < 3.40282347e+38) || !(-3.40282347e+38 < floatBuffer) || !(floatBuffer < -1.17549435e-38))){
                throw new Exception("out of range");
            }
        } catch (Exception e){
            lexico.addErrorLexico("ERROR LÉXICO (Línea " + lexico.LINEA + "): la constante f32 " + buffer + " está fuera de rango.") ;
            e.printStackTrace(); //fuera de rango
        }

        int idToken = lexico.getIdToken("cte");
        lexico.setTokenActual(idToken);
        lexico.agregarRegistro(buffer, idToken);
        return true;
    }
}
