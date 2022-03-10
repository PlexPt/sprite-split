package com.github.plexpt.spritesplit.utili;

import com.bulenkov.darcula.DarculaLaf;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicLookAndFeel;

import static java.awt.Toolkit.getDefaultToolkit;

/**
 * @author pengtao
 * @email plexpt@gmail.com
 * @date 2022-03-10 13:34
 */
public class LookAndFeelUtil {


    /**
     * 使用系统主题
     */
    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**
     * 使用系统主题
     */
    public static void setDarculaLookAndFeel() {
        try {
            BasicLookAndFeel darcula = new DarculaLaf();
            UIManager.setLookAndFeel(darcula);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 窗口居中
     */
    public static void centerSizeWindow(JFrame frame) {
        try {
            //窗体居中显示
            frame.setLocationRelativeTo(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 屏幕宽度
     *
     * @return
     */
    public static int getScreenSizeWidth() {
        try {
            Dimension screenSize = getDefaultToolkit().getScreenSize();
            return screenSize.width;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1920;
    }

    /**
     * 屏幕高度
     *
     * @return
     */
    public static int getScreenSizeHeight() {
        try {
            Dimension screenSize = getDefaultToolkit().getScreenSize();
            return screenSize.height;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1080;
    }
}
