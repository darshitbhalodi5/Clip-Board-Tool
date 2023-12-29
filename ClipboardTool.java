import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ClipboardTool {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Clipboard Tool!");
        System.out.println("--------------------------");
        System.out.print("Enter the file name or path : ");
        String filePath = scanner.nextLine();
        System.out.print("------------------------------");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
            reader.close();

            System.out.println("File Content:\n" + fileContent);

            System.out.println("Choose an operation:");
            System.out.println("1. Cut");
            System.out.println("2. Copy");
            System.out.println("3. Paste");
            System.out.println("--------------------");
            int operationChoice = Integer.parseInt(scanner.nextLine());

            switch (operationChoice) {
                case 1:
                    System.out.print("Enter start line number: ");
                    int cutStartLine = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter start index in terms of character count: ");
                    int cutStartIndex = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter end line number: ");
                    int cutEndLine = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter end index in terms of character count: ");
                    int cutEndIndex = Integer.parseInt(scanner.nextLine());
                    String cutText = fileContent.substring(0,
                            getCharIndex(fileContent.toString(), cutStartLine, cutStartIndex)) +
                            fileContent.substring(getCharIndex(fileContent.toString(), cutEndLine, cutEndIndex));
                    writeToClipboard(cutText);
                    System.out.println("Text cut successfully!");
                    break;

                case 2:
                    System.out.print("Enter start line number: ");
                    int copyStartLine = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter start index: ");
                    int copyStartIndex = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter end line number: ");
                    int copyEndLine = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter end index: ");
                    int copyEndIndex = Integer.parseInt(scanner.nextLine());
                    String copiedText = fileContent.substring(
                            getCharIndex(fileContent.toString(), copyStartLine, copyStartIndex),
                            getCharIndex(fileContent.toString(), copyEndLine, copyEndIndex));
                    writeToClipboard(copiedText);
                    System.out.println("Text copied successfully!");
                    break;

                case 3:
                    System.out.print("Enter line number to paste: ");
                    int pasteLine = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter index to paste: ");
                    int pasteIndex = Integer.parseInt(scanner.nextLine());
                    String clipboardContent = readFromClipboard();
                    String updatedContent = fileContent.substring(0,
                            getCharIndex(fileContent.toString(), pasteLine, pasteIndex)) +
                            clipboardContent +
                            fileContent.substring(getCharIndex(fileContent.toString(), pasteLine, pasteIndex));
                    writeToFile(updatedContent, filePath);
                    System.out.println("Text pasted successfully!");
                    break;

                default:
                    System.out.println("Invalid choice. Please choose a valid operation.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int getCharIndex(String content, int lineNumber, int index) {
        String[] lines = content.split("\n");
        int charIndex = 0;
        for (int i = 0; i < lineNumber - 1; i++) {
            charIndex += lines[i].length() + 1;
        }
        charIndex += index;
        return charIndex;
    }

    private static void writeToClipboard(String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("clipboard.txt"));
        writer.write(text);
        writer.close();
    }

    private static String readFromClipboard() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("clipboard.txt"));
        StringBuilder clipboardContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            clipboardContent.append(line).append("\n");
        }
        reader.close();
        return clipboardContent.toString();
    }

    private static void writeToFile(String content, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(content);
        writer.close();
    }
}
