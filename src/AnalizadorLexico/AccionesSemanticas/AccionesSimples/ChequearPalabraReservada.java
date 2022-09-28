package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Atributo;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
------------------------------------------
AS6
------------------------------------------
 */
public class ChequearPalabraReservada extends AccionSemanticaSimple {
    private TablaSimbolos tablaSimbolos;

    public ChequearPalabraReservada(AnalizadorLexico analizadorLexico, TablaSimbolos tablaSimbolos){
        super(analizadorLexico);
        this.tablaSimbolos = tablaSimbolos;
    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        if (this.tablaSimbolos.isPalabraReservada(buffer)){
            this.getAnalizadorLexico().setIdToken(this.tablaSimbolos.getIdToken(buffer));
        } else { // era identificador
            if (buffer.length() > 25) {
                Logger l = Logger.getLogger(ChequearPalabraReservada.class.getName());
                l.setLevel(Level.WARNING);                  //TODO tal vez hay una mejor forma para tirar el warning
                l.warning(String.format("Identifier %s is too long, max length is 25 characters",buffer));
                buffer = buffer.substring(0,24); // si el identificador tiene mas de 25 chars se trunca.
            }
            this.getAnalizadorLexico().setIdToken(this.tablaSimbolos.getIdToken("id"));
            this.tablaSimbolos.agregarRegistro(buffer, new Atributo(this.tablaSimbolos.getIdToken("id")));
        }
        return true;
    }
}
