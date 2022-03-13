package com.github.plexpt.spritesplit.utili;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.Resizers;

import org.apache.commons.lang3.StringUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import lombok.Data;
import lombok.SneakyThrows;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2022-03-04 11:43
 */
@Data
public class IconReSizer {

    String inFile;

    String outDir = "";

    ReProgressListener listener = evt -> {
    };

    @SneakyThrows
    public static void main(String[] args) {

//        resize("L:\\Download\\u素材\\超凡之路\\预备\\cfitem");

    }

    @SneakyThrows
    public static IconReSizer of(String pathname) {
        IconReSizer reSizer = new IconReSizer();
        reSizer.setInFile(pathname);
        return reSizer;
    }

    @SneakyThrows
    public IconReSizer outDir(String pathname) {
        outDir = pathname;
        return this;
    }

    @SneakyThrows
    public IconReSizer onProgress(ReProgressListener listener) {
        this.listener = listener;
        return this;
    }

    @SneakyThrows
    public void resize() {
        centerLog("开始");
        File in = new File(inFile);

        if (StringUtils.isNotBlank(outDir)) {
            FileUtil.mkdir(outDir);
        } else {
            if (in.isDirectory()) {
                outDir = in.getAbsolutePath();
            } else {
                outDir = in.getParentFile().getAbsolutePath();
            }
        }

        if (in.isDirectory()) {
            for (File file : in.listFiles()) {
                resizeFile(file);
            }
        } else {
            resizeFile(in);

        }

        centerLog("处理完成");

    }

    private void resizeFile(File file) throws IOException {
        if (!file.getName().endsWith("png")) {
            return;
        }
//        File parent = FileUtil.getParent(file, 1);
//
//        File tmpdir = FileUtil.mkdir(new File(parent, dir.getName() + "/"));

        BufferedImage image = ImageIO.read(file);
        int size = getReSize(image);

        log("处理：" + file.getName() + ", size: " + image.getWidth() + "," + image.getHeight() + "==>" + size);

        image = Thumbnails.of(image)
                .imageType(BufferedImage.TYPE_INT_ARGB)
                .size(size, size)
                .asBufferedImage();

        BufferedImage bg = Thumbnails.of(ResourceUtil.getResource("images/trans128.png"))
                .forceSize(size, size)
                .resizer(Resizers.NULL)
                .asBufferedImage();

        Graphics2D graphics = bg.createGraphics();
//            bg = graphics.getDeviceConfiguration()
//                    .createCompatibleImage(size, size, Transparency.TRANSLUCENT);
//            graphics = bg.createGraphics();

        int x;
        int y;
        if (image.getWidth() < image.getHeight()) {
            x = (bg.getWidth() - image.getWidth()) / 2;
            y = 0;
        } else {
            y = (bg.getHeight() - image.getHeight()) / 2;
            x = 0;
        }

        graphics.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
        graphics.dispose();
        bg.flush();
        image.flush();
        ImageIO.write(bg, "png", new File(outDir, file.getName()));
    }

    public void centerLog(String text) {
        listener.progressUpdate(StringUtils.center(text, 50, "="));
    }

    public void log(String text) {
        listener.progressUpdate(text);
    }

    public static int getReSize(BufferedImage image) {
        int size = image.getWidth() > image.getHeight() ? image.getWidth() : image.getHeight();
        int reSize = getReSize(size);
        return reSize;
    }

    @SneakyThrows
    public void test() {
        Thumbnails.of("images/object_huwanggu01.png")
                .forceSize(128, 128)
                .resizer(Resizers.NULL)
                .scale(1)
                .toFile("images/tmp/1.png");

        BufferedImage image = null;
        Thumbnails.of("images/trans32.png")
                .forceSize(128, 128)
                .watermark(image, 0.0f)
                .outputQuality(0.8f)
                .resizer(Resizers.NULL)
                .toFile("images/tmp/1.png");
    }

    public static int getReSize(int size) {
        if (size <= 64) {
            return 64;
        } else if (size <= 128) {
            return 128;
        } else if (size <= 256) {
            return 256;
        } else if (size <= 512) {
            return 512;
        }
        return 1024;
    }

    public interface ReProgressListener {
        void progressUpdate(String evt);

    }
}
