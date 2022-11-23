package Main;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import AnalizadorLexico.*;


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


                AnalizadorLexico lexico = new AnalizadorLexico(archivoEntrada);

                while (!lexico.isCodigoLeido() )
                    lexico.procesarYylex();

                ArrayList<Token> detectados = lexico.getListaTokens();

                if (detectados.isEmpty()) { // Si esta vacio, no se detectan tokens
                    System.out.println("!!! ---------------ANALIZADOR LÉXICO--------------- !!!");
                    System.out.println("|--- TOKENS ---|");
                    System.out.println("|--- No se detectaron tokens dentro del archivo. ! --|-");
                }else{
                    System.out.println("(Info) ---------------ANALIZADOR LÉXICO--------------- (Info)");
                    for (Token t : detectados) {

                        System.out.println("Tipo de token --> " + t.getId());
                        System.out.println("Lexema --> " + t.getLexema());
                    }
                }

                System.out.println("!!! ____________________________________________________________ !!!");
                System.out.println("ERRORES");
               // lexico.imprimirErrores();

                System.out.println("(Tabla) ---------------TABLA DE SIMBOLOS--------------- (Tabla)");
                System.out.println("|-- TABLA DE SIMBOLOS --|");
                lexico.imprimirTablaSimbolos();
            }
        } catch(IOException e) {
            System.out.println("ERROR: El archivo que se indica en la ruta ingresada no existe.");
        }


    }
}