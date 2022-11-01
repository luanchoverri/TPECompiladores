%{

package AnalizadorSintactico;

import java.util.Vector;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorLexico.Atributo;

%}

%token id cte If then Else end_if out fun Return BREAK i32 when For CONTINUE f32 cadena menorigual mayorigual distinto opasignacion Const
%start programa

%%


programa : encabezado_prog bloque_sentencias
	 ;


encabezado_prog : id
                ;

bloque_sentencias : bloque_sentencias '{' sentencia '}'
		  | '{' sentencia '}'
		  | '{' sentencia    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar el bloque.");}
		  |  sentencia '}'   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque.");}
		  ;

declaracion_const : Const lista_de_asignacion_const ';' { sintactico.addAnalisis("Se reconoció una declaración de CONSTANTE. (Línea " + AnalizadorLexico.LINEA + ")"); }
                  | Const lista_de_asignacion_const error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; al final de la declaracion de constantes.");}
                  | Const ';'                           error { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): No se reconoce una lista de constantes.");}
                  ;

lista_de_asignacion_const : decl_const
                          | lista_de_asignacion_const ',' decl_const
                          ;

decl_const : id op_asignacion cte
           | id op_asignacion error  { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta constante luego de la asignacion.");}
           | id cte  error           { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta el operador asignacion luego del identificador.");}
           | id        error         { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta la asignacion luego del identificador.");}
           ;

bloque_sentencias_For : sentencias_For
                      | bloque_sentencias_For sentencias_For
                      ;



sentencia : declarativas
           | ejecutables
           | sentencia declarativas
           | sentencia ejecutables
           ;


declarativas : tipo lista_de_variables ';'        { sintactico.addAnalisis("Se reconoció una declaración de variable. (Línea " + AnalizadorLexico.LINEA + ")"); }
             | tipo lista_de_variables error      { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta ';' al final de la declaración de variable."); }
             | tipo error                         { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el identificador de variable"); }
             | declaracion_func
             | declaracion_const
             ;

ejecutables : asignacion
            | salida
            | sentencia_If
            | expresion_For
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

ret_fun:   Return '(' expresion ')'  ';'	 { sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") "); }
        |  Return  	 expresion ')'  ';' 	 { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
        |  Return '(' expresion   	';'	 { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
        |  Return  	 expresion   	';' 	 { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
        |  Return '(' expresion ')'  error 	 { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
        | error 				 { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN5(Línea " + AnalizadorLexico.LINEA + "): no se reconoce return"); }
        ;

cuerpo_fun: sentencia ret_fun
	  | ret_fun
	  ;


op_asignacion : opasignacion    { $$.sval = new String("=:"); }
	      | ':''=' 		{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
              | '=' 		{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
              ;


asignacion : id op_asignacion expresion ';'
           | id op_asignacion expresion  error { sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
           | id op_asignacion expresion_For Else cte ';'
           | id op_asignacion expresion_For error  { sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  "); }
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

cuerpo_If :  '{' bloque_sentencias_For '}'
	  |   sentencias_For
          ;

cuerpo_Else : '{' bloque_sentencias_For'}'
	    | sentencias_For
            ;

sentencia_when : when '(' condicion_for ')' then cuerpo_when ';'         { sintactico.addAnalisis("Se reconocio una sentencia when");}
             | when condicion_for ')' then cuerpo_when ';' error           { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
             | when '(' condicion_for  then cuerpo_when';' error                        { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
             | when '(' condicion_for ')' cuerpo_when ';' error              { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta la declaración de then."); }
             ;

cuerpo_when : '{' sentencia '}'
            | '{' sentencia error       { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
            |  sentencia '}' error      { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
            ;

cola_For : '{' bloque_sentencias_For '}' ';'
	|  sentencias_For
	;


encabezado_For : For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' 	cola_For 	  { sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")"); }
	       | For     id op_asignacion cte ';' condicion_for ';' signo id ')' 	cola_For 	error  { sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
	       | For     id op_asignacion cte ';' condicion_for ';' signo id 		cola_For	error  { sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
	       | For '(' id op_asignacion cte ':'   condicion_for ':' signo id ')' 	cola_For	error  { sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
               | id ':' For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For

               ;

condicion_for :  id comparador cte  // para en un futuro expandirla y coparar con expresion
	      ;


signo : '+'
      | '-'
      ;


sentencias_For : ejecutables
               | sentencia_BREAK
               | sentencia_CONTINUE
               | declarativas error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
               ;

expresion_For : encabezado_For
              ;


sentencia_BREAK : BREAK ';'
                | BREAK cte ';'
                | BREAK error  { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
                ;

sentencia_CONTINUE : CONTINUE ';'
                   | CONTINUE ':' id ';'
                   | CONTINUE id ';' error   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
                   | CONTINUE error           { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' "); }
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
       | cte      {
                        sintactico.setTipo(sintactico.getTipoFromTS($1.ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo($1.ival);
                  }
       | '-' cte    {
                        sintactico.setNegativoTablaSimb($2.ival);
                    }
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


