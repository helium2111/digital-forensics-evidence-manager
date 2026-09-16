package util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final Path dataDirectory;

    public FileManager(String directory) {
        dataDirectory = Paths.get(directory);
        try {
            Files.createDirectories(dataDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create data directory.", e);
        }
    }

    public List<String> readLines(String fileName) {
        Path path = dataDirectory.resolve(fileName);
        try {
            if (!Files.exists(path)) return new ArrayList<>();
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Warning: Could not read " + path);
            return new ArrayList<>();
        }
    }

    public void writeLines(String fileName, List<String> lines) {
        Path path = dataDirectory.resolve(fileName);
        try {
            Files.write(path, lines, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not save " + path, e);
        }
    }
}
