
package monoalphabeticcryptanalysis;
import java.util.*;

public class MonoalphabeticCryptanalysis {

    private static final String ENGLISH_FREQ_ORDER = "ETAOINSHRDLCUMWFGYPBVKJXQZ";

    public static Map<Character, Integer> frequencyAnalysis(String ciphertext) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : ciphertext.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }

        return frequencyMap;
    }

    public static Map<Character, Character> generateSubstitutionMap(String ciphertext) {
        Map<Character, Integer> frequencyMap = frequencyAnalysis(ciphertext);

        List<Character> sortedCipherLetters = new ArrayList<>(frequencyMap.keySet());
        sortedCipherLetters.sort((a, b) -> frequencyMap.get(b) - frequencyMap.get(a));

        Map<Character, Character> substitutionMap = new HashMap<>();
        for (int i = 0; i < sortedCipherLetters.size() && i < ENGLISH_FREQ_ORDER.length(); i++) {
            substitutionMap.put(sortedCipherLetters.get(i), ENGLISH_FREQ_ORDER.charAt(i));
        }

        return substitutionMap;
    }

    public static String decrypt(String ciphertext, Map<Character, Character> substitutionMap) {
        StringBuilder plaintext = new StringBuilder();

        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char decryptedChar = substitutionMap.getOrDefault(Character.toUpperCase(c), c);
                plaintext.append(Character.isLowerCase(c) ? Character.toLowerCase(decryptedChar) : decryptedChar);
            } else {
                plaintext.append(c);
            }
        }

        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter encrypt text: ");
        String ciphertext = scanner.nextLine();

        Map<Character, Character> substitutionMap = generateSubstitutionMap(ciphertext);
        String decryptedText = decrypt(ciphertext, substitutionMap);

        System.out.println("encrypt text: " + ciphertext);
        System.out.println("map: " + substitutionMap);
        System.out.println("decrypted text: " + decryptedText);
    }
}
