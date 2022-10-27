package com.github.plexpt.spritesplit.utili;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import cn.hutool.core.img.gif.GifDecoder;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import lombok.SneakyThrows;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2022-03-23 17:28
 */
public class GifSplit {
    public static void main(String[] args) {

        splitGif("D:\\desktop\\DNF_Extractor\\output\\sprite_character_swordman_effect_cutin.NPK\\(18)atsword_majesty.img\\0.GIF");
    }

    @SneakyThrows
    private static void splitGif(String s) {

        GifDecoder d = new GifDecoder();
        d.read(s);
        File file = new File(s);
        File dir = new File(file.getParent(), "/sp/");
        FileUtil.mkdir(dir);
        int n = d.getFrameCount();
        for (int i = 0; i < n; i++) {
            BufferedImage frame = d.getFrame(i);  // frame i
            File out = new File(dir, FileNameUtil.getPrefix(file) + "-" + i + ".png");
            ImageIO.write(frame, "png", out);
            int t = d.getDelay(i);  // display duration of frame in milliseconds
            // do something with frame
            System.out.println(out.getName() + " writed ");
        }


    }
}
