import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.clamp;

public class ImageHandler {
    private BufferedImage image;

    public void loadImage(String path) {
        File file = new File(path);
        try {
            image = ImageIO.read(file);
            System.out.println("Załadowano plik: " + path);
        } catch (IOException e) {
            System.out.println("Błąd odczytu");
        }
    }

    public void saveImage(String path) {
        File file = new File(path);
        String formatName = path.substring(path.lastIndexOf(".") + 1);
        try {
            ImageIO.write(image, formatName, file);
            System.out.println("Zapisano plik: " + path);
        } catch (IOException e) {
            System.out.println("Błąd zapisu");
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    // Metoda do zwiększenia jasności obrazu o podaną stałą
    public void adjustBrightness(int value) {
        if (image == null) {
            System.out.println("Brak wczytanego obrazu.");
            return;
        }

        // Iteracja po pikselach obrazu i zmiana jasności
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Zmiana jasności dla każdego kanału koloru
                red = clamp(red + value);
                green = clamp(green + value);
                blue = clamp(blue + value);

                int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newRGB);
            }
        }
    }
    // Metoda pomocnicza do ograniczenia wartości do zakresu 0-255
    private int clamp(int value) {
        return Math.min(Math.max(0, value), 255);
    }

    //zad 3.
    private class AdjustBrightnessThread extends Thread{
        private int start;
        private int end;
        private int value;

        public AdjustBrightnessThread(int start, int end, int value){
            this.start = start;
            this.end = end;
            this.value = value;
        }

        @Override
        public void run(){
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = start; y < end; y++) {
                    int rgb = image.getRGB(x, y);
                    int alpha = (rgb >> 24) & 0xFF;
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // Zmiana jasności dla każdego kanału koloru
                    red = clamp(red + value);
                    green = clamp(green + value);
                    blue = clamp(blue + value);

                    int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    image.setRGB(x, y, newRGB);
                }
            }
        }
    }

    public void parallelAdjustBrightness(int value, int numberOfThreads) throws InterruptedException {
        if (image == null) {
            System.out.println("Brak wczytanego obrazu.");
            return;
        }
        Thread[] threads = new Thread[numberOfThreads];
        int start;
        int end;
        int heightOfRow = image.getHeight()/numberOfThreads;
        for(int i = 0; i < numberOfThreads; i++){
            start = i * heightOfRow;
            if(i == numberOfThreads - 1){
                end = image.getHeight();
            }else {
                end = start + heightOfRow;
            }
            threads[i] = new AdjustBrightnessThread(start, end, value);
            threads[i].start();
        }
        for (int j = 0; j < numberOfThreads; j++){
            threads[j].join();
        }
    }

    public void adjustBrightnessPoolThread(int value){
        if (image == null) {
            System.out.println("Brak wczytanego obrazu.");
            return;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(image.getHeight());
        for(int i = 0; i < image.getHeight(); i++){
            executorService.execute(new AdjustBrightnessThread(i, i + 1, value));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
