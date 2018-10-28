package com.rdlsmile.myCode.chap12;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/**
 * LocalDateTime是LocalDate和LocalTime的合体，同时表示日期和时间，可以直接创建也可以通过合并构造
 *
 */
public class LocalDateTimeAPI {
    public static void main(String[] args) {

        LocalDateTime dt1  = LocalDateTime.of(2018, Month.OCTOBER,28,22,41,50);
        System.out.println(dt1); //2018-10-28T22:41:50

        LocalDate date = LocalDate.of(2018,10,28);
        LocalTime time = LocalTime.of(22,45,46);

        LocalDateTime dt2 = LocalDateTime.of(date,time);
        System.out.println(dt2); //2018-10-28T22:44:52.404

        LocalDateTime dt3 = date.atTime(time);
        LocalDateTime dt4 = date.atTime(22,45,50);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println("dt3="+dt3+" | dt4="+dt4+" | dt5="+dt5);//dt3=2018-10-28T22:45:46 | dt4=2018-10-28T22:45:50 | dt5=2018-10-28T22:45:46


        LocalDate date1 = dt1.toLocalDate();
        LocalTime time1 = dt1.toLocalTime();
        System.out.println("date1="+ date1+" | time1="+time1);//date1=2018-10-28 | time1=22:41:50
    }
}
