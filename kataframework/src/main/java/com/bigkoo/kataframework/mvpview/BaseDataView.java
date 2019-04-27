package com.bigkoo.kataframework.mvpview;

/**
 * Created by Sai on 2018/3/15.
 */

public interface BaseDataView extends IBaseView {
    void onRefreshing(boolean refreshing);

    void onStatusEmpty(String msg);

    void onStatusLoading();

    void onStatusError(int code, String msg);

    void onStatusNetworkError(String msg);

    void onDataSetChange(Object data, String msg);

    void onLoadComplete();
}
