package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.AnalizadorLexico;

import java.util.ArrayList;

public class AccionSemanticaCompuesta implements AccionSemantica{

    ArrayList<AccionSemantica> accionesSemanticas;
    AnalizadorLexico analizadorLexico;

    public AccionSemanticaCompuesta(ArrayList<AccionSemantica> accionesSemanticas, AnalizadorLexico analizadorLexico) {
        this.accionesSemanticas = accionesSemanticas;
        this.analizadorLexico = analizadorLexico;
    }

    @Override
    public boolean ejecutar(String buffer, char ultimoLeido) {
        for (AccionSemantica accion : accionesSemanticas){
            buffer = this.analizadorLexico.getBuffer(); // hay que actualizarlo porque tal vez fue modificado por una accion anterior
            if (! accion.ejecutar(buffer,ultimoLeido))
                return false;
        }
        return true;
    }
}
