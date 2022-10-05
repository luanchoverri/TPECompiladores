%{

package analizadorSintactico;

import java.util.Vector;

import analizadorLexico.AnalizadorLexico;
import analizadorSintactico.AnalizadorSintactico;
import analizadorLexico.RegistroSimbolo;

%}

%token id cte if then else endif out fun return break i32 when for continue f32 cadena menorigual mayorigual distinto opasignacion
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

bloque_sentencias_for : sentencias_for
                      | bloque_sentencias_for sentencias_for
                      ;

//TODO




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
                       | sentencia_if
                       | expresion_for
                       | sentencia_when
                       ;

lista_de_variables : id
                   | lista_de_variables ',' id
                   | lista_de_variables id error    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identificadores."); }
                   ;

encabezado_func : fun id '('
                | id '(' error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta fun en la definición de la función."); }
                ;

parametro : tipo id ')' ':' tipo '{'
          | tipo id ',' tipo id ')' ':' tipo '{'
          | tipo id tipo id ')' ':' tipo '{' error      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre parametros."); }
          | ')' ':' tipo '{'
          ;

declaracion_func : encabezado_func parametro bloque return '(' expresion ')' ';' '}'
                 | encabezado_func parametro return '(' expresion ')' ';' '}'
                 ;



op_asignacion : opasignacion    { $$.sval = new String("=:"); }
              ;

asignacion : id op_asignacion expresion ';'
           | id op_asignacion expresion error  { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' luego de la asignación."); }
           | id expresion error                { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta el operador de asignación."); }
           | id op_asignacion error            { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + (AnalizadorLexico.LINEA - 1) + "): falta ';' luego de la asignación."); }
           | id expresion_for
           ;

salida : out '(' cadena ')' ';'
       | out '(' cadena ')' error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + analizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
       | out '(' cadena error ';'   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + analizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
       | out cadena error ';'       { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + analizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
       | '(' cadena error             { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + analizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
       | out '(' ')' error ';'      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + analizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
       ;


sentencia_if : if '(' condicion ')' then cuerpo_if endif ';'                      { sintactico.agregarAnalisis("Se reconoció una sentencia IF. (Línea " + AnalizadorLexico.LINEA + ")"); }
             | if '(' condicion ')' then cuerpo_if else cuerpo_else endif ';'     { sintactico.agregarAnalisis("Se reconoció una sentencia if. (Línea " + AnalizadorLexico.LINEA + ")"); }
             | if condicion error                                                 { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): la condición de if debe estar entre paréntesis."); }
             | if '(' condicion  then error                                       { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
             | if '(' condicion ')' cuerpo_if error                               { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }
             | if '(' condicion ')' then cuerpo_if error ';'                      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cierre de endif."); }
             | if '(' condicion ')' then cuerpo_if endif error                    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de endif."); }
             | if '(' condicion ')' then cuerpo_if else cuerpo_else error ';'     { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cierre de endif."); }
             | if '(' condicion ')' then cuerpo_if else cuerpo_else endif error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de endIF."); }
             ;

cuerpo_if :  bloque_sentencias_ejecutables
          ;

cuerpo_else : bloque_sentencias_ejecutables
            ;

sentencia_when : when '(' condicion ')' then cuerpo_when ';'
             | when condicion error                                                 { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): la condición de when debe estar entre paréntesis."); }
             | when '(' condicion  then error                                       { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
             | when '(' condicion ')' cuerpo_when error                               { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }

cuerpo_when : bloque_sentencias
            ;

encabezado_for : for '(' asignacion ';' condicion ';' signo id ')'
               | for  asignacion ';' condicion ';' signo id ')' error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir parentisis."); }
               | for '(' asignacion ';' condicion ';' signo id error    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar parentesis."); }
               | for '(' asignacion  condicion ';' signo id ')' error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
               | for '(' asignacion ';' condicion  signo id ')' error  { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
               | for '(' ';' condicion ';' signo id ')' error           { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta asignacion "); }
               | for '(' asignacion ';' ';' signo id ')' error          { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta condicion "); }
               | id ':' for '(' asignacion ';' condicion ';' signo id ')'
               | ':' for '(' asignacion ';' condicion ';' signo id ')' error    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la etiqueta"); }
               | id for '(' asignacion ';' condicion ';' signo id ')' error     { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ':'"); }
               ;


signo : '+'
      | '-'
      ;

cuerpo_for : '{' bloque_sentencias_for '}' ';'
           | bloque_sentencias_for '}' ';'  error    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta abrir llave "); }
           | '{' bloque_sentencias_for ';'  error    { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta cerrar llave "); }
           | '{' bloque_sentencias_for '}'  error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
           | '{' bloque_sentencias_for '}' else cte ';'
           | '{' bloque_sentencias_for '}' else ';' error  { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta la constante "); }
           | '{' bloque_sentencias_for '}' cte ';'  error   { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta el else. "); }
           ;

sentencias_for : sentencias_ejecutables
               | sentencia_break
               | sentencia_continue
               | sentencias_declarativas error{ sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias declarativas adentro del for"); }
               ;

expresion_for : encabezado_for cuerpo_for
              ;


sentencia_break : break ';'
                | break cte
                | break error  { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de break."); }
                ;

sentencia_continue : continue ';'
                   | continue error  { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de continue."); }
                   | continue ':' id ';'
                   | continue ':' ';' error     { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta etiqueta"); }
                   | continue id ';' error      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ':'."); }
                   | continue ':' id error      { sintactico.addErrorSintactico("ERROR SINTÁCTICO (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
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


