package com.cywl.launcher.mymvpproject.base.impl;

import com.cywl.launcher.mymvpproject.base.IPresenter;
import com.cywl.launcher.mymvpproject.base.IView;
import com.cywl.launcher.mymvpproject.model.tools.ApiRetrofit;
import com.cywl.launcher.mymvpproject.model.tools.ApiService;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter<V extends IView> implements IPresenter<V> {
    //Model数据
    protected CompositeDisposable compositeDisposable;
    protected ApiService apiService =
            ApiRetrofit.getInstance().getRetrofit().create(ApiService.class);
    //View 显示回显的接口
    private WeakReference<V> mRootView;
    private LifecycleProvider mProvider;
//    private LifecycleProvider<FragmentEvent> mFragmentProvider;

    protected BasePresenter(LifecycleProvider provider) {
        this.mProvider = provider;
        onStart();
    }


    protected LifecycleProvider getProvider() {
        return mProvider;
    }

    /*public LifecycleProvider<FragmentEvent> getFProvider() {
        return mFragmentProvider;
    }

    */

    /**
     * Fragment LifecycleProvider
     *
     * @param provider
     *//*
    public void setFragmentLifecycleProvider(LifecycleProvider<FragmentEvent> provider) {
        this.mFragmentProvider = provider;

    }*/
    private void clearCompositeDisposable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        if (mRootView != null) {
            mRootView.clear();
            mRootView = null;
        }
        clearCompositeDisposable();
    }

    @Override
    public void attachView(V view) {
        this.mRootView = new WeakReference<>(view);
    }

    @Override
    public V getView() {
        return mRootView.get();
    }

    /**
     * 用于 Activity
     *
     * @param observable
     * @param subscriber
     * @param <T>
     */
    protected <T> void addSubscription(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mProvider.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(subscriber);
    }

    /**
     * 用于 Fragment
     *
     * @param observable
     * @param subscriber
     * @param <T>
     */
    protected <T> void addSubscriptionForFragment(Observable<T> observable,
                                                  Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mProvider.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(subscriber);
    }


}
