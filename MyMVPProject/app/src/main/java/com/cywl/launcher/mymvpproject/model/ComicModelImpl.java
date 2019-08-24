package com.cywl.launcher.mymvpproject.model;

import com.cywl.launcher.mymvpproject.base.ContractComic;

import java.util.ArrayList;
import java.util.List;

public class ComicModelImpl implements ContractComic.ComicModel {
    private ContractComic.IComicModelCallBack iComicCallBack;

    @Override
    public void getIComicCallBack(ContractComic.IComicModelCallBack iComicCallBack) {
        this.iComicCallBack = iComicCallBack;
    }

    public void getList() {
        if (iComicCallBack != null) {
            List<String> strings = new ArrayList<>();
            strings.add("hello");
            strings.add("World");
            strings.add("hello");
            strings.add("World");
            strings.add("hello");
            strings.add("hello");
            strings.add("hello");
            strings.add("World");
            strings.add("hello");
            strings.add("World");
            strings.add("hello");
            strings.add("World");
            strings.add("hello");
            strings.add("World");
            strings.add("World");
            iComicCallBack.ok(1, strings);
        }
    }

    public void getString() {
        if (iComicCallBack != null) {
            List<String> strings = new ArrayList<>();
            strings.add("hello");
            strings.add("SHEN ZHEN");
            iComicCallBack.ok(2, strings);
        }
    }
}
