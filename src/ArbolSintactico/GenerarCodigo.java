package ArbolSintactico;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Token;
import AnalizadorSintactico.AnalizadorSintactico;

public class GenerarCodigo{

    private StringBuilder assemblerCode; //  Usamos StringBuilder ya que la cadena de caracteres va a cambiar frecuentemente, ya que hacemos cada caso dentro de esta. (no sincro)
    private StringBuilder inicio;
    private StringBuilder datosPrecarga;
    private TablaSimbolos tablaSimbolos;
    private int contadorEtiquetaLabel = 0;
    private int contadorAux = 0;

    private Stack <String> pilaControl;
    private Stack <String> pilaFor;

    private Stack <String> pilaInvocaciones;

    public GenerarCodigo(AnalizadorLexico l){
        this.assemblerCode = new StringBuilder("");
        this.inicio = new StringBuilder("");
        this.datosPrecarga = new StringBuilder("");
        this.pilaControl = new Stack<>();
        this.pilaFor = new Stack<>();
        this.pilaInvocaciones = new Stack<>();
        tablaSimbolos = l.getTablaSimbolos();
    }


    public void imprimirTablaSimbolosAssembler() {
        System.out.println();
        if (this.tablaSimbolos.isEmpty())
            System.out.println("|----- TABLA DE SIMBOLOS ASSEMBLER VACIA ------|");
        else {
            System.out.println("|----- TABLA DE SIMBOLOS ASSEMBLER ------|");
        }this.tablaSimbolos.imprimir();

    }

