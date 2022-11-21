package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;

public class TablaSimbolos {

    /** Registro Tokens carga todos los tokens que se van reconociendo. Cada Token tiene sus propios atributos, lexema-id-uso-ambito-etc y la referencia es el indice */
    private ArrayList<Token> registroTokens;

    /** ID Tokens tiene guardos el numero de identificacion que le corresponde a cada token, se usa ASCII para los caracteres y se inventan para palabras reservadas u otros */
    private HashMap<String, Integer> idTokens; // aca solo se guardan los id correspondientes



    public TablaSimbolos(){

        this.registroTokens = new ArrayList<Token>();
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
        this.idTokens.put("end_if", 262);
        this.idTokens.put("out", 263);
        this.idTokens.put("fun", 264);
        this.idTokens.put("return", 265);
        this.idTokens.put("break", 266);
        this.idTokens.put("i32", 267);
        this.idTokens.put("when", 268);
        this.idTokens.put("for", 269);
        this.idTokens.put("continue", 270);
        this.idTokens.put("f32",271);
        this.idTokens.put("const",277);

        // Cadena de caracteres
        this.idTokens.put("cadena", 272);

        // Comparadores
        this.idTokens.put(">", (int) '>');
        this.idTokens.put("<", (int) '<');
        this.idTokens.put("<=", 273);
        this.idTokens.put(">=", 274);
        this.idTokens.put("=", (int) '=');
        this.idTokens.put("=!", 275);

        // Operador de asignación
        this.idTokens.put("=:", 276);

    }

    public boolean isPalabraReservada(String lexema){
        switch (lexema){
            case "if":
            case "then":
            case "else":
            case "end_if":
            case "out":
            case "fun":
            case "return":
            case "break":
            case "i32":
            case "f32":
            case "when":
            case "for":
            case "continue":
            case "const":
                return true;
            default:
                return false;
        }
    }

    public int getIdToken(String lexema) {
        int id = this.idTokens.get(lexema);

        return id;

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
            case 276:
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
            case 271:
            case 277:
                tipo = "PALABRA RESERVADA";
                break;
            case 272:
                tipo = "CADENA DE CARACTERES";
                break;
            case (int) '<':
            case (int) '>':
            case (int) '=':
            case 273:
            case 274:
            case 275:
                tipo = "COMPARADOR";
                break;
            default:
                break;
        }

        return tipo;
    }

    public void agregarRegistro(String lexema, int id, int nroLinea){
        Token registro = new Token(lexema, id, nroLinea);
        if (getTipoToken(id).equals("CONSTANTE")){
            if (lexema.contains(".")) {
                registro.setTipo("f32");
            } else {
                registro.setTipo("i32");
            }
        }
        this.registroTokens.add(registro);
    }

    public int size(){
       return registroTokens.size();
    }

    public boolean isEmpty() {
        return registroTokens.isEmpty();
    }

    public Token getEntrada(int indice){
        return this.registroTokens.get(indice);
    }

    public void eliminarEntrada(int indice){
        this.registroTokens.remove(indice);
    }

    public void imprimir(){
        System.out.println("🟡🟡🟡🟡🟡 TABLA DE SIMBOLOS 🟡🟡🟡🟡🟡");
        for(Token token : registroTokens){
            System.out.println(token.toString());
        }
    }

    public int existeEntrada(String lexema){

        for(int i = 0; i < registroTokens.size(); i++) {
            if (registroTokens.get(i).getLexema().equals(lexema)){
                return i;
            }
        }
        return -1;
    }

    public int existeUso(String uso){

        for(int i = 0; i < registroTokens.size(); i++) {
            String usoActual = registroTokens.get(i).getUso();
            if (usoActual != null  && usoActual.equals(uso) ){

                    return i;

            }
        }
        return -1;
    }

}
