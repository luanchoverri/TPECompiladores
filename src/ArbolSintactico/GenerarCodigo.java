package ArbolSintactico;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class GenerarCodigo{

    private StringBuilder assamblerCode; //  Usamos StringBuilder ya que la cadena de caracteres va a cambiar frecuentemente, ya que hacemos cada caso dentro de esta. (no sincro)
    private HashMap<String, Integer> tablaDeSimbolos = new HashMap<>();

    public GenerarCodigo(HashMap<String,Integer> tablaDeSimbolos){
        this.assamblerCode = new StringBuilder("");
        this.tablaDeSimbolos = tablaDeSimbolos;
    }

    public void generarCodigoLeido(Nodo nodo){
        switch (nodo.getLexema()) { // switch case de prueba

            case ("=:"):
              asignacionAssembler(nodo);
            break;

            case ("+"):
              sumaAssembler(nodo);
            break;
        }
    }

    private void sumaAssembler(Nodo nodo) {

    }

    private void asignacionAssembler(Nodo nodo) {

    }


    public void imprimirSalida() {
        try {
            String ruta = "salida_archivo/salida_assembler.txt";
            String contenido;
            File file = new File(ruta);

            // Si el archivo no existe es creado

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            // Leo el codigo fuente
            String horaActual = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a").format(LocalDateTime.now());
            bw.write("Hora de ejecucion: " + horaActual);
            bw.write("\n" + "\n" + "\n" + "\n" + "-----------------------------------------------------------------" + "\n" + "\n");
            contenido = "|--- CODIGO ASSEMBLER ---|" + "\n" + "\n" + "\n";
            bw.write(contenido);
            bw.write("\n" + "\n"+ "-----------------------------------------------------------------" + "\n");
            contenido = this.assamblerCode.toString();
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
