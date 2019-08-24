package com.cywl.launcher.mymvpproject.base.impl;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import com.cywl.launcher.mymvpproject.base.IPresenter;
import com.cywl.launcher.mymvpproject.base.IView;
import com.trello.rxlifecycle3.components.RxActivity;

/**
 * MVP Activity基类
 *
 * @param <P>
 */
public abstract class BaseActivity<P extends IPresenter> extends RxActivity implements IView {
    public P mPresenter;

    public abstract P createPresenter();

    public abstract int getLayoutResId(Bundle savedInstanceState);

    public abstract void initData(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = getLayoutResId(savedInstanceState);
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //创建P对象
                mPresenter = createPresenter();
                if (mPresenter != null) {
                    Log.d("aaa", "onCreate: mPresenter");
                    mPresenter.attachView(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void setToolBar(Toolbar toolBar, String title, boolean needBackButton) {

    }

    @Override
    public void handleError(Exception e) {

    }


    @Override
    protected void onDestroy() {
        //把所有的数据销毁掉
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        System.gc();
        super.onDestroy();
    }
}
