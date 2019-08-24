package com.cywl.launcher.mymvpproject.base;

public interface IPresenter<T extends IView> {

    /**
     * 做一些初始化操作
     */
    void onStart();

    void onDestroy();

    void attachView(T view);

    T getView();

}
