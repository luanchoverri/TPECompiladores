package analizadorLexico;

import java.util.HashMap;

public class TablaSimbolos {

    private HashMap<String, Token> registroTokens;
    private HashMap<String, Integer> idTokens;



    public TablaSimbolos(){


        //--- CARGA DE TOKENS ---//
        // Operadores aritméticos
        this.idTokens.put("+", (int) '+');
        this.idTokens.put("-", (int) '-');
        this.idTokens.put("*", (int) '*');
        this.idTokens.put("/", (int) '/');

        // Símbolos de puntuación
        this.idTokens.put("(", (int) '(');
        this.idTokens.put(")", (int) ')');
        this.idTokens.put(",", (int) ',');
        this.idTokens.put(";", (int) ';');


        // Identificador y constante
        this.idTokens.put("ID", 257);
        this.idTokens.put("CTE", 258);
        // Palabras reservadas
        this.idTokens.put("IF", 259);
        this.idTokens.put("THEN", 260);
        this.idTokens.put("ELSE", 261);
        this.idTokens.put("ENDIF", 262);
        this.idTokens.put("OUT", 263);
        this.idTokens.put("FUN", 264);
        this.idTokens.put("RETURN", 265);
        this.idTokens.put("BREAK", 266);
        this.idTokens.put("DISCARD", 267);
        this.idTokens.put("WHEN", 268);
        this.idTokens.put("FOR", 269);
        this.idTokens.put("CONTINUE", 270);

        // Cadena de caracteres
        this.idTokens.put("CADENA", 280);

        // Comparadores
        this.idTokens.put(">", (int) '>');
        this.idTokens.put("<", (int) '<');
        this.idTokens.put("<=", 281);
        this.idTokens.put(">=", 282);
        this.idTokens.put("=", 283);
        this.idTokens.put("=!", 284);

        // Operador de asignación
        this.idTokens.put("=:", 285);

        // Operadores lógicos ?
    }



    public int getIdToken(String tipo) {
        return idTokens.get(tipo);
    }

    /**
     * En base al id de un token pasado por parámetro, determina su tipo.
     * @param id
     * @return
     */
    public String getTipoToken(int id) {
        String tipo = "";

        switch (id) {
            case (int) '+':
            case (int) '-':
            case (int) '*':
            case (int) '/':
                tipo = "OPERADOR ARITMETICO";
                break;
            case (int) '(':
            case (int) ')':
            case (int) ',':
            case (int) ';':
                tipo = "LITERAL";
                break;
            case 285:
                tipo = "ASIGNACION";
                break;
            case 257:
                tipo = "IDENTIFICADOR";
                break;
            case 258:
                tipo = "CONSTANTE";
                break;
            case 259:
            case 260:
            case 261:
            case 262:
            case 263:
            case 264:
            case 265:
            case 266:
            case 267:
            case 268:
            case 269:
            case 270:
                tipo = "PALABRA RESERVADA";
                break;
            case 280:
                tipo = "CADENA DE CARACTERES";
                break;
            case (int) '<':
            case (int) '>':
            case 281:
            case 282:
            case 283:
            case 284:
                tipo = "COMPARADOR";
                break;
            default:
                break;
        }

        return tipo;
    }

    public void addToken(String lexema, String tipo){
        Token nuevo = new Token(lexema, tipo);
        this.registroTokens.put(lexema, nuevo);
        // que hacemos con la referencia?
    }

    public boolean esPalabraReservada(String palabra) {
        switch (palabra) {
            case "IF":
            case "THEN":
            case "ELSE":
            case "ENDIF":
            case "FUNC":
            case "RETURN":
            case "BREAK":
            case "FUN":
            case "OUT":
            case "DISCARD":
            case "WHEN":
            case "FOR":
            case "CONTINUE":
                return true;
            default:
                return false;
        }
    }
}
