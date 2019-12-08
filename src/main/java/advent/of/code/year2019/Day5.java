package advent.of.code.year2019;

import advent.of.code.Utils;

import java.util.Arrays;

public class Day5 {
    public static void main(String[] args) {
        String input = Utils.readFile("/2019/day5.txt");
        Integer[] program = Arrays.stream(input.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
        new IntCode().processIntcode(program);
    }
}
