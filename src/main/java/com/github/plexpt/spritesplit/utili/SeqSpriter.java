package com.github.plexpt.spritesplit.utili;

import java.util.Arrays;
import java.util.List;

/**
 * @author pengtao
 * @email plexpt@gmail.com
 * @date 2022-03-14 11:38
 */
public class SeqSpriter {
    public static void main(String[] args) {


        System.out.println(makelinefeed("The function \nreturns the \nstring, \nbut with line " +
                "breaks " +
                "inserted at just the right places to make \nsure \nthat\n no line is longer " +
                "than the column number.", 10));


//        for (int i = 0; i < 100; i++) {
//            System.out.println(i + "=" + "[font][color=1][aa=1]");
//        }
    }

    /**
     * 分割长串
     *
     *
     * @param s 一般拆分这样的 1=xx\n2=xx
     * @param length
     * @return
     */
    public static List<String> makelinefeed(String s, int length) {
        //用空格作为分隔符，将单词存到字符数组里面
        String[] str = s.split("\n");
        //利用StringBuffer对字符串进行修改
        StringBuffer buffer = new StringBuffer();
        //判断单词长度，计算
        int len = 0;
        for (String line : str) {
            len += line.length();

            if (len > length) {
                buffer.append("\n" + line + "\n");
                //利用StringBuffer对字符串进行修改
                len = line.length() + 1;
                //+1为换行后读出空格一位
            } else {
                buffer.append(line + "\n");
                len++;
            }
        }
        String result = buffer.toString();
        String[] split = result.split("\n\n");
        return Arrays.asList(split);
    }

}
