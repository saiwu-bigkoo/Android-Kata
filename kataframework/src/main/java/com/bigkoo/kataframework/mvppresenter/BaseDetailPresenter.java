package com.bigkoo.kataframework.mvppresenter;

import com.bigkoo.kataframework.http.observer.HttpResultObserver;
import com.bigkoo.kataframework.mvpview.BaseDetailView;
import io.reactivex.Observable;

/**
 * Created by Sai on 2018/3/15.
 */

public abstract class BaseDetailPresenter<V extends BaseDetailView> extends BaseDataPresenter<V>{
    Object data;
    public void setData(Object data, String msg){
        this.data = data;
        view.onDataSetChange(data, msg);
    }

    public Object getData() {
        return data;
    }

    /**
     * 刷新/加载数据
     */
    public void onLoadData(){
        setRefreshing(true);
        //这里考虑到首次加载是 loading，以后加载是refresh 模式
        if(!isOnce())
            setStatusLoading();
        Observable observable = onLoadDataHttpRequest();
        if(observable!=null) {
            onCallHttpRequest(observable,callBack);
        }
    }


    public HttpResultObserver callBack = new HttpResultObserver<Object>() {
        @Override
        public void onHttpSuccess(Object resultData, String msg) {
            if (resultData != null) {
                setData(resultData, msg);
            }
            else {
                setStatusEmpty(msg);
            }
        }

        @Override
        public void onHttpFail(int code, String msg) {
            setStatusError(code,msg);
        }

        @Override
        public void onNetWorkError(String msg) {
            setStatusNetworkError(msg);
        }

        @Override
        public void onComplete() {
            setOnce(true);
            setRefreshing(false);
            onLoadComplete();
        }
    };
}
