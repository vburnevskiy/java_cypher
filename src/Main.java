import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private static final String SOURCE_FILE_NAME = "data.txt";
    private static final String ENCRYPTED_FILE_NAME = "encrypted.txt";
    private static final String DECRYPTED_FILE_NAME = "decrypted.txt";

    private static final char[] ALPHABET = getAlphabet();

    public static void main(String[] args) {

        Scanner  scanner = new Scanner(System.in);
        int shift;
        while (true) {
            System.out.println("Введите команду:");
            System.out.println("1 - Зашифровать файл.");
            System.out.println("2 - Расшифровать файл");
            System.out.println("4 - Выход из программы");
            int command = scanner.nextInt();

            switch (command) {
                case 1:
                    System.out.println("Введите число для шифрования:");
                    shift = scanner.nextInt();
                    System.out.println("Выполняется шифрование...");
                    cypherFile(SOURCE_FILE_NAME, ENCRYPTED_FILE_NAME, shift);
                    System.out.println("Готово.");
                    break;
                case 2:
                    System.out.println("Введите число для дешифрования:");
                    shift = scanner.nextInt();
                    System.out.println("Выполнятся дешифрование");
                    cypherFile(ENCRYPTED_FILE_NAME, DECRYPTED_FILE_NAME, -shift);
                    System.out.println("Готово.");
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Ошибка. Неизвестная команда");
            }
        }
    }

    private static void cypherFile(String inputFileName, String outputFileName, int shift) {
        String inputStr = getStringFromFile(inputFileName);
        String outputStr = shiftString(inputStr, shift);
        saveStringToFile(outputStr, outputFileName);
    }

    private static char[] getAlphabet() {
        String asciiAlphabet = getStringByIncChars(' ', 127-32); // с пробела и по ~
        String rusUpperAlphabet = getStringByIncChars('А', 32);
        String rusLowerAlphabet = getStringByIncChars('а', 32);

        return (asciiAlphabet + rusUpperAlphabet + rusLowerAlphabet).toCharArray();
    }

    private static String getStringByIncChars(char startChar, int length) {
        char[] incChars = new char[length];
        for (int i=0; i < length; i++) {
            incChars[i] = (char) (startChar+i);
        }

        return new String(incChars);
    }

    private static String getStringFromFile(String fileName) {
        Path path = Path.of(fileName);
        String textStr;
        try {
            textStr = Files.readString(path);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return textStr;
    }

    private static void saveStringToFile(String text, String fileName) {
        Path path = Path.of(fileName);
        try {
            Files.writeString(path, text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static String shiftString(String text, int shift) {
        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = shiftChar(charArray[i], shift);
        }
        return new String(charArray);
    }

    private static char shiftChar(char input, int shift) {
        for (int inputIndex = 0; inputIndex < ALPHABET.length; inputIndex++) {
            if (input == ALPHABET[inputIndex]) {
                int outputIndex = (inputIndex + shift) % ALPHABET.length;
                outputIndex = outputIndex + (outputIndex < 0 ? ALPHABET.length : 0);
                return ALPHABET[outputIndex];
            }
        }
        return input;
    }
}