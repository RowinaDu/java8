package com.rdlsmile.myCode.chap12;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class OperatorParseFormatter {
    /*
    java8提供了非常简单的日期操作

    表示时间点的日期-时间类的通用方法
    --------------------------------------------------------------------------------------------------------------------
    方法名             是否静态方法              描述
    --------------------------------------------------------------------------------------------------------------------
    form                是                   依据传入的Temporal对象创建对象实例
    now                 是                   依据系统时钟创建Temporal对象
    of                  是                   由Temporal对象的某个部分创建该对象的实例
    parse               是                   由字符串创建Temporal对象实例
    atOffset            否                   将Temporal对象和某个时区偏移相结合
    atZone              否                   将Temporal对象和某个时区相结合
    format              否                   使用某个指定的格式器将Temporal对象转化为字符串（Instant不提供该方法）
    get                 否                   读取Temporal对象的某一部分值
    minus               否                   创建Temporal对象的一个副本，通过将当前Temporal对象的值减去一定的时长创建该副本
    plus                否                   创建Temporal对象的一个副本，通过将当前Temporal对象的值加上一定的时长创建该副本
    with                否                   以该Temporal对象为模板，对某些状态进行修改创建该对象的副本
    --------------------------------------------------------------------------------------------------------------------



    有时候需要更复杂的操作是可以使用TemporalAdjuster中的静态方法
    --------------------------------------------------------------------------------------------------------------------
    方法名                             描述
    --------------------------------------------------------------------------------------------------------------------
    dayOfWeekInMonth                创建一个新的日期，它的值为同一个月中每一周的第几天
    firstDayOfMonth                 创建一个新的日期，它的值为当月的第一天
    firstDayOfNextMonth             创建一个新的日期，它的值为下月的第一天
    firstDayOfNextYear              创建一个新的日期，它的值为明年的第一天
    firstDayOfYear                  创建一个新的日期，它的值为当年的第一天
    firstInMonth                    创建一个新的日期，它的值同一个月中，第一个符合星期几要求的值
    lastDayOfMont                   创建一个新的日期，它的值为当月的最后一天
    lastDayOfNextYear               创建一个新的日期，它的值为明年的最后一天
    lastDayOfYear                   创建一个新的日期，它的值为今年的最后一天
    lastInMonth                     创建一个新的日期，它的值为同一个月中，最有一个符合星期几要求的值
    next/previous                   创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星期几要求的日期
    nextOrSame/previousOrSame       创建一个新的日期，并将其值设定为日期调整后或调整前，第一个符合指定星期几要求的日期，
                                        如果该日期已经符合要求，直接返回该对象
    --------------------------------------------------------------------------------------------------------------------


     */

    public static void main(String[] args) {
        //以直观的方式操作LocalDate的属性
        LocalDate date = LocalDate.of(2018,10,28);
        LocalDate date2 = date.withYear(2011);
        LocalDate date3 = date2.withDayOfMonth(25);
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR,9); //2011-09-25
        System.out.println(date4);

        //以相对的方式操作LocalDate的属性

        LocalDate date5 = date.plusWeeks(1);
        LocalDate date6 = date5.minusYears(1);
        LocalDate date7 = date6.plus(6, ChronoUnit.MONTHS);
        System.out.println(date7);//2018-05-04


        LocalDate date8 = date.with(lastDayOfMonth());
        System.out.println(date8);//2018-10-31


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        System.out.println(formattedDateTime);
        LocalDateTime dateTime = LocalDateTime.parse(formattedDateTime,formatter);
        System.out.println(dateTime);
    }


}
