package com.rdlsmile.myCode.chap3;

import com.rdlsmile.myCode.base.Apple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;


/**
 * Created by Administrator on 2017/10/5.
 */
public class SortingToEnd {
    /*
    第三章小结：
    1.lambda表达式可以理解为一种匿名函数：它没有名称，但有参数列表、函数主题、返回类型，可能还有一个可以抛出的异常列表
    2.lambda表达式让你可以简洁的传递代码
    3.函数式接口就是仅仅声明了一个抽象方法的接口
    4.只有在接受函数式接口的地方才可以使用lambda表达式
    5.lambda表达式允许你直接内联，为函数式接口的抽象方法提供实现，并且将整个表达式作为函数接口的一个实例
    6.java8 自带一些常用的函数式，放在java.util.function包里，包括Ptedicate<T> Function<T,R> Supply<T> Consumer<T> 和 BinaryOperator<T>
    7.为了避免装箱操作，对Predicate<T>和Funciton<T,R>等通用函数式接口的原始类型特征化：IntPredicate  IntToLongFunction等
    8.环绕执行模式（即在方所必须的代码中间，你需要执行点什么操作，比如资源分配和清理）可以配合lambda提高灵活性和可用性
    9.lambda表达式是需要代表的类型成为目标类型
    10.方法引用让你重复使用现有的方法实现并直接传递他们
    11.Comparator Predicate和Function等函数式接口都可以有几个可以用来结合lambda表达式的默认方法
     */
    /*
    无参数的构造函数
    构造函数没有参数适合使用 Supplier
    它具有() -> void 的签名，并提供了get方法
    如可以定义(前提是Apple具有无参构造函数)
    Supplier<Apple> c1 = Apple::new
    Apple a1 = c1.get();

    一个参数的构造函数
    构造函数没有参数适合使用 Function
    如可以定义(前提是Apple具有一个参构造函数)
    Function<Integer, Apple> c2 = Apple::new
    Apple a2 = c2.apply(10);

    两个参数的构造函数
    构造函数没有参数适合使用 BiFunction
    如可以定义(前提是Apple具有两个参构造函数)
    Function<String， Integer, Apple> c2 = Apple::new
    Apple a3 = c3.apply("red",10);

    三个参数的构造函数,java没有提供需要自己定义，参照下面code
     */


    // 第一种排序方案
    public static class AppleComparator implements Comparator<Apple> {
        public int compare(Apple a1, Apple a2){
            return a1.getWeight().compareTo(a2.getWeight());
        }
    }
    public static void main(String[] args) {
        //三个参数的构造函数
        TriFunction<String, Integer, Integer, Apple> appleFactory = Apple::new;
        Apple a = appleFactory.apply("red",1,2);
        System.out.println(a);

        // 第一种排序方案
        List<Apple> list = new ArrayList<>();
        list.add(a);
        list.sort(new AppleComparator());

        //第二种排序方案（使用匿名内部类）
        list.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        });

        //第三种排序方案（使用lambda）
        list.sort((Apple a1, Apple a2) ->a1.getWeight().compareTo(a2.getWeight()));
        //简略写法
        list.sort((a1, a2) ->a1.getWeight().compareTo(a2.getWeight()));

        //第四种排序方案
        list.sort(comparing((apple) -> apple.getWeight()));

        // 使用方法引
        list.sort(comparing(Apple::getWeight));
    }

}
