package app.views;

import app.config.Config;
import app.services.FileReadService;
import app.utils.FilesGetUtil;

import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Scanner;

public class ReadView {
    private static final String readMenu = """
            ------------ READ --------------
            Choice 1 => exist files list
            Choice 2 => read file
            Choice 3 => to previous menu
            """;
    private final FileReadService readService = new FileReadService();

    public void readViewProcessing(Scanner scanner, FilesGetUtil existsFiles) {
        while (true) {
            System.out.println(readMenu);
            System.out.print("Input your choice: ");
            String readChoice = scanner.nextLine();
            switch (readChoice){
                case "1":
                    String printFilesString = existsFiles.stringToPrintExistFiles();
                    System.out.println(printFilesString);
                    break;
                case "2":
                    readFileProcessing(scanner, existsFiles);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        }
    }

    private void readFileProcessing(Scanner scanner, FilesGetUtil existsFiles) {
        System.out.println("Input read file name:");
        String fileName = scanner.nextLine();
        String findFile = fileName + Config.FILE_EXTENSION;
        Hashtable<String, String> currentFilesHashTable= existsFiles.directoryFiles();
        if (!currentFilesHashTable.containsKey(findFile)) {
            System.out.println("!!!!!!!!!!! File with your name does not exist !!!!!!!!!!!!");
            return;
        }
        String contentFile = readService.readFile(Paths.get(currentFilesHashTable.get(findFile)));
        System.out.println("File: " + findFile);
        System.out.println(contentFile);
    }

}
