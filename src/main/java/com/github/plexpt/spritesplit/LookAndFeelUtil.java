package com.github.plexpt.spritesplit;

import javax.swing.UIManager;

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
}
