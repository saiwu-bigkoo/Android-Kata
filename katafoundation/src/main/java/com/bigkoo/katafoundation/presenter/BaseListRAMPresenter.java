package com.bigkoo.katafoundation.presenter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.bigkoo.katafoundation.mvpview.BaseListRAMView;
import com.bigkoo.kataframework.mvppresenter.BaseListPresenter;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by sai on 2018/3/18.
 * refresh and loadMore 基类Presenter
 */

public abstract class BaseListRAMPresenter<V extends BaseListRAMView>  extends BaseListPresenter<V> {
    protected BaseQuickAdapter.RequestLoadMoreListener loadingMoreListener;
    protected BaseQuickAdapter.OnItemClickListener onItemClickListener;
    protected BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener;
    protected PtrHandler ptrHandler;
    protected RecyclerView.LayoutManager layoutManager;
    public BaseQuickAdapter.RequestLoadMoreListener getLoadingMoreListener(){
        if(loadingMoreListener == null) {
            loadingMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if(!isLoadingMore()&&!isRefreshing()) {
                        onLoadMore();
                    }
                }
            };
        }
        return loadingMoreListener;
    }

    public BaseQuickAdapter.OnItemClickListener getOnItemClickListener() {
        if(onItemClickListener == null) {
            onItemClickListener = new BaseQuickAdapter.OnItemClickListener(){
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    getView().onItemClick(adapter, view, position);
                }
            };
        }

        return onItemClickListener;
    }

    public BaseQuickAdapter.OnItemChildClickListener getOnItemChildClickListener() {
        if(onItemChildClickListener == null) {
            onItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener(){
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    getView().onItemChildClick(adapter, view, position);
                }
            };
        }
        return onItemChildClickListener;
    }


    public PtrHandler getPtrHandler() {
        if(ptrHandler == null){
            ptrHandler = new PtrDefaultHandler() {
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    if(!isLoadingMore()&&!isRefreshing()) {
                        onLoadData();
                    }
                }
            };
        }
        return ptrHandler;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(context);
    }
}
