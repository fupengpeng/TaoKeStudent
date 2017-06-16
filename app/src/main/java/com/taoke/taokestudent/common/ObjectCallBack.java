package com.taoke.taokestudent.common;

/**
 * 回调
 */
public interface ObjectCallBack<T> {

    /**
     * 当操作成功时，调用该方法
     *
     * @param data
     */
    void onSuccess(T data);

    /**
     * 当操作失败时，调用该方法
     *
     * @param e
     */
    void onFail(Exception e);
}
