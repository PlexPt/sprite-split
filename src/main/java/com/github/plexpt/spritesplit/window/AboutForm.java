package com.github.plexpt.spritesplit.window;

import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.Getter;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2022-03-12 18:25
 */
@Getter
public class AboutForm {
    private JPanel aboutPanel;
    private JLabel sloganLabel;
    private JLabel versionLabel;
    private JLabel checkUpdateLabel;
    private JLabel companyLabel;
    private JLabel helpDocLabel;

    private static AboutForm aboutForm;

    public AboutForm() {

    }

    public static AboutForm getInstance() {
        if (aboutForm == null) {
            aboutForm = new AboutForm();
        }
        return aboutForm;
    }

}
