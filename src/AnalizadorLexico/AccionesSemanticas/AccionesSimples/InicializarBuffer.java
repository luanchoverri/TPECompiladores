package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;
/*
------------------------------------------
AS1
------------------------------------------
 */

public class InicializarBuffer extends AccionSemanticaSimple {

    public InicializarBuffer(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);
    }
    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        buffer = "";
        this.getAnalizadorLexico().setBuffer(buffer);
        return true;
    }
}
