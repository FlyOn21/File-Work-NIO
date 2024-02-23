import app.config.Config;
import app.utils.FilesGetUtil;
import app.views.ReadView;
import app.views.WriteView;

import java.util.Scanner;

public class Main {
    private static final String menu =  """
                        --------------
                        Choice action:
                        --------------
                        Choice 1 => exist files list
                        Choice 2 => write file(s)
                        Choice 3 => read file(s)
                        Choice 4 => stop and exit
                        """;

    private static final WriteView writeView = new WriteView();

    private static final ReadView readView = new ReadView();
    public static void main(String[] args) {
        FilesGetUtil existsFiles = new FilesGetUtil(Config.BASE_DIRECTORY);
        Scanner scanner = new Scanner(System.in);
        String printFilesString = existsFiles.stringToPrintExistFiles();
        while (true) {
            System.out.println(menu);
            System.out.print("Input your choice: ");
            String action = scanner.nextLine();
            switch (action) {
                case "1":
                    System.out.println(printFilesString);
                    break;
                case "2":
                    writeView.writeViewProcessing(scanner, existsFiles);
                    break;
                case "3":
                    readView.readViewProcessing(scanner, existsFiles);
                    break;
                case "4":
                    System.out.println("By-by");
                    return;
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        }
    }
}
