import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = new String(
                Files.readAllBytes(Paths.get(Day1.class.getResource("day2.txt").toURI()))
        );

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
        return processIntcode(program);
    }

    static Integer[] processIntcode(Integer[] program) {
        Integer[] programCopy = new Integer[program.length];
        System.arraycopy(program, 0, programCopy, 0, program.length);

        for (int i = 0; i < programCopy.length; i += 4) {
            int opcode = programCopy[i];
            switch (opcode) {
                case 1:
                    programCopy[programCopy[i + 3]] = programCopy[programCopy[i + 1]] + programCopy[programCopy[i + 2]];
                    break;
                case 2:
                    programCopy[programCopy[i + 3]] = programCopy[programCopy[i + 1]] * programCopy[programCopy[i + 2]];
                    break;
                default:
                    break;
            }
        }

        return programCopy;
    }
}
