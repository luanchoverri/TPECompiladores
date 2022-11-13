package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;

public class DevolverUltimoLeido extends AccionSemanticaSimple {

    public DevolverUltimoLeido(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);
    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
//
//        buffer = buffer.substring(0, buffer.length() - 1);
//        this.getAnalizadorLexico().setBuffer(buffer);
        return true;

    }
}
