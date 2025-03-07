package playfair;
import java.util.*;

public class Playfair{
    private static final int SIZE = 5; 
    private char[][] matrix;
    
    public Playfair(String keyword) {
        matrix = generateMatrix(keyword);
    }

    private char[][] generateMatrix(String keyword) {
        Set<Character> seen = new LinkedHashSet<>();
        keyword = keyword.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");

        for (char ch : keyword.toCharArray()) {
            seen.add(ch);
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (ch != 'J' && !seen.contains(ch)) {
                seen.add(ch);
            }
        }

        char[][] matrix = new char[SIZE][SIZE];
        Iterator<Character> iterator = seen.iterator();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                matrix[row][col] = iterator.next();
            }
        }
        return matrix;
    }

    public void printMatrix() {
        System.out.println("Playfair Matrix:");
        for (char[] row : matrix) {
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    public String encrypt(String text) {
        return processText(text, true);
    }

    public String decrypt(String text) {
        return processText(text, false);
    }

    private String processText(String text, boolean encrypt) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        List<String> pairs = getPairs(text);

        StringBuilder result = new StringBuilder();
        for (String pair : pairs) {
            char[] encryptedPair = processPair(pair.charAt(0), pair.charAt(1), encrypt);
            result.append(encryptedPair[0]).append(encryptedPair[1]);
        }
        return result.toString();
    }

    private char[] processPair(char a, char b, boolean encrypt) {
        int[] posA = findPosition(a);
        int[] posB = findPosition(b);
        int shift = encrypt ? 1 : -1;

        if (posA[0] == posB[0]) { 
            return new char[]{matrix[posA[0]][(posA[1] + shift + SIZE) % SIZE], 
                              matrix[posB[0]][(posB[1] + shift + SIZE) % SIZE]};
        } else if (posA[1] == posB[1]) { // Same column
            return new char[]{matrix[(posA[0] + shift + SIZE) % SIZE][posA[1]], 
                              matrix[(posB[0] + shift + SIZE) % SIZE][posB[1]]};
        } else { // Rectangle swap
            return new char[]{matrix[posA[0]][posB[1]], matrix[posB[0]][posA[1]]};
        }
    }

    private int[] findPosition(char ch) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (matrix[row][col] == ch) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private List<String> getPairs(String text) {
        List<String> pairs = new ArrayList<>();
        StringBuilder modifiedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            modifiedText.append(text.charAt(i));
            if (i < text.length() - 1 && text.charAt(i) == text.charAt(i + 1)) {
                modifiedText.append('X'); 
            }
        }

        if (modifiedText.length() % 2 != 0) {
            modifiedText.append('X'); 
        }

        for (int i = 0; i < modifiedText.length(); i += 2) {
            pairs.add(modifiedText.substring(i, i + 2));
        }
        return pairs;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Get the keyword
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();

        Playfair playfair = new Playfair(keyword);
        playfair.printMatrix();

        System.out.print("Enter text to encrypt or decrypt: ");
        String inputText = scanner.nextLine();
        System.out.print("Choose operation (E for Encrypt, D for Decrypt): ");
        char choice = scanner.next().toUpperCase().charAt(0);

        if (choice == 'E') {
            System.out.println("Encrypted Text: " + playfair.encrypt(inputText));
        } else if (choice == 'D') {
            System.out.println("Decrypted Text: " + playfair.decrypt(inputText));
        } else {
            System.out.println("Invalid choice! Please enter E or D.");
        }

        scanner.close();
    }
}