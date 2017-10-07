package com.rdlsmile.myCode.chap5;

/**
 * Created by Administrator on 2017/10/7.
 */
public class Stream {

    /*

第五章 小结
1.Stream API 在下表中，可以进行复杂的数据处理
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

2.可以使用filter distinct skip limit 对流进行筛选和切片
3.可以使用map flatMap 提取或转换流中的元素
4.可以使用findFirst findAny 查找流中的元素，可以使用allMatch noneMatch anyMatch 方法让流匹配给定的元素
    这些方法都用到了短路
5.可以使用reduce方法将流中所有的元素迭代合并成一个结果，例如求和或查找最大值
6.filter map 操作都是无状态的，他们并不存储任何状态。reduce要存储状态才能计算出一个值。sorted 和 distinct 操作也要存储状态
    因为它们需要把流中的元素缓存起来才能返回一个新的流，这种操作叫做有状态操作
7.流有三种基本的原始类型特化： IntStream DoubleStream LongStream ,它们的操作也有相应的特化
8.流不仅可以从集合创建，也可以从值 数组 文件 以及 iterate 和 generate 等特定方法创建
9.无限流是没有固定大小的流





*/
}
