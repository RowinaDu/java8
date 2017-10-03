package com.rdlsmile.myCode.chap2;

import com.rdlsmile.myCode.base.Apple;

/**
 * Created by Administrator on 2017/10/3.
 */
public class AppleGreenColorPredicate implements ApplePredicate{

    @Override
    public boolean test(Apple apple) {
        return "green".equals(apple.getColor());
    }
}
