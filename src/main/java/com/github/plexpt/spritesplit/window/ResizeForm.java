package com.github.plexpt.spritesplit.window;

import com.github.plexpt.spritesplit.utili.IconReSizer;

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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import cn.hutool.core.io.FileUtil;
import lombok.Getter;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2022-03-12 14:17
 */
@Getter
public class ResizeForm {
    private JPanel content;
    private JTextPane catpre;
    private JButton but1;
    private JTextField pathInput;
    private JTextField pathOutput;
    private JButton but2;
    private JCheckBox autoOutCheckBox;
    private JTextArea prviewer;
    private JButton start;
    private JCheckBox overCheck;


    private static ResizeForm form;

    public ResizeForm() {
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
        IconReSizer.of(inputText)
                .outDir(outputText)
                .onProgress(evt -> {
                    prviewer.append(evt + "\n");
                })
                .resize();

    }

    public static ResizeForm getInstance() {
        if (form == null) {
            form = new ResizeForm();
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
                String parent = new File(file).getParent() + "/" + FileUtil.getName(new File(file).getParent());
                if (directory) {
                    parent = file + "/" + FileUtil.getName(file);
                }
                pathOutput.setText(parent);
            }
        }
    }

}
