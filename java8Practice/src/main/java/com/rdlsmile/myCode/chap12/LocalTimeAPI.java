package com.rdlsmile.myCode.chap12;

import java.time.LocalTime;

/**
 * 一天的时间可以用LocalTime来表示
 */
public class LocalTimeAPI {
    public static void main(String[] args) {
        LocalTime time = LocalTime.of(22, 34,50);
        System.out.println(time); //22:34:50
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        System.out.println("hour="+hour+" | minute="+minute+" | second="+second);//hour=22 | minute=34 | second=50

        //格式化
        LocalTime time1 = LocalTime.parse("22:37:50");//22:37:50
        System.out.println(time1);

    }
}
