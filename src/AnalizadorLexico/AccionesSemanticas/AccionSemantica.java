package AnalizadorLexico.AccionesSemanticas;

import AnalizadorLexico.AnalizadorLexico;

public interface AccionSemantica {
    boolean ejecutar(String buffer, char ultimoLeido);
}
