import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * H2 Database �̌������ʂ� CSV �ɏo�͂�����@.
 * csvwrite �֐����g�p����.
 * 
 * ��
 * call csvwrite('export.csv', 'select item_id, path from image', 'charset=UTF-8');
 * 
 * http://www.h2database.com/html/functions.html#csvwrite
 */
public class GenerateImageSizePatchSql {
    public static void main(String... args) throws Exception {
        if (args.length != 2) {
            System.out.println("�������� ITEM_ID,PATH �`���� CSV �̃p�X���A\n�������� ENTRACNE_HOME �̃p�X���w�肷��");
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("./patch.sql"), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            List<String> lines = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);
            Path entranceHomePath = Paths.get(args[1]);
    
            for (String line : lines) {
                String[] elements = line.split(",");
                String itemId = elements[0].replaceAll("^\"|\"$", "");
                String imagePath = elements[1].replaceAll("^\"|\"$", "");
                System.out.println("imageId=" + imageId);
    
                Path actualImagePath = entranceHomePath.resolve("library").resolve(imagePath);
                BufferedImage image = ImageIO.read(actualImagePath.toFile());
                int width = image.getWidth();
                int height = image.getHeight();
                writer.write("UPDATE IMAGE SET WIDTH=" + width + ", HEIGHT=" + height + " WHERE ITEM_ID=" + itemId + ";\n");
            }
        }
    }
}