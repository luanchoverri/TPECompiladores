%{

package AnalizadorSintactico;

import java.util.Vector;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorLexico.Atributo;

%}

%token id cte IF then ELSE endIF out fun RETURN BREAK i32 when FOR CONTINUE f32 cadena menorigual mayorigual distinto opasignacion
%start programa

%%


programa : id bloque_sentencias ';'
         | id  ';'
         | id bloque_sentencias error      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' ."); }
         ;

bloque : sentencias
       | bloque sentencias
       ;


bloque_sentencias : '{' bloque '}'
                     | '{' '}'
                     | sentencias
                     | bloque '}'  error     { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque."); }
                     | '{' bloque  error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): bloque mal cerrado."); }
                     ;

bloque_sentencias_FOR : sentencias_FOR
                      | bloque_sentencias_FOR sentencias_FOR
                      ;


bloque_sentencias_ejecutables : sentencias_ejecutables
                              | bloque_sentencias_ejecutables sentencias_ejecutables
                              ;



sentencias : sentencias_declarativas
           | sentencias_ejecutables
           ;


sentencias_declarativas : tipo lista_de_variables ';'        { sintactico.agregarAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
                        | tipo lista_de_variables error      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' al final de la declaración de variable."); }
                        | tipo error                         { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta el nombre de la variable"); }
                        | declaracion_func
                        ;

sentencias_ejecutables : asignacion
                       | salida
                       | sentencia_IF
                       | expresion_FOR
                       | sentencia_when
                       ;

lista_de_variables : id
                   | lista_de_variables ',' id
                   | lista_de_variables id error    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIFicadores."); }
                   ;

encabezado_func : fun id '('
                | id '(' error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta fun en la definición de la función."); }
                ;

parametro : tipo id ')' ':' tipo '{'
          | tipo id ',' tipo id ')' ':' tipo '{'
          | tipo id tipo id ')' ':' tipo '{' error      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre parametros."); }
          | ')' ':' tipo '{'
          ;

declaracion_func : encabezado_func parametro bloque RETURN '(' expresion ')' ';' '}'
                 | encabezado_func parametro RETURN '(' expresion ')' ';' '}'
                 ;



op_asignacion : opasignacion    { $$.sval = new String("=:"); }
              ;

asignacion : id op_asignacion expresion ';'
           | id op_asignacion expresion error  { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' luego de la asignación."); }
           | id expresion error                { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta el operador de asignación."); }
           | id op_asignacion error            { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' luego de la asignación."); }
           | id expresion_FOR
           ;

salida : out '(' cadena ')' ';'
       | out '(' cadena ')' error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
       | out '(' cadena error ';'   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
       | out cadena error ';'       { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
       | '(' cadena error             { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
       | out '(' ')' error ';'      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
       ;


sentencia_IF : IF '(' condicion ')' then cuerpo_IF endIF ';'                      { sintactico.agregarAnalisis("Se reconoció una sentencia IF. (Línea " + AnalizadorLexico.LINEA + ")"); }
             | IF '(' condicion ')' then cuerpo_IF ELSE cuerpo_ELSE endIF ';'     { sintactico.agregarAnalisis("Se reconoció una sentencia IF. (Línea " + AnalizadorLexico.LINEA + ")"); }
             | IF condicion error                                                 { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): la condición de IF debe estar entre paréntesis."); }
             | IF '(' condicion  then error                                       { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
             | IF '(' condicion ')' cuerpo_IF error                               { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
             | IF '(' condicion ')' then cuerpo_IF error ';'                      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cierre de endIF."); }
             | IF '(' condicion ')' then cuerpo_IF endIF error                    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de endIF."); }
             | IF '(' condicion ')' then cuerpo_IF ELSE cuerpo_ELSE error ';'     { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cierre de endIF."); }
             | IF '(' condicion ')' then cuerpo_IF ELSE cuerpo_ELSE endIF error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de endIF."); }
             ;

cuerpo_IF :  bloque_sentencias_ejecutables
          ;

cuerpo_ELSE : bloque_sentencias_ejecutables
            ;

sentencia_when : when '(' condicion ')' then cuerpo_when ';'
             | when condicion error                                                 { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): la condición de when debe estar entre paréntesis."); }
             | when '(' condicion  then error                                       { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
             | when '(' condicion ')' cuerpo_when error                               { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }

cuerpo_when : bloque_sentencias
            ;

encabezado_FOR : FOR '(' asignacion ';' condicion ';' signo id ')'
               | FOR  asignacion ';' condicion ';' signo id ')' error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir parentisis."); }
               | FOR '(' asignacion ';' condicion ';' signo id error    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar parentesis."); }
               | FOR '(' asignacion  condicion ';' signo id ')' error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
               | FOR '(' asignacion ';' condicion  signo id ')' error  { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
               | FOR '(' ';' condicion ';' signo id ')' error           { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta asignacion "); }
               | FOR '(' asignacion ';' ';' signo id ')' error          { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta condicion "); }
               | id ':' FOR '(' asignacion ';' condicion ';' signo id ')'
               | ':' FOR '(' asignacion ';' condicion ';' signo id ')' error    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la etiqueta"); }
               | id FOR '(' asignacion ';' condicion ';' signo id ')' error     { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ':'"); }
               ;


signo : '+'
      | '-'
      ;

cuerpo_FOR : '{' bloque_sentencias_FOR '}' ';'
           | bloque_sentencias_FOR '}' ';'  error    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir llave "); }
           | '{' bloque_sentencias_FOR ';'  error    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar llave "); }
           | '{' bloque_sentencias_FOR '}'  error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
           | '{' bloque_sentencias_FOR '}' ELSE cte ';'
           | '{' bloque_sentencias_FOR '}' ELSE ';' error  { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la constante "); }
           | '{' bloque_sentencias_FOR '}' cte ';'  error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta el ELSE. "); }
           ;

sentencias_FOR : sentencias_ejecutables
               | sentencia_BREAK
               | sentencia_CONTINUE
               | sentencias_declarativas error{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias declarativas adentro del FOR"); }
               ;

expresion_FOR : encabezado_FOR cuerpo_FOR
              ;


sentencia_BREAK : BREAK ';'
                | BREAK cte
                | BREAK error  { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
                ;

sentencia_CONTINUE : CONTINUE ';'
                   | CONTINUE error  { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de CONTINUE."); }
                   | CONTINUE ':' id ';'
                   | CONTINUE ':' ';' error     { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta etiqueta"); }
                   | CONTINUE id ';' error      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ':'."); }
                   | CONTINUE ':' id error      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
                   ;

condicion : expresion_relacional
          ;

expresion_relacional : expresion comparador expresion
                     ;

expresion : '(' expresion ')' signo termino
          | termino
          ;

termino : termino '*' factor
        | termino '/' factor
        | factor
        ;

factor : id
       | cte
       | '-' cte
       ;

comparador : '<'            { $$.sval = new String("<"); }
           | '>'            { $$.sval = new String(">"); }
           | menorigual     { $$.sval = new String("<="); }
           | mayorigual     { $$.sval = new String(">="); }
           | '='            { $$.sval = new String("="); }
           | distinto       { $$.sval = new String("=!"); }
           ;

tipo : i32     {
                    sintactico.setTipo("i32");
                    $$.sval = new String("i32");
                }
     | f32   {
                    sintactico.setTipo("f32");
                    $$.sval = new String("f32");
                }
     ;

%%

private AnalizadorLexico lexico;
private AnalizadorSintactico sintactico;

public void setLexico(AnalizadorLexico lexico) { this.lexico = lexico; }

public void setSintactico(AnalizadorSintactico sintactico) { this.sintactico = sintactico; }

public AnalizadorLexico getLexico() { return this.lexico; }

public AnalizadorSintactico getSintactico() { return this.sintactico; }

public int yylex() {
    int token = lexico.procesarYylex();
    if (lexico.getRefTablaSimbolos() != -1)
        yylval = new ParserVal(lexico.getRefTablaSimbolos());
    return token;
}

public void yyerror(String string) {
	//sintactico.addErrorSintactico("par: " + string);
}


