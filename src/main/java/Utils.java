import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Utils {
    static String readFile(String filepath) {
        try {
            return new String(
                    Files.readAllBytes(Paths.get(Utils.class.getResource(filepath).toURI()))
            );
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Couldn't find file: " + filepath);
        }
    }
}
