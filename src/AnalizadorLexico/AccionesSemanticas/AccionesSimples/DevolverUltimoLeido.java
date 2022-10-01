package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;

public class DevolverUltimoLeido extends AccionSemanticaSimple {

    public DevolverUltimoLeido(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);
    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        // TODO aca deberia hacer que el indice que marca donde leer en el string del archivo sea uno menos
        return true;
    }
}
