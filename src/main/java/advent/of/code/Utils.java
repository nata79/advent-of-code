package advent.of.code;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String readFile(String filepath) {
        try {
            return new String(
                    Files.readAllBytes(Paths.get(Utils.class.getResource(filepath).toURI()))
            );
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Couldn't find file: " + filepath);
        }
    }

    public static String[] readLines(String filepath) {
        return readFile(filepath).split("\n");
    }

    public static List<Integer> getDigits(int i) {
        List<Integer> digits = new ArrayList<>();

        while (i > 0) {
            digits.add(0, i % 10);
            i = i / 10;
        }

        return digits;
    }

    public static BigInteger[] buildBigIntegerArray(int... integers) {
        BigInteger[] result = new BigInteger[integers.length];

        for (int i = 0; i < integers.length; i++)
            result[i] = BigInteger.valueOf(integers[i]);

        return result;
    }

    public static BigInteger max(BigInteger i1, BigInteger i2) {
        return i1.compareTo(i2) > 0 ? i1 : i2;
    }

    public static List<Integer> getDigits(BigInteger i) {
        List<Integer> digits = new ArrayList<>();

        for (char c : i.toString().toCharArray())
            digits.add(Integer.parseInt(String.valueOf(c)));

        return digits;
    }
}
