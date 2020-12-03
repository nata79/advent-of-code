package advent.of.code.year2018;

import advent.of.code.Utils;

import java.util.HashMap;
import java.util.Map;

public class Day2 {
    public static void main(String[] args) {
        String[] codes = Utils.readLines("/2018/day2.txt");

        checksum(codes);
        findSimilarCodes(codes);
    }

    private static void findSimilarCodes(String[] codes) {
        for (int i = 0; i < codes.length; i++) {
            for (int j = i + 1; j < codes.length; j++) {
                String intersection = calculateIntersection(codes[i], codes[j]);
                if (codes[i].length() == codes[j].length() && codes[i].length() - intersection.length() == 1) {
                    System.out.println(intersection);
                    break;
                }
            }
        }
    }

    private static String calculateIntersection(String s1, String s2) {
        StringBuilder result = new StringBuilder();

        char[] s1Chars = s1.toCharArray();
        char[] s2Chars = s2.toCharArray();

        for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
            if (s1Chars[i] == s2Chars[i])
                result.append(s1Chars[i]);
        }

        return result.toString();
    }

    private static void checksum(String[] codes) {
        int two = 0;
        int three = 0;

        for (String code : codes) {
            Map<String, Integer> counters = countLetters(code);

            if (counters.values().stream().anyMatch(count -> count == 2))
                two++;

            if (counters.values().stream().anyMatch(count -> count == 3))
                three++;
        }

        System.out.println(two * three);
    }

    private static Map<String, Integer> countLetters(String s) {
        Map<String, Integer> result = new HashMap<>();

        for (char c : s.toCharArray()) {
            String letter = String.valueOf(c);
            result.put(letter, result.getOrDefault(letter, 0) + 1);
        }

        return result;
    }
}
