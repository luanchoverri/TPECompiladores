package ArbolSintactico;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import AnalizadorLexico.TablaSimbolos;

public class GenerarCodigo{

    private StringBuilder assamblerCode; //  Usamos StringBuilder ya que la cadena de caracteres va a cambiar frecuentemente, ya que hacemos cada caso dentro de esta. (no sincro)
    private StringBuilder inicio;
    private TablaSimbolos tablaDeSimbolos;

    public GenerarCodigo(TablaSimbolos tablaDeSimbolos){
        this.assamblerCode = new StringBuilder("");
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


//    public void generarCodigoLeido(Nodo nodo){
//        switch (nodo.getLexema()) { // switch case de prueba
//
//            case ("=:"):
//              asignacionAssembler(nodo);
//            break;
//
//            case ("+"):
//              sumaAssembler(nodo);
//            break;
//        }
//    }

    private void sumaAssembler(Nodo nodo) {

    }

    private void asignacionAssembler(Nodo nodo) {

    }


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
            contenido = this.assamblerCode.toString();
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
