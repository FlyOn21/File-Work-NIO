package app.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import app.config.Config;

public class FileWriteService {
    public void writeToFile(Path writePath, String content, boolean isAdd) {
        try {
            String newLine = content + System.lineSeparator();
            if (isAdd) {
                Files.writeString(writePath, newLine, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            } else {
                Files.writeString(writePath, newLine, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println("Problem with writing file... Check permission to write");
            e.printStackTrace();
        }
    }

    public static Path makeNewFilePath(String newFileName) {
        return Config.BASE_DIRECTORY.resolve(newFileName);
    }
}
