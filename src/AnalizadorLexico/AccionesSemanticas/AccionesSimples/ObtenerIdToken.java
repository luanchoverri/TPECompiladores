package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TablaSimbolos;

/*
------------------------------------------
AS3
------------------------------------------
 */
public class ObtenerIdToken extends AccionSemanticaSimple {

    private TablaSimbolos tablaSimbolos;

    public ObtenerIdToken(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);

    }
    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        int idToken = this.getAnalizadorLexico().getIdToken(buffer);
        this.getAnalizadorLexico().setTokenActual(idToken);
        return true;
    }
}
