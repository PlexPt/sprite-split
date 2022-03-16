package com.github.plexpt.spritesplit.utili;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.LinkedHashMap;

import cn.hutool.core.io.FileUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2022-03-04 11:43
 */
@Slf4j
@Data
public class CfgTranslator {

    /**
     * 处理术语和特殊情况
     */
    static LinkedHashMap<String, String> replaceMap = new LinkedHashMap<>();

    static {
        replaceMap.put("[color=# ", "[color=#");
        replaceMap.put("插入器", "机械臂");
        replaceMap.put("[颜色=", "[color=");
        replaceMap.put("[/颜色]", "[/color]");
        replaceMap.put("[字体=", "[font=");
        replaceMap.put("[/字体]", "[/font]");
    }

    String inFile;

    String outDir = "";

    ReProgressListener listener = new ReProgressListener() {
        @Override
        public void progressUpdate(String evt) {
        }

        @Override
        public void progressRightUpdate(String evt) {
        }
    };

    @SneakyThrows
    public static CfgTranslator of(String pathname) {
        CfgTranslator instance = new CfgTranslator();
        instance.setInFile(pathname);
        return instance;
    }

    @SneakyThrows
    public CfgTranslator outDir(String pathname) {
        outDir = pathname;
        return this;
    }

    @SneakyThrows
    public CfgTranslator onProgress(ReProgressListener listener) {
        this.listener = listener;
        return this;
    }

    @SneakyThrows
    public void start() {
        centerLog("开始");
        File in = new File(inFile);

        if (StringUtils.isNotBlank(outDir)) {
            FileUtil.mkdir(outDir);
        } else {
            if (in.isDirectory()) {
                outDir = in.getAbsolutePath();
            } else {
                outDir = in.getParentFile().getAbsolutePath();
            }
        }

        if (in.isDirectory()) {
            for (File file : in.listFiles()) {
                transFile(file);
            }
        } else {
            transFile(in);

        }

        centerLog("处理完成");

    }

    private void transFile(File file) {
        if (!file.getName().endsWith("cfg")) {
            return;
        }
        listener.progressUpdate(FileUtil.readUtf8String(file));

        CfgMerger merger = new CfgMerger();
        merger.putFile(file);

        String sequence = merger.toSequence();

        GoogleTranslateFree.noProxy();
        String translate = GoogleTranslateFree.translate(sequence);
        translate = CfgTranslator.replace(translate);
        merger.fromSequence(translate);

        log.info("翻译前：");
        log.info(sequence);
        log.info("翻译结果：");
        log.info(merger.toSequence());

        File outFile = new File(outDir, file.getName());
        merger.write(outFile);
        listener.progressRightUpdate(FileUtil.readUtf8String(outFile));
    }

    /**
     * 替换器
     * 全文搜索 [颜色= 和 [/颜色] ，恢复成 [color= 和 [/color]
     *
     * @param text
     * @return
     */
    public static String replace(String text) {

        String[] searchList = replaceMap.keySet().toArray(new String[]{});
        String[] replacementList = replaceMap.values().toArray(new String[]{});

        return StringUtils.replaceEach(text, searchList, replacementList);
    }

    public void centerLog(String text) {
        listener.progressUpdate(StringUtils.center(text, 50, "="));
    }

    public void log(String text) {
        listener.progressUpdate(text);
    }

    public interface ReProgressListener {
        void progressUpdate(String evt);

        void progressRightUpdate(String evt);

    }
}
