package com.github.plexpt.spritesplit.window;

import com.github.plexpt.spritesplit.App;
import com.github.plexpt.spritesplit.utili.LookAndFeelUtil;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import lombok.Getter;

/**
 * @author pengtao
 * @email plexpt@gmail.com
 * @date 2022-03-16 17:31
 */
@Getter
public class SettingForm {
    private JPanel content;
    private JButton mdButton;
    private JButton sysButton;
    private JButton darkButton;

    static SettingForm form;

    public SettingForm() {
        sysButton.addActionListener(e -> {
            LookAndFeelUtil.setSystemLookAndFeel();
            SwingUtilities.updateComponentTreeUI(App.mainFrame);
            SwingUtilities.updateComponentTreeUI(AppWindow.getInstance().getTabbedPane());
        });
        mdButton.addActionListener(e -> {
            LookAndFeelUtil.setMaterialLookAndFeel();
            SwingUtilities.updateComponentTreeUI(App.mainFrame);
            SwingUtilities.updateComponentTreeUI(AppWindow.getInstance().getTabbedPane());
        });
        darkButton.addActionListener(e -> {
            LookAndFeelUtil.setDarculaLookAndFeel();
            SwingUtilities.updateComponentTreeUI(App.mainFrame);
            SwingUtilities.updateComponentTreeUI(AppWindow.getInstance().getTabbedPane());
        });
    }

    public static SettingForm getInstance() {
        if (form == null) {
            form = new SettingForm();
        }
        return form;
    }

}
