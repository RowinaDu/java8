package com.rdlsmile.myCode.chap12;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;

/**
 * LocalDate 该类的实例匙一个不可变的对象，它只提供了简单的日期，并不包含当天的时间信息。另外它也不附带任何与时区相关的信息
 */
public class LocalDateAPI {

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2018, 10, 25);
        System.out.println(date);//2018-10-25

        int year = date.getYear(); //2018
        Month month = date.getMonth();//OCTOBER
        int day = date.getDayOfMonth();//25
        DayOfWeek dow = date.getDayOfWeek();//THURSDAY
        int len = date.lengthOfMonth();//31
        boolean leap = date.isLeapYear();//false

        System.out.println("year="+year+" | month="+month+" | day="+day+" | dow="+dow+" | len="+len+" | leap="+leap);

        //从系统中获取当前日期
        LocalDate today = LocalDate.now();
        System.out.println(today);//2018-10-28


        //使用TemporalField读取LocalDate的值
        int year1 = date.get(ChronoField.YEAR);//2018
        int month1 = date.get(ChronoField.MONTH_OF_YEAR);//10
        int day1 = date.get(ChronoField.DAY_OF_MONTH);//25
        System.out.println(year1+" "+month1+" "+day1);

        //格式化
        LocalDate date1 = LocalDate.parse("2018-10-28");
        System.out.println(date1);//2018-10-28

    }

}
