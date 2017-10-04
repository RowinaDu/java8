package com.rdlsmile.myCode.chap2;

import com.rdlsmile.myCode.base.Apple;

import java.util.ArrayList;
import java.util.List;

/**
 * 将List类型也抽象化
 * Created by Administrator on 2017/10/3.
 * 小结：
 * 1.行为参数化，就是一个方法接受不同的行为作为参数，并在内部使用他们，完成不同行为的能力
 * 2.行为参数化可以让代码更好的适应不断变化的要求，减轻未来的工作量
 * 3.传递代码，就是讲新行为作为参数传递给方法。但在java8以前这实现起来很啰嗦。为接口声明许多只使用一次的实体类而造成的啰嗦代码，在java8之前可以使用匿名类来减少。
 * 4.java API包含很多可以不用行为进行参数化的方法，包括排序、线程和GUI处理。
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
    public static void main(String[] args){
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










