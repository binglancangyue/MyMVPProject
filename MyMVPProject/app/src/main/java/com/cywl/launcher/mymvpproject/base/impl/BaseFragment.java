package com.cywl.launcher.mymvpproject.base.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.cywl.launcher.mymvpproject.base.IPresenter;
import com.cywl.launcher.mymvpproject.base.IView;
import com.trello.rxlifecycle3.components.support.RxFragment;

public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements IView {
    private View parentView;
    protected FragmentActivity activity;
    protected P mPresenter;

    public abstract void finishCreateView(Bundle state);

    public abstract P createPresenter();

    public abstract int getLayoutResId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();//创建presenter

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getActivity();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //bind = ButterKnife.bind(this, view);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        finishCreateView(savedInstanceState);
    }


    @Override
    public void showLoading() {
        Toast.makeText(activity, "加载中", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        Toast.makeText(activity, "加载结束", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setToolBar(Toolbar toolBar, String title, boolean needBackButton) {

    }

    @Override
    public void handleError(Exception e) {
        hideLoading();
        showMessage(e.getMessage());
        e.printStackTrace();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

}
