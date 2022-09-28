package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.AnalizadorLexico;

public abstract class AccionSemanticaSimple implements AccionSemantica {

    AnalizadorLexico analizadorLexico;

    public AccionSemanticaSimple(AnalizadorLexico analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }

    public AnalizadorLexico getAnalizadorLexico() {
        return analizadorLexico;
    }

    public void setAnalizadorLexico(AnalizadorLexico analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }

    @Override
    public abstract boolean ejecutar(String buffer, char ultimoLeido);
}
