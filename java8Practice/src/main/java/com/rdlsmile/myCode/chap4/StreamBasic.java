package com.rdlsmile.myCode.chap4;

import com.rdlsmile.myCode.base.Dish;

import java.util.Arrays;
import java.util.List;

import static com.rdlsmile.myCode.base.Dish.menu;
import static java.util.stream.Collectors.toList;

/**
 * Created by Administrator on 2017/10/5.
 */
/*
流允许以声明性方式处理数据集合
流和迭代器类型，只能遍历一次，只能消费一次
诸如filter，map,sorted等中间操作返回另一个流
诸如collect等终端操作会终止一个流

中间操作
=======================================================================================
    操作           类型           返回类型            操作参数            函数描述
    filter         中间           Stream<T>          Predicate<T>        T -> boolean
    map            中间           Stream<T>          Function<T,R>       T -> R
    limit          中间           Stream<T>
    sorted         中间           Stream<T>          Comparator<T>       (T,T) -> int
    distinct       中间           Stream<T>
=======================================================================================

终端操作
=======================================================================================
    操作           类型                          目的
    forEach        终端         消费流中的每个元素并对其应用lambda,这一操作返回void
    count          终端         返回流中元素的个数，这一操作返回long
    collect        终端         把流归约成一个集合，比如List Map 甚至是Integer
=======================================================================================
 */

/*
第四章小结
1.流是从支持数据处理操作的源生成的一系列元素
2.流利用内部迭代：通过filter map sorted等操作被抽象掉了
3.流操作有两种：中间操作和终端操作
4.filter和map等中间操作会返回一个流，并且可以链接到一起。可以用他们来设置一条流水线，但并不会生成任何结果
5.foEach和count等终端操作会返回一个非流的值，并处理流水线以返回结果
6.流中的元素是按需计算的
 */
public class StreamBasic {
    public static void main(String[] args) {

        List<String> threeHighCaloricDishName =
                menu.stream().filter(d -> d.getCalories()>300).map(Dish::getName).limit(3).collect(toList());
        System.out.println(threeHighCaloricDishName);

        /*
        limit操作和一种称为短路的技巧，使得只选出了前三个
        filter和map是两个独立的操作，但它们合并到一次遍历中了（我们成这种技术为循环合并）
        */
        List<String> toSeeClearly =
                menu.stream()
                        .filter(
                                d -> {
                                    System.out.println("filtering: "+d.getName());
                                    return d.getCalories()>300;
                                })
                        .map(d -> {
                            System.out.println("mapping: "+d.getName());
                            return d.getName();
                        })
                        .limit(3)
                        .collect(toList());
        System.out.println(toSeeClearly);
    }



}
