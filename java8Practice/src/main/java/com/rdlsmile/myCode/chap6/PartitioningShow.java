package com.rdlsmile.myCode.chap6;

import com.rdlsmile.myCode.base.Dish;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.rdlsmile.myCode.base.Dish.menu;
import static java.util.stream.Collectors.*;

public class PartitioningShow {
    public static void main(String[] args) {
        /*
        分区的优势在于保留了分区函数返回true 或false 的两套流元素列表
        可以把分区看做是分组的特殊情况
         */
        Map<Boolean, List<Dish>> partitionedMap = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println(partitionedMap);
        System.out.println("素食为："+ partitionedMap.get(true));
        //使用filter同样可以
        List<Dish> vegetarianDishes = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
        System.out.println(vegetarianDishes);

        //partitioning提供了重载方法，可以传递第二个收集器
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        System.out.println(vegetarianDishesByType);

        //找出素食和非素食中热量最高的
        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, collectingAndThen(
                    maxBy(Comparator.comparingInt(Dish::getCalories)),Optional::get)));
        System.out.println(mostCaloricPartitionedByVegetarian);

        /*
        和groupingBy类似的 partitioningBy收集器也可以结合其他的收集器使用，尤其是他可以与第二个partitioningBy一起使用来实现多级分区
         */
        menu.stream().collect(partitioningBy(Dish::isVegetarian,partitioningBy(
                d -> d.getCalories()>500
        ))).forEach((b, m) -> System.out.println(b+"="+m));

        //以下这句编译错误，因为partitioningBy需要一个谓词，也就是返回一个布尔值的函数
        //menu.stream().collect(partitioningBy(Dish::isVegetarian, partitioningBy(Dish::getType)));

        //计算每一个分区中的项目的数目
        menu.stream().collect(partitioningBy(Dish::isVegetarian, counting())).forEach((b, m) -> System.out.println(b+"="+m));

        //将质数和非质数分区
        System.out.println(partitionPrimes(50));

    }
    public static boolean isPrime(int candidate){
        return IntStream.range(2, candidate).noneMatch(i -> candidate % i == 0);
    }
    //优化后的测评因子
    public static boolean isPrimeForOptimize(int candidate){
        int candidateRoot = (int)Math.sqrt((double)candidate);
        return IntStream.range(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }
    //给质数分区
    public static Map<Boolean, List<Integer>> partitionPrimes(int n){
        return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(candidate -> isPrime(candidate)));
    }
}
