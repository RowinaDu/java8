package com.rdlsmile.myCode.chap2;

/**
 * Created by Administrator on 2017/10/3.
 */
public class MeaningOfThis {
    public final int value = 4;
    public void doIt(){
        int value = 6;
        Runnable r = new Runnable() {
            int value = 5;
            @Override
            public void run() {
                int value = 10;
                System.out.println(this.value); //这里this指的是包含它的Runnable,而不是外面的类MeaningOfThis;
            }
        };
        r.run();
    }
    public static void main(String...args){
        MeaningOfThis m = new MeaningOfThis();
        m.doIt();
    }
}
