package com.rdlsmile.myCode.chap1;

/**
 * Created by Administrator on 2017/9/21.
 */
import java.io.File;
import java.io.FileFilter;

public class FileFilterCode {

    public static void main(String ... args){
        //原先的方法
        File[] hiddenFile = new File(".").listFiles(new FileFilter(){
            public boolean accept(File file){
                return file.isHidden();
            }
        });
        //java8方法
        /*
            java8中方法引用使用 :: 即把这个方法作为值
         */
        File[] hiddenFiles = new File(".").listFiles(File::isHidden);
    }
}










