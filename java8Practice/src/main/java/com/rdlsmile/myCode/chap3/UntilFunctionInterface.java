package com.rdlsmile.myCode.chap3;

import com.rdlsmile.myCode.base.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Administrator on 2017/10/4.
 */
public class UntilFunctionInterface {
    /*
    java.util.function.Predicate<T>接口中定义了一个名叫test的抽象方法
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T t: list){
            if(p.test(t)){
                result.add(t);
            }
        }
        return result;
    }

    /*
    java.util.function.Consumer<T>接口中定义了一个名叫accept的抽象方法,没有返回值。你如果需要访问类型为T的对象，并对其进行某些操作
    就可以使用该接口
     */
   public static <T> void forEach(List<T> list, Consumer<T> c){
       for(T t: list){
           c.accept(t);
       }
   }

   /*
    java.util.function.Function<T, R>接口中定义了一个名叫apply的抽象方法，它接受一个泛型T的对象，并返回一个泛型R的对象
    */
   public static <T, R> List<R> map(List<T> list, Function<T, R> f){
       List<R> result = new ArrayList<>();
       for(T t: list){
           result.add(f.apply(t));
       }
    return result;
   }

   //测试
    public static void main(String[] args){
       Predicate<String> nonEmptyStingPredicate = (String s) -> !s.isEmpty();
       List<String> nonEmpty = filter(Arrays.asList("lambda", "in", "action","     ","","end"), nonEmptyStingPredicate);
       System.out.println(nonEmpty);

       forEach(Arrays.asList(1,2,3,4,5), (Integer i) ->System.out.println(i));

       List<Integer> l = map(Arrays.asList("lambda", "in", "action"),(String s) -> s.length());
       System.out.println(l);
    }

}
