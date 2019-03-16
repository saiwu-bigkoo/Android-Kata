package com.bigkoo.kataframework.mvppresenter;

import com.bigkoo.kataframework.http.constants.HttpStatusConstants;
import com.bigkoo.kataframework.http.observer.HttpResultObserver;
import com.bigkoo.kataframework.mvpview.BaseListView;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by Sai on 2018/3/15.
 */

public abstract class BaseListPresenter<V extends BaseListView> extends BaseDataPresenter<V>{
    protected ArrayList<Object> datas = new ArrayList<>();
    protected final int firstPage = 1;
    //分页页码
    protected int page = firstPage;
    //分页每页Item数量
    public static final int PAGESIZE_DEFULT = 20;
    protected int pageSize = PAGESIZE_DEFULT;
    protected boolean isHasMore = true;
    protected boolean isLoadingMore = false;

    public int getFirstPage() {
        return firstPage;
    }

    public boolean isFirstPage() {
        return firstPage==page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHasMore() {
        return isHasMore;
    }

    public void setHasMore(boolean hasMore) {
        isHasMore = hasMore;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public void setLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
    }

    public void setPageAdd(){
        page++;
    }

    public void addData(Object data){
        this.datas.add(data);
        view.onDataSetChange(data);
    }

    public boolean isDataEmpty() {
        return datas.isEmpty();
    }

    public void clearDatas() {
        datas.clear();
    }

    /**
     * 这里重写onLoadData，列表不能直接onLoadData，而是刷新
     */
    @Override
    public void onLoadData() {
        onListRefresh();
    }

    /**
     * 加载更多
     */
    public void onLoadMore() {
        onListLoadMore();
    }

    /**
     * 刷新数据
     */
    public void onListRefresh(){
        setRefreshing(true);
        //把分页配置还原成加载第一页状态
        setPage(getFirstPage());
        setHasMore(true);
        setLoadingMore(false);
        if(!isOnce())
            setStatusLoading(true);
        try {
            onLoadHttpData();
        }catch (NullPointerException e){}
    }

    /**
     * 加载数据
     */
    public void onListLoadMore(){
        //判断是否已经在进行加载更多 或 没有更多了，是则直接返回等待加载完成。
        if(isLoadingMore()||!isHasMore())return;
        //刷新中也直接返回不加载更多
        if(isRefreshing())return;
        setLoadingMore(true);
        //分页增加
        setPageAdd();

        onLoadHttpData();
    }

    /**
     * 刷新/加载数据
     */
    public void onLoadHttpData(){
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

            //如果是第一页就清空旧数据再加入新数据
            if(isFirstPage()) {
                clearDatas();
            }
            if(resultData != null) {
                addData(resultData);
            }
            else {
                setHasMore(false);
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
                setStatusEmpty(isDataEmpty());

            setRefreshing(false);

            if(isFirstPage())//因为在刷新之前已经把page设为了firstPage，所以可以判断isFirstPage()来判断当前是否刷新
                setRefreshing(false);
            else
                setLoadingMore(false);

            onLoadComplete();
        }
    };
}
