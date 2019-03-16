package com.bigkoo.kataframework.mvppresenter;

import com.bigkoo.kataframework.http.constants.HttpStatusConstants;
import com.bigkoo.kataframework.http.observer.HttpResultObserver;
import com.bigkoo.kataframework.mvpview.BaseDetailView;
import io.reactivex.Observable;

/**
 * Created by Sai on 2018/3/15.
 */

public abstract class BaseDetailPresenter<V extends BaseDetailView> extends BaseDataPresenter<V>{
    Object data;
    public void setData(Object data){
        this.data = data;
        view.onDataSetChange(data);
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
            setStatusLoading(true);
        Observable observable = onLoadDataHttpRequest();
        if(observable!=null) {
            onCallHttpRequest(observable,callBack);
        }
        else{
            setStatusLoading(false);
        }
    }


    public HttpResultObserver callBack = new HttpResultObserver<Object>() {
        @Override
        public void onHttpSuccess(Object resultData, String msg) {
            setStatusError(false, HttpStatusConstants.CODE_SUCCESS, msg);
            setStatusNetworkError(false, msg);

            if(resultData != null) {
                setData(resultData);
            }
        }

        @Override
        public void onHttpFail(int code, String msg) {
            setStatusError(true,code,msg);
        }

        @Override
        public void onNetWorkError(String msg) {
            setStatusNetworkError(true,msg);
        }

        @Override
        public void onComplete() {
            setOnce(true);
            setStatusLoading(false);
            if(!isStatusError()&&!isStatusNetworkError())
                setStatusEmpty(getData() == null);

            setRefreshing(false);
            onLoadComplete();
        }
    };
}
