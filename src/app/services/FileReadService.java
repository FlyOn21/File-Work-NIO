package app.services;

import app.config.Config;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.stream.Stream;


public class FileReadService {
    public String readFile(Path path) {
        StringBuilder content = new StringBuilder();
        try (Stream<String> dataStream = Files.lines(path, Config.ENCODING)){
            dataStream.forEach(line -> content.append(line).append("\n"));
        } catch (IOException e) {
            System.out.println("Problem with read file... Maybe file is broken");
            e.printStackTrace();
        }
        return content.toString();
    }
}