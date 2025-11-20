package com.uj.expenseManager.util;

import org.springframework.stereotype.Service;

@Service
public class StringUtils extends org.springframework.util.StringUtils {

    /**
     * Converts input string to title case, capitalizing each word.
     * @param input the input string to be converted to title case
     * @return the title-cased string
     */
    public static String titleize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        input = input.trim().toLowerCase();
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase());
                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
        }

        return result.toString();
    }
}
