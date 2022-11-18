package ArbolSintactico;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Stack;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Token;

public class GenerarCodigo{

    private StringBuilder assemblerCode; //  Usamos StringBuilder ya que la cadena de caracteres va a cambiar frecuentemente, ya que hacemos cada caso dentro de esta. (no sincro)
    private StringBuilder inicio;
    private StringBuilder datosPrecarga;
    private TablaSimbolos tablaSimbolos;
    private int contadorEtiquetaLabel = 0;
    private int contadorAux = 0;

    private Stack <String> pila;
    private Stack <String> pilaFor;

    public GenerarCodigo(AnalizadorLexico l){
        this.assemblerCode = new StringBuilder("");
        this.inicio = new StringBuilder("");
        this.datosPrecarga = new StringBuilder("");
        this.pila = new Stack<>();
        this.pilaFor = new Stack<>();
        tablaSimbolos = l.getTablaSimbolos();
    }



    private void cargarLibrerias () {
		this.inicio.append(";------------ INCLUDES y LIBRERIAS ------------\r\n" +
                ".386\r\n" +
				".model flat, stdcall\r\n" +
				".stack 200h\r\n"+
				"option casemap :none\r\n" +
				"include \\masm32\\include\\windows.inc\r\n" +
				"include \\masm32\\include\\kernel32.inc\r\n" +
				"include \\masm32\\include\\user32.inc\r\n" +
				"includelib \\masm32\\lib\\kernel32.lib\r\n" +
				"includelib \\masm32\\lib\\user32.lib\r\n" +
				"include \\masm32\\include\\masm32.inc\n" +
				"includelib \\masm32\\lib\\masm32.lib\n"+
                ";------------ DATA ------------\n"+
				".data\n"+
                ";------------ ERRORES DE EJECUCION ------------\n"+
                "errorOverflow db 'ERROR EN LA EJECUCION: Overflow de de datosPrecarga de punto flotante (f32)',0 \n" +
				"errorDivCeroEntero db 'ERROR EN LA EJECUCION: Division por cero para datosPrecarga enteros',0 \n" +
                "errorDivCeroFlotante db 'ERROR EN LA EJECUCION: Division por cero para datosPrecarga de punto flotante',0 \n" +
				"errorRecursion db 'ERROR EN LA EJECUCION: Recursión en invocaciones de funciones',0 \n" +
                "flagTry dw 0,0\n");

	}

    // -------------- REVISAR QUE OPERACIONES O LLAMADOS A FUNCIONES FALTARIAN
    // -------------- REVISAR SETEO DE USO, GET PADRE, GET VALOR, GET PARAMETRO, COMPARACIONES
    // -------------- REVISAR LLAMADOS A FUNCIONES, CODIGO CONTINUE
    // -------------- REVISAR ENCABEZADO DE PROGRAMA Y DE FUNCIONES CUANDO SE DECLARAN LAS VARIABLES
    // -------------- CONST?????
    // -------------- REVISAR SI FALTA AGREGAR ALGO AL SWITCH CASE
    // -------------- FALTA REVISAR LA ESTRUCTURA DE ASSEMBLER EN SI PARA LA EJECUCION CORRECTA DEL CODIGO
    // -------------- DEBUG TODO
    // -------------- REVISION GENERAL
    // -------------- CASOS DE PRUEBA

