import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ConvertBigImage {

    public static void main(String[] args) throws Exception {
        long before = printMemory("START");

        File imageFile = new File(args[0]);
        BufferedImage image = ImageIO.read(imageFile);
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("width*height=" + (width*height));
        System.out.println("size?=" + ((4*width*height)/1024.0/1024.0));

        long after = printMemory("END  ");
        long b = (after - before);
        double kb = b/1024.0;
        double mb = kb/1024.0;
        System.out.println("mb=" + mb);
    }

    private static long printMemory(String tag) {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long usage = (total - free);
        System.out.println("[" + tag + "] max=" + runtime.maxMemory() + ", total=" + total + ", free=" + free + ", usage=" + usage);
        return usage;
    }
}