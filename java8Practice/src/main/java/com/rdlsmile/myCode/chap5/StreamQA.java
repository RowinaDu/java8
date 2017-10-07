package com.rdlsmile.myCode.chap5;

import com.rdlsmile.myCode.base.Trader;
import com.rdlsmile.myCode.base.Transaction;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.rdlsmile.myCode.base.Dish.menu;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by Administrator on 2017/10/6.
 */
public class StreamQA {

    public static void main(String[] args) {
        //给定一个数字列表，返回每个元素的平方
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream().map(n -> n*n).collect(toList());
        System.out.println(squares);

        //返回给定列表的数对形式
        List<Integer> numbers1 = Arrays.asList(1,2,3);
        List<Integer> numbers2 = Arrays.asList(4,5,6);
        List<int[]> pairs = numbers1.stream()
                .flatMap(
                        i -> numbers2.stream()
                                .map(j -> new int[]{ i,j }))
                .collect(toList());
        System.out.println(Arrays.deepToString(pairs.toArray()));//[[1, 4], [1, 5], [1, 6], [2, 4], [2, 5], [2, 6], [3, 4], [3, 5], [3, 6]]

        //返回总和能被3整除的数对
        List<int[]> pairs1 = numbers1.stream()
                .flatMap(
                    i -> numbers2.stream()
                            .filter(j -> (i+j)%3 == 0)
                            .map(j -> new int[]{i,j})
                ).collect(toList());
        System.out.println(Arrays.deepToString(pairs1.toArray()));//[[1, 5], [2, 4], [3, 6]]

        //统计菜单菜品的数量
        menu.stream().map(d -> 1).reduce((a,b) -> a+b).ifPresent(System.out::println);
        System.out.println(menu.stream().count());


        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950));

        //找出2011年的所有交易并按交易额排序
        List<Transaction> tr2011 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
        System.out.println(tr2011);

        //交易员在那些城市工作
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());
        System.out.println(cities);
        //或者
        System.out.println(transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(toSet()));

        //查找所有来自剑桥的交易员，按姓名排序
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
        System.out.println(traders);

        //返回所有交易员的名字字符串，按字母顺序排列
        String tradeStr = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("",(s1,s2) -> s1+s2);
        System.out.println(tradeStr);

        //有没有交易员在米兰工作
        boolean milanBased = transactions.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(milanBased);

        //打印生活在剑桥的交易员的所有交易额
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        //所有交易中最高的交易额是多少
        Optional<Integer> highestValue = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
        System.out.println(highestValue);

        //最小交易额的交易
        Optional<Transaction> smallestTransaction = transactions.stream().reduce((t1,t2) -> t1.getValue()<t2.getValue() ? t1:t2);
        System.out.println(smallestTransaction);
        //或者
        System.out.println(transactions.stream().min(comparing(Transaction::getValue)));





    }





}
