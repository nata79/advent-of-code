package advent.of.code.year2019;

import advent.of.code.year2019.intcode.IntCodeProcess;
import advent.of.code.year2019.intcode.IntCodeProgram;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        IntCodeProgram program = IntCodeProgram.load("/2019/day2.txt");

        System.out.println(
                Arrays.stream(processIntcode(program, 12L, 2L).getProgram())
                        .map(Object::toString)
                        .collect(Collectors.joining(","))
        );

        for (long i = 0; i < 100; i++) {
            for (long j = 0; j < 100; j++) {
                Long[] result = processIntcode(program, i, j).getProgram();
                if (result[0].equals(19690720L)) {
                    System.out.println(String.format("Noun: %s Verb: %s", i, j));
                    System.out.println(String.format("100 * %s + %s = %s", i, j, (100 * i) + j));
                    break;
                }
            }
        }
    }

    private static IntCodeProcess processIntcode(IntCodeProgram program, Long noun, Long verb) {
        program.getCode()[1] = noun;
        program.getCode()[2] = verb;
        return program.run();
    }
}
