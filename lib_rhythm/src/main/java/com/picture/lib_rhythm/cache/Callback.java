package com.picture.lib_rhythm.cache;

/**
 * Time:2019/11/7
 * Author:xmz-dell
 * Description:
 */
public interface Callback {
    void onSuccess();

    void onError();

    public static class EmptyCallback implements Callback {

        @Override public void onSuccess() {
        }

        @Override public void onError() {
        }
    }
}
