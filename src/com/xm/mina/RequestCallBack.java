package com.xm.mina;


/**
 * Created by liuwei on 2017/2/21.
 */

public interface RequestCallBack<T> {
    void Response(T t);
}
