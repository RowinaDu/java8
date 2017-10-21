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

    /*
    虽然分支/合并框架还算简单易用，不幸的是它也很容易被误用。以下是有效使用它的最佳做法
    1.对于一个任务调用join方法会阻塞调用方，指导该任务做出结果。因此，有必要在两个任务的计算都开始之后在调用它。否则，
    你得到的版本会比原始的顺序算法更慢更复杂，因为每个子任务都必须等待另一个子任务完成才能启动。
    2.不应该在RecursiveTask内部使用ForkJoinPool的invoke方法。相反你应该始终直接调用compute或fork方法，只用顺序代码才应该
    使用invoke来启动并行计算。
    3.对子任务调用fork方法会把它排进ForkJoinPool。同时对左边和右边的子任务调用它似乎很自然，但这样做的效率要比直接对其中
    一个调用compute低，这样做你可以为其中一个子任务重用同一个线程，从而避免在线程池中分配一个任务造成的开销。
    4.调用使用分支/合并框架的并行计算可能有些棘手。特别是在你平常都喜欢的IDE里面看栈跟踪（stacktrace）来找问题，但放在
    分支-合并计算上就不行了，因为调用compute的线程并不是概念上的调用方，后者是调用fork的那个。
    5.和并行流一样，你不应理所当然的认为在多核处理器上使用分支/合并框架就比顺序计算块。我们已经说过，一个任务可以分解成
    多个独立的子任务，才能让性能在并行化时有所提升。所有这些子任务的运行时间都应该比分出新任务所花的时间长；
    一个惯用方式把输入/输出放在一个子任务里，计算放在另一个里，这样计算就可以和输入/输出同时进行。此外，在比较同一算法
    的顺序和并行版本的性能时还有别的因素要考虑。就像其他任何Java代码一样，分支/合并框架需要预热或者说要执行几遍才会被JTT
    编译器优化。这就是为什么在测量性能之前要跑几遍程序很重要，我们的测试框架就是这么做的。同时还有知道，编译器内置的优化
    可能会为顺序版本带来一些优势（例如执行死码分析--删去从未被使用的计算）。
    6.

     */

}
