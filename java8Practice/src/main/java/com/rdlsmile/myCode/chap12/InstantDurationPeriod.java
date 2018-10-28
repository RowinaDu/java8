package com.rdlsmile.myCode.chap12;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class InstantDurationPeriod {
    /*

    作为人，我们习惯一星期几、几号、几点、几分来理解日期和时间，但计算机的建模时间是表示一个持续时间段上某个点的单一大整型数。
    新的java.time.Instant对时间的见面方式，是一Unix元年时间开始所经历的的秒数来进行计算，

    duration和period表示时间间隔

    LocalDateTime和Instant时为不同的目的设计的，一个是为了便于人阅读使用，一个是微利便于机器处理，所以二者不可以混用。


    日期-时间类中表示时间间隔的通用方法
    --------------------------------------------------------------------------------------------------------------------
    方法名     是否静态方法          方法描述
    --------------------------------------------------------------------------------------------------------------------
    between         是               创建两个时间点之间的interval
    form            是               由一个临时间点创建interval
    of              是               由它的组成部分创建interval的实例
    parse           是               由字符串创建interval的实例
    addTo           否               创建该interval的副本，并将其叠加到某个指定的temporal对象
    get             否               读取该interval的状态
    isNegative      否               检查该interval是否为负值，不包含零
    isZero          否               检查该interval的时长是否为零
    minus           否               通过减去一定的时间创建该interval的副本
    multipliedBy    否               将interval的值乘以某个标量创建该interval的副本
    negated         否               以忽略某个时长的方式创建该interval的副本
    plus            否               以增减某个时长的方式创建该interval的副本
    subtractFrom    否               从指定的temporal对象中减去该interval
    --------------------------------------------------------------------------------------------------------------------

     */
    public static void main(String[] args) {
        Instant instant = Instant.ofEpochMilli(123);//表示从1970年1月1日0时往后123毫秒
        System.out.println(instant);//1970-01-01T00:00:00.123Z

        Duration threeMinutes = Duration.ofMinutes(3);
        Duration threeMinutes1 = Duration.of(3, ChronoUnit.MINUTES);

        Period tenDays = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2,6,1);

    }
}
