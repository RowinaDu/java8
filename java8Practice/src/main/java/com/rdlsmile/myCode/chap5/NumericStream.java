package com.rdlsmile.myCode.chap5;

import com.rdlsmile.myCode.base.Dish;

import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.rdlsmile.myCode.base.Dish.menu;

/**
 * Created by Administrator on 2017/10/7.
 */
public class NumericStream {

    public static void main(String[] args) {
        /*
        可以使用reduce来计算菜品的热量和
        我们想这样 menu.stream().map(Dish::getCalories).sum(); 就好啦，但这样是错误的Stream()没有定义sum接口

        java 8 引入三个原始类型特化流接口 IntStream DoubleStream LongStream ,分别将流中的元素特化为int double long,
        从而避免暗含的装箱成本，并且提供了常用的数值归约新方法 sum max min

        将流真伪特化版本的常用方法是 mapToInt mapToDouble mapToLong 这些方法和map一样，只是返回一个特化流

        转回对象流 可以使用boxed

        三种特化流分别有一个Optional原始类特化版本 OptionalInt OptionalDouble OptionalLong

        java 8 引入了两个可以用于IntStream LongStream 的静态方法，帮助生成范围 range rangeClosed 区别是前者不含结束值
        */
        menu.stream().map(Dish::getCalories).reduce(Integer::sum);
        menu.stream().mapToInt(Dish::getCalories).sum();
        menu.stream().mapToInt(Dish::getCalories).average();

        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed();
        stream.reduce(Integer::sum);

        OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
        System.out.println(maxCalories);

        IntStream evenNumbers = IntStream.range(1,100).filter(n -> n%2==0);
        IntStream evenNumbers1 = IntStream.rangeClosed(1,100).filter(n -> n%2==0);
        System.out.println(evenNumbers.count()+"  "+evenNumbers1.count());

        //生成勾股数组
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1,100)
                .boxed()
                .flatMap(
                    a -> IntStream.rangeClosed(a,100)
                            .filter(b -> Math.sqrt(a*a + b*b)%1 ==0)
                            .mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a*a + b*b)})
        );
        pythagoreanTriples.limit(5).forEach(t -> System.out.println(t[0]+","+t[1]+","+t[2]));
        //上述代码并不完美，因为要求两次平方根，
        IntStream.rangeClosed(1,100)
                .boxed()
                .flatMap(
                        a -> IntStream
                                .rangeClosed(a,100)
                                .mapToObj(b -> new double[]{a ,b, Math.sqrt(a*a + b*b)}))
                .filter(t -> t[2]%1==0)
                .limit(5)
                .forEach(t -> System.out.println((int)t[0]+","+(int)t[1]+","+(int)t[2]));
    }
}
