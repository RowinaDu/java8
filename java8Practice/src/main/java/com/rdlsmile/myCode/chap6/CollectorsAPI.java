package com.rdlsmile.myCode.chap6;


import java.util.*;
import java.util.function.*;
import java.util.stream.Collector.*;
import static java.util.stream.Collector.Characteristics.*;

public class CollectorsAPI {
    /*
    下表展示了Collectors类静态工厂方法能够创建的所有的收集器，以及他们用于一个叫做menuStream的Stream<Dish>上的例子
========================================================================================================================
    工厂方法            返回类型                                                用于
    toList              List<T>                                 把流中所有项目收集到一个List
                        使用示例：List<Dish> dishes = menuStream.collect(toList());
------------------------------------------------------------------------------------------------------------------------
    toSet               Set<T>                                 把流中所有项目收集到一个Set,删除重复项
                        使用示例：Set<Dish> dishes = menuStream.collect(toSet());
------------------------------------------------------------------------------------------------------------------------
    toCollection        Collection<T>                          把流中所有项目收集到给定的供应源创建的集合
                        使用示例：Collection<Dish> dishes = menuStream.collect(toCollection(), ArrayList::new);
------------------------------------------------------------------------------------------------------------------------
    counting            Long                                   计算流中的个数
                        使用示例：int howManyDishes = menuStream.collect(counting());
------------------------------------------------------------------------------------------------------------------------
    summingInt          Integer                                对流中项目的一个整数属性求和
                        使用示例：int totalCalories = menuStream.collect(summingInt(Dish::getCalories));
------------------------------------------------------------------------------------------------------------------------
    averagingInt        Double                                 计算流中的Integer属性的平均值
                        使用示例：Double avgCalories = menuStream.collect(averagingInt(Dish::getCalories));
------------------------------------------------------------------------------------------------------------------------
    summarizingInt      IntSummaryStatistics                   收集关于流中项目Integer属性的统计值，
                                                                    例如最大值 最小值总和与平均值
                        使用示例：IntSummaryStatistics menuStatistics = menuStream
                                                                        .collect(summarizingInt(Dish::getCalories));
------------------------------------------------------------------------------------------------------------------------
    joining             String                                 连接对流中每个项目调用toString方法所生成的字符串
                        使用示例：String shortMenu = menuStream.map(Dish::getName).collect(joining(","));
------------------------------------------------------------------------------------------------------------------------
    maxBy               Optional<T>                            一个包裹了流中按照给定比较器选出的最大元素的Optional，
                                                                    或如果流为空则为Optional.empty()
                        使用示例：Optional<Dish> fattest = menuStream.collect(maxBy(ComparingInt(Dish::getCalories)));
------------------------------------------------------------------------------------------------------------------------
    minBy               Optional<T>                            一个包裹了流中按照给定比较器选出的最小元素的Optional，
                                                                    或如果流为空则为Optional.empty()
                        使用示例：Optional<Dish> fattest = menuStream.collect(minBy(ComparingInt(Dish::getCalories)));
------------------------------------------------------------------------------------------------------------------------
    reducing            归约操作产生的类型                      从一个作为累加器的初始值开始，利用BinaryOperator与
                                                                    流中的元素逐个结合，从而将流归约为单个值
                        使用示例：int totalCalories = menuStream.collect(reducing(0, Dish::getCalories, Integer::sum));
------------------------------------------------------------------------------------------------------------------------
    collectingAndThen   转换函数返回的类型                      包裹另一个收集器，对其结果应用转换函数
                        使用示例：int howManyDishes = menuStream.collect(collectingAndThen(toList(), List::size));
------------------------------------------------------------------------------------------------------------------------
    groupingBy          Map<K, List<T>>                         根据项目的一个属性的值对流中的项目作分组，并将属性值作为
                                                                    结果Map的键值
                        使用示例：Map<Dish.Type, List<Dish>> dishesByType = menuStream.collect(groupingBy(Dish::getType));
------------------------------------------------------------------------------------------------------------------------
    partitioningBy      Map<Boolean, List<T>>                   根据对流中每个项目应用谓词的结果来对项目进行分区
                        使用示例：Map<Boolean, List<Dish>> vegetarianDishes = menuStream
                                                                    .collect(partitioningBy(Dish::isVegetarian));
========================================================================================================================

第六章 小结
    1.collect是一个终端操作，它接受的参数是将流中元素累积到汇总结果的各种方式（称为收集器）
    2.预定义收集器包括将流元素归约和汇总到一个值，例如计算最小值，醉最大值或平均值，这些收集器在上表中
    3.预定义收集器可以使用groupingBy对流中元素进行分组，或用partitioningBy进行分区
    4.收集器可以高效的复合起来，进行多级分组，分区和归约
    你可以实现Collector接口中定义的方法来开发你自己的收集器。

     */

