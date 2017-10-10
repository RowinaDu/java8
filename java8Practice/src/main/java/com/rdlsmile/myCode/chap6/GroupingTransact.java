package com.rdlsmile.myCode.chap6;

import com.rdlsmile.myCode.base.Transact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rdlsmile.myCode.base.Transact.transacts;
import static java.util.stream.Collectors.groupingBy;
/*
Collector接口找那个方法的实现决定了如何对流进行归约操作，
Collectors实用类提供了很多静态方法，可以方便的创建常见收集器的实例，最常用的是toList

收集器主要提供三大功能：
1.将元素归约和汇总为一个值
2.元素分组
3.元素分区
 */
public class GroupingTransact {
    public static void main(String[] args) {
        //按照货币对交易分组
        //之前的写法
        Map<Transact.Currency, List<Transact>> transactsByCurrencyBefore = new HashMap<>();
        for(Transact transact : transacts){
            Transact.Currency currency = transact.getCurrency();
            List<Transact> transactsForCurrencyBefore = transactsByCurrencyBefore.get(currency);
            if(transactsForCurrencyBefore == null){
                transactsForCurrencyBefore = new ArrayList<>();
                transactsByCurrencyBefore.put(currency, transactsForCurrencyBefore);
            }
            transactsForCurrencyBefore.add(transact);
        }
        System.out.println(transactsByCurrencyBefore);

        //Lambda写法--groupingBy 意思是生成一个Map 它的键这里是货币，值是交易的列表
        Map<Transact.Currency, List<Transact>> transactsByCurrency = transacts.stream().collect(groupingBy(Transact::getCurrency));
        System.out.println(transactsByCurrency);
    }

}
