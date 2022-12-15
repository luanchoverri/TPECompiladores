package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;


public class RangoFlotante extends AccionSemanticaSimple {

    public static final float MINIMO_FLOAT = (float) (1.17549435 * Math.pow(10, -38)); // 1.17549435E-38f (Rango minimo negativo)
    public static final float MAXIMO_FLOAT = (float) (3.40282347 * Math.pow(10, 38)); // 3.40282347E+38f (Rango maximo positivo)

    public RangoFlotante(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);

    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        AnalizadorLexico lexico = this.getAnalizadorLexico();
        try {
            if (buffer.equals("0.0") || buffer.equals("0.") || buffer.equals(".0")){ // Si es cualquier caso de cero, retorna true
                int idToken = lexico.getIdToken("cte");
                lexico.setTokenActual(idToken);
               lexico.agregarRegistro(buffer, idToken);
                return true;
            }
            float floatBuffer = 0f;
                if (buffer.contains("F")){
                    String[] parts = buffer.split("F");
                    floatBuffer =  (float) (Double.valueOf(parts[0]) * Math.pow(10, Double.valueOf(parts[1])));
                }else{
                    if(buffer.startsWith("."))
                        buffer = buffer.replace(".", "0.");
                    else
                        if(buffer.endsWith("."))
                           buffer = buffer.replace(".", ".0");

                    floatBuffer = Float.parseFloat(buffer);
                }
            if (((Math.abs(floatBuffer) <= MINIMO_FLOAT) || (Math.abs(floatBuffer) >= MAXIMO_FLOAT))){ // el abs es para contemplar casos positivos y negativos
                throw new Exception("FUERA DE RANGO"); // genero la excepcion
            }
        } catch (Throwable e){
            lexico.addErrorLexico("ERROR LÉXICO (Línea " + lexico.LINEA + "): la constante f32 con valor -> " + buffer + " está fuera de rango."+"\n") ;
        }

        int idToken = lexico.getIdToken("cte");
        lexico.setTokenActual(idToken);
        lexico.agregarRegistro(buffer, idToken);
        return true;
   }
}
