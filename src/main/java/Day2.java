import java.util.Arrays;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        String input = Utils.readFile("day2.txt");

        Integer[] program = Arrays.stream(input.split(",")).map(Integer::parseInt).toArray(Integer[]::new);

        System.out.println(
                Arrays.stream(processIntcode(program, 12, 2))
                        .map(Object::toString)
                        .collect(Collectors.joining(","))
        );

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                Integer[] result = processIntcode(program, i, j);
                if (result[0] == 19690720) {
                    System.out.println(String.format("Noun: %s Verb: %s", i, j));
                    System.out.println(String.format("100 * %s + %s = %s", i, j, (100 * i) + j));
                    break;
                }
            }
        }
    }

    private static Integer[] processIntcode(Integer[] program, int noun, int verb) {
        program[1] = noun;
        program[2] = verb;
        return new IntCode().processIntcode(program);
    }
}
