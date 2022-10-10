package AnalizadorLexico.AccionesSemanticas.AccionesSimples;

import AnalizadorLexico.AccionesSemanticas.AccionSemanticaSimple;
import AnalizadorLexico.AnalizadorLexico;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
------------------------------------------
AS6
------------------------------------------
 */
public class ChequearPalabraReservada extends AccionSemanticaSimple {

    private static final int LONGITUD_MAXIMA = 25;

    public ChequearPalabraReservada(AnalizadorLexico analizadorLexico){
        super(analizadorLexico);

    }

    public boolean isIdentificador(String buffer){
        return ((Character.isLetter(buffer.charAt(0))));
    }


    /*
    ------------------------------------------
        Verifica si se encuentra en el buffer la palabra reservada, de ser asi, setea el id del token
        De lo contrario, era identificador por lo cual si el identificador excede los 25 chars, se trunca
    ------------------------------------------
     */
    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {

        AnalizadorLexico lexico = this.getAnalizadorLexico();
        try{
                if (lexico.isPalabraReservada(buffer)){ // Retorna TRUE si existe la palabra reservada (Era identificador)
                    int idTokenReservada = lexico.getIdToken(buffer);
                    lexico.setTokenActual(idTokenReservada); // Obtiene el ID de la palabra reservada
                    return true;
                }else{
                    if (this.isIdentificador(buffer)){
                        if (buffer.length() > LONGITUD_MAXIMA) {
                            Logger l = Logger.getLogger(ChequearPalabraReservada.class.getName());
                            l.setLevel(Level.WARNING);
                            l.warning(String.format("Warning: La longitud del identificador "+ buffer + " buffer es mayor a 25 y fue truncado"));
                            lexico.addErrorLexico("WARNING (Línea " + lexico.LINEA + "): el identificador " + buffer + " excede el rango y fue truncado") ;
                            buffer = buffer.substring(0, LONGITUD_MAXIMA-1); // si el identificador tiene mas de 25 chars se trunca.
                        }
                        int idTokenIdentificador = lexico.getIdToken("id");
                        this.getAnalizadorLexico().setTokenActual(idTokenIdentificador);
                        lexico.agregarRegistro(buffer, idTokenIdentificador);
                    }else
                        throw new Exception("digito o '_'"); // genero la excepcion
                }
            } catch (Throwable e){
                lexico.addErrorLexico("ERROR LÉXICO (Línea " + lexico.LINEA + "): Se encontro un digito o '_'. El identificador debe comenzar con una letra");
            }

            return true;

        }
    }


