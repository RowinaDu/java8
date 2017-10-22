package com.rdlsmile.myCode.chap7;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CountWords {
    //统计文本单词数量
    static final String testWords = " Nel   mezzo del cammin di nostra  vita " +
            "mi ritrovai in una  selva  oscura che la dritta via era smarrita ";
    //迭代器写法
    public static int countWordsIteratively(String s) {
        int count = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) count++;
                lastSpace = false;
            }
        }
        return count;
    }

    //归约方法
    public static int countWords(Stream<Character> stream){
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }

    //测试
    public static void main(String[] args) {
        System.out.println(countWordsIteratively(testWords));

        //将String转换为一个流
        Stream<Character> stream = IntStream.range(0, testWords.length()).mapToObj(testWords::charAt);
        System.out.println(countWords(stream));
        //使用并行,此处输出25不是正确结果，因为并没有限制在那个地方拆分单词
        stream = IntStream.range(0, testWords.length()).mapToObj(testWords::charAt);
        System.out.println(countWords(stream.parallel()));

        //测试自定义Spliterator
        Spliterator spliterator1 = new WordCounterSpliterator(testWords);
        Stream<Character> stream1 = StreamSupport.stream(spliterator1, true);
        System.out.println(countWords(stream1));
    }

}

//没有规定拆分方案
class WordCounter{
    private final int counter;
    private final boolean lastSpace;
    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    public WordCounter accumulate(Character c){
        if(Character.isWhitespace(c)){
            return lastSpace ? this : new WordCounter(counter, true);
        }else {
            return lastSpace ? new WordCounter(counter + 1, false) : this;
        }
    }

    public WordCounter combine(WordCounter wordCounter){
        return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
    }

    public int getCounter(){
        return counter;
    }
}

//自定义Spliterator
class WordCounterSpliterator implements Spliterator<Character> {

    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(String string){
        this.string = string;
    }
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++));
        return currentChar < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if(currentSize < 10){
            return null;
        }
        for(int splirPos = currentSize / 2 + currentChar; splirPos < string.length(); splirPos++){
            if(Character.isWhitespace(string.charAt(splirPos))){
                Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splirPos));
                currentChar = splirPos;
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}