import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class Day8 {
    public static void main(String[] args) throws IOException {
        String input = Utils.readFile("day8.txt");
        char[] chars = input.toCharArray();
        Integer[] pixels = new Integer[input.length()];

        for (int i = 0; i < chars.length; i++)
            pixels[i] = Integer.parseInt(String.valueOf(chars[i]));

        Image image = Image.parse(pixels, 6, 25);

        Optional<Integer[]> layerWithMinZeros = Arrays.stream(image.getLayers())
                .min(Comparator.comparingLong(Day8::countZeros));

        layerWithMinZeros.ifPresent(layer ->
                System.out.println(countDigits(layer, 1) * countDigits(layer, 2)));

        image.printImage();
    }

    private static long countDigits(Integer[] l, int digit) {
        return Arrays.stream(l).filter(i -> i == digit).count();
    }

    private static long countZeros(Integer[] l1) {
        return countDigits(l1, 0);
    }

    private static class Image {
        private final int height;
        private final int width;
        private final Integer[][] layers;

        static Image parse(Integer[] pixels, int height, int width) {
            int layerSize = height * width;
            int numOfLayers = pixels.length / layerSize;

            Integer[][] layers = new Integer[numOfLayers][layerSize];

            for (int i = 0; i < numOfLayers; i++) {
                System.arraycopy(pixels, (i * layerSize), layers[i], 0, layerSize);
            }

            return new Image(height, width, layers);
        }

        Image(int height, int width, Integer[][] layers) {
            this.height = height;
            this.width = width;
            this.layers = layers;
        }

        Integer[][] getLayers() {
            return layers;
        }

        void printImage() throws IOException {
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();

            int imageSize = height * width;
            Integer[] display = new Integer[imageSize];
            Arrays.fill(display, 2);

            for (Integer[] layer : layers) {
                for (int j = 0; j < imageSize; j++) {
                    if (display[j] == 2)
                        display[j] = layer[j];
                }
            }

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = display[(i * width) + j];

                    switch (pixel) {
                        case 2:
                            break;
                        case 1:
                            g2d.setColor(Color.WHITE);
                            g2d.fillRect(j, i, 1, 1);
                            break;
                        case 0:
                            g2d.setColor(Color.BLACK);
                            g2d.fillRect(j, i, 1, 1);
                            break;
                    }
                }
            }

            g2d.dispose();

            File file = new File("day8.png");
            ImageIO.write(bufferedImage, "png", file);
        }
    }
}
