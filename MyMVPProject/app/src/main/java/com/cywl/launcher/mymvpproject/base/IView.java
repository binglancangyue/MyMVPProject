package com.cywl.launcher.mymvpproject.base;

import android.widget.Toolbar;

/**
 * 根据需求添加大多数View需要用到方法
 */
public interface IView {
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     */
    void showMessage(String message);

    void setToolBar(Toolbar toolBar, String title, boolean needBackButton);

    void handleError(Exception e);
}
