package com.rdlsmile.myCode.base;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/10/5.
 */
public class Dish {
    private String name;
    private boolean vegetarian;
    private int calories;
    private Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int colories) {
        this.calories = colories;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        MEAT,FISH,OTHER
    }
}
