package com.bigkoo.kataframework.mvpview;

/**
 * Created by Sai on 2018/3/15.
 */

public interface BaseDataView extends IBaseView {
    void onRefreshing(boolean refreshing);

    void onStatusEmpty(boolean statusEmpty);

    void onStatusLoading(boolean statusLoading);

    void onStatusError(boolean statusError, int code, String msg);

    void onStatusNetworkError(boolean statusNetworkError, String msg);

    void onDataSetChange(Object data);

    void onLoadComplete();
}
