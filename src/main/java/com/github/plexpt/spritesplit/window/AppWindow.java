package com.github.plexpt.spritesplit.window;

import com.github.plexpt.spritesplit.App;
import com.intellij.uiDesigner.core.GridConstraints;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import lombok.Getter;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2022-03-12 12:10
 */
@Getter
public class AppWindow {

    private static final GridConstraints GRID_CONSTRAINTS = new GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
            new Dimension(200, 200), null, 0, false);

    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel aboutPanel;
    private JPanel spritecutPanel;
    private JPanel resizePanel;
    private JPanel transPanel;
    private JPanel boostPanel;
    private JPanel schedulePanel;
    private JPanel settingPanel;

    private static SpriteCutPannel spriteCutPannel;

    private static AppWindow window;


    public static AppWindow getInstance() {
        if (window == null) {
            window = new AppWindow();
        }
        return window;
    }

    public static void start() {
        if (window == null) {
            window = new AppWindow();
        }
        JFrame frame = App.mainFrame;
        frame.setContentPane(window.getMainPanel());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public void init() {
        window = getInstance();
        window.getMainPanel().updateUI();
        window.getSpritecutPanel().add(SpriteCutPannel.getInstance().getContent(),
                GRID_CONSTRAINTS);
        window.getResizePanel().add(ResizeForm.getInstance().getContent(), GRID_CONSTRAINTS);
        window.getAboutPanel().add(AboutForm.getInstance().getAboutPanel(), GRID_CONSTRAINTS);
        window.getTransPanel().add(TransForm.getInstance().getContent(), GRID_CONSTRAINTS);
//        window.getPushHisPanel().add(PushHisForm.getInstance().getPushHisPanel(),
//        GRID_CONSTRAINTS);
//        window.getSettingPanel().add(SettingForm.getInstance().getSettingPanel(),
//        GRID_CONSTRAINTS);
//        window.getMessageEditPanel().add(MessageEditForm.getInstance().getMessageEditPanel(),
//        GRID_CONSTRAINTS);
//        window.getMessageManagePanel().add(MessageManageForm.getInstance()
//        .getMessageManagePanel(), GRID_CONSTRAINTS);
//        window.getPushPanel().add(PushForm.getInstance().getPushPanel(), GRID_CONSTRAINTS);
//        window.getSpritecutPanel().add(MessageTypeForm.getInstance().getMessageTypePanel(),
//        GRID_CONSTRAINTS);
//        window.getBoostPanel().add(BoostForm.getInstance().getBoostPanel(), GRID_CONSTRAINTS);
//        window.getTransPanel().add(InfinityForm.getInstance().getTransPanel(), GRID_CONSTRAINTS);
//        window.getMessagePanel().setDividerLocation((int) (App.mainFrame.getWidth() / 5.6));
        window.getMainPanel().updateUI();
    }
}
