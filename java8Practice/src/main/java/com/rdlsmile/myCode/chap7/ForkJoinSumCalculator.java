package com.rdlsmile.myCode.chap7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

import static com.rdlsmile.myCode.chap7.ParallelStream.measureSumPerf;

/*
分支合并框架的目的是以递归的方式将可以并行的任务拆分成更小的任务，然后将每个子任务的结果合并起来生成整体结果。
他是ExecutorService接口的一个实现，把子任务分配给线程池（称为ForkJoinPool）中的工作线程。
使用RecursiveTask
要将任务提交到这个池，必须创建RecursiveTask的一个子类，其中R是并行化任务产生的结果类型，或者任务不放结果，则是RecursiveAction
类型。要定义RecursiveTask，只需实现它唯一的抽象方法compute：
protected abstract R compute();
其实现的伪代码为
    if(任务足够小或不可拆分){
        顺序执行该任务
    }else{
        将任务拆分为来个子任务
        递归调用本方法，拆分每个子任务，等待所有子任务完成
        合并每个子任务的结果
    }
 */

public class ForkJoinSumCalculator extends RecursiveTask<Long>{ //继承RecursiveTask来创建可以用于分支合并的任务

    private final long[] numbers;   //要求求和的数组
    private final int start;    //子任务处理数组的起始和终止位置
    private final int end;

    public static final long THRESHOLD = 10000; //不再将任务分解为子任务的数组大小

    //公共构造函数用于创建主任务
    public ForkJoinSumCalculator(long[] numbers){
        this(numbers, 0, numbers.length);
    }

    //私有构造函数用于递归方式为主任务创建子任务
    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    //覆盖RecursiveTask抽象方法，该任务负责求和的部分的大小
    @Override
    protected Long compute() {
        int length = end - start;
        //如果大小小于或等于阈值，顺序执行
        if(length <= THRESHOLD){
            return computeSequentially();
        }
        //创建一个子任务来为数组的前一半求和
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
        //利用另一个ForkJoinPool线程异步执行新创建的子任务
        leftTask.fork();
        //创建一个任务为数组的后一半求和
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
        //同步执行第二个子任务，有可能允许进一步递归划分
        long rightResult = rightTask.compute();
        //读取第一个子任务的结果，如果尚未完成就等待
        long leftResult = leftTask.join();
        //该任务的结果是子任务结果的组合
        return rightResult + leftResult;
    }

    //在子任务不再可分时计算结果的简单算法
    private long computeSequentially(){
        long sum = 0;
        for(int i = start; i < end; i++){
            sum +=numbers[i];
        }
        return sum;
    }

    //编写方法来并行对前n个自然数求和就简单了
    public static long forkJoinSum(long n){
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    //测试并行计算,这个版本性能不是很好，因为必须先要把整个数字流都放进一个long[]，之后才能在ForkJoinSumCalculator中使用
    public static void main(String[] args) {
        System.out.println("forkJoinSum: "+ measureSumPerf(ForkJoinSumCalculator::forkJoinSum, 10000000L) +"msecs");
    }

}
