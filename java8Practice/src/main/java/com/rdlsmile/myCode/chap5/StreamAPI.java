package com.rdlsmile.myCode.chap5;

import com.rdlsmile.myCode.base.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.rdlsmile.myCode.base.Dish.menu;
import static java.util.stream.Collectors.toList;

/**
 * Created by Administrator on 2017/10/6.
 */
/*
中间操作和终端操作整理
=========================================================================================================
    操作           类型                       返回类型        函数式接口/使用类型            函数描述
    filter         中间                       Stream<T>          Predicate<T>             T -> boolean
    map            中间                       Stream<R>          Function<T,R>            T -> R
    flatMap        中间                       Stream<R>          Function<T,Stream<R>>
    limit          中间(有状态--有界)         Stream<T>          long
    sorted         中间(有状态--无界)         Stream<T>          Comparator<T>            (T,T) -> int
    distinct       中间(有状态--无界)         Stream<T>
    skip           中间(有状态--有界)         Stream<T>          long
---------------------------------------------------------------------------------------------------------
    anyMatch       终端                       boolean            Predicate<T>             T -> boolean
    noneMatch      终端                       boolean            Predicate<T>             T -> boolean
    allMatch       终端                       boolean            Predicate<T>             T -> boolean
    findAny        终端                       Optional<T>
    findFirst      终端                       Optional<T>
    forEach        终端                       void               Consumer<T>              T -> boolean
    collect        终端                       R                  Collector<T,A,R>
    reduce         终端(有状态--有界)         Optional<T>        BinaryOperator<T>        (T,T) -> T
    count          终端                       long
=========================================================================================================
 */
public class StreamAPI {
    /*
    filter : 该操作会接受一个谓词（一个返回boolean的函数）作为参数，返回一个包含符合谓词的元素的流
    distinct :它会返回一个元素的各异（根据流所生成的hashCode和equals方法实现）的流
    limit : 它会返回一个不超过给定长度的流。如果流是有序的，则最多会返回前n个元素
    skip : 返回一个扔掉了前n个元素的流。如果流中的元素不足n个，则返回一个空流
    map和flatMap 即映射：map会接受一个函数作为参数，这个函数会应用到每个元素上，并将其映射成一个新的元素；
        flatMap就是将一个流中的每个值都转换成另一个流，然后把所有的流链接起来成为一个流
    allMatch  anyMatch  noneMatch  findFirst  findAny  查找和匹配
        allMatch  anyMatch  noneMatch 都用到了短路 如同 && ||
    reduce 归约--可以求和 计算最大最小值等
    流也支持 min  max
     */
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,1,4,4,2,8,9);
        numbers.stream().filter(a -> a % 2 ==0).distinct().forEach(System.out :: println);

        List<String> dishName = menu.stream().map(Dish::getName).collect(toList());
        List<String> words = Arrays.asList("Java 8", "Lambda", "In", "Action");
        List<Integer> wordLengths  = words.stream().map(String::length).collect(toList());
        List<Integer> dishNameLengths = menu.stream().map(Dish::getName).map(String::length).collect(toList());
        System.out.println(dishName+"\n"+wordLengths+"\n"+dishNameLengths);

        //返回单词中不重复的字母
        //错误的方案
        words.stream().map(w -> w.split("")).distinct().collect(toList());//这里只能返回List<String[]>
        words.stream().map(w -> w.split("")).map(Arrays::stream).distinct().collect(toList());//仍然不能达到目的，现在得到的是一个留的列表
        //正确的方案--各个数组并不是分别映射成为一个流，而是映射成为流的内容；使用map(Arrays::stream)是生成的单个流都被合并起来了，成为了一个扁平化的流
        List<String> word = words.stream().map(w -> w.split("")).flatMap(Arrays::stream).distinct().collect(toList());
        System.out.println(word);

        //查找匹配示例
        if(menu.stream().anyMatch(Dish::isVegetarian)){ //anyMatch  返回一个Boolean，是一个终端操作
            System.out.println("The menu is (somewhat) vegetarian friendly");
        }
        boolean isHealthy1 = menu.stream().allMatch(d -> d.getCalories()<1000);  //allMatch  所有都匹配
        boolean isHealthy2 = menu.stream().noneMatch(d -> d.getCalories()>=1000);//noneMatch 没有匹配的
        System.out.println(isHealthy1+" "+isHealthy2);

        /*
        Optional<T> （java.util.Optional） 是一个容器，代表一个值存在或不存在，避免null检查相关的bug
            isPresent(） 将在Optional包含值的时候返回true,否则返false
            ifPresent(Consumer<T> block)  会在值存在的时候执行代码块
            T get()  会在值存在的时候返回值，否则抛出NoSuchElement异常
            T orElse(T other)  会在值存在时返回值，否则返回一个默认值
         */
        Optional<Dish> dish = menu.stream().filter(Dish::isVegetarian).findAny();
        dish.ifPresent(d -> System.out.println(d.getName()));

        //findAny 和 findFirst 都能返回一个元素。但找到第一个在并行上限制更多，如果不关心返回的元素是哪个请使用findAny
        Arrays.asList(1,2,3,4,5,6).stream().filter(i -> i%3 ==0).findFirst().ifPresent(d -> System.out.println(d));

        //归约
        int sum = numbers.stream().reduce(0, (a, b) -> a+b);
        System.out.println(sum);
        System.out.println(numbers.stream().reduce(Integer::sum));//Integer提供了sum方法。当reduce没有默认值时返回Optional
        System.out.println(numbers.stream().reduce(0,Integer::max));
        System.out.println(numbers.stream().reduce(Integer::min)+" <=> "+numbers.stream().reduce((a, b) -> a<b?a:b));
    }

}
