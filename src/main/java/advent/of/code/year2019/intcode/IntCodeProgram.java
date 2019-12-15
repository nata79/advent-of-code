package advent.of.code.year2019.intcode;

import advent.of.code.Utils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class IntCodeProgram {
    private final Long[] code;

    private static IntCodeProgram parse(String s) {
        return new IntCodeProgram(Arrays.stream(s.split(",")).map(Long::parseLong).toArray(Long[]::new));
    }

    public static IntCodeProgram parse(long... l) {
        Long[] result = new Long[l.length];

        for (int i = 0; i < l.length; i++)
            result[i] = l[i];

        return new IntCodeProgram(result);
    }

    public static IntCodeProgram load(String path) {
        String input = Utils.readFile(path);
        return parse(input);
    }

    public void overrideInstruction(int position, Long newValue) {
        code[position] = newValue;
    }

    private IntCodeProgram(Long[] code) {
        this.code = code;
    }

    public Long[] getCode() {
        return code;
    }

    public IntCodeBlockingIOExecutor getBlockingIOExecutor() {
        return new IntCodeBlockingIOExecutor(code);
    }

    public IntCodeProcess run() {
        IntCodeProcess intCodeProcess = new IntCodeProcess(code);
        intCodeProcess.run();
        return intCodeProcess;
    }

    private IntCodeProcess run(IOProvider ioProvider) {
        IntCodeProcess intCodeProcess = new IntCodeProcess(code, ioProvider);
        intCodeProcess.run();
        return intCodeProcess;
    }

    public CompletableFuture<IntCodeProcess> runAsync(IOProvider ioProvider) {
        return CompletableFuture.supplyAsync(() -> run(ioProvider));
    }
}
