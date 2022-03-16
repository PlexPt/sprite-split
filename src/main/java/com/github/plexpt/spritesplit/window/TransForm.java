package com.github.plexpt.spritesplit.window;

import com.github.plexpt.spritesplit.App;
import com.github.plexpt.spritesplit.utili.CfgTranslator;

import org.apache.commons.lang3.StringUtils;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.hutool.core.io.FileUtil;
import lombok.Getter;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2022-03-12 14:17
 */
@Getter
public class TransForm {
    private JPanel content;
    private JTextField pathInput;
    private JTextField pathOutput;
    private JCheckBox autoOutCheckBox;
    private JTextArea prviewer;
    private JTextArea transed;
    private JButton start;
    private JCheckBox overCheck;
    private JButton but1;


    private static TransForm form;

    public TransForm() {
        start.addActionListener(e -> {
            start.setEnabled(false);

            try {
                startResize();
            } catch (Exception ex) {
                ex.printStackTrace();
                prviewer.append(ex.toString() + "\n");
            }

            start.setEnabled(true);
        });

        but1.addActionListener(e -> {
            // 消息对话框无返回, 仅做通知作用
            JOptionPane.showMessageDialog(
                    App.mainFrame,
                    "拖放cfg文件到左边，点击 开始处理按钮 自动翻译\n翻译完成人工校对一下即可\n之后可考虑提交到 https://mods.factorio.com/mod/chinese",
                    "消息",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        new DropTarget(pathInput, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable tf = dtde.getTransferable();
                    if (tf.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        List files = (List) tf.getTransferData(DataFlavor.javaFileListFlavor);
                        if (files.size() > 1) {
                            dtde.rejectDrop();
                            return;
                        }
                        File file = (File) files.get(0);
//                        if (file.isDirectory()) {
//                            dtde.rejectDrop();
//                            return;
//                        }
                        selectInputFile(file.getAbsolutePath());

                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        new DropTarget(pathOutput, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable tf = dtde.getTransferable();
                    if (tf.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        List files = (List) tf.getTransferData(DataFlavor.javaFileListFlavor);
                        if (files.size() > 1) {
                            dtde.rejectDrop();
                            return;
                        }
                        File file = (File) files.get(0);
                        if (!file.isDirectory()) {
                            dtde.rejectDrop();
                            return;
                        }
                        pathOutput.setText(file.getAbsolutePath());

                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        overCheck.addActionListener(e -> {
            selectInputFile(pathInput.getText());
        });
    }

    private void startResize() {
        String inputText = pathInput.getText();
        String outputText = pathOutput.getText();
        if (StringUtils.isAnyEmpty(inputText, outputText)) {

            return;
        }
        prviewer.setText("");


        CfgTranslator.of(inputText)
                .outDir(outputText)
                .onProgress(new CfgTranslator.ReProgressListener() {
                    @Override
                    public void progressUpdate(String evt) {
                        prviewer.append(evt + "\n");
                    }

                    @Override
                    public void progressRightUpdate(String evt) {
                        transed.append(evt + "\n");
                    }
                })
                .start();

    }

    public static TransForm getInstance() {
        if (form == null) {
            form = new TransForm();
        }
        return form;
    }


    private void selectInputFile(String file) {
        pathInput.setText(file);

        if (StringUtils.isEmpty(file)) {
            return;
        }

        boolean directory = FileUtil.isDirectory(file);

        if (autoOutCheckBox.isSelected()) {
            boolean selected = overCheck.isSelected();
            if (selected) {
                String parent = new File(file).getParent();
                if (directory) {
                    parent = file;
                }
                pathOutput.setText(parent);
            } else {
                String parent =
                        new File(file).getParent() + "/zh-CN";
                if (directory) {
                    parent = file + "/zh-CN";
                }
                pathOutput.setText(parent);
            }
        }
    }

}
