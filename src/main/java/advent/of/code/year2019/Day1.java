package advent.of.code.year2019;

import advent.of.code.Utils;

import java.util.function.Function;

public class Day1 {
    public static void main(String[] args) {
        String input = Utils.readFile("/2019/day1.txt");
        String[] lines = input.split("\n");

        System.out.println(calculateSumOf(lines, Day1::calculateFuel));
        System.out.println(calculateSumOf(lines, Day1::calculateFuelWithFuel));
    }

    private static int calculateSumOf(String[] lines, Function<Integer, Integer> calculate) {
        int sum = 0;

        for (String line : lines) {
            int module = Integer.parseInt(line);
            sum += calculate.apply(module);
        }
        return sum;
    }

    static int calculateFuel(int module) {
        return (module / 3) - 2;
    }

    static int calculateFuelWithFuel(int module) {
        int totalFuel = 0;
        int fuel = module;

        do {
            fuel = calculateFuel(fuel);
            if (fuel > 0)
                totalFuel += fuel;
        } while (fuel > 0);

        return totalFuel;
    }
}
