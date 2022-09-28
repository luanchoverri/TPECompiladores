package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Atributo;

public class RangoFlotante extends AccionSemanticaSimple {

    TablaSimbolos tablaSimbolos;

    public RangoFlotante(AnalizadorLexico analizadorLexico, TablaSimbolos tablaSimbolos){
        super(analizadorLexico);
        this.tablaSimbolos = tablaSimbolos;
    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
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
            e.printStackTrace(); //fuera de rango
        }
        this.getAnalizadorLexico().setIdToken(this.tablaSimbolos.getIdToken("f32"));
        this.tablaSimbolos.agregarRegistro(buffer, new Atributo(this.tablaSimbolos.getIdToken("f32")));
        return true;
    }
}
