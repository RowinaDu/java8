package com.rdlsmile.myCode.base;

/**
 * Created by Administrator on 2017/10/3.
 */
public class Apple {
    private String color;
    private Integer size;
    private Integer weight;

    public Apple(String color, Integer size, Integer weight){
        this.color = color;
        this.size = size;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", size=" + size +
                ", weight=" + weight +
                '}';
    }
}
