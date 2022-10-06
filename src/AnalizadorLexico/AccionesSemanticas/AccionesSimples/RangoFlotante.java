package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;


public class RangoFlotante extends AccionSemanticaSimple {

    public static final float MINIMO_FLOAT = (float) Math.pow(1.17549435, -38); // 1.17549435E-38f (Rango minimo negativo)
    public static final float MAXIMO_FLOAT = (float) Math.pow(3.40282347, 38); // 3.40282347E+38f (Rango maximo positivo)

    public RangoFlotante(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);

    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        AnalizadorLexico lexico = this.getAnalizadorLexico();
        try {
            if (buffer.equals("0.0") || buffer.equals("0.") || buffer.equals(".0")) // Si es cualquier caso de cero, retorna true
                return true;

            float floatBuffer = 0f;
                if (buffer.contains("F")){
                    String[] parts = buffer.split("F");
                    floatBuffer = (float) Math.pow(Double.valueOf(parts[0]), Double.valueOf(parts[1]));
                }else{
                    if(!buffer.endsWith("F")){
                        floatBuffer = Float.parseFloat(buffer);
                    }
                }
            if (((floatBuffer < MINIMO_FLOAT) || (floatBuffer > MAXIMO_FLOAT))){
                throw new Exception("FUERA DE RANGO"); // genero la excepcion
            }
        } catch (Throwable e){
            lexico.addErrorLexico("ERROR LÉXICO (Línea " + lexico.LINEA + "): la constante f32 " + buffer + " está fuera de rango.") ;
        }

        int idToken = lexico.getIdToken("cte");
        lexico.setTokenActual(idToken);
        lexico.agregarRegistro(buffer, idToken);
        return true;
   }
}
