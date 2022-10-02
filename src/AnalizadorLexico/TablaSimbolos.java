package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;

public class TablaSimbolos {

    private ArrayList<Atributo> registroTokens;
    private HashMap<String, Integer> idTokens;



    public TablaSimbolos(){

        this.registroTokens = new ArrayList<Atributo>();
        this.idTokens = new HashMap<>();

        //--- CARGA DE TOKENS ---//
        // Operadores aritméticos
        this.idTokens.put("+", (int) '+');
        this.idTokens.put("-", (int) '-');
        this.idTokens.put("*", (int) '*');
        this.idTokens.put("/", (int) '/');

        // Símbolos de puntuación
        this.idTokens.put("(", (int) '(');
        this.idTokens.put(")", (int) ')');
        this.idTokens.put("{", (int) '{');
        this.idTokens.put("}", (int) '}');
        this.idTokens.put(",", (int) ',');
        this.idTokens.put(";", (int) ';');
        this.idTokens.put(":", (int) ':');


        // Identificador y constante
        this.idTokens.put("id", 257);
        this.idTokens.put("cte", 258);
        // Palabras reservadas
        this.idTokens.put("if", 259);
        this.idTokens.put("then", 260);
        this.idTokens.put("else", 261);
        this.idTokens.put("endif", 262);
        this.idTokens.put("out", 263);
        this.idTokens.put("fun", 264);
        this.idTokens.put("return", 265);
        this.idTokens.put("break", 266);
        this.idTokens.put("i32", 267);
        this.idTokens.put("when", 268);
        this.idTokens.put("for", 269);
        this.idTokens.put("continue", 270);
        this.idTokens.put("f32",271);

        // Cadena de caracteres
        this.idTokens.put("cadena", 280);

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

    public boolean isPalabraReservada(String lexema){
        switch (lexema){
            case "if":
            case "then":
            case "else":
            case "endif":
            case "out":
            case "fun":
            case "return":
            case "break":
            case "i32":
            case "f32":
            case "when":
            case "for":
            case "continue":
                return true;
            default:
                return false;
        }
    }

    public int getIdToken(String lexema) {
        System.out.println("lexema " + lexema);
        return this.idTokens.get(lexema);
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
            case (int) '{':
            case (int) '}':
            case (int) ',':
            case (int) ';':
            case (int) ':':
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

    public void agregarRegistro(String lexema, int id){
        Atributo registro = new Atributo(lexema, id);
        this.registroTokens.add(registro);
    }

    public int size(){
       return registroTokens.size();
    }

}