    // TODO controlar division por cero assembler
    // TODO controlar los demas errores que debe generar y controlar el assembler
    // TODO realizar mas casos de prueba para probar los @aux

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
            "errorRecursion db 'ERROR EN LA EJECUCION: Recursión en invocaciones de funciones',0 \n"+
            "ok db 'OK',0 \n"+
            "mem2bytes dw ?\n"
            );

}

    public void generarCodigoFunciones(HashMap<String, Nodo> arbolFunciones) {
        for (Map.Entry<String,Nodo> funcion : arbolFunciones.entrySet()) {

            String lexemaFunc = funcion.getValue().getLexema();
            // encabezado assembler funcion
            this.assemblerCode.append("PUBLIC "+"_"+lexemaFunc+"\n");
            this.assemblerCode.append("_"+lexemaFunc+" PROC"+"\n");
            ArrayList<Integer> params = tablaSimbolos.getParametros(lexemaFunc);


                // obtenemos los parametros de la tabla
                // [] no tiene param --> no hay que hacer nada
                // [int] tiene 1 --> cargas EAX
                // [int,int] tiene 2 --> cargas con el primero EAX y con el segundo en el otro reg
                if (!params.isEmpty()){
                    if (params.size() == 1){
                        String lexParam = "_"+tablaSimbolos.getEntrada(params.get(0)).getLexema();
                        if(tablaSimbolos.getEntrada(params.get(0)).getTipo().equals("i32")){
                            this.assemblerCode.append("MOV "+ lexParam + ",EAX\n");
                        }else{
                            this.assemblerCode.append("FSTP "+lexParam+"\n");
                        }
                    } else {
                        String lexParam1 = "_"+tablaSimbolos.getEntrada(params.get(0)).getLexema().replace('.','_').replace('-', '_');
                        String lexParam2 = "_"+tablaSimbolos.getEntrada(params.get(1)).getLexema().replace('.','_').replace('-', '_');

                        // Chequea tipo de parametro 1
                        if (tablaSimbolos.getEntrada(params.get(0)).getTipo().equals("i32")){
                            this.assemblerCode.append("MOV "+ lexParam1 + ",EAX\n");
                        }else{
                            this.assemblerCode.append("FSTP "+lexParam1+"\n");
                        }
                        // chequea tipo de parametro 2
                        if (tablaSimbolos.getEntrada(params.get(1)).getTipo().equals("i32")){
                            this.assemblerCode.append("MOV "+ lexParam2 + ",EBX\n");
                        }else{
                            this.assemblerCode.append("FSTP "+lexParam2+"\n");
                        }
                    }
                }
            this.generarCodigoLeido(funcion.getValue());
            this.assemblerCode.append("_"+lexemaFunc+" ENDP\n");
        }
        this.assemblerCode.append("start:\n");
    }


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

                    case ("condicionIf"):

                    case ("condicionWhen"):
                        condicionIfAssembler(nodo);
                        break;

                    case ("if"):

                    case ("when"):
                        ifAssembler(nodo);
                        break;

                    case ("then"):
                        thenAssembler(nodo);
                        break;

                    case ("else"):
                        elseAssembler(nodo);
                        break;

                    case ("cuerpo-if"):
                        cuerpoAssembler(nodo);
                        break;

                    case ("cuerpoFor"):
                        cuerpoForAssembler(nodo);
                        break;

                    case ("asignacionFor"):
                        asignacionForAssembler(nodo);
                        break;

                    case ("operacionFor"):
                        operacionForAssembler(nodo);
                        break;

                    case ("condicionFor"):
                        condicionForAssembler(nodo);
                        break;

                    case ("condicion y operacion for"):
                        condicionYOperacionForAssembler(nodo);
                        break;

                    case ("encabezado for"):
                        encabezadoForAssembler(nodo);
                        break;

                    case ("for-etiquetado"):
                        forEtiquetadoAssembler(nodo);
                        break;

                    case ("etiqueta"):
                        etiquetaAssembler(nodo);
                        break;

                    case ("For"):
                        forAssembler(nodo);
                        break;

                    // Funciones

                    case ("break"):
                        breakAssembler(nodo);
                        break;

                    case ("continue"):
                        continueAssembler(nodo);
                        break;

                    case ("continue-etiqueta"):
                        continueEtiquetaAssembler(nodo);
                        break;

                    case ("return"):
                        returnAssembler(nodo);
                        break;

                    case ("out"):
                        outAssembler(nodo);
                        break;

                    case ("paramInv"):
                        paramAssembler(nodo);
                        break;
                }
                nodo.descolgarHijos();
            }
        }

        }

    private void outAssembler(Nodo nodo) {
        this.assemblerCode.append("invoke MessageBox, NULL, addr "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+", addr "+"program"+", MB_OK");
    }

    private void returnAssembler(Nodo nodo) {
        if (nodo.getTipo().equals("i32"))
            if (nodo.getHijoIzquierdo().getLexema().contains("@")){
                this.assemblerCode.append("MOV EAX, "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }else {
                this.assemblerCode.append("MOV EAX, " + "_" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            }
        else {
            if (nodo.getHijoIzquierdo().getLexema().contains("@")){
                assemblerCode.append("FLD "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }else {
                assemblerCode.append("FLD "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }
        }
        this.assemblerCode.append("ret "+"\n");
    }

    private void continueAssembler(Nodo nodo) {
		this.assemblerCode.append("JMP " + this.pilaFor.peek()  + "\n");
    }

    private void continueEtiquetaAssembler(Nodo nodo) {
        String tag = "_" + nodo.getHijoIzquierdo().getLexema().split("@")[0].replace('.','_').replace('-', '_');
        this.assemblerCode.append("JMP " + tag + "\n");
    }

    private void breakAssembler(Nodo nodo) {
        String labelBreak = this.pilaFor.pop();
		this.assemblerCode.append("JMP " + this.pilaFor.peek()  + "\n");
        this.pilaFor.push(labelBreak);
    }

    private void asignacionForAssembler(Nodo nodo){
        String label = "_label" + this.contadorEtiquetaLabel;
		this.contadorEtiquetaLabel++;
        this.pilaFor.push(label);
        this.assemblerCode.append(label+":\n");
    }

    private void condicionForAssembler(Nodo nodo){
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelVerdadero = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelBucle = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        if (nodo.getHijoIzquierdo().getLexema().startsWith("@")){
            this.assemblerCode.append("MOV EAX, "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
        }else {
            this.assemblerCode.append("MOV EAX, " + "_" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
        }
        this.assemblerCode.append("CMP EAX, 0 \n");
        this.assemblerCode.append("JE " + labelFalso + "\n"); // Salta a labelFalso si es Igual
        this.assemblerCode.append("JNE "+ labelVerdadero+"\n"); // Salta a labelVerdadero si NO es Igual
        this.assemblerCode.append(labelBucle+ ":\n"); // Continua el bucle
        String condicionLabelPilaFor = this.pilaFor.pop();
        this.pilaFor.push(labelFalso);
        this.pilaFor.push(labelBucle);
        this.pilaFor.push(labelVerdadero);
        this.pilaFor.push(condicionLabelPilaFor);
    }

    private void operacionForAssembler(Nodo nodo){
        String labelCondicion = this.pilaFor.pop();
        this.assemblerCode.append("JMP "+labelCondicion+"\n");
        String labelVerdadero = this.pilaFor.pop();
        this.assemblerCode.append(labelVerdadero+ ":\n");
    }

    private void cuerpoForAssembler(Nodo nodo){
        String labelBucle = this.pilaFor.pop();
        this.assemblerCode.append("JMP "+labelBucle+"\n");
        String labelFalso = this.pilaFor.pop();
        this.assemblerCode.append(labelFalso+ ":\n");
    }

    private void condicionYOperacionForAssembler(Nodo nodo){

    }

    private void encabezadoForAssembler(Nodo nodo){

    }

    private void etiquetaAssembler(Nodo nodo){
        String tag = "_"+nodo.getHijoIzquierdo().getLexema().split("@")[0].replace('.','_').replace('-', '_');
        this.assemblerCode.append(tag + ":\n");
    }

    private void forEtiquetadoAssembler(Nodo nodo){}

    private void forAssembler(Nodo nodo){

    }

    private void distintoAssembler(Nodo nodo) {

        String aux = "@aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;

        if (nodo.getTipo().equals("i32")) {

            if (nodo.getHijoIzquierdo().getLexema().startsWith("@")){
                this.assemblerCode.append("MOV EAX, "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }else {
                this.assemblerCode.append("MOV EAX, " + "_" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            }
            this.assemblerCode.append("CMP EAX, " + "_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_') + "\n");
            this.assemblerCode.append("JE " + labelFalso + "\n"); // Salta a labelFalso si es Igual
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "i32", "variableAuxiliarCompDistinto");
        } else {

            if(nodo.getTipo().equals("f32")){
            String mem2bytes = "@aux" + contadorAux;
            this.contadorAux++;
            this.tablaSimbolos.agregarRegistroAssembler(mem2bytes, "f32", "variableAuxiliarCompDistinto");
            this.assemblerCode.append("FLD "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FLD "+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FCOMPP "+"\n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV EAX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JE " + labelFalso + "\n");
            this.assemblerCode.append("FLD1 "+"\n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "f32", "variableAuxiliarCompDistinto");
        }
    }
            int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoIzquierdo().getLexema());
            Token t = this.tablaSimbolos.getEntrada(idLexema);
            nodo.setTipo(t.getTipo());
            nodo.setLexema(aux);

    }

    private void igualAssembler(Nodo nodo) {

        String aux = "@aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;


        if (nodo.getTipo().equals("i32")) {
            if (nodo.getHijoIzquierdo().getLexema().startsWith("@")){
                this.assemblerCode.append("MOV EAX, "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }else {
                this.assemblerCode.append("MOV EAX, " + "_" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            }
            this.assemblerCode.append("CMP EAX, " +"_"+ nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_') + "\n");
            this.assemblerCode.append("JNE " + labelFalso + "\n"); // Salta a labelFalso si no es igual
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "i32", "variableAuxiliarCompIgual");

        } else {
            if(nodo.getTipo().equals("f32")){


            String mem2bytes = "@aux" + contadorAux;
            this.contadorAux++;
            this.tablaSimbolos.agregarRegistroAssembler(mem2bytes, "f32", "variableAuxiliarCompIgual");

            this.assemblerCode.append("FLD "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FLD "+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FCOMPP "+ "\n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV AX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JNE " + labelFalso + "\n");
            this.assemblerCode.append("FLD1 "+"\n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "f32", "variableAuxiliarCompIgual");
        }
        }
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoIzquierdo().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        nodo.setTipo(t.getTipo());
        nodo.setLexema(aux);
    }

    private void mayorIgualAssembler(Nodo nodo) {

        String aux = "@aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;

        if (nodo.getTipo().equals("i32")) {
            if (nodo.getHijoIzquierdo().getLexema().startsWith("@")){
                this.assemblerCode.append("MOV EAX, "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }else {
                this.assemblerCode.append("MOV EAX, " + "_" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            }
            this.assemblerCode.append("CMP EAX, " + "_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_') + "\n");
            this.assemblerCode.append("JL " + labelFalso + "\n");
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "i32", "variableAuxiliarCompMayorIgual");

        } else {
            if(nodo.getTipo().equals("f32")){

            String mem2bytes = "@aux" + contadorAux;
            this.contadorAux++;
            this.tablaSimbolos.agregarRegistroAssembler(mem2bytes, "f32", "variableAuxiliarCompMayorIgual");


            this.assemblerCode.append("FLD "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FLD "+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");

            this.assemblerCode.append("FCOMPP \n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV EAX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JB " + labelFalso + "\n");
            this.assemblerCode.append("FLD1 "+"\n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "f32", "variableAuxiliarCompMayorIgual");
        }
    }
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoIzquierdo().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        nodo.setTipo(t.getTipo());
        nodo.setLexema(aux);

    }

    private void menorIgualAssembler(Nodo nodo) {

        String aux = "@aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;

        if (nodo.getTipo().equals("i32")) {
            if (nodo.getHijoIzquierdo().getLexema().startsWith("@")){
                this.assemblerCode.append("MOV EAX, "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }else {
                this.assemblerCode.append("MOV EAX, " + "_" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            }
            this.assemblerCode.append("CMP EAX, " +"_"+ nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_') + "\n");
            this.assemblerCode.append("JG " + labelFalso + "\n"); // Salta a labelFalso si es Mayor
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "i32", "variableAuxiliarCompMenorIgual");
        } else {
            if (nodo.getTipo().equals("f32")){


            String mem2bytes = "@aux" + contadorAux;
            this.contadorAux++;
            this.tablaSimbolos.agregarRegistroAssembler(mem2bytes, "f32", "variableAuxiliarCompMenorIgual");
            this.assemblerCode.append("FLD "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FLD "+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FCOMPP "+"\n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV EAX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JA " + labelFalso + "\n"); // Salta a labelFalso si es Mayor
            this.assemblerCode.append("FLD1\n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "f32", "variableAuxiliarCompMenorIgual");
        }
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoIzquierdo().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        nodo.setTipo(t.getTipo());
        nodo.setLexema(aux);
    }
    }

    private void menorAssembler(Nodo nodo) {

        String aux = "@aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel;
        this.contadorEtiquetaLabel++;
        if (nodo.getTipo().equals("i32")) {
            if (nodo.getHijoIzquierdo().getLexema().startsWith("@")){
                this.assemblerCode.append("MOV EAX, "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }else {
                this.assemblerCode.append("MOV EAX, " + "_" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            }
            this.assemblerCode.append("CMP EAX, " +"_"+ nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_') + "\n");
            this.assemblerCode.append("JGE " + labelFalso + "\n"); // Si es Mayor o Igual, salto a labelFalso
            this.assemblerCode.append("MOV " + aux + ",1 \n"); // Guardo en aux un 1
            this.assemblerCode.append("JMP " + labelContinuar + "\n"); // Continuo la ejecucion
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n"); // Al ser falso, seteo un 0 en aux
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "i32", "variableAuxiliarCompMenor");
        } else {
            if(nodo.getTipo().equals("f32")){


            String mem2bytes = "@aux" + contadorAux;
            this.contadorAux++;
            this.tablaSimbolos.agregarRegistroAssembler(mem2bytes, "f32", "variableAuxiliarCompMenor");
            this.assemblerCode.append("FLD "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FLD "+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FCOMPP \n");
            this.assemblerCode.append("FSTSW " + mem2bytes + "\n");
            this.assemblerCode.append("MOV EAX," + mem2bytes + "\n");
            this.assemblerCode.append("SAHF \n");
            this.assemblerCode.append("JAE " + labelFalso + "\n"); // Si es mayor o Igual, salta a labelFalso
            this.assemblerCode.append("FLD1\n"); // Setea un 1
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("FLDZ \n");
            this.assemblerCode.append("FSTP " + aux + " \n");
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "f32", "variableAuxiliarCompMenor");
        }
    }
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoIzquierdo().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        nodo.setTipo(t.getTipo());
        nodo.setLexema(aux);

    }

    private void mayorAssembler(Nodo nodo) {

        String aux = "@aux" + contadorAux;
        this.contadorAux++;
        String labelContinuar = "_label" + this.contadorEtiquetaLabel; // Casos: (THEN, Condicion TRUE en un FOR, es decir, ejecuta lo de adentro del FOR)
        this.contadorEtiquetaLabel++;
        String labelFalso = "_label" + this.contadorEtiquetaLabel; // Casos: (ELSE, Condicion FALSE en un FOR, es decir corta el FOR)
        this.contadorEtiquetaLabel++;

        if (nodo.getTipo().equals("i32")) {

            if (nodo.getHijoIzquierdo().getLexema().startsWith("@")){
                this.assemblerCode.append("MOV EAX, "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }else {
                this.assemblerCode.append("MOV EAX, " + "_" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            }
            this.assemblerCode.append("CMP EAX, " +"_"+ nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_') + "\n"); // COMPARO LA CONDICION PARA SABER SI ES MAYOR, MENOR O IGUAL
            this.assemblerCode.append("JLE " + labelFalso + "\n"); // Si NO ES MAYOR (CASO JLE CUANDO ES <=) (ELSE O FALSE DE FOR), salto a la etiqueta labelFalso y ejecuto las instrucciones debajo
            this.assemblerCode.append("MOV " + aux + ",1 \n");
            this.assemblerCode.append("JMP " + labelContinuar + "\n");
            this.assemblerCode.append(labelFalso + ":\n");
            this.assemblerCode.append("MOV " + aux + ", 0 \n");
            this.assemblerCode.append(labelContinuar + ":\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "i32", "variableAuxiliarCompMayor");
        } else {
            if (nodo.getTipo().equals("f32")){


                String mem2bytes = "@aux" + contadorAux;
                this.contadorAux++;
                this.tablaSimbolos.agregarRegistroAssembler(mem2bytes, "f32", "variableAuxiliarCompMayor");

                this.assemblerCode.append("FLD " +"_"+ nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_') + "\n");
                this.assemblerCode.append("FLD " +"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_') + "\n");
                this.assemblerCode.append("FCOMPP " + "\n");
                this.assemblerCode.append("FSTSW " + mem2bytes + "\n"); // guarda el valor de la comparacion en mem2bytes
                this.assemblerCode.append("MOV EAX," + mem2bytes + "\n"); // Guardo el valor de mem2bytes (de la comparacion anterior) en EAX
                this.assemblerCode.append("SAHF \n"); // Toma de EAX el estado de la comparacion
                this.assemblerCode.append("JBE " + labelFalso + "\n"); // Si es menor o igual, va al labelFalso
                this.assemblerCode.append("FLD1\n"); // Carga un 1 en ST
                this.assemblerCode.append("FSTP " + aux + " \n"); // Carga el valor de la pila en aux
                this.assemblerCode.append("JMP " + labelContinuar + "\n"); // Continua la ejecucion
                this.assemblerCode.append(labelFalso + ":\n");
                this.assemblerCode.append("FLDZ " + "\n"); // Carga un 0 en ST
                this.assemblerCode.append("FSTP " + aux + " \n"); // Carga el valor de la pila en aux
                this.assemblerCode.append(labelContinuar + ":\n");
                this.tablaSimbolos.agregarRegistroAssembler(aux, "f32", "variableAuxiliarCompMayor");
        }
        }
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoIzquierdo().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        nodo.setTipo(t.getTipo());
        nodo.setLexema(aux);
    }

    private void elseAssembler(Nodo nodo) {

	}

	private void thenAssembler(Nodo nodo) {
		String label = "_label"+contadorEtiquetaLabel;
		contadorEtiquetaLabel++;
		this.assemblerCode.append("JMP "+label + "\n");
		String labelAux= this.pilaControl.pop();
		this.assemblerCode.append(labelAux + ":\n");
		this.pilaControl.push(label);
	}

	private void condicionIfAssembler(Nodo nodo) {
		String label="_label"+contadorEtiquetaLabel;
		contadorEtiquetaLabel++;
		if (nodo.getHijoIzquierdo().getTipo().equals("i32")) {
            if (nodo.getHijoIzquierdo().getLexema().startsWith("@")){
                this.assemblerCode.append("MOV EAX, "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }else {
                this.assemblerCode.append("MOV EAX, " + "_" + nodo.getHijoIzquierdo().getLexema().replace('.', '_').replace('-', '_') + "\n");
            }
			this.assemblerCode.append("CMP EAX,  0"+"\n");
		} else {
            if (nodo.getHijoIzquierdo().getTipo().equals("f32")){
			String mem2bytes= "@aux"+contadorAux;
			this.contadorAux++;

            this.tablaSimbolos.agregarRegistroAssembler(mem2bytes, "f32", "variableAuxiliarCondIf");
			this.assemblerCode.append("FLDZ "+"\n");
            this.assemblerCode.append("FLD "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
			this.assemblerCode.append("FCOMPP "+"\n");
			this.assemblerCode.append("FSTSW " + mem2bytes + "\n" );
			this.assemblerCode.append("MOV AX," + mem2bytes + "\n");
			this.assemblerCode.append("SAHF "+"\n");
		    }
        }
		this.assemblerCode.append("JE " + label + "\n");
		this.pilaControl.push(label);
	}

    private void ifAssembler(Nodo nodo){
        String label=this.pilaControl.pop();
		this.assemblerCode.append(label+":\n");
    }

	private void cuerpoAssembler(Nodo nodo) {
	}

    private void divisionAssembler(Nodo nodo) {

        String aux = "@aux" + contadorAux;
        this.contadorAux++;
        if (nodo.getTipo().equals("i32")) {

            String label = "_label" + contadorEtiquetaLabel;
            contadorEtiquetaLabel++;

            // Controlamos la division por CERO

            this.assemblerCode.append("MOV EAX, "+nodo.getHijoDerecho().getLexema()+"\n");
            this.assemblerCode.append("CMP EAX, 0 \n"); // COMPARA EL VALOR DE EAX CON 0 (MENOR, MAYOR O IGUAL)
            this.assemblerCode.append("JNE " + label + "\n"); // Si no es igual, salto a la etiqueta label correspondiente

            // Es igual a cero, entonces genero un mensaje de error
            this.assemblerCode.append("invoke MessageBox, NULL, addr errorDivCeroEntero, addr errorDivCeroEntero, MB_OK\n"); // DIVISION POR CERO CONTROL
            this.assemblerCode.append("invoke ExitProcess, 0\n");

            // Si no es igual a cero, sigo la ejecucion
            this.assemblerCode.append(label + ":\n");
            this.assemblerCode.append("MOV EAX, "+nodo.getHijoIzquierdo().getLexema()+"\n");
			this.assemblerCode.append("MOV EDX, 0"+"\n");
			this.assemblerCode.append("MOV EBX, "+nodo.getHijoDerecho().getLexema()+"\n");
			this.assemblerCode.append("IDIV EBX\n");
            this.assemblerCode.append("invoke MessageBox, NULL, addr ok, addr ok, MB_OK\n");
			this.assemblerCode.append("MOV "+aux+","+"EAX"+"\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "i32", "variableAuxiliarDiv");
        } else {

            if(nodo.getTipo().equals("f32")){

                String label = "_label" + contadorEtiquetaLabel;
                contadorEtiquetaLabel++;
               // String mem2bytes = "@aux" + contadorAux;
                this.contadorAux++;
                this.assemblerCode.append("FLD "+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_') + "\n");
                this.assemblerCode.append("FLDZ\n");                    // Carga el número 0 en el tope de la pila.
                this.assemblerCode.append("FCOM\n");                    // Compara el tope de ST(0) = 0 con ST(1) = b, a fin de determinar si el divisor es igual a cero.
                this.assemblerCode.append("FSTSW mem2bytes\n"); // Setea el flag
                this.assemblerCode.append("MOV AX, mem2bytes\n"); // Guarda el flag en EAX
                this.assemblerCode.append("SAHF\n");                    // Almaceno en los 8 bits menos significativos del registro de indicadores el valor de AH, tomado de EAX (estado de la comparación).
                this.assemblerCode.append("JE " + label + "\n"); // Si no es igual a cero, salta a la etiqueta label correspondiente, sino tira mensaje de error
                this.assemblerCode.append("invoke MessageBox, NULL, addr errorDivCeroFlotante, addr errorDivCeroFlotante, MB_OK\n");
                this.assemblerCode.append("invoke ExitProcess, 0\n");

                // Si no es cero

                this.assemblerCode.append(label + ":\n");
                if (nodo.getHijoIzquierdo().getLexema().startsWith("@")){
                    this.assemblerCode.append("FLD "+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_') + "\n");
                }else {
                    this.assemblerCode.append("FLD " + "_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_') + "\n");
                }
                this.assemblerCode.append("FIDIV " + "_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_') + "\n");
                this.assemblerCode.append("FSTP " + aux + "\n");
                this.tablaSimbolos.agregarRegistroAssembler(aux, "f32", "variableAuxiliarDiv");
             //  this.tablaSimbolos.agregarRegistroAssembler(mem2bytes, "f32", "variableAuxiliarDiv");

            }
        }
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoIzquierdo().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        nodo.setTipo(t.getTipo());
        nodo.setLexema(aux);

    }

    private void multiplicacionAssembler(Nodo nodo) {

        String aux = "@aux" + contadorAux;
        this.contadorAux++;
        if (nodo.getTipo().equals("i32")) {

            this.assemblerCode.append("MOV "+"EAX"+","+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("IMUL "+"EAX"+","+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("MOV "+aux+","+"EAX"+"\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "i32", "variableAuxiliarMult");

        }else{
            if (nodo.getTipo().equals("f32")){
                this.assemblerCode.append("FLD "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
                this.assemblerCode.append("FIMUL" +"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+ "\n");
                this.assemblerCode.append("FSTP "+aux+"\n");
                this.tablaSimbolos.agregarRegistroAssembler(aux, "f32", "variableAuxiliarMult");
            }
        }
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoIzquierdo().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        nodo.setTipo(t.getTipo());
        nodo.setLexema(aux);
    }

    private void restaAssembler(Nodo nodo) {

        String aux = "@aux" + contadorAux;
        this.contadorAux++;
        if (nodo.getTipo().equals("i32")) {

            this.assemblerCode.append("MOV "+"EAX"+","+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("SUB "+"EAX"+","+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("MOV "+aux+","+"EAX"+"\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "i32", "variableAuxiliarSub");
        }else{
            this.assemblerCode.append("FLD " + "_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FSUB "+ "_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("FSTP "+aux+"\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "f32", "variableAuxiliarSub");
        }
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoIzquierdo().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        nodo.setTipo(t.getTipo());
        nodo.setLexema(aux);

    }

    private void sumaAssembler(Nodo nodo) {
        String aux = "@aux" + contadorAux;
        this.contadorAux++;
        if (nodo.getTipo().equals("i32")) {

            this.assemblerCode.append("MOV "+"EAX"+","+ ""+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_') + "\n");
            this.assemblerCode.append("ADD "+"EAX"+","+ ""+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
            this.assemblerCode.append("MOV "+aux+","+"EAX"+"\n");
            this.tablaSimbolos.agregarRegistroAssembler(aux, "i32", "variableAuxiliarAdd");
        }else{
            if (nodo.getTipo().equals("f32")) {
                if (nodo.getHijoIzquierdo().getLexema().startsWith("@")){
                    this.assemblerCode.append("FLD " +nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_') + "\n");
                }else {
                    this.assemblerCode.append("FLD " + "_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_') + "\n");
                }
                this.assemblerCode.append("FADD " +"_"+ nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_') + "\n");
                this.assemblerCode.append("FSTP "+ aux + "\n");
                this.tablaSimbolos.agregarRegistroAssembler(aux, "f32", "variableAuxiliarAdd");
            }
        }
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoIzquierdo().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        nodo.setTipo(t.getTipo());
        nodo.setLexema(aux);
    }

    private void paramAssembler(Nodo nodo){
        if (nodo.getHijoDerecho() != null){
            if (nodo.getHijoDerecho().getTipo().equals("i32")){
                this.assemblerCode.append("NODO:"+nodo.getHijoDerecho().getLexema()+"\n");
                this.assemblerCode.append("MOV EBX, "+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
            } else {
                this.assemblerCode.append("FLD "+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
            }
        }
        if (nodo.getHijoIzquierdo().getTipo().equals("i32")){
            this.assemblerCode.append("MOV EAX, "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
        } else {
            this.assemblerCode.append("FLD "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
        }
    }

    private void asignacionAssembler(Nodo nodo) {
        int idLexema = this.tablaSimbolos.existeEntrada(nodo.getHijoDerecho().getLexema());
        Token t = this.tablaSimbolos.getEntrada(idLexema);
        if ((t.getUso() != null) && t.getUso().equals("func")){

            // chequeamos por recursion directa
            if (!this.pilaInvocaciones.isEmpty() && this.pilaInvocaciones.peek().equals(t.getLexema())){
                this.assemblerCode.append("invoke MessageBox, NULL, addr errorRecursion, addr program, MB_OK\n"); // DIVISION POR CERO CONTROL
                this.assemblerCode.append("invoke ExitProcess, 0\n");
            }

            // cargamos los parametros
            if (!nodo.getHijoDerecho().esHoja()){
                if (nodo.getHijoDerecho().getHijoIzquierdo().getHijoDerecho() != null){
                    if (nodo.getHijoDerecho().getHijoIzquierdo().getHijoDerecho().getTipo().equals("i32")){



                        this.assemblerCode.append("MOV EBX, "+"_"+nodo.getHijoDerecho().getHijoIzquierdo().getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
                    } else {
                        this.assemblerCode.append("FLD "+""+nodo.getHijoDerecho().getHijoIzquierdo().getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
                    }
                }
                if (nodo.getHijoDerecho().getHijoIzquierdo().getHijoIzquierdo().getTipo().equals("i32")){
                    this.assemblerCode.append("MOV EAX, "+"_"+nodo.getHijoDerecho().getHijoIzquierdo().getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
                } else {
                    this.assemblerCode.append("FLD "+""+nodo.getHijoDerecho().getHijoIzquierdo().getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
                }
            }

            this.pilaInvocaciones.push(t.getLexema());
            this.assemblerCode.append("call _"+t.getLexema().replace('.','_').replace('-', '_')+"\n");

            if (nodo.getTipo().equals("i32")){
                this.assemblerCode.append("MOV "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+","+"EAX"+"\n");
            } else {
                this.assemblerCode.append("FSTP "+""+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
            }

            this.pilaInvocaciones.pop();
        }
        else {
            if (nodo.getHijoIzquierdo().getTipo().equals("i32")) { // a(izq) =:(raiz) 1(der); // aca puede traer conflictos el _ en algunos aux
                if (nodo.getHijoIzquierdo().getLexema().contains("@")){
                    this.assemblerCode.append("MOV "+"EAX"+","+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n"); // 1
                }else {
                    this.assemblerCode.append("MOV "+"EAX"+","+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n"); // 1
                }
				this.assemblerCode.append("MOV "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+","+"EAX"+"\n"); // a =: 1
            }
            else{
                if (nodo.getHijoIzquierdo().getTipo().equals("f32")) { // a(izq) =: (raiz) 1.5(der)
                    if (nodo.getHijoIzquierdo().getLexema().contains("@")){
                        this.assemblerCode.append("FLD "+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
                    }else {
                        this.assemblerCode.append("FLD "+""+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
                    }
                    this.assemblerCode.append("FSTP "+"_"+nodo.getHijoIzquierdo().getLexema().replace('.','_').replace('-', '_')+"\n");
                }
            }
        }
    }

    private void cargarVariablesAuxiliares(int indice) { // En los parametros deberiamos pasarle para poder acceder a los tipos (nodo ??)
        Token t = tablaSimbolos.getEntrada(indice);

            if (tablaSimbolos.getTipoToken(indice).equals("CADENA DE CARACTERES")) {
                this.datosPrecarga.append(""+t.getLexema().replace('.','_').replace('-', '_'));
                this.datosPrecarga.append(" db '" + t.getLexema() + "'" + ",0");
                this.datosPrecarga.append("\n");
            } else {
                if (!(t.getId() == 258)){
                    if(t.getLexema().contains("@aux")){
                        this.datosPrecarga.append(t.getLexema());
                    }else{
                        this.datosPrecarga.append("_" + t.getLexema().replace('.', '_').replace('-', '_'));
                    }
                    this.datosPrecarga.append(" dd ?,?");
                    this.datosPrecarga.append("\n");
                }
                else{
                    if(t.getLexema().contains(".")){
                        this.datosPrecarga.append("_"+t.getLexema().replace('.', '_').replace('-', '_'));
                        this.datosPrecarga.append(" dd ?,?");
                        this.datosPrecarga.append("\n");
                    }
                }
            }


    }

    private void cargarTablaSimbolos() {
        for (int i = 0; i < tablaSimbolos.size(); i++){
            cargarVariablesAuxiliares(i);
        }
        this.datosPrecarga.append(".code\r\n");
    }





    // -------------------- GENERACION DE CODIGO (SALIDA) -------------------- //

    public void generacionDeCodigo(Nodo nodo) {
        try {
           String ruta = "salida_archivo/codigo_generado_assembler.asm";
           String contenido;
           StringBuilder code = new StringBuilder();
           File file = new File(ruta);

           // Si el archivo no existe es creado

           if (!file.exists()) {
               file.createNewFile();
           }

           code.append(";------------ CODE ------------\r\n");
           this.cargarLibrerias(); // Carga el encabezado de assembler importando las librerias necesarias.
           this.generarCodigoLeido(nodo); // Carga el codigo completothis.cargarTablaSimbolos();
           this.cargarTablaSimbolos();// Cargar las variables auxiliares (IMPLEMENTAR)
           this.assemblerCode.append(";------------ FIN ------------\n");
           this.assemblerCode.append("invoke ExitProcess, 0\n");
			this.assemblerCode.append("end start");

           FileWriter fw = new FileWriter(file);
           BufferedWriter bw = new BufferedWriter(fw);
           // Leo el codigo fuente
           contenido = this.inicio.toString()+this.datosPrecarga.toString()+code.toString();
           bw.write(contenido);
           contenido = this.assemblerCode.toString();
           bw.write(contenido);
           bw.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
