package com.cywl.launcher.mymvpproject.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cywl.launcher.mymvpproject.R;
import com.cywl.launcher.mymvpproject.base.ContractComic;
import com.cywl.launcher.mymvpproject.base.impl.BaseActivity;
import com.cywl.launcher.mymvpproject.model.adapter.MyRecyclerViewAdapter;
import com.cywl.launcher.mymvpproject.presenter.HomeActivityPresenter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class HomeActivity extends BaseActivity<HomeActivityPresenter> implements ContractComic.ComicView,
        View.OnClickListener {
    private final static String TAG = "HomeActivity";
    private Context mContext;
    private XRecyclerView xRecyclerView;
    private List<String> mStringList;
    private Button button;
    private Button button2;

    @Override
    public HomeActivityPresenter createPresenter() {
        return new HomeActivityPresenter(this);
    }

    @Override
    public int getLayoutResId(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        this.mContext = this;
        initView();
    }

    private void initView() {
        button = findViewById(R.id.btn_test);
        button2 = findViewById(R.id.btn_test2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        initRecyclerView();
        initRecyclerViewListener();
    }

    private void initRecyclerView() {
        mPresenter.showData();
        xRecyclerView = findViewById(R.id.rcv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        //添加分割线
        xRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, mStringList);
        xRecyclerView.setAdapter(adapter);
    }

    private void initRecyclerViewListener() {
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() { //下拉刷新
                Log.d(TAG, "onLoadMore: 下拉刷新");
                mPresenter.refreshComplete(3, 1);
            }

            @Override
            public void onLoadMore() { //上拉加载
                Log.d(TAG, "onLoadMore: 上拉加载");
                mPresenter.refreshComplete(4, 2);
            }
        });
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
    public void showList(int type, List<String> list) {
        mStringList = list;
        StringBuilder stringBuilder = new StringBuilder();
        if (type == 1) {
            for (String s : list) {
                stringBuilder.append(s);
            }
            button.setText(stringBuilder);
            return;
        }
        if (type == 2) {
            for (String s : list) {
                stringBuilder.append(s);
            }
            button2.setText(stringBuilder);
            return;
        }
        if (type == 3) {
            xRecyclerView.refreshComplete();
            return;
        }
        if (type == 4) {
            xRecyclerView.loadMoreComplete();
        }
        if (type == 5) {
            Toast.makeText(this, list.get(0), Toast.LENGTH_SHORT).show();

        }
//        mPresenter.aaa(button, stringBuilder.toString());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_test) {
            mPresenter.showData();
        } else {

            mPresenter.showString();
        }
    }
}
