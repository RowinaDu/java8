package com.rdlsmile.myCode.chap6;


import com.rdlsmile.myCode.base.Dish;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rdlsmile.myCode.base.Dish.menu;
import static java.util.stream.Collectors.*;

public class CollectorsShow {
    public static void main(String[] args) {
        //统计菜品的数量
        long howManyDishes = menu.stream().collect(Collectors.counting());
        //更简洁的写法
        System.out.println(menu.stream().count());
        System.out.println(howManyDishes);

        //查找最大最小
        Comparator<Dish> dishCaloriesComparator = Comparator.comparing(Dish::getCalories);
        Optional<Dish> mostCaloriesDish = menu.stream().collect(maxBy(dishCaloriesComparator));
        System.out.println(mostCaloriesDish);

        //菜品的总热量--同样的还有summingLong summingDouble
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(totalCalories);

        //计算平均数--同样的还有averagingLong averagingInt
        double avgCalories = menu.stream().collect(averagingDouble(Dish::getCalories));
        System.out.println(avgCalories);

        //收集器会先将信息存放在 ingSummaryStatistics的类里，它提供了方便的getter方法来访问结果
        //类似的还有 LongSummaryStatistics  DoubleSummaryStatistics
        IntSummaryStatistics summaryStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(summaryStatistics+"\n  getter  "+summaryStatistics.getAverage());

        //链接字符 joining 还有重载方法可以添加分隔符
        String shortMenu = menu.stream().map(Dish::getName).collect(joining());
        System.out.println(shortMenu);
        String shortMenus = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println(shortMenus);

        //其实以上的方法都可以用Collectors.reducing来实现
        /*
            第一个参数：归约的起始值，也就是流中没有元素时的返回值
            第二个参数：映射方法，返回一个收集器
            第三个参数：是一个BinaryOperator 将两个项目积累成一个同类型的值

            单参数的reducing 方法可以看做是三个参数的特殊情况，
                它把流中的第一个项目作为起点
                把恒等函数(即一个函数仅仅是返回其输入参数)作为一个转换函数
                这就意味着要把reducing收集器传递给空流的collect方法，收集器没有起点
         */
        int totalCaloriesBase = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i+j));
        System.out.println(totalCaloriesBase);

        Optional<Dish> mostCaloriesDishBase = menu.stream().collect(reducing((d1, d2) -> d1.getCalories()>d2.getCalories()? d1:d2));
        System.out.println(mostCaloriesDishBase);

        //收集器框架十分灵活，不同方法可以得到同样结果
        int totalCaloriesOther = menu.stream().collect(reducing(0,Dish::getCalories,Integer::sum));
        System.out.println(totalCaloriesOther);

        /*
        就像流中的任何但参数reduce操作一样，reduce(Integer::sum)返回的不是int而是Optional<Integer>，以便在空流时
        安全的执行归约操作，然后get就能取到值了，这种情况下get方法是安全的，以为你已经确定流不为空
        一般来说，使用允许提供默认的方法，如orElse或orElseGet来解开Optional中包含的值更为安全

        其实，最简洁的方法是将流映射到一个intStream然后调用sum方法(首选)
         */
        totalCaloriesOther = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
        System.out.println(totalCaloriesOther);
        totalCaloriesOther = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(totalCaloriesOther);

        /*
        Stream接口的collect和reduce方法都可以获得相同的结果，但有什么区别呢
            reduce 方法旨在把两个值结合起来，他是一个不可变的归约
            collect 方法的设计就是要改变容器，从而积累要输出的结果

            下面的例子是在滥用reduce方法，因为它在原地改变了作为累加器的List
            以错误的语义使用reduce方法还会造成一个实际问题，该归约不能并行工作，因为由多个线程并发修改一个数据结构可能
            会破坏List本身，这种情况下要想线程安全，就需要每次分配一个新的List，而对象分配有会影响性能。
            这就是collect方法特别适合表达可变容器上归约的原因--它适合并行操作
         */
        Stream<Integer> stream = Arrays.asList(1,3,4,5,6).stream();
        List<Integer> numbers = stream.reduce(
                new ArrayList<>(),
                (List<Integer> l, Integer e) -> {l.add(e);return l;},
                (List<Integer> l1, List<Integer> e1) -> {l1.addAll(e1);return l1;}
        );
        System.out.println(numbers);

        /*
        用reducing连接字符串(以下方法不建议使用，建议使用joining)
        替代  String shortMenu = menu.stream().map(Dish::getName).collect(joining());

         */
        shortMenu = menu.stream().collect(reducing("", Dish::getName, (s1, s2) -> s1 + s1));
        System.out.println("="+shortMenu);//为甚是空串？
        shortMenu = menu.stream().map(Dish::getName).collect(reducing((s1, s2) ->s1+s2)).get();
        System.out.println(shortMenu);

        /*
        以下这句是错误的 无法编译，因为reducing接受的参数是一个BinaryOperator<t> 也就是BinaryOperator<T, T, T>
        这就意味着它需要的函数必须能接受来个参数，然后返回一个相同类型的值，这里接受的参数是两个菜，返回却是一个字符串
        shortMenu = menu.stream().collect(reducing((d1, d2) -> d1.getName() + d2.getName() )).get();
        */

    }
}











