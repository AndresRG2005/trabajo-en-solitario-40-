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

        System.out.println("Hasta luego.");
    }

    private static void registrar(UserManager userManager) {
        System.out.println("---- REGISTRO ----");
        System.out.print("Email: ");
        String email = SC.nextLine().trim();
        System.out.print("Contraseña: ");
        String password = SC.nextLine().trim();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Email y contraseña no pueden estar vacíos.");
            return;
        }

        try {
            boolean ok = userManager.registrarUsuario(email, password);
            System.out.println(ok ? "Usuario registrado." : "El usuario ya existe.");
        } catch (IOException e) {
            System.out.println("Error registrando usuario.");
        }
    }

    private static String login(UserManager userManager) {
        System.out.println("---- LOGIN ----");
        System.out.print("Email: ");
        String email = SC.nextLine().trim();
        System.out.print("Contraseña: ");
        String password = SC.nextLine().trim();

        try {
            boolean ok = userManager.login(email, password);
            if (ok) {
                System.out.println("Login correcto.");
                return email;
            } else {
                System.out.println("Credenciales incorrectas.");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error en login.");
            return null;
        }
    }

    private static void menuUsuario(String email, NoteManager noteManager) {
        boolean cerrar = false;

        while (!cerrar) {
            System.out.println("==================================");
            System.out.println("   MENÚ DE USUARIO - " + email);
            System.out.println("==================================");
            System.out.println("1. Crear nota");
            System.out.println("2. Listar notas");
            System.out.println("3. Ver nota por número");
            System.out.println("4. Eliminar nota");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");

            String opcion = SC.nextLine().trim();

            switch (opcion) {
                case "1" -> crearNota(email, noteManager);
                case "2" -> listarNotas(email, noteManager);
                case "3" -> verNota(email, noteManager);
                case "4" -> eliminarNota(email, noteManager);
                case "0" -> cerrar = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void crearNota(String email, NoteManager noteManager) {
        System.out.println("---- CREAR NOTA ----");
        System.out.print("Título: ");
        String titulo = SC.nextLine().trim();
        System.out.print("Contenido: ");
        String contenido = SC.nextLine().trim();

        if (titulo.isEmpty() || contenido.isEmpty()) {
            System.out.println("No puede haber campos vacíos.");
            return;
        }

        try {
            noteManager.crearNota(email, titulo, contenido);
            System.out.println("Nota creada.");
        } catch (IOException e) {
            System.out.println("Error creando nota.");
        }
    }

    private static void listarNotas(String email, NoteManager noteManager) {
        System.out.println("---- LISTAR NOTAS ----");
        try {
            List<Nota> notas = noteManager.leerNotas(email);
            if (notas.isEmpty()) {
                System.out.println("No hay notas.");
                return;
            }
            int i = 1;
            for (Nota n : notas) {
                System.out.println(i + ". " + n.titulo());
                i++;
            }
        } catch (IOException e) {
            System.out.println("Error leyendo notas.");
        }
    }

    private static void verNota(String email, NoteManager noteManager) {
        listarNotas(email, noteManager);
        System.out.print("Número de nota: ");
        try {
            int index = Integer.parseInt(SC.nextLine()) - 1;
            Nota n = noteManager.leerNotas(email).get(index);
            System.out.println("Título: " + n.titulo());
            System.out.println("Contenido: " + n.contenido());
        } catch (Exception e) {
            System.out.println("Índice inválido.");
        }
    }

    private static void eliminarNota(String email, NoteManager noteManager) {
        listarNotas(email, noteManager);
        System.out.print("Número de nota a eliminar: ");
        try {
            int index = Integer.parseInt(SC.nextLine()) - 1;
            noteManager.eliminarNota(email, index);
            System.out.println("Nota eliminada.");
        } catch (Exception e) {
            System.out.println("Índice inválido.");
        }
    }
}
