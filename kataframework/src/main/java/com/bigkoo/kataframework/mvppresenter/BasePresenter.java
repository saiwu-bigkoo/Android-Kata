package com.bigkoo.kataframework.mvppresenter;

import android.content.Context;

/**
 * T-MVP Presenter基类
 * Created by Sai on 2018/3/15.
 */

public abstract class BasePresenter<V> {
    protected Context context;
    protected V view;

    public void setVM(V v,  Context mContext) {
        this.context = mContext;
        this.view = v;
    }

    public void onDestroy() {
        context = null;
        view = null;
    }

    public Context getContext() {
        return context;
    }

    public V getView() {
        return view;
    }

}