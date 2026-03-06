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

}

