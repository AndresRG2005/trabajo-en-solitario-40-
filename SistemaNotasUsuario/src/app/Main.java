package app;

import service.*;
import model.Nota;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {

        FileManager fileManager;
        try {
            fileManager = new FileManager();
        } catch (IOException e) {
            System.out.println("Error inicializando sistema de ficheros.");
            return;
        }

        UserManager userManager = new UserManager(fileManager);
        NoteManager noteManager = new NoteManager(fileManager);

        boolean salir = false;

        while (!salir) {
            System.out.println("==================================");
            System.out.println("   SISTEMA DE NOTAS POR USUARIO   ");
            System.out.println("==================================");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            String opcion = SC.nextLine().trim();

            switch (opcion) {
                case "1" -> registrar(userManager);
                case "2" -> {
                    String email = login(userManager);
                    if (email != null) menuUsuario(email, noteManager);
                }
                case "0" -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }
}
