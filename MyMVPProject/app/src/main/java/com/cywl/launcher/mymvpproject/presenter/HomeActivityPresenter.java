package com.cywl.launcher.mymvpproject.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Button;

import com.cywl.launcher.mymvpproject.base.ContractComic;
import com.cywl.launcher.mymvpproject.base.impl.BasePresenter;
import com.cywl.launcher.mymvpproject.model.ComicModelImpl;
import com.cywl.launcher.mymvpproject.model.bean.User;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import static android.content.ContentValues.TAG;

/**
 * HomeActivity presenter
 */
public class HomeActivityPresenter extends BasePresenter<ContractComic.ComicView> implements ContractComic.IComicModelCallBack {
    //Model
    private ComicModelImpl mContractComic;

    private Subject<Button> subject;
    private String setText;

    public HomeActivityPresenter(LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        subject = PublishSubject.create();
        mContractComic = new ComicModelImpl();
        mContractComic.getIComicCallBack(this);

    }


    public void showData() {
        if (mContractComic != null) {
            mContractComic.getList();
        }
    }

    public void showString() {
        if (mContractComic != null) {
            mContractComic.getString();
        }
    }

    public void aaa(final Button mButton, final String s) {
        setText = s;
    }

    @SuppressLint("CheckResult")
    public void refreshComplete(final int callBackType, int type) {
        Observable.just(type).delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getProvider().bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        if (integer == 1) {
                            getView().showList(callBackType, null);
                        } else {
                            getView().showList(callBackType, null);
                        }
                    }
                });
    }

    private void update() {
        subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getProvider().bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<Button>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Button button) {
                        Log.d(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void ok(int type, List<String> list) {
        switch (type) {
            case 1:
                getView().showList(type, list);
                break;
            case 2:
                getWeather();
//                mRootView.showList(type, list);
                break;
            case 3:
//                getWeather();
                break;
        }
    }

    private void getWeather() {
        addSubscription(apiService.getUser("simplezhli"), new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(User user) {
                List<String> strings = new ArrayList<>();
                strings.add(user.toString());
                getView().showList(5, strings);
                Log.d(TAG, "onNext: onNext " + user.toString());
            }


            @Override
            public void onError(Throwable e) {
                List<String> strings = new ArrayList<>();
                strings.add(e.getMessage());
                getView().showList(5, strings);
            }

            @Override
            public void onComplete() {

            }
        });
    }

}
