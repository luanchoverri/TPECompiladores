package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;

/*
------------------------------------------
AS2
------------------------------------------
 */
public class AgregarChar extends AccionSemanticaSimple {

    public AgregarChar(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);
    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        buffer += ultimoLeido;
        this.getAnalizadorLexico().setBuffer(buffer);
        return true;
    }
}
