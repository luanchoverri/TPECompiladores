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

//TODO listo
programa : encabezado_prog bloque_sentencias {$$ = new ParserVal(sintactico.crearNodoControl("raiz", $2)); sintactico.setRaiz($$); }
	 ;


encabezado_prog : id
                ;
//TODO listo
bloque_sentencias : bloque_sentencias '{' sentencia '}'{$$ = new ParserVal(sintactico.crearNodoControl("primera_sentencia", $3));}
		  | '{' sentencia '}' {$$ = new ParserVal(sintactico.crearNodoControl("primera_sentencia", $2));}
		  | '{' sentencia    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar el bloque.");}
		  |  sentencia '}'   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque.");}
		  ;

declaracion_const : Const lista_de_asignacion_const ';' { sintactico.addAnalisis("Se reconoció una declaración de CONSTANTE. (Línea " + AnalizadorLexico.LINEA + ")");
							  $$ = new ParserVal(sintactico.crearNodoControl("lista_ctes", $2));
							}
                  | Const lista_de_asignacion_const error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; al final de la declaracion de constantes.");}
                  | Const ';'                           error { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): No se reconoce una lista de constantes.");}
                  ;

lista_de_asignacion_const : decl_const {$$ = new ParserVal(sintactico.crearNodo("declaracion_constante", $1, null));} //TODO fijarse nombre
                          | lista_de_asignacion_const ',' decl_const {$$ = new ParserVal(sintactico.crearNodo("declaracion_constante", $3, $1));}
                          ;

