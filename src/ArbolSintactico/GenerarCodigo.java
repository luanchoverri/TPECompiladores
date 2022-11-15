package ArbolSintactico;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import AnalizadorLexico.TablaSimbolos;

public class GenerarCodigo{

    private StringBuilder assemblerCode; //  Usamos StringBuilder ya que la cadena de caracteres va a cambiar frecuentemente, ya que hacemos cada caso dentro de esta. (no sincro)
    private StringBuilder inicio;
    private TablaSimbolos tablaDeSimbolos;
    private int contadorEtiquetaLabel=0;

    public GenerarCodigo(TablaSimbolos tablaDeSimbolos){
        this.assemblerCode = new StringBuilder("");
        this.inicio = new StringBuilder("");
        this.tablaDeSimbolos = tablaDeSimbolos;
    }



    private void cargarLibrerias () {
		this.inicio.append(".386\r\n" +
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
				".data\n"+
                "errorOverflow db 'ERROR EN LA EJECUCION: Overflow de de datos de punto flotante (f32)',0 \n" +
				"errorDivCeroEntero db 'ERROR EN LA EJECUCION: Division por cero para datos enteros',0 \n" +
                "errorDivCeroFlotante db 'ERROR EN LA EJECUCION: Division por cero para datos de punto flotante',0 \n" +
				"errorRecursion db 'ERROR EN LA EJECUCION: Recursión en invocaciones de funciones',0 \n" +
                "flagTry dw 0,0\n");

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

                }
            }
        }
    }

    private void continueAssembler(Nodo nodo) {
    }



    private void distintoAssembler(Nodo nodo) {
    }



    private void igualAssembler(Nodo nodo) {
    }



    private void mayorIgualAssembler(Nodo nodo) {
    }



    private void menorIgualAssembler(Nodo nodo) {
    }



    private void menorAssembler(Nodo nodo) {
    }



    private void mayorAssembler(Nodo nodo) {
    }



    private void breakAssembler(Nodo nodo) {
    }



    private void elseAssembler(Nodo nodo) {
    }



    private void thenAssembler(Nodo nodo) {
    }



    private void ifAssembler(Nodo nodo) {
    }



    private void divisionAssembler(Nodo nodo) {
    }



    private void multiplicacionAssembler(Nodo nodo) {
    }



    private void restaAssembler(Nodo nodo) {
    }



    private void sumaAssembler(Nodo nodo) {

    }

    private void asignacionAssembler(Nodo nodo) {
       // if (this.tablaDeSimbolos.get(nodo.getHijoDerecho().getLexema()).getUso()=="Nombre de funcion") {
			//if (nodo.getHijoDerecho().getPadre()==null) {
				// Chequeo de recursion mutua????
				String label="_label"+contadorEtiquetaLabel;
				contadorEtiquetaLabel++;
				this.assemblerCode.append("pop AX" + "\n");
				this.assemblerCode.append("CMP AX,"+nodo.getHijoDerecho().getValor() + "\n");
				this.assemblerCode.append("JNE " + label + "\n");
				this.assemblerCode.append("invoke MessageBox, NULL, addr error, addr error, MB_OK" + "\n");
				this.assemblerCode.append("invoke ExitProcess, 0" + "\n");
				this.assemblerCode.append(label+": \n");
				this.assemblerCode.append("push "+nodo.getValor()+"\n");

				if (nodo.getHijoIzquierdo().getTipo()=="f32") {
					//this.assemblerCode.append("MOV EAX,"+nodo.getHijoDerecho().getParametro()+"\n");
					//if ((this.tablaDeSimbolos.get(nodo.getHijoDerecho().getLexema()).getP()==null)) {
						this.assemblerCode.append("call _"+nodo.getHijoDerecho().getLexema());}
					else {
				//		this.assemblerCode.append("call _"+this.tablaDeSimbolos.get(nodo.getHijoDerecho().getLexema()).getP()+"\n");}
					this.assemblerCode.append("MOV "+nodo.getHijoIzquierdo().getLexema()+",EAX" + "\n");
				}
			//	else {
				//	this.assemblerCode.append("FLD "+nodo.getHijoDerecho().getParametro()+"\n");
				//	if ((this.tablaDeSimbolos.get(nodo.getHijoDerecho().getLexema()).getP()==null)) {
			//			this.assemblerCode.append("call _"+nodo.getHijoDerecho().getLexema());}
				//	else {
					//	this.assemblerCode.append("call _"+this.tablaDeSimbolos.get(nodo.getHijoDerecho().getLexema()).getP()+"\n");}
					this.assemblerCode.append("FSTP "+nodo.getHijoIzquierdo().getLexema()+"\n");
			//	}
				this.assemblerCode.append("pop BX"+"\n");
		//	}
		//	else
			{
			//	this.tablaDeSimbolos.get(nodo.getHijoIzquierdo().getLexema()).setP(nodo.getHijoDerecho().getLexema());
			//	this.tablaDeSimbolos.get(nodo.getHijoIzquierdo().getLexema()).setUso("Nombre de funcion");
			}
	//	}
	//	else {
			if (nodo.getHijoIzquierdo().getTipo()=="f32") { // Asignacion de tipo PUNTO FLOTANTE (32 BITS)
				this.assemblerCode.append("MOV EAX"+","+nodo.getHijoDerecho().getLexema()+"\n");
				this.assemblerCode.append("MOV "+nodo.getHijoIzquierdo().getLexema()+","+"EAX"+"\n");
            }
			else{
			//	if(this.tablaDeSimbolos.get(nodo.getHijoDerecho().getLexema()).getUso()=="VS"){
					this.assemblerCode.append("FLD "+"_"+nodo.getHijoDerecho().getLexema().replace('.','_').replace('-', '_')+"\n");
					this.assemblerCode.append("FSTP "+nodo.getHijoIzquierdo().getLexema()+"\n");
				}
			//	else{
					this.assemblerCode.append("FLD "+nodo.getHijoDerecho().getLexema()+"\n");
					this.assemblerCode.append("FSTP "+nodo.getHijoIzquierdo().getLexema()+"\n");
			//	}
			//}
	//	}
    }




















    // -------------------- GENERACION DE CODIGO (SALIDA) -------------------- //

    public void generacionDeCodigo() {
        try {
            String ruta = "codigo_generado_assembler.asm";
            String contenido;
            File file = new File(ruta);

            // Si el archivo no existe es creado

            if (!file.exists()) {
                file.createNewFile();
            }
            this.cargarLibrerias(); // Carga el encabezado de assembler importando las librerias necesarias.
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            // Leo el codigo fuente
            contenido = this.inicio.toString();
            bw.write(contenido);
            contenido = this.assemblerCode.toString();
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
