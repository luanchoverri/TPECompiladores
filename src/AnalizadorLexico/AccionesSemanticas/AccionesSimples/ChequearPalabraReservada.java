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




    /*
    ------------------------------------------
        Verifica si se encuentra en el buffer la palabra reservada, de ser asi, setea el id del token    
        De lo contrario, era identificador por lo cual si el identificador excede los 25 chars, se trunca
    ------------------------------------------
     */
    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        if (this.tablaSimbolos.isPalabraReservada(buffer)){ // Retorna TRUE si existe la palabra reservada
            this.getAnalizadorLexico().setIdToken(this.tablaSimbolos.getIdToken(buffer)); // Obtiene el ID de la palabra reservada
        } else { // era identificador
            if (buffer.length() > 25) {
                Logger l = Logger.getLogger(ChequearPalabraReservada.class.getName());
                l.setLevel(Level.WARNING);                  
                l.warning(String.format("Warning: La longitud del identificador %s es mayor a 25 y fue truncado",buffer));
                buffer = buffer.substring(0,24); // si el identificador tiene mas de 25 chars se trunca.
            }
            this.getAnalizadorLexico().setIdToken(this.tablaSimbolos.getIdToken("id"));
            this.tablaSimbolos.agregarRegistro(buffer, new Atributo(this.tablaSimbolos.getIdToken("id")));
        }
        return true;
    }
}
