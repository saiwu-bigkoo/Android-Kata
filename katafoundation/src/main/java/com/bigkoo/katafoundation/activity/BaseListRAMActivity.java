package com.bigkoo.katafoundation.activity;

import com.bigkoo.katafoundation.R;
import com.bigkoo.katafoundation.mvpview.BaseListRAMView;
import com.bigkoo.katafoundation.presenter.BaseListRAMPresenter;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by sai on 2018/3/18.
 * refresh and loadMore 基类Activity
 */

public abstract class BaseListRAMActivity<P extends BaseListRAMPresenter> extends BaseListActivity<P> implements BaseListRAMView {

    protected PtrFrameLayout ptrFrameLayout;
    public float PTRRESISTANCE = 1.7f;
    protected boolean refreshable = true;

    @Override
    protected void initView() {
        super.initView();

        recyclerView.setLayoutManager(getPresenter().getLayoutManager());
        if (refreshable) {

            ptrFrameLayout = findViewById(R.id.ptrFrameLayout);
            ptrFrameLayout.setResistance(PTRRESISTANCE);
            ptrFrameLayout.setKeepHeaderWhenRefresh(true);
            ptrFrameLayout.disableWhenHorizontalMove(true);

            MaterialHeader header = new MaterialHeader(this);
            header.setColorSchemeColors(new int[]{R.color.refresh_color});
            header.setPadding(0, 50, 0, 50);

            ptrFrameLayout.setHeaderView(header);
            ptrFrameLayout.addPtrUIHandler(header);
            PtrHandler ptrHandler = getPresenter().getPtrHandler();
            if (ptrHandler != null) {
                ptrFrameLayout.setPtrHandler(ptrHandler);
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnLoadMoreListener(getPresenter().getLoadingMoreListener(), recyclerView);
        adapter.setOnItemClickListener(getPresenter().getOnItemClickListener());
        adapter.setOnItemChildClickListener(getPresenter().getOnItemChildClickListener());
    }


    @Override
    public void onRefreshing(boolean refreshing) {
        if (refreshing) {
            if (!ptrFrameLayout.isRefreshing())
                ptrFrameLayout.autoRefresh(true);
        } else {
            ptrFrameLayout.refreshComplete();
        }
    }

    @Override
    public void onLoadingMore(boolean isLoadingMore) {
        if (!isLoadingMore) {
            if (adapter.isLoading()) {
                adapter.loadMoreComplete();
            }
        }
    }

    @Override
    public void onStatusEmpty(String msg) {

    }

    @Override
    public void onStatusLoading() {

    }

    @Override
    public void onStatusError(int code, String msg) {

    }

    @Override
    public void onStatusNetworkError( String msg) {

    }

    @Override
    public void onLoadComplete() {
        if (adapter.isLoading())
            adapter.loadMoreComplete();
        if (!getPresenter().isHasMore()) {
            adapter.loadMoreEnd();
        }
    }

    public void addData(List datas) {
        if (datas == null || datas.isEmpty()) {
            getPresenter().setHasMore(false);
            adapter.loadMoreEnd();
            return;
        }
        if (getPresenter().isFirstPage()) {
            adapter.setNewData(datas);
        } else {
            adapter.addData(datas);
        }
        if (datas.size() < getPresenter().getPageSize()) {
            getPresenter().setHasMore(false);
            adapter.loadMoreEnd();
        }

    }

    public void addData(Object data){
        if (data == null) return;
        adapter.addData(data);
    }
    public void addData(int position,Object data){
        if (data == null) return;
        adapter.addData(position,data);
        adapter.notifyItemRangeChanged(position + adapter.getHeaderLayoutCount(),adapter.getItemCount()-position );//通知数据与界面重新绑定
    }

    public Object getItem(int position){
        return adapter.getItem(position);
    }}
