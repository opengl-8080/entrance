import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class SearchBigImage {

    public static void main(String[] args) throws Exception {
        Pattern pattern = Pattern.compile("^.*\\.(jpg|jpeg|JPG|JPEG|png|PNG|gif|GIF)$");
        Path baseDir = Paths.get(args[0]);
        final int limit = 10000 * 10000;
        Files.walkFileTree(baseDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                Matcher matcher = pattern.matcher(fileName);

                if (matcher.find()) {
                    BufferedImage image = ImageIO.read(file.toFile());
                    int width = image.getWidth();
                    int height = image.getHeight();
                    if (limit < width * height) {
                        System.out.println("file=" + file);
                    }
                }

                return FileVisitResult.CONTINUE;
            }
        });
    }
}