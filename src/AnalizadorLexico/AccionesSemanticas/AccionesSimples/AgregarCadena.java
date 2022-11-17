package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;

/*
------------------------------------------
AS17
------------------------------------------
 */
public class AgregarCadena extends AccionSemanticaSimple {


    public AgregarCadena(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);
    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {

        int idToken = this.getAnalizadorLexico().getIdToken("cadena");
        this.getAnalizadorLexico().agregarRegistro(buffer, idToken);
        this.getAnalizadorLexico().setTokenActual(idToken);
        return true;
    }
}
