package bruteforcemonoalphabetic;
import java.util.*;

public class BruteForceMonoalphabetic {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String decryptWithKey(String ciphertext, Map<Character, Character> keyMapping) {
        StringBuilder decryptedText = new StringBuilder();
        for (char ch : ciphertext.toCharArray()) {
            if (Character.isLetter(ch)) {
                char upperCh = Character.toUpperCase(ch);
                char decryptedChar = keyMapping.getOrDefault(upperCh, upperCh);
                decryptedText.append(Character.isLowerCase(ch) ? Character.toLowerCase(decryptedChar) : decryptedChar);
            } else {
                decryptedText.append(ch);
            }
        }
        return decryptedText.toString();
    }

    public static void bruteForceDecrypt(String ciphertext) {
        List<String> permutations = new ArrayList<>();
        generatePermutations("", ALPHABET, permutations, 20);

        int count = 0;
        for (String key : permutations) {
            Map<Character, Character> keyMapping = new HashMap<>();
            for (int i = 0; i < ALPHABET.length(); i++) {
                keyMapping.put(key.charAt(i), ALPHABET.charAt(i));
            }

            String decryptedText = decryptWithKey(ciphertext, keyMapping);
            System.out.println((count + 1) + ": " + decryptedText);
            count++;
        }
    }

    public static void generatePermutations(String prefix, String str, List<String> results, int limit) {
        if (results.size() >= limit) return;

        int n = str.length();
        if (n == 0) {
            results.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                generatePermutations(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), results, limit);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the encrypted text: ");
        String ciphertext = scanner.nextLine().toUpperCase();
        
        bruteForceDecrypt(ciphertext);
        scanner.close();
    }
}
