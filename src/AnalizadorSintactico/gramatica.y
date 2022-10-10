%{

package AnalizadorSintactico;

import java.util.Vector;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorLexico.Atributo;

%}

%token id cte If then Else end_if out fun Return BREAK i32 when FOR CONTINUE f32 cadena menorigual mayorigual distinto opasignacion
%start programa

%%

// TODO CONST
// TODO CORREGIR WHEN
// TODO CORREGIR Else DESP DEL FOR PARA QUE SI O SI VENGA DE UN ETIQUETADO


programa : encabezado_prog bloque_sentencias
	 ;


encabezado_prog : id
                ;

bloque_sentencias : bloque_sentencias '{' sentencia '}'
		  | '{' sentencia '}'
		  | '{' sentencia    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar el bloque.");}
		  |  sentencia '}'   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque.");}
		  ;



bloque_sentencias_FOR : sentencias_FOR
                      | bloque_sentencias_FOR sentencias_FOR
                      ;



sentencia : declarativas
           | ejecutables
           | sentencia declarativas
           | sentencia ejecutables
           ;


declarativas : tipo lista_de_variables ';'        { sintactico.addAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
             | tipo lista_de_variables error      { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta ';' al final de la declaración de variable."); }
             | tipo error                         { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el nombre de la variable"); }
             | declaracion_func
             ;

ejecutables : asignacion
            | salida
            | sentencia_If
            | expresion_FOR
            | sentencia_when
            ;

lista_de_variables : id
                   | lista_de_variables ',' id
                   | lista_de_variables id error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIficadores."); }
                   ;

encabezado_func : fun id '('	     { sintactico.addAnalisis( "Se reconocio declaracion de funcion (Línea " + AnalizadorLexico.LINEA + ")" ); }
                | fun   '(' error   { sintactico.addErrorSintactico("SyntaxError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): problema en la definición de la función."); }
                ;

parametro : tipo id
	  | 	id  error { sintactico.addErrorSintactico("SyntaxError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
	  ;


asig_fun: ':' tipo
	|	 { sintactico.addErrorSintactico("SyntaxError. COLA_FUN(Línea " + AnalizadorLexico.LINEA + "): falta TIPO "); }
	;
cola_func: ')' asig_fun '{' cuerpo_fun '}' { sintactico.addAnalisis("Se reconoce TIPO funcion (Línea " + AnalizadorLexico.LINEA + ")"); }
	 ;


declaracion_func : encabezado_func  parametro  		   cola_func
                 | encabezado_func parametro ',' parametro cola_func
                 | encabezado_func parametro ',' parametro ',' parametro cola_func { sintactico.addErrorSintactico("SyntaxError. DECLARACION_FUN(Línea " + AnalizadorLexico.LINEA + "): no se permiten mas de 2 parametros "); }
                 | encabezado_func  			   cola_func
                 ;

cuerpo_fun: sentencia Return '(' expresion ')'  ';' { sintactico.addAnalisis("SyntaxError. RETURN_FUN(Línea " + AnalizadorLexico.LINEA + "): reconoce retorno de funcion"); }
	  | sentencia Return  	 expresion ')'  ';' { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
	  | sentencia Return '(' expresion   	';' { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
	  | sentencia Return  	 expresion   	';' { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
	  | sentencia Return '(' expresion ')'  error { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }

op_asignacion : opasignacion    { $$.sval = new String("=:"); }
	      | ':''=' 		{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
              | '=' 		{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
              ;

asignacion : id op_asignacion expresion ';'
           | id op_asignacion expresion  error { sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
           | id expresion_FOR
           ;

salida : out '(' cadena ')' ';'
       | out '(' cadena ')' error   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
       | out '(' cadena error ';'   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
       | out cadena error ';'       { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
       | '(' cadena error             { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
       | out '(' ')' error ';'      { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
       ;


sentencia_If : If condicion_if then cuerpo_If end_if ';'                      { sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
             | If condicion_if then cuerpo_If Else cuerpo_Else end_if ';'     { sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
             | If condicion_if then cuerpo_If Else cuerpo_Else end_if error   { sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
             | If condicion_if then cuerpo_If 			     error    { sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
             | If condicion_if then cuerpo_If Else cuerpo_Else error ';'      { sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
             | If condicion_if 	    cuerpo_If error                           { sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }


             ;

cuerpo_If :  '{' bloque_sentencias_FOR '}'
	  |   sentencias_FOR
          ;

cuerpo_Else : '{' bloque_sentencias_FOR'}'
	    | sentencias_FOR
            ;

sentencia_when : when '(' condicion_for ')' then cuerpo_when ';'
             | when condicion_for                                        { sintactico.addErrorSintactico( " SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): la condición de when debe estar entre paréntesis." ); }
             | when '(' condicion_for  then error                        { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta paréntesis de cierre en la lista de parámetros."); }
             | when '(' condicion_for ')' cuerpo_when error              { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la declaración de then."); }

/* CORREGIR WHEN */
cuerpo_when : bloque_sentencias
            ;

encabezado_FOR : 	FOR '(' asignacion ';' condicion_for ';' signo id ')'
               | 	FOR     asignacion ';' condicion_for ';' signo id ')' error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir parentisis."); }
               | 	FOR '(' asignacion ';' condicion_for ';' signo id     error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar parentesis."); }
               | 	FOR '(' asignacion     condicion_for ';' signo id ')' error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
               | 	FOR '(' asignacion ';' condicion_for  	 signo id ')' error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
               | 	FOR '(' 	   ';' condicion_for ';' signo id ')' error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta asignacion "); }
               | 	FOR '(' asignacion ';'		     ';' signo id ')' error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta condicion "); }
               | id ':' FOR '(' asignacion ';' condicion_for ';' signo id ')'
               |    ':' FOR '(' asignacion ';' condicion_for ';' signo id ')' error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la etiqueta"); }
               | id 	FOR '(' asignacion ';' condicion_for ';' signo id ')' error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'"); }
               ;

condicion_for :  id comparador cte
	      ;

signo : '+'
      | '-'
      ;

cuerpo_FOR : '{' bloque_sentencias_FOR '}' ';'
           | 	 bloque_sentencias_FOR '}' ';'  error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir llave "); }
           | '{' bloque_sentencias_FOR 	   ';'  error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar llave "); }
           | '{' bloque_sentencias_FOR '}'	error   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
       	/* REVISAR PORQUE EL Else CORRESPONDE SOLO A DEVOLVER UN VALOR SOLO SI VIENE DE LA ETIQUETA */
           | '{' bloque_sentencias_FOR '}' 	Else cte ';'
           | '{' bloque_sentencias_FOR '}' 	Else ';' error  { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta la constante "); }
           | '{' bloque_sentencias_FOR '}' 	cte ';'  error   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta el Else. "); }
           ;

sentencias_FOR : ejecutables
               | sentencia_BREAK
               | sentencia_CONTINUE
               | declarativas error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias declarativas adentro del FOR"); }
               ;

expresion_FOR : encabezado_FOR cuerpo_FOR
	      | encabezado_FOR sentencias_FOR  ';'
              ;


sentencia_BREAK : BREAK ';'
                | BREAK cte
                | BREAK error  { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
                ;

sentencia_CONTINUE : CONTINUE ';'
                   | CONTINUE error  { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de CONTINUE."); }
                   | CONTINUE ':' id ';'
                   | CONTINUE ':' ';' error     { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta etiqueta"); }
                   | CONTINUE id ';' error      { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'."); }
                   | CONTINUE ':' id error      { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
                   ;

condicion_if : '(' expresion_relacional ')'
	     | expresion_relacional ')'		{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
	     | '(' expresion_relacional 	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
	     | expresion_relacional 		{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
             ;

expresion_relacional : expresion comparador expresion
                     ;

expresion : expresion signo termino
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


