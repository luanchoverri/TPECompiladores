package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Atributo;

/*
------------------------------------------
AS17
------------------------------------------
 */
public class AgregarCadena extends AccionSemanticaSimple {

    TablaSimbolos tablaSimbolos;

    public AgregarCadena(AnalizadorLexico analizadorLexico, TablaSimbolos tablaSimbolos){
        super(analizadorLexico);
        this.tablaSimbolos = tablaSimbolos;
    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        tablaSimbolos.agregarRegistro(buffer,new Atributo(this.tablaSimbolos.getIdToken("cadena")));
        this.getAnalizadorLexico().setIdToken(this.tablaSimbolos.getIdToken("cadena"));
        return true;
    }
}
