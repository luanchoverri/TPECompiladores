package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;

/*
--------------------------------
AS9
--------------------------------
 */
public class EliminarCaracterBuffer extends AccionSemanticaSimple
{
    public EliminarCaracterBuffer(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);
    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        buffer = buffer.substring(0, buffer.length() -1);
        this.getAnalizadorLexico().setBuffer(buffer);
        return true;
    }
}
