package advent.of.code.year2018;

import advent.of.code.Utils;

import java.util.HashSet;
import java.util.Set;

public class Day1 {
    public static void main(String[] args) {
        String input = Utils.readFile("/2018/day1.txt");
        String[] frequencies = input.split("\n");

        sum(frequencies);
        findFirstRepetition(frequencies);
    }

    private static void sum(String[] frequencies) {
        int sum = 0;

        for (String line : frequencies)
            sum += Integer.parseInt(line);

        System.out.println(sum);
    }

    private static void findFirstRepetition(String[] frequencies) {
        int sum = 0;
        Set<Integer> repetitions = new HashSet<>();
        repetitions.add(sum);

        while (true) {
            for (String line : frequencies) {
                sum += Integer.parseInt(line);

                if (repetitions.contains(sum)) {
                    System.out.println(sum);
                    return;
                } else
                    repetitions.add(sum);
            }
        }
    }
}
