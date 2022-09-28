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

    public ObtenerIdToken(AnalizadorLexico analizadorLexico, TablaSimbolos tablaSimbolos){
        super(analizadorLexico);
        this.tablaSimbolos = tablaSimbolos;
    }
    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        this.getAnalizadorLexico().setIdToken(this.tablaSimbolos.getIdToken(buffer));
        return true;
    }
}
