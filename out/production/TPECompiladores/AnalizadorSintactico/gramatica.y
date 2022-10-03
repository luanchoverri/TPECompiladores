----------------------------------------------------
-- Formato:
----------------------------------------------------
-- <declaraciones>

-- %%

-- <reglas gramaticales>

-- %%

-- <codigo...>
----------------------------------------------------


%{

----------------------------------------------------
-- imports
----------------------------------------------------

%}

-- El Analizador Léxico detecta un token y retorna un número de token al parser.
-- Los números de token son definidos por YACC cuando procesa los tokens declarados en la especificación.

    -- Esto seria los que se definen en %token

-- Cada carácter ASCII es definido como un token cuyo número es su valor ASCII (0 a 256).
    -- Los tokens definidos por el usuario comienzan en 257.

-- Si, en la especificación para YACC, se incluye:

    -- %token ID CTE …

        -- En byacc para Java, en el archivo Parser.Java, junto con yyparse, se generan:

            -- public final static short ID=257;
            -- public final static short CTE=258;

----------------------------------------------------
-- Declaraciones
----------------------------------------------------

%token IF THEN ELSE ID CTE_INT FOR PRINT CADENA -- (...ETC...) (esto tenemos que modificar)
%start programa -- inicia el programa
----------------------------------------------------

-- Seccion de reglas gramaticales

----------------------------------------------------
-- FORMATO:
----------------------------------------------------
    -- no terminal : definicion1 {acción}
    --             | definicion2 {acción}
    --             | ...
    --             ;
----------------------------------------------------

%%

programa : asignacion
;

-- Token error
    -- El analizador sintáctico podrá seguir compilando aún cuando el código fuente contenga errores.
asignacion : ID ASIGN expresion ';'
           | error ';'
;

expresion : expresion '+' termino
          | expresion '-' termino
          | termino
;

termino : termino '*' factor
          | termino '/' factor
          | factor
;

factor : ID
          | CTE_ULON
;

-- (...seguir agregando...)

%% -- Fin de reglas gramaticales

------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------

-- Inicio de codigo:

private ArrayList<String> errores;
private ArrayList<String> tokens;
private ArrayList<String> salida;
private ArrayList<String> elemento_estructuras;
private String error_anterior;

private AnalizadorLexico lexico;
private AnalizadorSintactico sintactico;

-- Getters

public AnalizadorLexico getLexico() { return this.lexico; }

public AnalizadorSintactico getSintactico() { return this.sintactico; }

-- Setters

public void setLexico(AnalizadorLexico lexico) { this.lexico = lexico; }

public void setSintactico(AnalizadorSintactico sintactico) { this.sintactico = sintactico; }

-- Metodos Yacc

public int yylex() {
    int token = lexico.procesarYylex();
    if (lexico.getRefTablaSimbolos() != -1)
        yylval = new ParserVal(lexico.getRefTablaSimbolos());
    return token;
}

public void yyerror(String string) {
	//sintactico.addErrorSintactico("par: " + string);
}



