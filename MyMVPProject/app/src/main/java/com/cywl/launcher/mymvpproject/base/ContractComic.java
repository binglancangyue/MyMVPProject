package com.cywl.launcher.mymvpproject.base;

import java.util.List;

public interface ContractComic {
    interface ComicModel {
        void getIComicCallBack(IComicModelCallBack iComicCallBack);
    }

    interface ComicView extends IView {
        void showList(int type, List<String> list);
    }

    interface IComicModelCallBack {
        void ok(int type, List<String> list);
    }
}
