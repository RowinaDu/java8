package com.rdlsmile.myCode.chap3;

/**
 * Created by Administrator on 2017/10/5.
 */
public interface TriFunction<T, U ,V ,R> {
    R apply(T t, U u, V v);
}
