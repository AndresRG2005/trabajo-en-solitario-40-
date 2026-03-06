package SistemaNotasUsuario.src.service;

import java.io.IOException;
import java.nio.file.*;

public class FileManager {

    private final Path dataPath = Path.of("data");
    private final Path usersFile = dataPath.resolve("users.txt");
    private final Path usuariosPath = dataPath.resolve("usuarios");

    public FileManager() throws IOException {
        inicializar();
    }

    private void inicializar() throws IOException {
        if (!Files.exists(dataPath)) Files.createDirectories(dataPath);
        if (!Files.exists(usersFile)) Files.createFile(usersFile);
        if (!Files.exists(usuariosPath)) Files.createDirectories(usuariosPath);
    }

    public Path getUsersFile() {
        return usersFile;
    }

    public Path getNotasFileForUser(String email) throws IOException {
        String carpeta = email.replace("@", "_").replace(".", "_");
        Path userFolder = usuariosPath.resolve(carpeta);

        if (!Files.exists(userFolder)) Files.createDirectories(userFolder);

        Path notasFile = userFolder.resolve("notas.txt");
        if (!Files.exists(notasFile)) Files.createFile(notasFile);

        return notasFile;
    }
}


