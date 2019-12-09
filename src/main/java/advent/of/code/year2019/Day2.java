package advent.of.code.year2019;

import advent.of.code.Utils;
import advent.of.code.year2019.intcode.IntCodeProcess;
import advent.of.code.year2019.intcode.IntCodeProgram;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        IntCodeProgram program = IntCodeProgram.load("/2019/day2.txt");

        System.out.println(
                Arrays.stream(processIntcode(program, BigInteger.valueOf(12), BigInteger.valueOf(2)).getProgram())
                        .map(Object::toString)
                        .collect(Collectors.joining(","))
        );

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                BigInteger[] result = processIntcode(program, BigInteger.valueOf(i), BigInteger.valueOf(j)).getProgram();
                if (result[0].equals(BigInteger.valueOf(19690720))) {
                    System.out.println(String.format("Noun: %s Verb: %s", i, j));
                    System.out.println(String.format("100 * %s + %s = %s", i, j, (100 * i) + j));
                    break;
                }
            }
        }
    }

    private static IntCodeProcess processIntcode(IntCodeProgram program, BigInteger noun, BigInteger verb) {
        program.getCode()[1] = noun;
        program.getCode()[2] = verb;
        return program.run();
    }
}
