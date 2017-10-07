package com.rdlsmile.myCode.chap5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2017/10/7.
 */
public class BuildingStream {
    public static void main(String[] args) {

        //由值创建流 Stream.of
        Stream.of("Java 8","Lambda", "In", "Action").map(String::toLowerCase).forEach(System.out::println);

        //由数组创建 Arrays.stream
        int[] numbers = {1,2,3,4,5};
        int sum = Arrays.stream(numbers).sum();
        System.out.println(sum);

        //由文件生成流
        //java用于处理文件的nio已经更新 java.nio.file.Files中很多静态方法都会返回一个流，如Files.line
        //查看文件中有多少个不同的单词
        long uniqueWords= 0;
        try ( Stream<String> lines = Files.lines(Paths.get("D:\\java8\\java8\\java8Practice\\target\\classes\\data.txt"), Charset.defaultCharset())){
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(""))).distinct().count();
            System.out.println(uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //iterate迭代(无界)
        Stream.iterate(0, n -> n+2).limit(10).forEach(System.out::println);

        //iterate斐波那契元组
        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1] ,t[0]+t[1]}).limit(10).forEach(t -> System.out.println("("+t[0]+","+t[1]+")"));
        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1], t[0]+t[1]}).limit(10).map(t -> t[0]).forEach(System.out::println);

        //generate生成无限流
        Stream.generate(Math::random).limit(5).forEach(System.out::println);

        //generate斐波那契元组--不建议使用，因为创建了一个IntSupplier的实例，此对象有可变的状态
        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;
            @Override
            public int getAsInt() {
                int oldValue = this.previous;
                int newValue = this.previous + this.current;
                this.previous = this.current;
                this.current = newValue;
                return oldValue;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);
    }
}









