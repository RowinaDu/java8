package com.rdlsmile.myCode.chap2;

import com.rdlsmile.myCode.base.Apple;

import java.util.ArrayList;
import java.util.List;

/**
 * 将List类型也抽象化
 * Created by Administrator on 2017/10/3.
 */
public class Filter {
    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T e: list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return  result;
    }

    //测试
    public static void main(String...args){
        List<Apple> test = new ArrayList<>();
        test.add(new Apple("red",1,1));
        test.add(new Apple("greed",2,2));
        test.add(new Apple("red",3,3));
        test.add(new Apple("yellow",4,4));
        System.out.println(filter(test,(Apple apple) -> "red".equals(apple.getColor())));

        List<Integer> number = new ArrayList<>();
        number.add(12);
        number.add(23);
        number.add(105);
        number.add(102);
        System.out.println(filter(number,(Integer i) -> i % 2 == 0));
    }
}










