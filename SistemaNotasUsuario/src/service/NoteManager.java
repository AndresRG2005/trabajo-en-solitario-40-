package service;

import model.Nota;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class NoteManager {

    private final FileManager fm;

    public NoteManager(FileManager fm) {
        this.fm = fm;
    }

    public void crearNota(String email, String titulo, String contenido) throws IOException {
        Path notasFile = fm.getNotasFileForUser(email);
        String linea = titulo + ";" + contenido + System.lineSeparator();

        try (var writer = Files.newBufferedWriter(notasFile, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(linea);
        }
    }

    public List<Nota> leerNotas(String email) throws IOException {
        Path notasFile = fm.getNotasFileForUser(email);
        List<Nota> notas = new ArrayList<>();

        try (var reader = Files.newBufferedReader(notasFile, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] p = linea.split(";", 2);
                if (p.length == 2) notas.add(new Nota(p[0], p[1]));
            }
        }
        return notas;
    }
}