package com.rdlsmile.myCode.chap3;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * Created by Administrator on 2017/10/4.
 */
public class FileReadLambda {
    //这是没有使用lambda的方法
    public static String processFile() throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
            return br.readLine();
        }
    }

    //这是使用了lambda的方法
    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader  br = new BufferedReader(new FileReader("data.txt"))){
                return p.process(br);
            }
    }

    //测试
    public static void main(String[] args) throws IOException {
        String origin = processFile();
        System.out.println(origin);

        String oneLine = processFile((BufferedReader br) -> br.readLine());
        System.out.println(oneLine);

        String twoLine = processFile((BufferedReader br) -> br.readLine()+ " ,"+ br.readLine());
        System.out.println(twoLine);
    }
}
