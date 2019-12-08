package advent.of.code;

import java.io.IOException;
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
}
