package com.rdlsmile.myCode.chap7;

public class CountWords {
    //统计文本单词数量
    static final String testWords = " Nel   mezzo del cammin di nostra  vita " +
            "mi ritrovai in una  selva  oscura che la dritta via era smarrita ";
    //迭代器写法
    public static int countWordsIteratively(String s) {
        int count = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) count++;
                lastSpace = false;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countWordsIteratively(testWords));
    }



}
