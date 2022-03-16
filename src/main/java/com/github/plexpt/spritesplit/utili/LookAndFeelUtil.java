package com.github.plexpt.spritesplit.utili;

import com.bulenkov.darcula.DarculaLaf;
import com.github.plexpt.spritesplit.App;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicLookAndFeel;

import mdlaf.MaterialLookAndFeel;

import static java.awt.Toolkit.getDefaultToolkit;

/**
 * @author pengtao
 * @email plexpt@gmail.com
 * @date 2022-03-10 13:34
 */
public class LookAndFeelUtil {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static Insets screenInsets =
            Toolkit.getDefaultToolkit().getScreenInsets(App.mainFrame.getGraphicsConfiguration());

    private static int screenWidth = screenSize.width - screenInsets.left - screenInsets.right;

    private static int screenHeight = screenSize.height - screenInsets.top - screenInsets.bottom;

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
     * 使用MD主题
     */
    public static void setMaterialLookAndFeel() {
        try {
            MaterialLookAndFeel lookAndFeel = new MaterialLookAndFeel();

            Font font = new Font("微软雅黑", Font.PLAIN, 12);
            FontUIResource fontRes = new FontUIResource(font);
            for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof FontUIResource) {
                    UIManager.put(key, fontRes);
                }
            }

            UIManager.setLookAndFeel(lookAndFeel);
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
     * 设置组件preferSize并定位于屏幕中央(基于屏幕宽高的百分百)
     */
    public static void setPreferSizeAndLocateToCenter(Component component,
                                                      double preferWidthPercent,
                                                      double preferHeightPercent) {
        int preferWidth = (int) (screenWidth * preferWidthPercent);
        int preferHeight = (int) (screenHeight * preferHeightPercent);
        setPreferSizeAndLocateToCenter(component, preferWidth, preferHeight);
    }

    /**
     * 设置组件preferSize并定位于屏幕中央
     */
    public static void setPreferSizeAndLocateToCenter(Component component, int preferWidth,
                                                      int preferHeight) {
        component.setBounds((screenWidth - preferWidth) / 2, (screenHeight - preferHeight) / 2,
                preferWidth, preferHeight);
        Dimension preferSize = new Dimension(preferWidth, preferHeight);
        component.setPreferredSize(preferSize);
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
