package com.rdlsmile.myCode.chap3;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/4.
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
}
