import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zkx on 2016/2/23.
 */
public class Pdf2jpg {

    public static int dpi = 70;

    public static void convert(String inputFile, String outputPath) throws IOException{

        PDDocument document = PDDocument.load(new File(inputFile));

        File output = new File(outputPath);
        if (!output.exists()) output.mkdir();

        Runtime run = Runtime.getRuntime();

        PDFRenderer renderer = new PDFRenderer(document);

        int pages = document.getNumberOfPages();

        BufferedImage image = null;
        String outputFile = null;

        for (int i = 0; i < pages; i++){
            image = renderer.renderImageWithDPI(i, dpi, ImageType.RGB);
            outputFile = outputPath + File.separator + (i+1) + ".png";
            ImageIO.write(image, "png", new File(outputFile));
            //String[] cmd = {"pngquant", "--quality", "30", "--ext", "_compress.png", "-f", outputFile};
            String[] cmd = {"." + File.separator + "pngquant.exe", "--quality", "30", "--ext", "_compress.png", "-f", outputFile};
            //String[] cmd = {"."+ File.separator + "jpeg-recompress.exe", "-q", "10", outputFile, outputPath + File.separator + (i+1) + "_compress.jpg"};
            run.exec(cmd);
        }
    }

    public static void main(String[] args) throws IOException{
        long t1 = System.currentTimeMillis();
        Pdf2jpg.convert("test.pdf", "."+ File.separator +"output");
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
    }
}