    public void generarCodigoLeido(Nodo nodo) {

        if (!nodo.esHoja()) {
            if (nodo.getHijoIzquierdo() != null)
                generarCodigoLeido(nodo.getHijoIzquierdo());
            if (nodo.getHijoDerecho() != null)
                generarCodigoLeido(nodo.getHijoDerecho());

            if (nodo.hijosSonHoja()) {

                switch (nodo.getLexema()) {

                    // Asignacion

                    case ("=:"):
                        asignacionAssembler(nodo);
                        break;

                    // Operaciones aritmeticas

                    case ("+"):
                        sumaAssembler(nodo);
                        break;

                    case ("-"):
                        restaAssembler(nodo);
                        break;

                    case ("*"):
                        multiplicacionAssembler(nodo);
                        break;

                    case ("/"):
                        divisionAssembler(nodo);
                        break;

                    // Comparadores

                    case (">"):
                        mayorAssembler(nodo);
                        break;

                    case ("<"):
                        menorAssembler(nodo);
                        break;

                    case ("<="):
                        menorIgualAssembler(nodo);
                        break;

                    case (">="):
                        mayorIgualAssembler(nodo);
                        break;

                    case ("="):
                        igualAssembler(nodo);
                        break;

                    case ("=!"):
                        distintoAssembler(nodo);
                        break;

                    // Sentencias de control

                    case ("if"):
                        ifAssembler(nodo);
                        break;

                    case ("then"):
                        thenAssembler(nodo);
                        break;

                    case ("else"):
                        elseAssembler(nodo);
                        break;

                    // Funciones

                    case ("break"):
                        breakAssembler(nodo);
                        break;

                    case ("continue"):
                        continueAssembler(nodo);
                        break;

                    case ("return"):
                        returnAssembler(nodo);
                        break;

                    case ("out"):
                        outAssembler(nodo);
                        break;
                }
            }
        }
    }

    private void outAssembler(Nodo nodo) {
    }



    private void returnAssembler(Nodo nodo) { // FALTA

    }

    private void continueAssembler(Nodo nodo) { // FALTA
    }

    private void breakAssembler(Nodo nodo) { // Corte para los FOR cuando se encuentra un BREAK
        if (!this.pilaFor.isEmpty()) {
			this.assemblerCode.append("JMP " + this.pilaFor.peek()  + "\n");
		};
    }


