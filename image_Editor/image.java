import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.IOException;
public class image {

    public static BufferedImage grayscale(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                outputImage.setRGB(j, i, img.getRGB(j, i));
            }
        }
        return outputImage;
    }

    public static BufferedImage rotateClockwise(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage rotated = new BufferedImage(height, width, img.getType());

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                rotated.setRGB(height - i - 1, j, img.getRGB(j, i));
            }
        }

        return rotated;

    }

    public static BufferedImage rotateAntiClockwise(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage rotated = new BufferedImage(height, width, img.getType());

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                rotated.setRGB(i, width - j - 1, img.getRGB(j, i));
            }
        }

        return rotated;
    }

    public static BufferedImage invertHorizontal(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage invertedImage = new BufferedImage(width, height, img.getType());
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                invertedImage.setRGB(width-j-1, i, img.getRGB(j, i));
            }
        }
        return invertedImage;
    }

    public static BufferedImage invertVertical(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] pixels = new int[width][height];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                pixels[x][y] = img.getRGB(x, y);
            }
        }
        BufferedImage inverted = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                inverted.setRGB(x, height - y - 1, pixels[x][y]);
            }
        }
        return inverted;
    }

    public static BufferedImage changeBrightness(BufferedImage img, int amount) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage brightened = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Getting the RGB value of the pixel
                int rgb = img.getRGB(x, y);

                // Extracting the red, green, and blue components
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Increasing brightness for each channel
                red = Math.min(255, Math.max(0, red + amount));
                green = Math.min(255, Math.max(0, green + amount));
                blue = Math.min(255, Math.max(0, blue + amount));

                // Creating the new RGB value
                int newRGB = (red << 16) | (green << 8) | blue;

                // Setting the new pixel value in the output image
                brightened.setRGB(x, y, newRGB);
            }
        }
        return brightened;
    }

    public static BufferedImage blur(BufferedImage img) {
        int radius = 5;
        // Basic blur implementation
        for(int i=0; i<img.getWidth(); i++) {
            for(int j=0; j<img.getHeight(); j++) {
                int rgb = 0;
                int blurPixelCount = 0;

                for(int k=-radius; k<=radius; k++) {
                    for(int l=-radius; l<=radius; l++) {
                        if(i+k >= 0 && i+k < img.getWidth() && j+l >= 0 && j+l < img.getHeight()) {
                            rgb += img.getRGB(i+k, j+l);
                            blurPixelCount++;
                        }
                    }
                }
                rgb = rgb/blurPixelCount;
                img.setRGB(i, j, rgb);
            }
        }
        return img;
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter name of the image: ");
        String imagePath = sc.nextLine();

        File imgFile = new File(imagePath);
        try {
            BufferedImage image = ImageIO.read(imgFile);
            System.out.println("Enter 1 for grayscale");
            System.out.println("Enter 2 to rotate image CLOCKWISE");
            System.out.println("Enter 3 to rotate image ANTI-CLOCKWISE");
            System.out.println("Enter 4 to INVERT IMAGE HORIZANTALLY");
            System.out.println("Enter 5 to INVERT IMAGE VERTICALLY");
            System.out.println("Enter 6 to CHANGE BRIGHTNESS OF THE IMAGE");
            System.out.println("Enter 7 to TO BLUR THE IMAGE");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    image = grayscale(image);
                    break;

                case 2:
                    image = rotateClockwise(image);
                    break;

                case 3:
                    image = rotateAntiClockwise(image);
                    break;
                case 4:
                    image = invertHorizontal(image);
                    break;
                case 5:
                    image = invertVertical(image);
                    break;
                case 6:
                    System.out.println("Amount by which brightness should be increased");
                    int amount = sc.nextInt();
                    image = changeBrightness(image, amount);
                    break;
                case 7:
                    image = blur(image);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            ImageIO.write(image, "jpg", new File("output.jpg"));
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

}
