package com.rdlsmile.myCode.chap6;

import com.rdlsmile.myCode.base.Dish;

import java.util.*;

import static com.rdlsmile.myCode.base.Dish.menu;
import static java.util.stream.Collectors.*;

public class GroupingShow {
    public static void main(String[] args) {
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);
        dishesByType
                .forEach((t,f) ->
                {System.out.print("\n"+t+"=  ");f.stream()
                        .map(Dish::getName).forEach(a -> System.out.print(a+" "));});


        Map<Dish.CaloriesLevel, List<Dish>> dishesByCaloriesLevel = menu.stream().collect(groupingBy(
                dish ->{if(dish.getCalories() <= 400) return Dish.CaloriesLevel.DIET;
                        else if(dish.getCalories() <= 700) return Dish.CaloriesLevel.NORMAL;
                        else return Dish.CaloriesLevel.FAT;
                }));
        System.out.println(dishesByCaloriesLevel);
        dishesByCaloriesLevel
                .forEach((t,f) ->
                {System.out.print("\n"+t+"=  ");f.stream()
                        .map(Dish::getName).forEach(a -> System.out.print(a+" "));});

        //多级分组,这种分组能扩展至任意层级
        Map<Dish.Type, Map<Dish.CaloriesLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream()
                .collect(groupingBy(Dish::getType, groupingBy(dish -> {
                    if(dish.getCalories() <= 400) return Dish.CaloriesLevel.DIET;
                    else if(dish.getCalories() <= 700) return Dish.CaloriesLevel.NORMAL;
                    else return Dish.CaloriesLevel.FAT;}
                )));
        System.out.println(dishesByTypeCaloricLevel);
        dishesByTypeCaloricLevel.forEach((t, f) -> {
            System.out.print(t+"=");
            f.forEach(
                    (m, n) -> {
                        System.out.print(" "+m+"");
                        n.stream().map(Dish::getName).forEach(a -> System.out.print(" "+a));
                    });
            System.out.print("\n");
        });

        //groupingBy的第二个参数可以是任意类型,单个参数其实是groupingBy(f, toList()) 的简便写法
        Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println(typesCount);

        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream()
                .collect(groupingBy(Dish::getType, maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println(mostCaloricByType);
        mostCaloricByType
                .forEach((t,f) ->
                {System.out.print(t+"=  ");
                    f.map(Dish::getName).ifPresent(System.out::println);});

        //把收集器转换为另一种类型，可以使用collectingAndThen
        Map<Dish.Type, Dish> mostCaloricByTypeOther = menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(mostCaloricByTypeOther);
        mostCaloricByTypeOther
                .forEach((t,f) ->
                System.out.println(t+"=  "+f.getName()));

        //groupingBy 和其他收集器联合使用
        Map<Dish.Type, Integer> totalCaloricByType = menu.stream()
                .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        System.out.println(totalCaloricByType);

        //每种类型的菜品中都有哪些CaloricLevel
        Map<Dish.Type, Set<Dish.CaloriesLevel>> caloricLevelsByType= menu.stream().collect(
                    groupingBy(Dish::getType, mapping(
                            dish -> { if (dish.getCalories() <= 400) return Dish.CaloriesLevel.DIET;
                            else if (dish.getCalories() <= 700) return Dish.CaloriesLevel.NORMAL;
                            else return Dish.CaloriesLevel.FAT; },
                            toSet() )));
        System.out.println(caloricLevelsByType);
        //上面的例子对返回的set类型并么有任何保证。但通过toCollection 就可以更多的控制，如你可以传递一个构造函数引用来要求HashSet
        Map<Dish.Type, Set<Dish.CaloriesLevel>> caloricLevelsByTypeUseCollection = menu.stream().collect(
                groupingBy(Dish::getType, mapping(
                        dish -> {
                            if(dish.getCalories() <= 400) return Dish.CaloriesLevel.DIET;
                            else if (dish.getCalories() <= 700) return Dish.CaloriesLevel.NORMAL;
                            else return Dish.CaloriesLevel.FAT; },
                        toCollection(HashSet::new)
                )));
        System.out.println(caloricLevelsByTypeUseCollection);


    }

}
