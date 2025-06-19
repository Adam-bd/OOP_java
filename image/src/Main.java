public class Main {
    public static void main(String[] args) throws InterruptedException {
        ImageHandler handler = new ImageHandler();
        //handler.saveImage("obrazKopia.jpg");

        // Wczytanie obrazu
        handler.loadImage("obraz.jpg");

        // Zwiększenie jasności obrazu o 80
//        long startTimeParallel = System.currentTimeMillis();
//        handler.adjustBrightness(80);
//        long endTimeParallel = System.currentTimeMillis();
//        System.out.println("Czas wykonania jeden wątek: " + (endTimeParallel - startTimeParallel) + " ms");

//      long startTimeParallel = System.currentTimeMillis();
//        handler.parallelAdjustBrightness(80, Runtime.getRuntime().availableProcessors());
//        long endTimeParallel = System.currentTimeMillis();
//        System.out.println("Czas wykonania jeden wątek: " + (endTimeParallel - startTimeParallel) + " ms");

        long startTimeParallel = System.currentTimeMillis();
        handler.adjustBrightnessPoolThread(80);
        long endTimeParallel = System.currentTimeMillis();
        System.out.println("Czas wykonania jeden wątek: " + (endTimeParallel - startTimeParallel) + " ms");

        handler.saveImage("obrazKopia.jpg");
    }
}