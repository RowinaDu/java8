package com.rdlsmile.myCode.chap7;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/*
重要的结论：
1.对应是否是否有效率，记住有疑问，测量
2.留意装箱。自动拆装箱操作会大大降低性能，java8中有intSteam LongSteam DoubleStream 来避免拆装箱操作，但凡有可能都应用这些流
3.有些操作本身在并行流上的性能就比顺序流差。特别是limit和findFirst等依赖于元素顺序的操作，他们在并行执行代价非常大。
4.还有考虑流的成长流水线的总计成本
5.要考虑背后的数据结构是否易于分解
6.流自身的提点，以及流水线中的中间操作修改的方法，都可能会改变分解过程的性能
7.还有考虑终端操作中合并步骤的代价是大是小

一般的有如下的结论
====================================================
            源               可分解性
        ArrayList               极佳
        LinkList                差
        IntStream.range         极差
        Stream.iterate          差
        HashSet                 好
        TreeSet                 好
====================================================

 */
public class ParallelStream {
    /*
    java7以前的并行处理集合非常麻烦：
        1.你的明确的把包含数据的数据结构分成若干子部分；
        2.你要给每个子部分分配一个独立的线程；
        3.你需要在恰当的时候对他们进行同步来避免不希望出现的竞争条件，等所有的线程完成，最后把这些部分结果合并起来。
    java7引入了一个叫做分支/合并的框架
    java8使用Stream接口就可以实现对数据集执行并行操作。

     */
    //应用传统的Java来处理累加
    public static long iterativeSum(long n){
        long result = 0;
        for(long i=1L;i<=n;i++){
            result += i;
        }
        return result;
    }

    //使用流来处理
    public static long sequentialSum(long n){
        return Stream.iterate(1L, i -> i+1).limit(n).reduce(0L, Long::sum);
    }

    /*
    使用并行流处理--parallel--将流转换为并行流
    使用sequential可以将并行流转换为顺序流
    最终流是并行的还是顺序的取决于parallel和sequential谁在最后写
    如：steam.parallel().filter(...).sequential().map(...).parallel().reduce();
    最终会并行处理
    并行处理内部使用了默认的ForkJoinPool，默认的线程数量是你的处理器数量，这个值是有Runtime.getRuntime().availableProcessors()得到
    但可以通过系统属性java.util.concurrent.ForkJoinPool.common.parallelism来改变线程池的大小如
    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");
    这是一个全局设置，因此它将影响代码中所有的并行流。反过来说目前无法专门为某个并行流指定线程数量，推荐让ForkJoinPool的大小等于处理器数量


    */
    public static long parallelSum(long n){
        return Stream.iterate(1L, i -> i+1).limit(n).parallel().reduce(0L, Long::sum);
    }

    /*
    测试以上三个方法的效率
     */
    public static long measureSumPerf(Function<Long, Long> adder, Long n){
        long fastest = Long.MAX_VALUE;
        for(int i = 0; i < 10; i++){
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1000000;
            System.out.println("result: "+ sum);
            if(duration < fastest){
                fastest = duration;
            }
        }
        return fastest;
    }

    public static long rangedSum(long n){
        return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
    }
//====错误代码示例开始=====
    public static long sideEffectSum(long n){//顺序执行比没有错误
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }
    public static class Accumulator{
        public long total = 0;
        public void add(long vaule){total += vaule;}
    }
    //改为并行就错了
    public static long sideEffectParallelSum(long n){
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }
//====错误代码示例结束=====


    public static void main(String[] args) {
        //测试原始方法   7 msecs
        System.out.println("iterativeSum:"+measureSumPerf(ParallelStream::iterativeSum, 10000000L )+" msecs");
        //测试顺序流     123 msecs
        System.out.println("sequentialSum:"+measureSumPerf(ParallelStream::sequentialSum, 10000000L )+" msecs");
        //测试并行流     141 msecs
        System.out.println("sequentialSum:"+measureSumPerf(ParallelStream::parallelSum, 10000000L )+" msecs");
        /*
        结论：
        使用传统for循环的迭代版本执行起来会快很多，因为它更底层，更重要的是不需要对原始类型做任何的拆装箱操作
        iterate生成的是装箱的对象，必须拆箱成数字才能求和
        我们很难把iterate分成多个独立块来并行执行
        注意：
        必须明白某些流操作比其他操作更容易并行化，具体来说，iterate很难分割成能够独立执行的小块，因为每次应用这个
        函数都有依赖前一次的结果
        措施：
        使用更有针对性的方法，效果和for差不多
        我们在第5章讨论了一个叫LongSteam.rangeClosed的方法。这个方法与iterate相比有两个优点
        LongSteam.rangeClosed直接产生原始类型的long数字，么有拆装箱的开销
        LongStream.rangeClosed会生成数字范围，很容易拆分为独立的小块
         */
        System.out.println("sequentialSum:"+measureSumPerf(ParallelStream::rangedSum, 10000000L )+" msecs");
        //测试错误代码
        System.out.println("sideEffectSum:"+measureSumPerf(ParallelStream::sideEffectSum, 10000000L )+" msecs");
        System.out.println("sideEffectParallelSum:"+measureSumPerf(ParallelStream::sideEffectParallelSum, 10000000L )+" msecs");
        /*
        这种方法能不能并行已经无所谓了，结果是错误的。
        因为多个线程在同时访问累加器，执行total += value，不是一个原子操作，问题根源在于forEach中调用的方法有副作用，
        它会改变多个线程共享的对象的可变状态，要极力避免这种情况。
         */

    }




}
