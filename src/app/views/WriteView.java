package app.views;

import app.config.Config;
import app.services.FileWriteService;
import app.utils.FilesGetUtil;
import app.services.FileReadService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class WriteView {
    private static final String writeMenu = """
            ------------ WRITE --------------
            Choice 1 => exist files list
            Choice 2 => write file, rewrite file or add additional data to existing file
            Choice 3 => to previous menu
            """;

    private static final String optionWriteMenu = """
            Please choice file write mode:
            Choice r => rewrite file data
            Choice a => add new content data to file data
            Choice b => back to previous menu
            """;

    private final FileReadService readService = new FileReadService();
    private final FileWriteService writeService = new FileWriteService();

    private record UserChoices(Boolean ifFileExists, String fileNameWithExt) {
    }

    public void writeViewProcessing(Scanner scanner, FilesGetUtil existsFiles) {
        while (true) {
            System.out.println(writeMenu);
            System.out.print("Input your choice: ");
            String readChoice = scanner.nextLine();
            switch (readChoice) {
                case "1":
                    String printFilesString = existsFiles.stringToPrintExistFiles();
                    System.out.println(printFilesString);
                    break;
                case "2":
                    ArrayList<String> resultWrite = writeFileProcessing(scanner, existsFiles);
                    if (resultWrite.isEmpty()) {
                        System.out.println("Write file cancel!");
                        break;
                    }
                    String writeFileContent = readService.readFile(Paths.get(resultWrite.get(1)));
                    System.out.println("Write Success!");
                    System.out.println("File: " + resultWrite.get(0));
                    System.out.println(writeFileContent);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        }
    }

    private ArrayList<String> writeFileProcessing(Scanner scanner, FilesGetUtil existsFiles) {
        boolean flagAdd = false;
        ArrayList<UserChoices> findFile = checkExistFile(existsFiles, scanner);
        System.out.println("Input file content: ");
        String content = scanner.nextLine();
        Hashtable<String, String> currentFilesHashTable = existsFiles.directoryFiles();
        ArrayList<String> result = new ArrayList<>();
        UserChoices userChoices = findFile.getFirst();
        result.add(0, userChoices.fileNameWithExt);
        if (!userChoices.ifFileExists) {
            Path newFilePath = FileWriteService.makeNewFilePath(userChoices.fileNameWithExt);
            writeService.writeToFile(newFilePath, content, flagAdd);
            result.add(1, newFilePath.toString());
        } else {
            Path existFilePath = Paths.get(currentFilesHashTable.get(userChoices.fileNameWithExt));
            Optional<Boolean> userChoice = changeFlagAddState(scanner);
            if (userChoice.isPresent()) {
                writeService.writeToFile(existFilePath, content, userChoice.get());
                result.add(1, existFilePath.toString());
            } else {
                result.clear();
            }
        }
        return result;

    }


    private ArrayList<UserChoices> checkExistFile(FilesGetUtil existsFiles, Scanner scanner) {
        ArrayList<UserChoices> userChoices = new ArrayList<>();
        boolean continueAsking = true;

        while (continueAsking) {
            System.out.println("Input write file name:");
            String fileName = scanner.nextLine();
            String findFile = fileName + Config.FILE_EXTENSION;
            Hashtable<String, String> currentFilesHashTable = existsFiles.directoryFiles();

            if (currentFilesHashTable.containsKey(findFile)) {
                System.out.println("File with your name exists.");
                boolean fileNameChange = fileNameChangeOrNot(scanner);
                if (!fileNameChange) {
                    userChoices.add(new UserChoices(true, findFile));
                    continueAsking = false;
                }
            } else {
                userChoices.add(new UserChoices(false, findFile));
                continueAsking = false;
            }
        }
        return userChoices;
    }

    private Boolean fileNameChangeOrNot(Scanner scanner) {
        System.out.println("Do you want to change the file name? (yes/no)");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("yes");
    }


    private static Optional<Boolean> changeFlagAddState(Scanner scanner) {
        while (true) {
            System.out.println(optionWriteMenu);
            System.out.print("Input your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "r":
                    return Optional.of(false);
                case "a":
                    return Optional.of(true);
                case "b":
                    return Optional.empty();
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        }
    }
}
