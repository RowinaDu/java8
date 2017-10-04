package com.rdlsmile.myCode.chap3;

/**
 * Created by Administrator on 2017/10/4.
 */
public class Criterion {
    /*
    lambda 表达式的格式
    (Apple a1, Apple a2)    ->    a1.getWeight().compareTo(a2.getWeight());
    ====================    ==   =========================================
         lambda参数        箭头                 lambda主体


    java8中有效的5个lambda例子
         (String s) -> s.length();
         该表达式具有一个String类型的参数并返回了一个int。lambda没有return语句，因为已经隐含了return.

         (Apple a) -> a.getWeight() > 150;
         该表达式有一个Apple类型的参数并返回一个Boolean

         (int x, int y) -> {
            System.out.println("Result: ");
            System.out.println(x+y);
         }
         该表达式具有两个int类型的 参数而没有返回值（void返回）。注意lambda表达式可以包含多行语句，这里是两行

         () -> 42
         改表达式没有参数，返回一个int

         (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
         该表达式具有两个Apple类型的参数，返回一个int，比较两个Apple的重量


    lambda的基本语法是
        (parameters) -> expression
        或者
        (parameters) -> {statements; }

     */

}
