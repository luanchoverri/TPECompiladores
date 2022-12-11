%{

package AnalizadorSintactico;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;
import ArbolSintactico.NodoHijo;
import ArbolSintactico.Nodo;


%}

%token id cte If then Else end_if out fun Return BREAK i32 when For CONTINUE f32 cadena menorigual mayorigual distinto opasignacion Const
%start programa

%%

//TODO listo
programa : encabezado_prog bloque_sentencias	{$$ = new ParserVal(sintactico.crearNodoControl("raiz", $2)); sintactico.setRaiz($$); }
	 ;


encabezado_prog : id
                ;
//TODO listo
bloque_sentencias : bloque_sentencias '{' sentencia '}'	{$$ = $3;}
		  | '{' sentencia '}' 			{$$ = $2;}
		  | '{' sentencia    			{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar el bloque.");}
		  |  sentencia '}'   			{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir el bloque.");}
		  ;

declaracion_const : Const lista_de_asignacion_const ';'		{ sintactico.addAnalisis("Se reconoció una declaración de CONSTANTE. (Línea " + AnalizadorLexico.LINEA + ")");
							  	  $$ = new ParserVal(sintactico.crearNodoControl("lista_ctes", $2));}
                  | Const lista_de_asignacion_const error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ; al final de la declaracion de constantes.");}
                  | Const ';'                           error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): No se reconoce una lista de constantes.");}
                  ;

lista_de_asignacion_const : decl_const					{$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));} //TODO fijarse nombre
                          | lista_de_asignacion_const ',' decl_const	{ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $3, null));
                                                                         $$ = modificado;}
                          ;

// TODO listo el tipo y uso de la cte inferido en el id de la variable
decl_const : id op_asignacion cte	{
						int existente = enAmbito($1);
						if (existente < 0) {
							int i = $1.ival;
							sintactico.setTipoEnIndex(sintactico.getTipoFromTS($3.ival), i);
							sintactico.setUsoEnIndex("const", i);
							sintactico.setLexemaEnIndex($1.ival, "@"+this.ambito);
							$$ = new ParserVal(sintactico.crearNodo("=:", new ParserVal(sintactico.crearHoja($1.ival)), new ParserVal(sintactico.crearHoja($3.ival))));
						} else {
							sintactico.addErrorSintactico("SemanticError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
						}

					}
           | id op_asignacion error  { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta constante luego de la asignacion.");}
           | id cte  error           { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta el operador asignacion luego del identificador.");}
           | id        error         { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): Falta la asignacion luego del identificador.");}
           ;

// TODO listo
bloq_sentencias_For : sentencias_For 			     	{$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
                    | bloq_sentencias_For sentencias_For	{$$ = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));}
                    ;


// TODO listo
sentencia : declarativas 		{$$ = new ParserVal(sintactico.crearNodo("declarativa", $1, null));}
          | ejecutables  		{$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
          | sentencia declarativas	{$$ = sintactico.modificarHijo($1, sintactico.crearNodo("declarativa", $2, null));}
          | sentencia ejecutables	{$$ = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));}
          ;


declarativas : tipo lista_de_variables ';'        {
							String type = $1.sval;
						 	sintactico.completarConTipos(type);
						 	sintactico.addAnalisis("Se reconoció declaraciónes de variable de tipo " + type + ". (Línea " + AnalizadorLexico.LINEA + ")");
						  }
             | lista_de_variables ';'   error     {
             						sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): falta el tipo de variable");
             					  	sintactico.addAnalisis("Se reconoció declaraciónes de variable SIN TIPO. (Línea " + (AnalizadorLexico.LINEA-1) + ")");
             					   	sintactico.vaciarListaVariables();
             					  }
             | declaracion_func
             | declaracion_const
             | sentencia_when
             ;

// TODO listo
bloque_ejecutables: bloque_ejecutables ejecutables  {
							ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
							$$ = modificado;
						     }
		  | ejecutables 		    {
							$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));
						     }
		  ;

ejecutables : asignacion
            | salida
            | sentencia_If
            | expresion_For
            | invocacion_funcion
            | sentencia_BREAK error	{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias break fuera de una sentencia for "); }
            | sentencia_CONTINUE error	{ sintactico.addErrorSintactico("SyntaxError. If3 (Línea " + AnalizadorLexico.LINEA + "): no se permiten sentencias continue fuera de una sentencia for "); }


// TODO Tipo y Uso LISTO
lista_de_variables : id lista_de_variables	{
							sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta una ',' entre identIficadores.");
						 	int existente = enAmbito($1);
							if (existente < 0) {
								sintactico.setLexemaEnIndex($1.ival, "@"+this.ambito);
								sintactico.addListaVariables($1.ival);
								sintactico.setUsoEnIndex("var", $1.ival);
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
							}
						}
                   | id ',' lista_de_variables	{
							int existente = enAmbito($1);
							if (existente < 0) {
								sintactico.setLexemaEnIndex($1.ival, "@"+this.ambito);

								sintactico.addListaVariables($1.ival);
								sintactico.setUsoEnIndex("var", $1.ival);
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
							}
						  }
                   | id				{
                   					int existente = enAmbito($1);
                   					if (existente < 0) {
                   						sintactico.setLexemaEnIndex($1.ival, "@"+this.ambito);

                   						sintactico.addListaVariables($1.ival);
							    	sintactico.setUsoEnIndex("var", $1.ival);
                   					} else {
                   						sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): variable ya declarada.");
                   					}
                   				  }
                   ;



