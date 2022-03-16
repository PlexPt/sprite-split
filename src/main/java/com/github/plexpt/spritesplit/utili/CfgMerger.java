package com.github.plexpt.spritesplit.utili;

/**
 * @author pengtao
 * @email plexpt@gmail.com
 * @date 2022-03-16 15:23
 */

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.io.FileUtil;
import lombok.Data;

/**
 * @author pt
 * @email plexpt@gmail.com
 * @date 2022-02-26 19:57
 */
@Data
public class CfgMerger {


    LinkedHashMap<String, LinkedHashMap<String, String>> iniMap = new LinkedHashMap();


    public void put(String section, String key, String value) {
        LinkedHashMap<String, String> map = iniMap.getOrDefault(section, new LinkedHashMap<>());
        map.put(key, value);
        iniMap.put(section, map);
    }

    public void putFiles(List<File> files) {
        for (File file : files) {
            putFile(file);
        }
    }

    public void putFile(File file) {
        List<String> lines = FileUtil.readUtf8Lines(file);

        String section = "";
        for (String line : lines) {
            line = line.trim();

            if (StringUtils.isBlank(line) ||
                    StringUtils.startsWithAny(line, "#", ";")) {
                continue;
            }

            //读到section
            if (line.startsWith("[")) {
                section = line.substring(1, line.length() - 1);
                // System.out.println(line);
                continue;
            }

            //读到key
            String key = line.substring(0, line.indexOf("="));
            String value = line.substring(line.indexOf("=") + 1);

            put(section, key, value);
        }
    }

    public String toSequence() {
        String result = "";
        int index = 0;
        for (Map.Entry<String, LinkedHashMap<String, String>> entry : iniMap.entrySet()) {
            for (Map.Entry<String, String> stringEntry : entry.getValue().entrySet()) {
                result = result + index + "=" + stringEntry.getValue() + "\n";
                index++;
            }
        }
        return result;
    }

    public void fromSequence(String seq) {
        String[] split = seq.split("\n");

        int index = 0;
        for (Map.Entry<String, LinkedHashMap<String, String>> entry : iniMap.entrySet()) {
            for (Map.Entry<String, String> stringEntry : entry.getValue().entrySet()) {

                String value = split[index];
                value = value.substring(value.indexOf("=") + 1);
                stringEntry.setValue(value);
                index++;

            }
        }
    }


    public void write(File path) {
        FileUtil.touch(path);

        for (Map.Entry<String, LinkedHashMap<String, String>> entry : iniMap.entrySet()) {
            String section = entry.getKey();
            if (StringUtils.isNotBlank(section)) {
                section = "[" + section + "]";
            }
            section = section + "\n";

            FileUtil.appendUtf8String(section, path);

            LinkedHashMap<String, String> entryValue = entry.getValue();
            for (Map.Entry<String, String> stringEntry : entryValue.entrySet()) {
                String line = stringEntry.getKey() + "=" + stringEntry.getValue() + "\n";
                FileUtil.appendUtf8String(line, path);
            }
            FileUtil.appendUtf8String("\n", path);
        }
    }

//    public void writeExcel(String name) {
////        FileUtil.touch(path);
//
//        List<ExcelCfg> list = new ArrayList<>();
//
//        String section = "";
//        for (Map.Entry<String, LinkedHashMap<String, String>> entry : iniMap.entrySet()) {
//            section = entry.getKey();
//
//            LinkedHashMap<String, String> entryValue = entry.getValue();
//            for (Map.Entry<String, String> stringEntry : entryValue.entrySet()) {
//
//                ExcelCfg cfg = new ExcelCfg();
//                cfg.setSection(section);
//                cfg.setKey(stringEntry.getKey());
//                cfg.setV(stringEntry.getValue());
//                list.add(cfg);
//
//            }
//            Excel.simpleWrite(list, name);
//        }
//    }
//
//    public void readExcel(String name) {
////        FileUtil.touch(path);
//        List<ExcelCfg> list = Excel.simpleRead(name);
//
//        for (ExcelCfg cfg : list) {
//            put(cfg.getSection(), cfg.getKey(), cfg.getV());
//        }
//    }

    public static void main(String[] args) {
//        loadResult();

//
//        CfgMerger merger = new CfgMerger();
//        String prefix = "all-remain";
//
//        String pathname = "D:\\project\\othermods\\zhcfg";
//
//        File[] files = new File(pathname).listFiles();
//        for (File file : files) {
//            if (file.getName().startsWith(prefix)) {
//                merger.putFile(file);
//            }
//        }
//        //  merger.write(new File(pathname, prefix + ".cfg"));
//        merger.writeExcel(pathname + "\\" + prefix + ".xlsx");

    }
//
//    private static void loadResult() {
//        CfgMerger merger = new CfgMerger();
//        merger.readExcel("L:\\Download\\all-remain.xlsx");
//
//        merger.write(new File("D:\\project\\othermods\\zhcfg\\all-remain-zh.cfg"));
//    }


}