// TODO falcha inferir el tipo
decl_const : id op_asignacion cte {$$ = new ParserVal(sintactico.crearNodo("=:", new ParserVal(sintactico.crearHoja($1.ival)), new ParserVal(sintactico.crearHoja($3.ival))));} //TODO inferir el tipo de la cte
           | id op_asignacion error  { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta constante luego de la asignacion.");}
           | id cte  error           { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta el operador asignacion luego del identificador.");}
           | id        error         { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta la asignacion luego del identificador.");}
           ;

// TODO listo
bloque_sentencias_For : sentencias_For {$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
                      | bloque_sentencias_For sentencias_For {ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
                                                              $$ = modificado;}
                      ;


// TODO listo
sentencia : declarativas {$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
           | ejecutables {$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
           | sentencia declarativas {
							ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
							$$ = modificado;
						}
           | sentencia ejecutables {
							ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
							$$ = modificado;
						}
           ;


declarativas : tipo lista_de_variables ';'        { String type = $1.sval;
						   sintactico.addAnalisis("Se reconoció declaraciónes de variable de tipo " + type + ". (Línea " + AnalizadorLexico.LINEA + ")");
						   sintactico.completarConTipos(type);
						   }
             | lista_de_variables ';'   error    { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el tipo de variable");
             					   sintactico.addAnalisis("Se reconoció declaraciónes de variable SIN TIPO. (Línea " + (AnalizadorLexico.LINEA-1) + ")");
             					   sintactico.vaciarListaVariables();
             					 }
             | declaracion_func
             | declaracion_const
             | sentencia_when
             ;

// TODO listo
bloque_ejecutables: bloque_ejecutables ejecutables {
							ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
							$$ = modificado;
						}
		  | ejecutables {$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
		  ;

ejecutables : asignacion
            | salida
            | sentencia_If
            | expresion_For
            | invocacion_funcion
            | sentencia_BREAK error	{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
            | sentencia_CONTINUE error	{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }


// TODO Tipo y Uso LISTO
lista_de_variables : id lista_de_variables	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIficadores.");
						 sintactico.addListaVariables($1.ival);
						 sintactico.setUso("var", $1.ival);
						}
                   | id ',' lista_de_variables    {
                   				   sintactico.addListaVariables($1.ival);
                   				   sintactico.setUso("var", $1.ival);}
                   | id				  {
                   				  sintactico.addListaVariables($1.ival);
                                                  sintactico.setUso("var", $1.ival);}

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
cola_func: ')' asig_fun '{' cuerpo_fun '}' { sintactico.addAnalisis("Se reconoce TIPO funcion (Línea " + AnalizadorLexico.LINEA + ")");
 						$$ = $4;}
	 ;


declaracion_func : encabezado_func  parametro  		   cola_func {$$ = $3;}
                 | encabezado_func parametro ',' parametro cola_func
                 | encabezado_func parametro ',' parametro ',' parametro cola_func { sintactico.addErrorSintactico("SyntaxError. DECLARACION_FUN(Línea " + AnalizadorLexico.LINEA + "): no se permiten mas de 2 parametros "); }
                 | encabezado_func  			   cola_func
                 ;

ret_fun:   Return '(' expresion ')'  ';'	 { sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") ");
						   $$ = new ParserVal(sintactico.crearNodoControl("return",$3));}
        |  Return  	 expresion ')'  ';' error	 { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
        |  Return '(' expresion   	';' error	 { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
        |  Return  	 expresion   	';' error	 { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
        |  Return '(' expresion ')'  error 	 { sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
        ;

cuerpo_fun: bloque_sentencias_funcion
	  ;

ejecutables_funcion: asignacion
		   | sentencia_if_funcion
		   | salida
		   | invocacion_funcion
		   | sentencia_for_funcion
		   | ret_fun
		   | sentencia_BREAK error	{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
		   | sentencia_CONTINUE error	{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }
		   ;

// TODO listo
bloque_sentencias_ejecutables_funcion: bloque_sentencias_ejecutables_funcion ejecutables_funcion {
													ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
													$$ = modificado;
											 	}
				     | ejecutables_funcion {$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
				     ;
// TODO listo
bloque_sentencias_funcion: bloque_sentencias_funcion ejecutables_funcion {
										ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
										$$ = modificado;
									}
			 | bloque_sentencias_funcion declarativas {
									ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
									$$ = modificado;
								}
			 | ejecutables_funcion {$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
			 | declarativas {$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
			 ;







op_asignacion : opasignacion    { $$.sval = new String("=:"); }
	      | ':''=' 		{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
              | '=' 		{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
              ;

// TODO listo
asignacion : id op_asignacion expresion ';' {	ParserVal identificador = new ParserVal(sintactico.crearHoja($1.ival));
						$$ = new ParserVal(sintactico.crearNodo("=:", identificador , $3));
					   }
           | id op_asignacion expresion  error { sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }
           | id op_asignacion for_else_cte';' {$$ = new ParserVal(sintactico.crearNodo("=:", $1, $3));}

           ;

for_else_cte : expresion_For Else cte {$$ = new ParserVal(sintactico.crearNodo("else", $1, $3));} //TODO Aca tambien nodo de control???
	     | expresion_For error  {sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
	     ;

salida : out '(' cadena ')' ';'		{$$ = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja($3.ival))));}
       | out '(' cadena ')' error   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
       | out '(' cadena error ';'   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
       | out cadena error ';'       { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
       | '(' cadena error             { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
       | out '(' ')' error ';'      { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
       ;

// ------------------------------------------- SENTENCIAS IF ---------------------------------------------------------
// TODO listo
sentencia_If : If condicion_if cuerpo_If end_if ';'                      { 	$$ = new ParserVal(sintactico.crearNodo("if",$2,$3));
										sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
             | If condicion_if then cuerpo_If end_if error   { sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
             | If condicion_if then cuerpo_If 			     error    { sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
             ;

// TODO listo
condicion_if : '(' expresion_relacional ')'	{$$ = new ParserVal(sintactico.crearNodoControl("cond",$2));}
	     | expresion_relacional ')'		{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
	     | '(' expresion_relacional 	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
	     | expresion_relacional 		{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
             ;

// TODO listo REVISAR ERRORES
cuerpo_If: cuerpo_Then cuerpo_Else {$$ = new ParserVal(sintactico.crearNodo("cuerpo", $1, $2));}
	| cuerpo_Then {$$ = new ParserVal(sintactico.crearNodo("cuerpo", $1, null));}
	| cuerpo_Else { sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
	;

// TODO listo REVISAR ERRORES
cuerpo_Then : then '{' bloque_ejecutables '}' {$$ = new ParserVal(sintactico.crearNodoControl("then", $3));}
	  |   then ejecutables {$$ = new ParserVal(sintactico.crearNodoControl("then", $2));}
          ;

// TODO listo
cuerpo_Else : Else '{' bloque_ejecutables'}' {$$ = new ParserVal(sintactico.crearNodoControl("else", $3));}
	    | Else ejecutables {$$ = new ParserVal(sintactico.crearNodoControl("else", $2));}
	    ;

sentencia_if_for : If condicion_if cuerpo_If_for end_if ';'                      { sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 										   $$ = new ParserVal(sintactico.crearNodo("if",$2,$3));}
             | If condicion_if cuerpo_If_for end_if error   { sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
             | If condicion_if cuerpo_If_for error    { sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
             ;

cuerpo_If_for :  cuerpo_then_for cuerpo_Else_for {$$ = new ParserVal(sintactico.crearNodo("cuerpo",$1,$2));}
	  |   cuerpo_then_for {$$ = new ParserVal(sintactico.crearNodo("cuerpo",$1,null));}
	  | cuerpo_Else_for error {sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
          ;

cuerpo_then_for : then '{' bloque_sentencias_For'}' {$$ = new ParserVal(sintactico.crearNodoControl("then",$3));}
	    | then sentencias_For {$$ = new ParserVal(sintactico.crearNodoControl("then",$2));}
            ;

cuerpo_Else_for : Else '{' bloque_sentencias_For'}' {$$ = new ParserVal(sintactico.crearNodoControl("else",$3));}
	    | Else sentencias_For {$$ = new ParserVal(sintactico.crearNodoControl("else",$2));}
            ;

sentencia_if_funcion : If condicion_if cuerpo_If_funcion end_if ';'                      { sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
 											   $$ = new ParserVal(sintactico.crearNodo("if",$2,$3));}
             	     | If condicion_if cuerpo_If_funcion end_if error   { sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
             	     | If condicion_if cuerpo_If_funcion error    { sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
                     ;

cuerpo_If_funcion :  cuerpo_then_funcion cuerpo_Else_funcion {$$ = new ParserVal(sintactico.crearNodo("cuerpo",$1,$2));}
		  |  cuerpo_then_funcion {$$ = new ParserVal(sintactico.crearNodo("cuerpo",$1,null));}
		  | cuerpo_Else_funcion error {sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
		  ;

cuerpo_then_funcion : then '{' bloque_sentencias_ejecutables_funcion'}' {$$ = new ParserVal(sintactico.crearNodoControl("then",$3));}
		    | then ejecutables_funcion {$$ = new ParserVal(sintactico.crearNodoControl("then",$2));}
		    ;

cuerpo_Else_funcion : Else '{' bloque_sentencias_ejecutables_funcion'}' {$$ = new ParserVal(sintactico.crearNodoControl("else",$3));}
		    | Else ejecutables_funcion {$$ = new ParserVal(sintactico.crearNodoControl("else",$2));}
		    ;

sentencia_if_for_funcion : If condicion_if cuerpo_If_for_funcion end_if ';'                      { sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
												  $$ = new ParserVal(sintactico.crearNodo("if",$2,$3));}
             	     | If condicion_if cuerpo_If_for_funcion end_if error   { sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
             	     | If condicion_if cuerpo_If_for_funcion error    { sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
                     ;

cuerpo_If_for_funcion :  then_if_for_funcion else_if_for_funcion {$$ = new ParserVal(sintactico.crearNodo("cuerpo",$1,$2));}
		      | then_if_for_funcion {$$ = new ParserVal(sintactico.crearNodo("cuerpo",$1,null));}
		      | else_if_for_funcion error {sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
		      ;

then_if_for_funcion: then '{' bloque_sentencias_For_funcion '}' {$$ = new ParserVal(sintactico.crearNodoControl("then", $3));}
		  | then sentencias_For_funcion {$$ = new ParserVal(sintactico.crearNodoControl("then", $2));}
		  ;

else_if_for_funcion : Else '{' bloque_sentencias_For_funcion '}' {$$ = new ParserVal(sintactico.crearNodoControl("else", $3));}
                        | Else sentencias_For_funcion {$$ = new ParserVal(sintactico.crearNodoControl("else", $2));}
                        ;
// ------------------------------------------- FIN SENTENCIAS IF ---------------------------------------------------------

// ------------------------------------------- SENTENCIAS WHEN ---------------------------------------------------------
// TODO listo
sentencia_when : when '(' condicion_for ')' cuerpo_when ';'         { sintactico.addAnalisis("Se reconocio una sentencia when");
									$$ = new ParserVal(sintactico.crearNodo("when", $3, $5));}
             | when condicion_for ')' cuerpo_when ';' error           { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
             | when '(' condicion_for  cuerpo_when';' error                        { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
             ;

// TODO listo
cuerpo_when : then '{' sentencia '}'	{$$ = new ParserVal(sintactico.crearNodoControl("then",$3));}
            | then '{' sentencia error       { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
            | then  sentencia '}' error      { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
            ;
// ------------------------------------------- FIN SENTENCIAS WHEN ---------------------------------------------------------

// ------------------------------------------- SENTENCIAS FOR ---------------------------------------------------------

// TODO REVISAR ERRORES
encabezado_For : For '(' detalles_for ')' 	cola_For 	  { sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
									$$ = new ParserVal(sintactico.crearNodo("For",$3,$5));
									}
	       | For     id op_asignacion cte ';' condicion_for ';' signo id ')' 	cola_For 	error  { sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
	       | For     id op_asignacion cte ';' condicion_for ';' signo id 		cola_For	error  { sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
	       | For '(' id op_asignacion cte ':'   condicion_for ':' signo id ')' 	cola_For	error  { sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
               | id ':' For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For	{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
		;

 // TODO listo
detalles_for: asignacion_for ';' condicion_operacion_for {$$ = new ParserVal(sintactico.crearNodo("encabezado for",$1, $3));}
		;
// TODO listo
condicion_operacion_for: condicion_for ';' operacion_for {$$ = new ParserVal(sintactico.crearNodo("condicion y operacion for",  $1, $3));}

// TODO listo (lo del arbol sintactico)
condicion_for :  id comparador cte  {	ParserVal identificador = new ParserVal(sintactico.crearHoja($1.ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja($3.ival));
					$$ = new ParserVal(sintactico.crearNodoControl("cond", new ParserVal(sintactico.crearNodo($2.sval,identificador,constante))));}// para en un futuro expandirla y coparar con expresion
	      ;
// TODO listo
cola_For : '{' bloque_sentencias_For '}' ';'  {$$ = new ParserVal(sintactico.crearNodoControl("cuerpoFor",$2));}
	|  sentencias_For  {$$ = new ParserVal(sintactico.crearNodoControl("cuerpoFor",$1));}
	;

signo : '+' {$$.sval = new String("+");}
      | '-' {$$.sval = new String("-");}
      ;


sentencias_For : asignacion
		| salida
		| expresion_For
		| sentencia_if_for
		| invocacion_funcion
               | sentencia_BREAK
               | sentencia_CONTINUE
               | declarativas error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
               | ret_fun error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
               ;

expresion_For : encabezado_For
              ;

cola_For_funcion : '{' bloque_sentencias_For_funcion '}' ';' {$$ = new ParserVal(sintactico.crearNodoControl("cuerpoFor",$2));}
	|  sentencias_For_funcion {$$ = new ParserVal(sintactico.crearNodoControl("cuerpoFor",$1));}
	;


sentencia_for_funcion :  For '(' detalles_for ')' 	cola_For_funcion 	  { sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
                        									$$ = new ParserVal(sintactico.crearNodo("For",$3,$5));
										}
		       | For     id op_asignacion cte ';' condicion_for ';' signo id ')' 	cola_For_funcion 	error  { sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
		       | For     id op_asignacion cte ';' condicion_for ';' signo id 		cola_For_funcion	error  { sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
		       | For '(' id op_asignacion cte ':'   condicion_for ':' signo id ')' 	cola_For_funcion	error  { sintactico.addErrorSintactico("SyntaxError. FOR3(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
		       | id ':' For '(' id op_asignacion cte ';' condicion_for ';' signo id ')' cola_For_funcion	{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");}
			;
// TODO listo
asignacion_for: id op_asignacion cte {	ParserVal identificador = new ParserVal(sintactico.crearHoja($1.ival));
					ParserVal constante = new ParserVal(sintactico.crearHoja($3.ival));
					$$ = new ParserVal(sintactico.crearNodoControl("asignacionFor",new ParserVal(sintactico.crearNodo("=:",identificador,constante))));}
	      ;
// TODO listo
operacion_for: signo id	    {$$ = new ParserVal(sintactico.crearNodoControl("operacionFor",new ParserVal(sintactico.crearNodo($1.sval,new ParserVal(sintactico.crearHoja($2.ival)),null))));}
		;

sentencias_For_funcion : asignacion
	       | salida
	       | sentencia_for_funcion
	       | ret_fun
	       | invocacion_funcion
               | sentencia_BREAK
               | sentencia_CONTINUE
               | sentencia_if_for_funcion
               | declarativas error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
               ;

// TODO listo
bloque_sentencias_For_funcion : sentencias_For_funcion {$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
                      | bloque_sentencias_For_funcion sentencias_For_funcion {
										ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
										$$ = modificado;
									}
                      ;

// ------------------------------------------- FIN SENTENCIAS FOR ---------------------------------------------------------
// TODO listo
sentencia_BREAK : BREAK ';'	{ sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");
				$$ = new ParserVal(sintactico.crearNodoControl("break",null));}
                | BREAK cte ';'	{ sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");
                		$$ = new ParserVal(sintactico.crearNodoControl("break", new ParserVal(sintactico.crearHoja($2.ival))));}
                | BREAK error  { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
                ;
// TODO listo
sentencia_CONTINUE : CONTINUE ';'		{ sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");
						$$ = new ParserVal(sintactico.crearNodoControl("continue",null));}
                   | CONTINUE ':' id ';'	{ sintactico.addAnalisis("Se reconocio una sentencia continue con etiquetado(Línea " + AnalizadorLexico.LINEA + ")");
                   				$$ = new ParserVal(sintactico.crearNodoControl("continue", new ParserVal(sintactico.crearHoja($3.ival))));}
                   | CONTINUE id ';' error   { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
                   | CONTINUE error           { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
                   ;

invocacion_funcion: id '(' list_parametros ')' ';' { $$ = new ParserVal(sintactico.crearNodoFunc($1.ival, $3));}
		  | id '(' ')' ';' { $$ = new ParserVal(sintactico.crearNodoFunc($1.ival, null));}
		  ;

list_parametros: factor ',' factor {
						$$ = new ParserVal(sintactico.crearNodo("param", $1, $3));
					}
		      | factor  {$$ = new ParserVal(sintactico.crearNodo("param", $1, null));}
		      ;

// TODO listo
expresion_relacional : expresion comparador expresion { $$ = new ParserVal(sintactico.crearNodo($2.sval, $1, $3));}
                     ;

// TODO listo
expresion : expresion signo termino {$$ = new ParserVal(sintactico.crearNodo($2.sval, $1, $3)); }
          | termino
          ;

// TODO listo
termino : termino '*' factor {$$ = new ParserVal(sintactico.crearNodo("*",$1,$3));}
        | termino '/' factor {$$ = new ParserVal(sintactico.crearNodo("/",$1,$3));}
        | factor
        ;

// TODO falta chquear ambito
factor : id  { $$ = new ParserVal(sintactico.crearHoja($1.ival));}// TODO ACA SE CHEQUEA AMBITO
       | cte      {
                        sintactico.setTipo(sintactico.getTipoFromTS($1.ival));
                        if (sintactico.getTipo().equals("LONG"))
                             sintactico.verificarRangoEnteroLargo($1.ival);
                        $$ = new ParserVal(sintactico.crearHoja($1.ival));
                  }
       | '-' cte    {
                        sintactico.setNegativoTablaSimb($2.ival);
                        $$ = new ParserVal(sintactico.crearHoja($1.ival));
                    }
       ;

//TODO listo
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