parametro : tipo id	{
				int existente = enAmbito($2);
				if (existente < 0) {
					sintactico.setTipoEnIndex($1.sval, $2.ival);
					sintactico.setLexemaEnIndex($2.ival, "@"+this.ambito);
					sintactico.setUsoEnIndex("param", $2.ival);

					sintactico.addListaVariables($2.ival);

				} else {
					sintactico.addErrorSintactico("SematicError. ENC_FUN/PARAMS (Línea " + AnalizadorLexico.LINEA + "): el identificador ya ha sido utilizado.");
				}
			}

	  | id error 	{ sintactico.addErrorSintactico("SematicError. PARAM(Línea " + AnalizadorLexico.LINEA + "): falta TIPO en parametros."); }
	  ;


asig_fun: ':' tipo	{
				sintactico.setTipoGlobal($2.sval);
			}
	|	 	{
				sintactico.addErrorSintactico("SematicError. ENCAB_FUN(Línea " + AnalizadorLexico.LINEA + "): falta tipo de funcion ");
	 	 		sintactico.addAnalisis("Se reconoce declaracion de funcion pero falta tipo (Línea " + AnalizadorLexico.LINEA + ")");
			}
	;

cola_func: '{' cuerpo_fun '}'	{
					sintactico.addAnalisis("Se reconoce cuerpo de funcion (Línea " + AnalizadorLexico.LINEA + ")");
					this.ambito = borrarAmbito(this.ambito);
 					$$ = $2;
 				}
	 ;

lista_parametros:
		| parametro
		| parametro ',' parametro
		| error	{ sintactico.addAnalisis("Se reconocen mas parametros de los deseados en la funcion (Línea " + AnalizadorLexico.LINEA + ")");}
		;

encab_fun : fun id '('  lista_parametros  ')' asig_fun 		{
								sintactico.addAnalisis( "Se reconocio declaracion de funcion (Línea " + AnalizadorLexico.LINEA + ")" );

								String lexema = sintactico.getEntradaTablaSimb($2.ival).getLexema();

								int existente = enAmbito($2);
								if (existente < 0) { // no existe el id en el ambito
									sintactico.setTipoEnIndex(sintactico.getTipo(), $2.ival);
									sintactico.setLexemaEnIndex($2.ival, "@"+this.ambito);
									sintactico.setUsoEnIndex("func", $2.ival);
									agregarAmbito(lexema);
									sintactico.setUsoParam(sintactico.getEntradaTablaSimb($2.ival).getLexema());
									sintactico.vaciarListaVariables();
									$$ = new ParserVal($2.ival);
								} else {
									sintactico.addErrorSintactico("SematicError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): el identificador ya ha sido utilizado.");
								}

							}
	  | fun    '('  lista_parametros  ')' asig_fun error	{
	 								sintactico.addAnalisis("Se reconoce declaracion de funcion pero falta identificacion (Línea " + AnalizadorLexico.LINEA + ")");
							   		sintactico.addErrorSintactico("SematicError. ENC_FUN (Línea " + AnalizadorLexico.LINEA + "): funcion sin identificar.");
								}
	  ;

declaracion_func :  encab_fun  cola_func {
						Token t = sintactico.getEntradaTablaSimb($1.ival);
						Nodo n = sintactico.crearNodoControl(t.getLexema(), $2);
						n.setTipo(t.getTipo());
						sintactico.agregarArbolFuncion(new ParserVal(n),t.getLexema());
						sintactico.clearTipo();
					}
		 ;


