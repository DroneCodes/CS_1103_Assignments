package Assignment1;

import java.util.*;

public class assignment1 {

    /**
     * This assignment aims to assess your skills/knowledge on basics of String creation and various string handling functions. This is a scenario based practical assignment to create a text analysis tool, providing students with a hands-on opportunity to apply their programming skills in a real-world context. The assignment focuses on developing a program that performs various operations on text input, enhancing students' skills in handling strings, data analysis, and user interaction.
     *
     *
     * Scenario: You have been asked to create a text analysis tool that will perform various operations on a given text input. This tool will help users gain insights into the text data by performing character and word analysis.
     *
     * Assignment Tasks:
     *
     * User Input: Ask the user to input a paragraph or a lengthy text. Your program should read and store this input.
     *
     * Character Count: Calculate and display the total number of characters in the input text.
     *
     * Word Count: Calculate and display the total number of words in the input text. Assume that words are separated by spaces.
     *
     * Most Common Character: Find and display the most common character in the text. In case of a tie, select any of the tied characters.
     *
     * Character Frequency: Ask the user to input a character. Check and display the frequency of occurrences of this character in the text. Be case-insensitive (e.g., 'a' and 'A' should be considered the same character).
     *
     * Word Frequency: Ask the user to input a word. Check and display the frequency of occurrences of this word in the text. Be case-insensitive.
     *
     * Unique Words: Calculate and display the number of unique words in the text (case-insensitive).
     * @param args
     */

    public static void main(String[] args) {
        String name = "Fisayo";
        System.out.println("Java".replace("a", "o"));
        Scanner scanner = new Scanner(System.in);

        // User Input
        System.out.println("Enter a paragraph or lengthy text:");
        String inputText = scanner.nextLine();

        if (inputText.isEmpty()) {
            System.out.println("Input cannot be empty. Please restart the program and enter valid text.");
            return;
        }

        // Character Count
        int charCount = inputText.length();
        System.out.println("Total number of characters: " + charCount);

        // Word Count
        String[] words = inputText.split("\\s+");
        int wordCount = words.length;
        System.out.println("Total number of words: " + wordCount);

        // Most Common Character
        Map<Character, Integer> charFrequency = new HashMap<>();
        for (char c : inputText.toLowerCase().toCharArray()) {
            if (Character.isLetterOrDigit(c)) { // Count only letters and digits
                charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
            }
        }
        char mostCommonChar = Collections.max(charFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("Most common character: " + mostCommonChar);

        // Character Frequency
        System.out.println("Enter a character to find its frequency:");
        char searchChar = scanner.next().toLowerCase().charAt(0);
        int charOccurrences = charFrequency.getOrDefault(searchChar, 0);
        System.out.println("Frequency of '" + searchChar + "': " + charOccurrences);

        scanner.nextLine(); // Consume newline character

        // Word Frequency
        System.out.println("Enter a word to find its frequency:");
        String searchWord = scanner.nextLine().toLowerCase();
        int wordOccurrences = 0;
        for (String word : words) {
            if (word.equalsIgnoreCase(searchWord)) {
                wordOccurrences++;
            }
        }
        System.out.println("Frequency of \"" + searchWord + "\": " + wordOccurrences);

        // Unique Words
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(words));
        System.out.println("Number of unique words: " + uniqueWords.size());

        scanner.close();
    }
}