    private void distintoAssembler(Nodo nodo) {

        String aux = "aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
      //  Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        if (nodo.getTipo() == "i32") {
            this.assemblerCode.append("MOV EAX, " + nodo.getHijoIzquierdo().getLexema() + "\n");
            this.assemblerCode.append("CMP EAX, " + nodo.getHijoDerecho().getLexema() + "\n");
            this.assemblerCode.append("JE " + labelFalso + "\n");
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
        //    s.setUso("variable");
        } else {
            String mem2bytes = "aux" + contadorAux;
            this.contadorAux++;
         //   Simbolo s2 = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        //    s2.setUso("variableP");
          //  this.tablaDeSimbolos.put(mem2bytes, s2);

            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }
            if (nodo.getHijoIzquierdo().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoIzquierdo().getLexema() + "\n");
            }
            this.assemblerCode.append("FCOMPP \n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV AX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JE " + labelFalso + "\n");
            this.assemblerCode.append("FLD1\n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
          //  s.setUso("variable");
        //    s.setTipo("f32");
        }
      //  this.tablaDeSimbolos.put(aux, s);
        //nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
       // nodo.setLexema(aux);

    }


    private void igualAssembler(Nodo nodo) {

        String aux = "aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
    //    Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        if (nodo.getTipo() == "i32") {
            this.assemblerCode.append("MOV EAX, " + nodo.getHijoIzquierdo().getLexema() + "\n");
            this.assemblerCode.append("CMP EAX, " + nodo.getHijoDerecho().getLexema() + "\n");
            this.assemblerCode.append("JNE " + labelFalso + "\n");
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
          //  s.setUso("variable");
        } else {
            String mem2bytes = "aux" + contadorAux;
            this.contadorAux++;
         //   Simbolo s2 = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
           // s2.setUso("variableP");
           // this.tablaDeSimbolos.put(mem2bytes, s2);
            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }
            if (nodo.getHijoIzquierdo().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoIzquierdo().getLexema() + "\n");
            }
            this.assemblerCode.append("FCOMPP \n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV AX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JNE " + labelFalso + "\n");
            this.assemblerCode.append("FLD1\n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
          //  s.setUso("variable");
          //  s.setTipo("f32");
        }
       // this.tablaDeSimbolos.put(aux, s);
       // nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
       // nodo.setLexema(aux);

    }


    private void mayorIgualAssembler(Nodo nodo) {

        String aux = "aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
     //   Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        if (nodo.getTipo() == "i32") {
            this.assemblerCode.append("MOV EAX, " + nodo.getHijoIzquierdo().getLexema() + "\n");
            this.assemblerCode.append("CMP EAX, " + nodo.getHijoDerecho().getLexema() + "\n");
            this.assemblerCode.append("JL " + labelFalso + "\n");
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
           // s.setUso("variable");
        } else {
            String mem2bytes = "aux" + contadorAux;
            this.contadorAux++;
         //   Simbolo s2 = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
           // s2.setUso("variableP");
           // this.tablaDeSimbolos.put(mem2bytes, s2);
            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }
            if (nodo.getHijoIzquierdo().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoIzquierdo().getLexema() + "\n");
            }
            this.assemblerCode.append("FCOMPP \n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV AX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JB " + labelFalso + "\n");
            this.assemblerCode.append("FLD1\n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
           // s.setUso("variable");
         //   s.setTipo("f32");
        }
       // this.tablaDeSimbolos.put(aux, s);
       // nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        nodo.setLexema(aux);

    }


    private void menorIgualAssembler(Nodo nodo) {

        String aux = "aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
     //   Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        if (nodo.getTipo() == "i32") {
            this.assemblerCode.append("MOV EAX, " + nodo.getHijoIzquierdo().getLexema() + "\n");
            this.assemblerCode.append("CMP EAX, " + nodo.getHijoDerecho().getLexema() + "\n");
            this.assemblerCode.append("JG " + labelFalso + "\n");
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
          //  s.setUso("variable");
        } else {
            String mem2bytes = "aux" + contadorAux;
            this.contadorAux++;
          //  Simbolo s2 = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
          //  s2.setUso("variableP");
         //   this.tablaDeSimbolos.put(mem2bytes, s2);
            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }
            if (nodo.getHijoIzquierdo().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoIzquierdo().getLexema() + "\n");
            }
            this.assemblerCode.append("FCOMPP \n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV AX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JA " + labelFalso + "\n");
            this.assemblerCode.append("FLD1\n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
          //  s.setUso("variable");
           // s.setTipo("f32");
        }
       // this.tablaDeSimbolos.put(aux, s);
       // nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        nodo.setLexema(aux);

    }


    private void menorAssembler(Nodo nodo) {

        String aux = "aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
    //    Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        if (nodo.getTipo() == "i32") {
            this.assemblerCode.append("MOV EAX, " + nodo.getHijoIzquierdo().getLexema() + "\n");
            this.assemblerCode.append("CMP EAX, " + nodo.getHijoDerecho().getLexema() + "\n");
            this.assemblerCode.append("JGE " + labelFalso + "\n");
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
         //   s.setUso("variable");
        } else {
            String mem2bytes = "aux" + contadorAux;
            this.contadorAux++;
         //   Simbolo s2 = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
          //  s2.setUso("variableP");
           // this.tablaDeSimbolos.put(mem2bytes, s2);
            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }
            if (nodo.getHijoIzquierdo().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoIzquierdo().getLexema() + "\n");
            }
            this.assemblerCode.append("FCOMPP \n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV AX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JAE " + labelFalso + "\n");
            this.assemblerCode.append("FLD1\n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
          //  s.setUso("variable");
            //s.setTipo("f32");
        }
      //  this.tablaDeSimbolos.put(aux, s);
       // nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        nodo.setLexema(aux);

    }


    private void mayorAssembler(Nodo nodo) {

        String aux = "aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel; // Casos: (THEN, Condicion TRUE en un FOR, es decir, ejecuta lo de adentro del FOR)
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel; // Casos: (ELSE, Condicion FALSE en un FOR, es decir corta el FOR)
        this.contadorEtiquetaLabel++;
      //  Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        if (nodo.getTipo() == "i32") {
            this.assemblerCode.append("MOV EAX, " + nodo.getHijoIzquierdo().getLexema() + "\n");
            this.assemblerCode.append("CMP EAX, " + nodo.getHijoDerecho().getLexema() + "\n"); // COMPARO LA CONDICION PARA SABER SI ES MAYOR
            this.assemblerCode.append("JLE " + labelFalso + "\n"); // Si NO ES MAYOR (CASO JLE CUANDO ES <=) (ELSE O FALSE DE FOR), salto a la etiqueta labelFalso y ejecuto las instrucciones debajo
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
           // s.setUso("variable");
        } else {
            String mem2bytes = "aux" + contadorAux;
            this.contadorAux++;
        //    Simbolo s2 = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
          //  s2.setUso("variableP");
           // this.tablaDeSimbolos.put(mem2bytes, s2);
            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }
            if (nodo.getHijoIzquierdo().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoIzquierdo().getLexema() + "\n");
            }
            this.assemblerCode.append("FCOMPP \n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV AX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JBE " + labelFalso + "\n");
            this.assemblerCode.append("FLD1\n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
          //  s.setUso("variable");
           // s.setTipo("f32");
        }
      //  this.tablaDeSimbolos.put(aux, s);
       // nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
        nodo.setLexema(aux);

    }


    private void elseAssembler(Nodo nodo) { // REVISAR
        String label=this.pila.pop();
		this.assemblerCode.append(label+":\n");
    }


    private void thenAssembler(Nodo nodo) {
        String label="_label"+contadorEtiquetaLabel;
		contadorEtiquetaLabel++;
		//desapilo label1 y apilo label2
		this.assemblerCode.append("JMP "+label + "\n");
		String labelAux= this.pila.pop();
		this.assemblerCode.append(labelAux + ":\n");
		this.pila.push(label); // aca sera el pop de AX???
    }


    private void ifAssembler(Nodo nodo) { // REVISAR
        if (!this.pila.empty()) {
			String labelAux= this.pila.pop();
			this.assemblerCode.append(labelAux + ":\n");
		}
    }


    private void divisionAssembler(Nodo nodo) {

        String aux = "aux" + contadorAux;
        this.contadorAux++;
        if (nodo.getTipo() == "i32") {
            String label = "_label" + contadorEtiquetaLabel;
            contadorEtiquetaLabel++;
            this.assemblerCode.append("MOV EAX, " + nodo.getHijoDerecho().getLexema() + "\n");
            this.assemblerCode.append("CMP EAX, 0 \n"); // COMPARA EL VALOR DE EAX CON 0 (MENOR, MAYOR O IGUAL)
            this.assemblerCode.append("JNE " + label + "\n");
            this.assemblerCode.append("invoke MessageBox, NULL, addr errorDiv, addr program, MB_OK\n"); // DIVISION POR CERO CONTROL
            this.assemblerCode.append("invoke ExitProcess, 0\n");
            this.assemblerCode.append(label + ":\n");
            this.assemblerCode.append("MOV "+"EAX"+","+nodo.getHijoIzquierdo().getLexema()+"\n");
            this.assemblerCode.append("MOV "+"EDX"+","+" 0"+"\n");
            this.assemblerCode.append("MOV EBX, " + nodo.getHijoDerecho().getLexema() + "\n");
            this.assemblerCode.append("IDIV EBX\n");
            this.assemblerCode.append("MOV "+aux+","+"EAX"+"\n");
          //  Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
          //  s.setUso("variable");
           // this.tablaDeSimbolos.put(aux, s);
           // nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
            nodo.setLexema(aux);
        } else {
            String label = "_label" + contadorEtiquetaLabel;
            contadorEtiquetaLabel++;
            String mem2bytes = "aux" + contadorAux;
            this.contadorAux++;
          //  Simbolo s2 = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
            //s2.setUso("variableP");
           // this.tablaDeSimbolos.put(mem2bytes, s2);
            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }
            this.assemblerCode.append("FLDZ\n");
            this.assemblerCode.append("FCOMPP \n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV AX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JNE " + label + "\n");
            this.assemblerCode.append("invoke MessageBox, NULL, addr errorDiv, addr program, MB_OK\n");
            this.assemblerCode.append("invoke ExitProcess, 0\n");
            this.assemblerCode.append(label + ":\n");
            if (nodo.getHijoIzquierdo().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoIzquierdo().getLexema() + "\n");
            }
            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }
            this.assemblerCode.append("FDIV"+"\n");
            this.assemblerCode.append("FSTP "+aux+"\n");

          //  Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
          //  s.setUso("variable");
          //  s.setTipo("SINGLE");
           // this.tablaDeSimbolos.put(aux, s);
            //nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
            nodo.setLexema(aux);
        }

    }


    private void multiplicacionAssembler(Nodo nodo) {

        String aux = "aux" + contadorAux;
        this.contadorAux++;
        if (nodo.getTipo() == "i32") {
            String label = "_label" + contadorEtiquetaLabel;
            contadorEtiquetaLabel++;
            this.assemblerCode.append("MOV "+"EAX"+","+nodo.getHijoIzquierdo().getLexema()+"\n");
            this.assemblerCode.append("IMUL "+"EAX"+","+nodo.getHijoDerecho().getLexema()+"\n");
            this.assemblerCode.append("JNO " + label + "\n");
        //    this.assemblerCode.append("invoke MessageBox, NULL, addr errorOv, addr program, MB_OK\n");
          //  this.assemblerCode.append("invoke ExitProcess, 0\n");
            this.assemblerCode.append(label + ":\n");
            this.assemblerCode.append("MOV "+aux+","+"EAX"+"\n");
          //  Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
           // s.setUso("variable");
           // this.tablaDeSimbolos.put(aux, s);
           // nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
            nodo.setLexema(aux);

        } else { // SI ES PUNTO FLOTANTE (32 BITS) - f32

            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }
            if (nodo.getHijoIzquierdo().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoIzquierdo().getLexema() + "\n");
            }
            this.assemblerCode.append("FMUL" + "\n");
            this.assemblerCode.append("FSTP "+aux+"\n");

          //  Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
          //  s.setUso("variable");
           // s.setTipo("SINGLE");
           // this.tablaDeSimbolos.put(aux, s);
           // nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
            nodo.setLexema(aux);
        }

    }


    private void restaAssembler(Nodo nodo) {

        String aux = "aux" + contadorAux;
        this.contadorAux++;
        if (nodo.getTipo() == "i32") { // ENTERO LARGO (32 BITS)

            //if (this.tablaSimbolos.get(nodo.getHijoIzquierdo().getLexema()).getUso() == "Nombre de funcion") {
              //  this.assemblerCode.append("MOV EAX," + nodo.getHijoIzquierdo().getParametro());
                //this.assemblerCode.append("\n");
                //this.assemblerCode.append("call _" + nodo.getHijoIzquierdo().getLexema());
                //this.assemblerCode.append("\n");
                //this.assemblerCode.append("SUB EAX," + nodo.getHijoDerecho().getLexema());
                //this.assemblerCode.append("\n");
            } else {
               // if (this.tablaSimbolos.get(nodo.getHijoDerecho().getLexema()).getUso() == "Nombre de funcion") {
                  //  this.assemblerCode.append("MOV EAX," + nodo.getHijoDerecho().getParametro());
                    //this.assemblerCode.append("\n");
                    //this.assemblerCode.append("call _" + nodo.getHijoDerecho().getLexema());
                    //this.assemblerCode.append("\n");
                    //this.assemblerCode.append("SUB EAX," + nodo.getHijoIzquierdo().getLexema());
                    //this.assemblerCode.append("\n");

//                } else {
                    this.assemblerCode.append("MOV "+"EAX"+","+nodo.getHijoIzquierdo().getLexema()+"\n");
                    this.assemblerCode.append("SUB "+"EAX"+","+nodo.getHijoDerecho().getLexema()+"\n");
                }
         //  }
            this.assemblerCode.append("MOV "+aux+","+"EAX"+"\n");
          //  Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
            //s.setUso("variable");
           // this.tablaSimbolos.put(aux, s);
            //nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
            //nodo.setLexema(aux);


       // } else { // SI ES PUNTO FLOTANTE (32 BITS) - f32


            if (nodo.getHijoIzquierdo().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoIzquierdo().getLexema() + "\n");
            }
            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }
            this.assemblerCode.append("FSUB"+"\n");
            this.assemblerCode.append("FSTP "+aux+"\n");
         //   Simbolo s = new Simbolo(0, 0, this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
           // s.setUso("variable");
            //s.setTipo("SINGLE");
            //this.tablaDeSimbolos.put(aux, s);
            //nodo.setTipo(this.tablaDeSimbolos.get(nodo.getHijoI().getLexema()).getTipo());
            nodo.setLexema(aux);
        }

   // }

    private void sumaAssembler(Nodo nodo) {
        String aux = "aux" + contadorAux; // Variables auxiliares para guardar los valores de las operaciones a medida que se procesa
        this.contadorAux++;
        if (nodo.getTipo() == "i32") { // Si es suma ENTERO (32 bits)

            //if (this.tablaSimbolos.get(nodo.getHijoIzquierdo().getLexema()).getUso() == "Nombre de funcion") { // ESTO DEBERIA SER PARA LA DEFINION DE FUNCIONES Y PROGRAMA (PROGRAM, FUN, ETC) ???
               // this.assemblerCode.append("MOV EAX," + nodo.getHijoIzquierdo().getParametro() + "\n");
             //   this.assemblerCode.append("call _" + nodo.getHijoIzquierdo().getLexema() + "\n");
               // this.assemblerCode.append("ADD EAX," + nodo.getHijoDerecho().getLexema() + "\n");
            //} else {
              //  if (this.tablaSimbolos.get(nodo.getHijoDerecho().getLexema()).getUso() == "Nombre de funcion") {
               //     this.assemblerCode.append("MOV EAX," + nodo.getHijoDerecho().getParametro() + "\n");

                //    this.assemblerCode.append("call _" + nodo.getHijoDerecho().getLexema()+"\n");
                  //  this.assemblerCode.append("ADD EAX," + nodo.getHijoIzquierdo().getLexema()+"\n");
               // } else {
                    this.assemblerCode.append("MOV "+"EAX"+","+ nodo.getHijoIzquierdo().getLexema() + "\n"); // Realiza la suma (OK)
                    this.assemblerCode.append("ADD "+"EAX"+","+ nodo.getHijoDerecho().getLexema()+"\n"); // Realiza la suma (OK)
                //}
            //}
            this.assemblerCode.append("MOV "+aux+","+"EAX"+"\n"); // Realiza la suma (OK)
           // Simbolo s = new Simbolo(0, 0, this.tablaSimbolos.get(nodo.getHijoIzquierdo().getLexema()).getTipo());
          //  s.setUso("variable");
            //this.tablaSimbolos.put(aux, s);
            //nodo.setTipo(this.tablaSimbolos.get(nodo.getHijoIzquierdo().getLexema()).getTipo());
          //  nodo.setLexema(aux);

         } else { // PUNTO FLOTANTE (32 BITS)

            if (nodo.getHijoDerecho().getLexema().contains(".")) {
                this.assemblerCode
                        .append("FLD _" + nodo.getHijoDerecho().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoDerecho().getLexema() + "\n");
            }


            if (nodo.getHijoIzquierdo().getLexema().contains(".")) {
                this.assemblerCode.append(
                        "FLD _" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            } else {
                this.assemblerCode.append("FLD " + nodo.getHijoIzquierdo().getLexema() + "\n");
            }

        }
           // this.assemblerCode.append("FADD"+"\n");
          //  this.assemblerCode.append("FSTP "+aux+"\n");
          //  Simbolo s = new Simbolo(0, 0, this.tablaSimbolos.get(nodo.getHijoIzquierdo().getLexema()).getTipo());
           // s.setUso("variable");
           // s.setTipo("SINGLE");
           // this.tablaSimbolos.put(aux, s);
           // nodo.setTipo(this.tablaSimbolos.get(nodo.getHijoIzquierdo().getLexema()).getTipo());
           // nodo.setLexema(aux);
        }
   // }

    private void asignacionAssembler(Nodo nodo) {
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoDerecho().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        if (t.getUso() == "func"){
            String label="_label"+contadorEtiquetaLabel;
			contadorEtiquetaLabel++;
        }
        //.getUso()=="Nombre de funcion") { // ESTO DEBERIA SER PARA LA DEFINION DE FUNCIONES Y PROGRAMA (PROGRAM, FUN, ETC) ???
			//if (nodo.getHijoDerechoerecho().getParametro()==null) { // EL PARAMETRO SE RELACIONA CON EL AMBITO EN LA GRAMATICA??
				// Chequeo de recursion mutua????

                String label="_label"+contadorEtiquetaLabel;
			    contadorEtiquetaLabel++;


                this.assemblerCode.append("pop AX" + "\n"); // pop? porqueeeeekslodngbklsdg donde se hace el push????
				this.assemblerCode.append("CMP 1,"+nodo.getHijoDerecho().getValor() + "\n");
				this.assemblerCode.append("JNE " + label + "\n");
				this.assemblerCode.append("invoke MessageBox, NULL, addr error, addr error, MB_OK" + "\n");
				this.assemblerCode.append("invoke ExitProcess, 0" + "\n");
				this.assemblerCode.append(label+": \n");
				this.assemblerCode.append("push "+nodo.getValor()+"\n");


            if (nodo.getHijoIzquierdo().getTipo().equals("i32")) {
					//this.assemblerCode.append("MOV EAX,"+nodo.getHijoDerechoerecho().getParametro()+"\n");
					//if ((this.tablaSimbolos.get(nodo.getHijoDerechoerecho().getLexema()).getP()==null)) {
				//		this.assemblerCode.append("call _"+nodo.getHijoDerecho().getLexema());}
				//	else {
				//		this.assemblerCode.append("call _"+this.tablaSimbolos.get(nodo.getHijoDerechoerecho().getLexema()).getP()+"\n");}
					this.assemblerCode.append("MOV "+nodo.getHijoIzquierdo().getLexema()+",EAX" + "\n");
			}

			//	else { // PUNTO FLOTANTE (32 BITS)

				//	this.assemblerCode.append("FLD "+nodo.getHijoDerechoerecho().getParametro()+"\n");
				//	if ((this.tablaSimbolos.get(nodo.getHijoDerechoerecho().getLexema()).getP()==null)) {
			//			this.assemblerCode.append("call _"+nodo.getHijoDerechoerecho().getLexema());}
				//	else {
					//	this.assemblerCode.append("call _"+this.tablaSimbolos.get(nodo.getHijoDerechoerecho().getLexema()).getP()+"\n");}

					this.assemblerCode.append("FSTP "+nodo.getHijoIzquierdo().getLexema()+"\n"); // Co-procesador (solo para f32)
			//	}
				//this.assemblerCode.append("pop BX"+"\n");
		//	}
		//	else
			{
			//	this.tablaSimbolos.get(nodo.getHijoIzquierdozquierdo().getLexema()).setP(nodo.getHijoDerechoerecho().getLexema());
			//	this.tablaSimbolos.get(nodo.getHijoIzquierdozquierdo().getLexema()).setUso("Nombre de funcion");
			}
	//	}
	//	else {
			if (nodo.getHijoIzquierdo().getTipo().equals("i32")) { // Asignacion de tipo ENTERO LARGO (32 BITS)
				this.assemblerCode.append("MOV EAX"+","+nodo.getHijoDerecho().getLexema()+"\n");
				this.assemblerCode.append("MOV "+nodo.getHijoIzquierdo().getLexema()+","+"EAX"+"\n");
            }
			else{
			//	if(this.tablaSimbolos.get(nodo.getHijoDerechoerecho().getLexema()).getUso()=="VS"){ // SI ES ASIGNACION DEL LADO DERECHO FLOTANTE
					this.assemblerCode.append("FLD "+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
					this.assemblerCode.append("FSTP "+nodo.getHijoIzquierdo().getLexema()+"\n");
				}
			//	else{ // SI ES ASIGNACION DEL LADO DERECHO ENTERO
					this.assemblerCode.append("FLD "+nodo.getHijoDerecho().getLexema()+"\n");
					this.assemblerCode.append("FSTP "+nodo.getHijoIzquierdo().getLexema()+"\n");
			//	}
			//}
	//	}
    }

    /**
     * Metodo para cargar las variables auxiliares, segun el uso que corresponda (IMPLEMENTAR)
     * @param k
     * @param v
     */

    /*private void cargarVariablesAuxiliares(String k, Simbolo v) { // En los parametros deberiamos pasarle para poder acceder a los tipos (nodo ??)
        if (v.getUso() == "VS") { // segun el tipo generamos el assembler para las variables auxiliares
            String aux = "_" + k;
            this.datosPrecarga.append(aux.replace('.', '_').replace('-', '_'));
            this.datosPrecarga.append(" dd " + k);
            this.datosPrecarga.append("\n");
        } else {
            if (v.getUso() == "variableP") {
                this.datosPrecarga.append(k);
                this.datosPrecarga.append(" dw ?,?");
                this.datosPrecarga.append("\n");
            } else {
                if (v.getUso() != "") {
                    if (v.getTipo() == "CADENA") {
                        this.datosPrecarga.append(k.replace(' ', '_'));
                        this.datosPrecarga.append(" db '" + k + "'" + ",0");
                        this.datosPrecarga.append("\n");
                    } else {
                        this.datosPrecarga.append(k);
                        if (v.getTipo() == "LONG") {
                            this.datosPrecarga.append(" dd ?,?");
                            this.datosPrecarga.append("\n");
                        } else {
                            if (v.getTipo() == "SINGLE") {
                                this.datosPrecarga.append(" dd ?,?");
                                this.datosPrecarga.append("\n");
                            } else {
                                if (v.getTipo() == "CADENA") {
                                    this.datosPrecarga.append(" db '" + k + "'" + ",0");
                                    this.datosPrecarga.append("\n");
                                } else {
                                    this.datosPrecarga.append(" db '" + k + "'" + ",0");
                                    this.datosPrecarga.append("\n");
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/










    // -------------------- GENERACION DE CODIGO (SALIDA) -------------------- //

    public void generacionDeCodigo(Nodo nodo) {
        try {
            String ruta = "codigo_generado_assembler.asm";
            String contenido;
            StringBuilder code = new StringBuilder();
            File file = new File(ruta);

            // Si el archivo no existe es creado

            if (!file.exists()) {
                file.createNewFile();
            }
            // this.cargarVariablesAuxiliares(); // Cargar las variables auxiliares (IMPLEMENTAR)
            code.append(";------------ CODE ------------\r\n");
            code.append(".code\r\n");
            this.cargarLibrerias(); // Carga el encabezado de assembler importando las librerias necesarias.
            this.generarCodigoLeido(nodo); // Carga el codigo completo
            this.assemblerCode.append(";------------ FIN ------------\n");
            this.assemblerCode.append("invoke ExitProcess, 0\n");
			this.assemblerCode.append("end start");
            System.out.println("CODIGO RE ENOJADO (DEBUG FURIOSO):"+"\n"+"\n"+this.assemblerCode.toString());
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            // Leo el codigo fuente
            contenido = this.inicio.toString()+code.toString();
            bw.write(contenido);
            contenido = this.assemblerCode.toString();
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
