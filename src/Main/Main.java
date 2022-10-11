package Main;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorSintactico.AnalizadorSintactico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Levantar archivo con el codigo

        try {
            System.out.println("|--- Ingrese la ruta del archivo a analizar: ---|");

            try (
                Scanner scanner = new Scanner(System.in)) {
                String path = scanner.next();
                String archivoEntrada = Files.readString(Paths.get(path));

                Parser parser = new Parser();
                AnalizadorLexico lexico = new AnalizadorLexico(archivoEntrada);
                AnalizadorSintactico sintactico = new AnalizadorSintactico(lexico, parser);

                sintactico.start();

            }
        } catch (IOException e) {
            System.out.println("ERROR: El archivo que se indica en la ruta ingresada no existe.");
        }
    }
}