    /**
     * 这是Collector接口的定义
     * T 流中要收集的项目的泛型
     * A 累加器的类型，累加器是在收集过程中用于积累部分结果的对象
     * R 收集器操作得到的对象（通常不一定是集合）的类型
     */
    public interface Collector<T, A, R>{
        Supplier<A> supplier();
        BiConsumer<List<T>, T> accumulator();
        Function<A, R> finisher();
        BinaryOperator<A> combiner();
        Set<Characteristics> characteristics();
    }

    /*
    理解Collector接口中的方法
        前四个方法都会返回一个会被collect方法调用的函数，第五个方法characteristics则提供了一系列特征，也就是一个提示列表，
        告诉collect方法在执行归约操作时可以应用哪些优化

        1.建立新的结果容器： supplier方法
            supplier方法必须返回一个结果为空的Supplier，也就是一个无参函数，在调用时它会创建一个空的累加器，供数据收集过程使用。

        2.将元素添加到结果容器： accumulator方法
            accumulator方法会返回归约操作的函数。当遍历到流中第n个元素时，这个函数执行时会有两个参数：保存归约结果的累加器（已经
            收集了流中的n-1个项目），还有第n个元素本身。该函数返回void，因为累加器是原位更新，即函数的执行改变了它的内部状态以体现
            遍历的元素的效果。对于ToListCollector，这个函数仅仅会把当前项目添加至已经遍历过的项目的列表。

        3.对结果容器应用最终转换：finisher方法
            在遍历完流后，finisher方法必须返回在累积过程的最后要调用的一个函数，以便将累加器对象转换为整个集合操作的最终结果。通常，
            就像ToListCollector的情况一样，累加器对象恰好符合预期的最终结果，因此无需进行转换。所以finisher方法只需返回identity函数

        这三个方法已经足以对流进行顺序归约，实际中的实现细节可能还要复杂一点，一方面是因为流的延迟性质，可能在collect操作之前还需要完成
        其他中间操作的流水线，另一方面则是理论上可能要进行并行归约。

        4.合并两个结果容器：combiner方法
            四个方法中的最后一个----combiner方法会返回一个供归约操作使用的函数，它定义了对流的各个子部分进行并行处理时，各个子部分归约
            所得的累加器要如何合并。对toList而言。这个方法的实现非常简单，只要把从流的第二个部分收集到的项目列表加到遍历第一部分时得到的
            列表后面就行了

        有了这四个方法，就可以对流进行并行归约了。它会用到Java7中引入的分支/合并框架和Spliterator抽象。
            原始流会以递归方式拆分为子流，直到定义流是否需要进一步拆分的一个条件为非（如果分布式工作单位太小，并行计算往往比顺序计算要慢，
            而且是要生成的并行任务比处理器内核多很多的话就毫无意义了）
            现在，所有的子流都可以进行并行处理，即对每个子流应用顺序递归算法
            最后，使用收集器combiner方法返回的函数，将所有的部分结果两两合并。这时会把原始流每次拆分时得到的子流对应的结果合并起来。

        5.characteristics方法
            最后一个方法----characteristics会返回一个不可变的Characteristics集合，它定义了收集器的行为----尤其是关于流是否可以并行归约，
            以及可以使用哪些优化的提示。
            Characteristics是一个包含三个项目的枚举
                UNORDERED---归约结果不受流中项目的遍历和累积顺序的影响
                CONCURRENT---accumulator函数可以从多个线程同时调用，且该收集器可以并行归约。如果收集器没有标为UNORDERED,那它仅在用于无序
                数据源时才可以并行归约。
                IDENTITY_FINISH---这表明完成器方法返回的函数是一个恒等函数。可以跳过。这种情况下，累加器对象将会直接用作归约过程的最终结果。
                这也就意味着，将累加器A不加检查的转换为结果R是安全的。
                我们迄今开发的ToListCollector是IDENTITY_FINISH的，因为用来累积流中元素的List已经是我们要的最终结果，用不着进一步转换了，但它并
                不是UNORDERED,因为用在有序流上的时候，我们还是希望顺序能够保留在到的的List中。最终，它是CONCURRENT的，但我们刚才说过了，仅仅在
                背后的数据源无序时才会并行处理。


     */
    //我们将以上的融合到一起
    public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
        @Override
        public Supplier<List<T>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            return List::add;
        }

        @Override
        public Function<List<T>, List<T>> finisher() {
            return Function.identity();
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            return (list1, list2) -> {list1.addAll(list2); return list1;};
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
        }
    }



}
