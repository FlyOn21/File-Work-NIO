package app.utils;

import app.exceptions.DirectoryAccessException;
import app.exceptions.DirectoryCreateException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.stream.Stream;

public class FilesGetUtil {
    public Path path;

    public FilesGetUtil(Path path) {
        this.path = path;
    }

    public String stringToPrintExistFiles() {
        StringBuilder listFilesBuffer = new StringBuilder();
        listFilesBuffer.append("------------ EXIST FILES --------------\n\n");
        try {
            directoryFiles().forEach(
                    (fileName, filePath) -> listFilesBuffer
                            .append("=============================================\n")
                            .append("File name: ").append(fileName).append("\n")
                            .append("File path: ").append(filePath).append("\n")
                            .append("=============================================\n")
            );
        } catch (DirectoryCreateException e) {
            e.printStackTrace();
        }
        return listFilesBuffer.toString();
    }

    private void addFilesFromDirectory(Path path, Hashtable<String, String> filesTable) {
        //Return walk files tree
        try (Stream<Path> paths = Files.walk(path)) {
            // filter if obj is regular file
            paths.filter(Files::isRegularFile).forEach(file -> {
                // return relativePath current file
                Path relativePath = path.relativize(file);
                filesTable.put(relativePath.toString(), file.toString());
            });
        } catch (IOException e) {
            throw new DirectoryAccessException("Error accessing directory: " + path);
        }
    }

    public Hashtable<String, String> directoryFiles() throws DirectoryCreateException {
        Hashtable<String, String> filesTable = new Hashtable<>();
        addFilesFromDirectory(this.path, filesTable);
        return filesTable;
    }
}
