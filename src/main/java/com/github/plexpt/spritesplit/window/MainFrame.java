package com.github.plexpt.spritesplit.window;

import com.github.plexpt.spritesplit.utili.LookAndFeelUtil;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * <pre>
 * 主窗口
 * </pre>
 */
public class MainFrame extends JFrame {

    public static final Image IMAGE_LOGO = Toolkit.getDefaultToolkit()
            .getImage(UiConsts.class.getResource("/icon/logo.png"));

    public void init() {
        this.setName(UiConsts.APP_NAME);
        this.setTitle(UiConsts.APP_NAME);

        this.setIconImage(IMAGE_LOGO);

        LookAndFeelUtil.setPreferSizeAndLocateToCenter(this, 0.8, 0.88);
    }

    /**
     * 添加事件监听
     */
    public void addListeners() {
//        ThreadUtil.execute(MessageTypeListener::addListeners);
//        ThreadUtil.execute(AboutListener::addListeners);
//        ThreadUtil.execute(HelpListener::addListeners);
//        ThreadUtil.execute(PushHisListener::addListeners);
//        ThreadUtil.execute(SettingListener::addListeners);
//        ThreadUtil.execute(MessageEditListener::addListeners);
//        ThreadUtil.execute(MessageManageListener::addListeners);
//        ThreadUtil.execute(MemberListener::addListeners);
//        ThreadUtil.execute(PushListener::addListeners);
//        ThreadUtil.execute(InfinityListener::addListeners);
//        ThreadUtil.execute(BoostListener::addListeners);
//        ThreadUtil.execute(ScheduleListener::addListeners);
//        ThreadUtil.execute(TabListener::addListeners);
//        ThreadUtil.execute(FrameListener::addListeners);
    }
}
