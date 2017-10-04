package com.rdlsmile.myCode.chap2;

import com.rdlsmile.myCode.base.Apple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */
public class FilteringApples {
    /*
    第一个解决方案
     */
    public static List<Apple> fileterGreenApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for(Apple apple: inventory){
            if("green".equals(apple.getColor())){
                result.add(apple);
            }
        }
        return result;
    }

    /*
    将颜色作为参数传入
     */
    public static List<Apple> filterAppleByColor(List<Apple> inventory, String color){
        List<Apple> result = new ArrayList<>();
        for(Apple apple: inventory){
            if(apple.getColor().equals(color)){
                result.add(apple);
            }
        }
        return result;
    }

    /*
    使用接口
     */
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple: inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    /*
    使用匿名内部类
     */
    public static List<Apple> filterApples(List<Apple> inventory){
        List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
        return redApples;
    }

    /*
    使用lambda表达式
     */
    public static List<Apple> filterUseLambda(List<Apple> inventory){
        return filterApples(inventory, (Apple apple) -> "red".equals(apple.getColor()));
    }

    /*
    测试
     */
    public static void main(String[] args){
        List<Apple> test = new ArrayList<>();
        test.add(new Apple("red",1,1));
        test.add(new Apple("greed",20,20));
        test.add(new Apple("red",3,3));
        test.add(new Apple("yellow",4,4));
        //测试lambda表达式
        List<Apple> result = filterUseLambda(test);
        System.out.println(result);
        //测试匿名内部类
        result = filterApples(test);
        System.out.println(result);

        //java8 List 自带了sort
        test.sort((Apple a1 , Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(test);

        //线程里使用lambda表达式
        Thread r = new Thread(() -> System.out.println("Hello World"));
        r.start();
    }


}