ret_fun :  Return '(' expresion ')'  ';'	 	{ sintactico.addAnalisis("Se reconoce retorno de funcion(Línea " + AnalizadorLexico.LINEA + ") ");
						   	  sintactico.checkRetorno($3, sintactico.getTipo());
						   	  Nodo nodoRetorno = sintactico.crearNodoControl("return",$3);
						   	  Nodo n = (Nodo) $3.obj;
						   	  nodoRetorno.setTipo(n.getTipo());
						   	  $$ = new ParserVal(nodoRetorno);}
        |  Return  	 expresion ')'  ';' error	{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN1 (Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
        |  Return '(' expresion   	';' error	{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN2(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
        |  Return  	 expresion   	';' error	{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN3(Línea " + AnalizadorLexico.LINEA + "): problema en el retorno de la funcion"); }
        |  Return '(' expresion ')'  error 	 	{ sintactico.addErrorSintactico("SyntaxError. RETURN_FUN4(Línea " + AnalizadorLexico.LINEA + "): falta ; "); }
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
bloq_ejecutables_fun : bloq_ejecutables_fun ejecutables_funcion	{
									ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
									$$ = modificado;
								}
		     | ejecutables_funcion 			{$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
		      ;
// TODO listo
bloque_sentencias_funcion: bloque_sentencias_funcion ejecutables_funcion	{
											ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
											$$ = modificado;
										}
			 | bloque_sentencias_funcion declarativas	{
										ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("declarativa", $2, null));
										$$ = modificado;
									}
			 | ejecutables_funcion {$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
			 | declarativas {$$ = new ParserVal(sintactico.crearNodo("declarativa", $1, null));}
			 ;



op_asignacion : opasignacion    { $$.sval = new String("=:"); }
	      | ':''=' 	 error	{ sintactico.addErrorSintactico("SyntaxError. OP1(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
              | '=' 	 error	{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): error en el op de ASIG"); }
              ;

// TODO listo
asignacion : id op_asignacion expresion ';'	{
							System.out.println("ENNNNTRAAA");
							int existente = enAmbito($1);
							if (existente >= 0) {
								ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
								$$ = new ParserVal(sintactico.crearNodo("=:", identificador , $3));
								sintactico.eliminarEntrada($1.ival);
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): variable no declarada.");
							}
					  	}

           | id op_asignacion expresion  error	{ sintactico.addErrorSintactico("SyntaxError. OP(Línea " + (AnalizadorLexico.LINEA) + "): falta ';' luego de la ASIG."); }

           | id op_asignacion for_else_cte ';'	{
           					int existente = enAmbito($1);
						if (existente >= 0) {
							ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
							$$ = new ParserVal(sintactico.crearNodo("=:", identificador , $3));
							sintactico.eliminarEntrada($1.ival);
						} else {
							sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): variable no declarada.");}
						}
	   | id op_asignacion invocacion_funcion {
	   						int existente = enAmbito($1);
							if (existente >= 0) {
								ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
								$$ = new ParserVal(sintactico.crearNodo("=:", identificador , $3));
								sintactico.eliminarEntrada($1.ival);
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): variable no declarada.");
							}
						 }
           ;


for_else_cte : expresion_For Else cte	{ 	Nodo for_else = sintactico.crearNodo("for_else", $1, new ParserVal(sintactico.crearHoja($3.ival)));
						String cteElse = sintactico.getTipoFromTS($3.ival);
						if (cteElse.equals(tipoBreak)) {
							for_else.setTipo(cteElse);
                                                	$$ = new ParserVal(for_else);
						}else{
                                               		sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "):  los tipos en el BREAK/ELSE del FOR no coinciden");
                                                }

					} //TODO Aca tambien nodo de control???
	     | expresion_For error	{ sintactico.addErrorSintactico("SyntaxError. OP2(Línea " + (AnalizadorLexico.LINEA) + "): problema en devolver valor por defecto  ");}
	     ;

salida : out '(' cadena ')' ';'		{
						sintactico.setUsoEnIndex("cadena",$3.ival);
						sintactico.addCadena($3.ival);
						$$ = new ParserVal(sintactico.crearNodoControl("out", new ParserVal(sintactico.crearHoja($3.ival))));}
       | out '(' cadena ')' error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de la impresión de cadena."); }
       | out '(' cadena error ';'	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): cierre erróneo de la lista de parámetros de out."); }
       | out cadena error ';'		{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): los parámetros de out deben estar entre paréntesis."); }
       | '(' cadena error           	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): se esperaba out, se encontró '('."); }
       | out '(' ')' error ';'      	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta declarar una cadena para PRINT."); }
       ;

// ------------------------------------------- SENTENCIAS IF ---------------------------------------------------------
// TODO listo
sentencia_If : If condicion_if cuerpo_If PR_end_if ';'		 {	$$ = new ParserVal(sintactico.crearNodo("if",$2,$3));
									sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")"); }
             | If condicion_if then cuerpo_If PR_end_if error	{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
             | If condicion_if then cuerpo_If error    		{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
             ;

// TODO listo
condicion_if : '(' expresion_relacional ')'	{$$ = new ParserVal(sintactico.crearNodoControl("condicionIf",$2));}
	     | expresion_relacional ')'		{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
	     | '(' expresion_relacional 	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
	     | expresion_relacional 		{ sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta PARENTESIS EN If "); }
             ;

// TODO listo REVISAR ERRORES
cuerpo_If: cuerpo_Then cuerpo_Else	{$$ = new ParserVal(sintactico.crearNodo("cuerpo-if", $1, $2));}
	| cuerpo_Then 			{$$ = new ParserVal(sintactico.crearNodo("cuerpo-if", $1, null));}
	| cuerpo_Else 			{ sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta bloque then."); }
	;

// TODO listo REVISAR ERRORES
cuerpo_Then : PR_then '{' bloque_ejecutables '}'	{$$ = new ParserVal(sintactico.crearNodoControl("then", $3));}
	  |  PR_then ejecutables			{$$ = new ParserVal(sintactico.crearNodoControl("then", $2));}
          ;

// TODO listo
cuerpo_Else : PR_else '{' bloque_ejecutables'}'	{$$ = new ParserVal(sintactico.crearNodoControl("else", $3));}
	    | PR_else ejecutables 			{$$ = new ParserVal(sintactico.crearNodoControl("else", $2));}
	    ;

sentencia_if_for : If condicion_if cuerpo_If_for PR_end_if ';'     { sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
								   $$ = new ParserVal(sintactico.crearNodo("if",$2,$3));}
            	 | If condicion_if cuerpo_If_for PR_end_if error   { sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
             	 | If condicion_if cuerpo_If_for error    	{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
             	 ;

cuerpo_If_for : cuerpo_then_for cuerpo_Else_for	{$$ = new ParserVal(sintactico.crearNodo("cuerpo-if",$1,$2));}
	      | cuerpo_then_for 		{$$ = new ParserVal(sintactico.crearNodo("cuerpo-if",$1,null));}
	      | cuerpo_Else_for error		{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
              ;

cuerpo_then_for : PR_then '{' bloq_sentencias_For'}'	{$$ = new ParserVal(sintactico.crearNodoControl("then",$3));}
	    	| PR_then sentencias_For 			{$$ = new ParserVal(sintactico.crearNodoControl("then",$2));}
            	;

cuerpo_Else_for : PR_else '{' bloq_sentencias_For'}'	{$$ = new ParserVal(sintactico.crearNodoControl("else",$3));}
	    	| PR_else sentencias_For 			{$$ = new ParserVal(sintactico.crearNodoControl("else",$2));}
            	;

sentencia_if_funcion : If condicion_if cuerpo_If_funcion PR_end_if ';'     { sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
									   $$ = new ParserVal(sintactico.crearNodo("if",$2,$3));}
             	     | If condicion_if cuerpo_If_funcion PR_end_if error   { sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
             	     | If condicion_if cuerpo_If_funcion error		{ sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
                     ;

cuerpo_If_funcion :  cuerpo_then_funcion cuerpo_Else_funcion	{$$ = new ParserVal(sintactico.crearNodo("cuerpo-if",$1,$2));}
		  |  cuerpo_then_funcion 			{$$ = new ParserVal(sintactico.crearNodo("cuerpo-if",$1,null));}
		  | cuerpo_Else_funcion error 			{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
		  ;

cuerpo_then_funcion : PR_then '{' bloq_ejecutables_fun'}' {$$ = new ParserVal(sintactico.crearNodoControl("then",$3));}
		    | PR_then ejecutables_funcion 				{$$ = new ParserVal(sintactico.crearNodoControl("then",$2));}
		    ;

cuerpo_Else_funcion : PR_else '{' bloq_ejecutables_fun'}' {$$ = new ParserVal(sintactico.crearNodoControl("else",$3));}
		    | PR_else ejecutables_funcion 				{$$ = new ParserVal(sintactico.crearNodoControl("else",$2));}
		    ;

sentencia_if_for_fun : If condicion_if cuerpo_If_for_fun PR_end_if ';'     { sintactico.addAnalisis("Se reconoció una sentencia If. (Línea " + AnalizadorLexico.LINEA + ")");
									  $$ = new ParserVal(sintactico.crearNodo("if",$2,$3));
									}
             	     | If condicion_if cuerpo_If_for_fun PR_end_if error   { sintactico.addErrorSintactico("SyntaxError. If1 (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de end_if."); }
             	     | If condicion_if cuerpo_If_for_fun error     	{sintactico.addErrorSintactico("SyntaxError. If2 (Línea " + AnalizadorLexico.LINEA + "): falta cierre end_if; "); }
                     ;

cuerpo_If_for_fun : then_if_for_fun else_if_for_fun	{$$ = new ParserVal(sintactico.crearNodo("cuerpo-if",$1,$2));}
	          | then_if_for_fun			{$$ = new ParserVal(sintactico.crearNodo("cuerpo-if",$1,null));}
	          | else_if_for_fun error		{sintactico.addErrorSintactico("SyntaxError. If4 (Línea " + AnalizadorLexico.LINEA + "): falta el bloque then.");}
	          ;

then_if_for_fun : PR_then '{' bloq_for_funcion '}'	{$$ = new ParserVal(sintactico.crearNodoControl("then", $3));}
		| PR_then sentencias_For_funcion	{$$ = new ParserVal(sintactico.crearNodoControl("then", $2));}
		 ;

else_if_for_fun : PR_else '{' bloq_for_funcion '}'	{$$ = new ParserVal(sintactico.crearNodoControl("else", $3));}
                | PR_else sentencias_For_funcion	{$$ = new ParserVal(sintactico.crearNodoControl("else", $2));}
                ;
PR_then: then {this.contadorIf++; agregarAmbito("if-then"+contadorIf);}
	;

PR_else: Else {this.ambito = borrarAmbito(this.ambito); agregarAmbito("if-else"+this.contadorIf);}
	;

PR_end_if: end_if {this.ambito = borrarAmbito(this.ambito);}
	;

// ------------------------------------------- FIN SENTENCIAS IF ---------------------------------------------------------

// ------------------------------------------- SENTENCIAS WHEN ---------------------------------------------------------
// TODO listo
sentencia_when : when '(' condicion_when ')' cuerpo_when ';'	{ sintactico.addAnalisis("Se reconocio una sentencia when");
								  $$ = new ParserVal(sintactico.crearNodo("when", $3, $5));}
               | when condicion_when ')' cuerpo_when ';' error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta abrir paréntesis la condicion"); }
               | when '(' condicion_when  cuerpo_when';' error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-2) + "): falta paréntesis de cierre en la condicion."); }
               ;

condicion_when: expresion_relacional {$$ = new ParserVal(sintactico.crearNodoControl("condicionWhen",$1)); agregarAmbito("when"+this.contadorWhen); this.contadorWhen++;}
	      ;

// TODO listo
cuerpo_when : then '{' sentencia '}'	{$$ = new ParserVal(sintactico.crearNodoControl("then",$3)); this.ambito = borrarAmbito(this.ambito); }
            | then '{' sentencia error       { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta cerrar la llave del bloque."); }
            | then  sentencia '}' error      { sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta abrir la llave del bloque"); }
            ;
// ------------------------------------------- FIN SENTENCIAS WHEN ---------------------------------------------------------

// ------------------------------------------- SENTENCIAS FOR ---------------------------------------------------------


encabezado_For : For '(' detalles_for ')' cola_For 	{	sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
							  	$$ = new ParserVal(sintactico.crearNodo("For",$3,$5));
							  	this.ambito = borrarAmbito(this.ambito);
							}
	       | For     detalles_for ')' 	cola_For 	error  { sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
	       | For     detalles_for 		cola_For	error  { sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
               | id ':' For '(' detalles_for ')' cola_For	{ sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");

									int existente = sintactico.encontrarTag($1.ival, this.ambito);
									if (existente >= 0) {
										if (sintactico.getEntradaTablaSimb(existente).getUso().equals("tag")) {
											ParserVal nodoTag = new ParserVal(sintactico.crearNodoControl("etiqueta", new ParserVal(sintactico.crearHoja(existente))));
											$$ = new ParserVal( sintactico.crearNodo("for-etiquetado", nodoTag , new ParserVal(sintactico.crearNodo("For",$5,$7))));
											sintactico.eliminarEntrada($1.ival);
										} else {
											sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): el identificador utilizado no es una etiqueta.");
										}
									} else {
										sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): etiqueta invalida");
									}
									this.ambito = borrarAmbito(this.ambito);
								}
		;

 // TODO listo
detalles_for: asignacion_for ';' cond_op_for 	{	$$ = new ParserVal(sintactico.crearNodo("encabezado for",$1, $3));

						}
		;
// TODO listo
cond_op_for : condicion_for ';' operacion_for {$$ = new ParserVal(sintactico.crearNodo("condicion y operacion for",  $1, $3));}

// TODO listo (lo del arbol sintactico)

condicion_for :  id comparador cte	{
						int existente = enAmbito($1);
						if (existente >= 0) {
							if (sintactico.getEntradaTablaSimb(existente).getUso().equals("for_var")) {
								String lexExistente = sintactico.getEntradaTablaSimb(existente).getLexema();
								String [] aux = lexExistente.split("@");

								String ambitoExistente = aux[1];

								if ( ambitoExistente.equals(this.ambito)) {
									sintactico.setUsoEnIndex("i32",$3.ival);
									String typeOP2 = sintactico.getTipoFromTS($3.ival);
                                                                        String typeOP1 = sintactico.getTipoFromTS(existente);
                                                                        if (typeOP1.equals(typeOP2)) {
										ParserVal identificador = new ParserVal(sintactico.crearHoja(existente));
										ParserVal constante = new ParserVal(sintactico.crearHoja($3.ival));
										$$ = new ParserVal(sintactico.crearNodoControl("condicionFor", new ParserVal(sintactico.crearNodo($2.sval,identificador,constante))));
										sintactico.eliminarEntrada($1.ival);
									}else{
									sintactico.addErrorSintactico("SematicError. se reconoce FOR pero hay un problema de tipos en la condicion " + AnalizadorLexico.LINEA);
									}
								} else {
									sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): COND la variable utilizada no corresponde a este for loop");
								}
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): COND la variable utilizada en la condicion debe ser la declarada en el for loop.");
							}
						} else {
							sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): COND la variable usada no ha sido declarada.");
						}

				     	} //TODO para en un futuro expandirla y coparar con expresion
	      ;
// TODO listo
cola_For : '{' bloq_sentencias_For '}' ';'	{$$ = new ParserVal(sintactico.crearNodoControl("cuerpoFor",$2));}
	 |  sentencias_For  			{$$ = new ParserVal(sintactico.crearNodoControl("cuerpoFor",$1));}
	 | '{' bloq_sentencias_For '}' error { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): Falta el ; al final del bloque del for");}
	 ;

signo : '+' {$$.sval = new String("+");}
      | '-' {$$.sval = new String("-");}
      ;


sentencias_For  : asignacion
		| salida
		| expresion_For
		| sentencia_if_for
		| invocacion_funcion
		| sentencia_BREAK
		| sentencia_CONTINUE
		| declarativas error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
		| ret_fun error		{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten retornos fuera de una funcion"); }
		;

expresion_For : encabezado_For
              ;

cola_For_funcion : '{' bloq_for_funcion '}' ';' {$$ = new ParserVal(sintactico.crearNodoControl("cuerpoFor",$2));}
		 |  sentencias_For_funcion 	{$$ = new ParserVal(sintactico.crearNodoControl("cuerpoFor",$1));}
		 | '{' bloq_for_funcion '}' error { sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): falta ; al final del bloque del for."); }
		 ;

sentencia_for_funcion :  For '(' detalles_for ')' cola_For_funcion 	{	sintactico.addAnalisis("Se reconocio sentencia FOR. (Línea " + AnalizadorLexico.LINEA + ")");
                        							$$ = new ParserVal(sintactico.crearNodo("For",$3,$5));
										this.ambito = borrarAmbito(this.ambito);
									}
		       | For     detalles_for ')' 	cola_For_funcion 	error  { sintactico.addErrorSintactico("SyntaxError. FOR1(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
		       | For     detalles_for 		cola_For_funcion	error  { sintactico.addErrorSintactico("SyntaxError. FOR2(Línea " + AnalizadorLexico.LINEA + "): problema en la declaracion FOR"); }
		       | id ':' For '(' detalles_for ')' cola_For_funcion	{
		       									sintactico.addAnalisis("Se reconocio una sentencia for con etiqueta(Línea " + AnalizadorLexico.LINEA + ")");
											int existente = enAmbito($1);
											if (existente >= 0) {
												if (sintactico.getEntradaTablaSimb(existente).getUso().equals("tag")) {
													$$ = new ParserVal( sintactico.crearNodo("for-etiquetado", new ParserVal(sintactico.crearHoja(existente)), new ParserVal(sintactico.crearNodo("For",$5,$7))));
													sintactico.eliminarEntrada($1.ival);
												} else {
													sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): el identificador utilizado no es una etiqueta.");
												}
											} else {
												sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): etiqueta invalida");
											}
											this.ambito = borrarAmbito(this.ambito);
										}

			;

// TODO listo
asignacion_for: id op_asignacion cte {
					agregarAmbito("for"+this.contadorFor);
					this.contadorFor++;
					int existente = enAmbito($1);
					if (existente < 0){
						sintactico.setTipoEnIndex("i32", $1.ival);
						sintactico.setTipoEnIndex("i32", $3.ival);
						sintactico.setLexemaEnIndex($1.ival, "@"+this.ambito);
						sintactico.setUsoEnIndex("for_var", $1.ival);
						ParserVal identificador = new ParserVal(sintactico.crearHoja($1.ival));
						ParserVal constante = new ParserVal(sintactico.crearHoja($3.ival));
						$$ = new ParserVal(sintactico.crearNodoControl("asignacionFor",new ParserVal(sintactico.crearNodo("=:",identificador,constante))));
					} else {
						sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): la variable utilizada para el for loop ya ha sido declarada.");
					}
				   }



	      ;
// TODO listo
operacion_for: signo id		{
					int existente = enAmbito($2);
					if (existente >= 0) {
						if (sintactico.getEntradaTablaSimb(existente).getUso().equals("for_var")) {
							String lexExistente = sintactico.getEntradaTablaSimb(existente).getLexema();
							String [] aux = lexExistente.split("@");
                                                        String ambitoExistente = aux[1];
							if ( ambitoExistente.equals(this.ambito)) {
							 	// TODO FRAN HACER QUE LA OPERACION FOR TENGA UNA ASIGNACION ENTRE EL ID Y EL ID +/- 1 (+i ; -i)
							 	ParserVal hoja = new ParserVal(sintactico.crearHoja(existente));// variableFor
							 	Nodo n = sintactico.crearNodoControl("uno",null);
							 	n.setTipo("i32");
							 	ParserVal uno = new ParserVal(n);
							 	ParserVal operacion = new ParserVal(sintactico.crearNodo($1.sval, hoja, uno));
								$$ = new ParserVal(sintactico.crearNodoControl("operacionFor",new ParserVal(sintactico.crearNodo("=:",new ParserVal(sintactico.crearHoja(existente)),operacion))));
								sintactico.eliminarEntrada($2.ival);
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): OP la variable utilizada no corresponde a este for loop");
							}
						} else {
							sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): OP la variable utilizada en la condicion debe ser la declarada en el for loop.");
						}
					} else {
						sintactico.addErrorSintactico("SematicError. (Línea " + (AnalizadorLexico.LINEA) + "): OP la variable usada no ha sido declarada.");
					}
					}
	      ;

sentencias_For_funcion : asignacion
	       | salida
	       | sentencia_for_funcion
	       | ret_fun
	       | invocacion_funcion
               | sentencia_BREAK
               | sentencia_CONTINUE
               | sentencia_if_for_fun
               | declarativas error	{ sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA-1) + "): no se permiten sentencias declarativas adentro del For"); }
               ;

// TODO listo
bloq_for_funcion : sentencias_For_funcion 			{	$$ = new ParserVal(sintactico.crearNodo("sentencia", $1, null));}
                 | bloq_for_funcion sentencias_For_funcion	{
									ParserVal modificado = sintactico.modificarHijo($1, sintactico.crearNodo("sentencia", $2, null));
									$$ = modificado;
								}
		 | error {sintactico.addErrorSintactico("SyntaxError. (Línea " + (AnalizadorLexico.LINEA) + "): no se permiten cuerpos de for vacios.");}
                 ;

// ------------------------------------------- FIN SENTENCIAS FOR ---------------------------------------------------------
// TODO listo
sentencia_BREAK : BREAK ';'	{	sintactico.addAnalisis("Se reconocio una sentencia break (Línea " + AnalizadorLexico.LINEA + ")");
					$$ = new ParserVal(sintactico.crearNodoControl("break",null));}
                | BREAK cte ';'	{	sintactico.addAnalisis("Se reconocio una sentencia break con retorno de valor (Línea " + AnalizadorLexico.LINEA + ")");
                			$$ = new ParserVal(sintactico.crearNodoControl("breakValor", new ParserVal(sintactico.crearHoja($2.ival))));
                			tipoBreak = sintactico.getTipoFromTS($2.ival);
                			}

                | BREAK error   {	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego de BREAK."); }
                ;
// TODO listo
sentencia_CONTINUE : CONTINUE ';'		{
							sintactico.addAnalisis("Se reconocio una sentencia continue (Línea " + AnalizadorLexico.LINEA + ")");
							$$ = new ParserVal(sintactico.crearNodoControl("continue",null));}
                   | CONTINUE ':' id ';'	{ 	sintactico.addAnalisis("Se reconocio una sentencia continue con etiquetado(Línea " + AnalizadorLexico.LINEA + ")");
                   					int existente = enAmbito($3);
							if (existente < 0 ) {
								sintactico.setLexemaEnIndex($3.ival,"@"+this.ambito);
								sintactico.setUsoEnIndex("tag",$3.ival);
								$$ = new ParserVal(sintactico.crearNodoControl("continue-etiqueta", new ParserVal(sintactico.crearHoja($3.ival))));
							} else {
								sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): el identificador de la etiqueta ya ha sido utilizado.");
							}
                   					}
                   | CONTINUE id ';' error	{ 	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ':'CONTINUE."); }
                   | CONTINUE error		{ 	sintactico.addErrorSintactico("SyntaxError. (Línea " + AnalizadorLexico.LINEA + "): falta ';' luego del CONTINUE "); }
                   ;

invocacion_funcion: id '(' list_parametros_Inv ')' ';'  {
								int existente = enAmbito($1);
								if (existente >= 0) {
									Token idFuncInvocada = sintactico.getEntradaTablaSimb(existente);
									if (idFuncInvocada.getUso().equals("func")) {
										sintactico.checkParametros(idFuncInvocada.getLexema());
										$$ = new ParserVal(sintactico.crearNodoFunc(existente, $3));
										sintactico.eliminarEntrada($1.ival);
									} else {
										sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): el identificador no corresponde a una funcion.");
									}
								} else {
									sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): funcion no declarada.");
								}

							}
//		  | id '(' ')' ';' 			{
//		  						int existente = enAmbito($1);
//								if (existente >= 0) {
//									if (sintactico.getEntradaTablaSimb(existente).getUso().equals("func")) {
//										$$ = new ParserVal(sintactico.crearNodoFunc($1.ival, null));
//										sintactico.eliminarEntrada($1.ival);
//									} else {
//										sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): el identificador no corresponde a una funcion.");
//									}
//								} else {
//									sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): funcion no declarada.");
//								}
//
//		 					 }
		  ;

list_parametros_Inv :
		    |factor ',' factor {
		    				$$ = new ParserVal(sintactico.crearNodoParam("paramInv", $1, $3));
		    				NodoHijo aux = (NodoHijo)$1.obj;
						sintactico.addListaVariables(aux.getRefTablaSimbolos());
						NodoHijo aux1 = (NodoHijo)$3.obj;
                                                sintactico.addListaVariables(aux1.getRefTablaSimbolos());

					}
		    | factor		{	$$ = new ParserVal(sintactico.crearNodoParam("paramInv", $1, null));

		    				NodoHijo aux = (NodoHijo)$1.obj;
                                         	sintactico.addListaVariables(aux.getRefTablaSimbolos());

		 			   }
		    ;

// TODO listo
expresion_relacional : expresion comparador expresion { $$ = new ParserVal(sintactico.crearNodo($2.sval, $1, $3));}
                     ;

// TODO listo
expresion : expresion signo termino {$$ = new ParserVal(sintactico.crearNodo($2.sval, $1, $3)); }
          | termino
          ;

// TODO listo
termino : termino '*' factor	{$$ = new ParserVal(sintactico.crearNodo("*",$1,$3));}
        | termino '/' factor	{$$ = new ParserVal(sintactico.crearNodo("/",$1,$3));}
        | factor
        ;

factor : id  		{
				int existente = enAmbito($1);
				if (existente >= 0) {

					$$ = new ParserVal(sintactico.crearHoja(existente));
					sintactico.eliminarEntrada($1.ival);
				} else {
					sintactico.addErrorSintactico("SematicError. (Línea " + AnalizadorLexico.LINEA + "): variable no declarada.");
				}
				}
       | cte		{
				String lexema = sintactico.getEntradaTablaSimb($1.ival).getLexema();
                                int existente = sintactico.getTS().existeEntrada(lexema);
				if (existente >= 0 && existente < $1.ival) {
					$$ = new ParserVal(sintactico.crearHoja(existente));
					sintactico.eliminarEntrada($1.ival);
				} else {
					String type = sintactico.getTipoFromTS($1.ival);
					if (type.equals("i32"))
					     sintactico.verificarRangoEnteroLargo($1.ival);

					$$ = new ParserVal(sintactico.crearHoja($1.ival));
				}
                  	}
       | '-' cte	{
				sintactico.setNegativoTablaSimb($2.ival);
				String lexema = sintactico.getEntradaTablaSimb($2.ival).getLexema();
				int existente = sintactico.getTS().existeEntrada(lexema);
                                if (existente >= 0  && existente < $2.ival) {
                                	$$ = new ParserVal(sintactico.crearHoja(existente));
                                        sintactico.eliminarEntrada($2.ival);
                                }else{
					$$ = new ParserVal(sintactico.crearHoja($2.ival));
				}
                   	}
       ;

//TODO listo
comparador : '<'            { $$.sval = new String("<") ; }
           | '>'            { $$.sval = new String(">") ; }
           | menorigual     { $$.sval = new String("<="); }
           | mayorigual     { $$.sval = new String(">="); }
           | '='            { $$.sval = new String("=") ; }
           | distinto       { $$.sval = new String("=!"); }
           ;


tipo : i32	{ $$.sval = new String("i32"); }
     | f32	{ $$.sval = new String("f32"); }
     ;

%%

private AnalizadorLexico lexico;
private AnalizadorSintactico sintactico;
private String ambito;
private int contadorFor;
private int contadorIf;
private int contadorWhen;
private String tipoBreak = new String();


public void activarAmbito(){this.ambito = "$"; this.contadorFor = 0; this.contadorIf = 0; this.contadorWhen = 0;} // $ va a simblizar el ambito global.

public void setLexico(AnalizadorLexico lexico) { this.lexico = lexico; }

public void setSintactico(AnalizadorSintactico sintactico) { this.sintactico = sintactico; }

public AnalizadorLexico getLexico() { return this.lexico; }

public AnalizadorSintactico getSintactico() { return this.sintactico; }

public void agregarAmbito(String nuevo) {

	this.ambito = this.ambito + "_" + nuevo;

}

public String borrarAmbito(String ambito){
	if (ambito.length() > 1) { // si es 1 solo tiene el ambito global
		String [] aux = ambito.split("_"); // separo los elementos individuales del ambito
		String last = aux[aux.length - 1 ]; // obtengo el ultimo, el que tengo que eliminar
		return ambito.substring(0, ambito.length() - last.length() - 1);
	}
	return "$";
}

public int yylex() {
    int token = lexico.procesarYylex();
    if (lexico.getRefTablaSimbolos() != -1)
        yylval = new ParserVal(lexico.getRefTablaSimbolos());
    return token;
}

public void yyerror(String string) {
	//sintactico.addErrorSintactico("par: " + string);
}

// retorna -1 si no existe un identificador con el lexema dado en el ambito dado. Si existe, retorna el indice de la entrada existente.
public int enAmbito(ParserVal pv){

	String lexema = sintactico.getEntradaTablaSimb(pv.ival).getLexema();
	String ambitoAux = this.ambito;

	String [] aux = ambitoAux.split("_");
	for (int i = 0 ; i < aux.length ; i++){
		int existente = sintactico.getTS().existeEntrada(lexema + "@"+ambitoAux);
		if (existente >= 0 ){
			return existente;
		}
		ambitoAux = borrarAmbito(ambitoAux);
	}
	return -1;
}
