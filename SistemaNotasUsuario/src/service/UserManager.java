package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class UserManager {

    private final FileManager fm;

    public UserManager(FileManager fm) {
        this.fm = fm;
    }

    public boolean registrarUsuario(String email, String password) throws IOException {
        if (existeUsuario(email)) return false;

        String linea = email + ";" + password + System.lineSeparator();

        try (var writer = Files.newBufferedWriter(fm.getUsersFile(), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(linea);
        }

        fm.getNotasFileForUser(email);
        return true;
    }

    public boolean login(String email, String password) throws IOException {
        try (var reader = Files.newBufferedReader(fm.getUsersFile(), StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] p = linea.split(";");
                if (p.length == 2 && p[0].equals(email) && p[1].equals(password)) return true;
            }
        }
        return false;
    }

    private boolean existeUsuario(String email) throws IOException {
        try (var reader = Files.newBufferedReader(fm.getUsersFile(), StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith(email + ";")) return true;
            }
        }
        return false;
    }
}



