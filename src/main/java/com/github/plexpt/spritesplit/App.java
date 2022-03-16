package com.github.plexpt.spritesplit;

import com.github.plexpt.spritesplit.utili.LookAndFeelUtil;
import com.github.plexpt.spritesplit.window.AppWindow;
import com.github.plexpt.spritesplit.window.MainFrame;

import javax.swing.JFrame;

import mdlaf.MaterialLookAndFeel;

public class App {

    public static MainFrame mainFrame;

    public static void main(String[] args) {
        mainFrame = new MainFrame();
        LookAndFeelUtil.setMaterialLookAndFeel();
        mainFrame.init();

        if (LookAndFeelUtil.getScreenSizeWidth() <= 1366) {
            // 低分辨率下自动最大化窗口
            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
//        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

//        mainFrame.setContentPane(AppWindow.getInstance().getMainPanel());
        AppWindow.getInstance().init();

//        SpriteCutPannel.start();
        AppWindow.start();
    }
}
