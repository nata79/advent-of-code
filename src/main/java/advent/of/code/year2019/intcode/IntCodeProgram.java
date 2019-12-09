package advent.of.code.year2019.intcode;

import advent.of.code.Utils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class IntCodeProgram {
    private final BigInteger[] code;

    private static IntCodeProgram parse(String s) {
        return new IntCodeProgram(Arrays.stream(s.split(",")).map(BigInteger::new).toArray(BigInteger[]::new));
    }

    public static IntCodeProgram parse(long... l) {
        BigInteger[] result = new BigInteger[l.length];

        for (int i = 0; i < l.length; i++)
            result[i] = BigInteger.valueOf(l[i]);

        return new IntCodeProgram(result);
    }

    public static IntCodeProgram load(String path) {
        String input = Utils.readFile(path);
        return parse(input);
    }

    private IntCodeProgram(BigInteger[] code) {
        this.code = code;
    }

    public BigInteger[] getCode() {
        return code;
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
