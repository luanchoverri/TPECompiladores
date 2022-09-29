package Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Atributo;

public class ConsumidorTokens {
    public static void main(String[] args) {

        // Levantar archivo con el codigo

        try {
            System.out.println("|--- ANALIZADOR LÉXICO ---|");
            System.out.println("|--- Ingrese la ruta del archivo a analizar: ---|");

            try (// Utilizamos la clase Scanner
            Scanner scanner = new Scanner(System.in)) {
                String path = scanner.next();
                String archivoEntrada = Files.readString(Paths.get(path));

                AnalizadorLexico analizadorLexico = new AnalizadorLexico(archivoEntrada);

                while (analizadorLexico.isCodigoLeido() == false)
                    analizadorLexico.procesarYylex();
                ArrayList<Atributo> tokens = analizadorLexico.getListaTokens();

                if (tokens.isEmpty()) { // Si esta vacio, no se detectan tokens
                    System.out.println("!!! ---------------ANALIZADOR LÉXICO--------------- !!!");
                    System.out.println("|--- TOKENS ---|");
                    System.out.println("|--- No se detectaron tokens dentro del archivo. ! --|-");
                }

                for (Atributo token : tokens) {
                    System.out.println("(Info) ---------------ANALIZADOR LÉXICO--------------- (Info)");
                    System.out.println("Tipo de token --> " + token.getIdToken()); // Devuelve el id con el tipo de token?
                    System.out.println("Lexema --> " + token.getLexema());
                }

                System.out.println("!!! ---------------ANALIZADOR LÉXICO--------------- !!!");
                System.out.println("ERRORES");
                analizadorLexico.imprimirErrores();

                System.out.println("(Tabla) ---------------ANALIZADOR LÉXICO--------------- (Tabla)");
                System.out.println("|-- TABLA DE SIMBOLOS --|");
                analizadorLexico.imprimirTablaSimbolos();
            }
        } catch(IOException e) {
            System.out.println("ERROR: El archivo que se indica en la ruta ingresada no existe.");
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /* String buffer = "1.17549435F-38";
        if (buffer.contains("F") && !buffer.endsWith("F")){
            buffer = buffer.replace("F","e");
            buffer += "f";
        }
        try {
            float floatBuffer = Float.parseFloat(buffer);
            if ((!(1.17549435e-38 < floatBuffer) || !(floatBuffer < 3.40282347e+38) || !(-3.40282347e+38 < floatBuffer) || !(floatBuffer < -1.17549435e-38))){
                throw new Exception("out of range");
            }
        } catch (Exception e){
            e.printStackTrace(); //fuera de rango
        }*/

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}