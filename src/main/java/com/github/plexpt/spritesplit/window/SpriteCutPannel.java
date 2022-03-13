package com.github.plexpt.spritesplit.window;

import com.github.plexpt.spritesplit.utili.ReaderImage;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import lombok.Getter;

/**
 * 项目名称: SpriteSplit.
 * 创建时间: 2016/8/19.
 * 创 建 人: Var_雨中行.
 * 类 描 述: .
 */
@Getter
public class SpriteCutPannel     {
    private JPanel content;
    private JTextField pathInput;
    private JTextField pathOutput;
    private JTextField name;
    private JTextField num;
    private JTextField format;
    private JTextPane catpre;
    private JButton but1;
    private JButton but2;
    private JTextPane prviewer;
    private JButton start;
    private JCheckBox autoOutCheckBox;

    private String inFile;
    private String outFloder;

    private final double WINDOW_WIDTH = 1366;
    private final double WINDOW_HEIGHT = 740;

    private static SpriteCutPannel spriteCutPannel;

    public static SpriteCutPannel getInstance() {
        if (spriteCutPannel == null) {
            spriteCutPannel = new SpriteCutPannel();
        }
        return spriteCutPannel;
    }

    private SpriteCutPannel() {
        name.setText("split_");
        num.setText("0");
        format.setText(".png");
        initListener();
    }


    public SpriteCutPannel(int x) {
//        this.setContentPane(content);
//        this.setTitle("雪碧图集切割工具 -- 1.0");
//        this.setVisible(true);
//        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        this.setSize((int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);
//        LookAndFeelUtil.centerSizeWindow(this);
//        this.setResizable(false);
        name.setText("split_");
        num.setText("0");
        format.setText(".png");
        initListener();
    }

    public static void start() {
        new SpriteCutPannel();
    }

    private void initListener() {

        DropTargetAdapter targetAdapter = new DropTargetAdapter() {
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
                        if (file.isDirectory()) {
                            dtde.rejectDrop();
                            return;
                        }
                        selectInputFile(file.getAbsolutePath());

                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        DropTarget target = new DropTarget(pathInput, targetAdapter);
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
                        outFloder = pathOutput.getText();

                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        but1.addActionListener(e -> {
            pathInput.setText("");

            JFileChooser fileChooser = new JFileChooser(pathInput.getText());
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int approve = fileChooser.showOpenDialog(content);
            String dbFilePath;
            if (approve == JFileChooser.APPROVE_OPTION) {
                dbFilePath = fileChooser.getSelectedFile().getAbsolutePath();
//                pathInput.setText(dbFilePath);
//                this.inFile = pathInput.getText();
                selectInputFile(dbFilePath);

            }

//            JFileChooser chooser = new JFileChooser();
//            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//            chooser.showDialog(new JLabel(), "选择");
//            String path = "";
//            if ((path = chooser.getSelectedFile().getPath()) != null) {
//                pathInput.setText(path);
//                this.inFile = pathInput.getText();
//            }
        });

        pathInput.addCaretListener(e1 -> {
            catpre.setText("");
            prviewer.setText("");
            this.inFile = pathInput.getText();
            if (!inFile.equals("")) {
                prviewer.insertIcon(new ImageIcon(inFile));
            }
        });

        pathOutput.addCaretListener(e1 -> this.outFloder = pathOutput.getText());

        but2.addActionListener(e -> {
            pathOutput.setText("");
            JFileChooser fileChooser = new JFileChooser(pathInput.getText());
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int approve = fileChooser.showOpenDialog(content);
            String dbFilePath;
            if (approve == JFileChooser.APPROVE_OPTION) {
                dbFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                pathOutput.setText(dbFilePath);
                this.outFloder = pathOutput.getText();

            }

        });

        start.addActionListener(e -> {
            start.setEnabled(false);
            ReaderImage readerImage = new ReaderImage(inFile, this);
            readerImage.setCont(new Integer(num.getText()));
            readerImage.setFileName(name.getText());
            readerImage.setFormat(format.getText());
            readerImage.setPath(outFloder);
            try {
                readerImage.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            start.setEnabled(true);

        });
    }

    private void selectInputFile(String file) {
        pathInput.setText(file);
        inFile = pathInput.getText();
        if (autoOutCheckBox.isSelected()) {
            String parent = new File(file).getParent() + "/split";
            pathOutput.setText(parent);
            this.outFloder = pathOutput.getText();
        }
    }

    public JTextPane getCatpre() {
        return catpre;
    }

    public void showOk() {
        start.setEnabled(true);
        try {
            Runtime.getRuntime().exec("explorer.exe " + outFloder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OkDialog dialog = new OkDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
