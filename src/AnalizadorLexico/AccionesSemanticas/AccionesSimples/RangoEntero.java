package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Atributo;

public class RangoEntero extends AccionSemanticaSimple {

    private TablaSimbolos tablaSimbolos;

    public RangoEntero(AnalizadorLexico analizadorLexico, TablaSimbolos tablaSimbolos){
        super(analizadorLexico);
        this.tablaSimbolos = tablaSimbolos;
    }
    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        try {
            int intBuffer = Integer.parseInt(buffer);

        } catch (Exception e){
            e.printStackTrace(); //fuera de rango
        }
        this.getAnalizadorLexico().setIdToken(this.tablaSimbolos.getIdToken("i32"));
        this.tablaSimbolos.agregarRegistro(buffer, new Atributo(this.tablaSimbolos.getIdToken("i32")));
        return true;
    }
}
