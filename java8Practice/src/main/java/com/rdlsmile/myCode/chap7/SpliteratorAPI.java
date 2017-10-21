package com.rdlsmile.myCode.chap7;

import java.util.function.Consumer;

public class SpliteratorAPI {
    /*
    Spliterator是Java8中加入的另一个新接口，代表 可分迭代器（splitable iterator）,和Iterator一样Spliterator也用于遍历数据源
    中的数据，但它是为了并行执行而设计的。
    Java8已经为集合框架提供了一个默认的Spliterator实现。集合实现了Spliterator接口，接口提供了一个spliterator方法。
     */
    //Spliterator接口
    public interface Spliterator<T>{
        boolean tryAdvance(Consumer<? super T> action);
        Spliterator<T> trySplit();
        long estinateSize();
        int characteristics();
    }
    /*
    与往常一样，T是Spliterator遍历的元素的类型。tryAdvance方法的行为类似于普通的Iterator，因为它会顺序一个一个使用Spliterator
    中的元素，并且如果还有其他元素要遍历就返回true。
    将Stream拆分成多个部分的算法是一个递归过程，第一步是对第一个Spliterator调用trySplit生成第二个Spliterator。第二步对这两个
    Spliterator调用trySplit，这样总共就有了四个Spliterator。这个框架不断对Spliterator调用trySplit直到它返回null，
    表明它处理的数据结构不能再分割。最后所有的Spliterator在调用trySplit时都返回了null
    Spliterator接口声明的最后一个抽象方法是characteristics 它将返回一个int，代表Spliterator本身特性集的编码。区别与收集器的特性
    尽管他们特性有重叠，但编码却不一样
    ==============================================================================================
      特性                                        含义
    ORDERED         元素有既定的顺如List，因此Spliterator在遍历和划分时也会遵循这一顺序
    DISTINCT        对任意一对遍历过的元素x和y，x.equals(y)返回false
    SORTED          遍历的元素按照一个预定义的顺序排序
    SIZED           该Spliterator由一个已知大小的源建立如Set，因此estimateSize()返回的是精确值
    NONNULL         保证遍历的元素不为null
    IMMUTABLE       Spliterator的数据源不能修改，这意味着在遍历是不能添加 删除 或修改任何元素
    CONCURRENT      该Spliterator的数据源可以被其他线程同时修改而无需同步
    SUBSIZED        该Spliterator和所有从它拆分出来的Spliterator都是SIZED
    ==============================================================================================




     */





}
