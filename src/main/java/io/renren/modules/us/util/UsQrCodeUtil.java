package io.renren.modules.us.util;

import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

@Component
public class UsQrCodeUtil {

    private static final String CHARSET = "UTF-8";
    private static final String FORMAT_NAME = "JPG";
    @Value("${us.qrcode.size}")
    private int QRCODE_SIZE;
    @Value("${us.qrcode.logoWidth}")
    private int WIDTH;
    @Value("${us.qrcode.logoHeight}")
    private int HEIGHT;

    /**
     * 生成二维码
     *
     * @param content      源内容
     * @param imgPath      生成二维码保存的路径
     * @param needCompress 是否要压缩
     * @return 返回二维码图片
     * @throws WriterException, IOException
     */
    private BufferedImage createImage(String content, String imgPath, boolean needCompress) throws WriterException, IOException {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        this.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 在生成的二维码中插入图片
     *
     * @param source
     * @param imgPath
     * @param needCompress
     * @throws Exception
     */
    private void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws IOException {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    private void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir。(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成带logo二维码，并保存到磁盘
     *
     * @param content      链接或者内容
     * @param imgPath      logo图片
     * @param destPath     保存二维码图片的文件路径
     * @param needCompress
     * @throws Exception
     */
    public Boolean encode(String content, String imgPath, String destPath, boolean needCompress, String random) throws IOException, WriterException {
        BufferedImage image = this.createImage(content, imgPath, needCompress);
        if (image == null) {
            return false;
        }
        mkdirs(destPath);
        String file = random + ".jpg";//生成随机文件名
        ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
        return true;
    }

    public void encode(String content, String imgPath, OutputStream output, boolean needCompress) throws IOException, WriterException {
        BufferedImage image = this.createImage(content, imgPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    public void encode(String content, OutputStream output) throws IOException, WriterException {
        this.encode(content, null, output, false);
    }

    /**
     * 从二维码中，解析数据
     *
     * @param file 二维码图片文件
     * @return 返回从二维码中解析到的数据值
     */
    public String decode(File file) throws IOException, NotFoundException {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    public String decode(String path) throws IOException, NotFoundException {
        return this.decode(new File(path));
    }

}