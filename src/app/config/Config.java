package app.config;

import app.exceptions.DirectoryCreateException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;


public class Config {
    private static final String FOLDER_NAME = "files";
    public static final Path BASE_DIRECTORY = makeBaseDirectory();
    public static final String FILE_EXTENSION = ".txt";
    public static final Charset ENCODING = StandardCharsets.UTF_8;

    private static Path makeBaseDirectory() {
        String userDir = System.getProperty("user.dir");
        String systemSeparator = FileSystems.getDefault().getSeparator();
        String baseDirectory = userDir + systemSeparator + FOLDER_NAME;
        Path baseDirectoryPath = Paths.get(baseDirectory);
        try {
            Files.createDirectories(baseDirectoryPath);
        } catch (IOException err) {
            throw new DirectoryCreateException("Failed to create directory for files. Check permissions.", err);
        }
        return baseDirectoryPath;
    }
}
