package com.github.plexpt.spritesplit;

import com.github.plexpt.spritesplit.utili.LookAndFeelUtil;
import com.github.plexpt.spritesplit.window.MainWindow;

public class Main {


    public static void main(String[] args) {
        LookAndFeelUtil.setDarculaLookAndFeel();

        new MainWindow();
    }
}
