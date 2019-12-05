import java.util.Arrays;

public class Day5 {
    public static void main(String[] args) {
        String input = Utils.readFile("day5.txt");
        Integer[] program = Arrays.stream(input.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
        IntCode.processIntcode(program);
    }
}
