package com.bigkoo.katafoundation.activity;

import com.bigkoo.katafoundation.R;
import com.bigkoo.katafoundation.mvpview.BaseListRAMView;
import com.bigkoo.katafoundation.presenter.BaseListRAMPresenter;
import com.bigkoo.kataframework.mvppresenter.BaseListPresenter;

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
    public void onStatusEmpty(boolean statusEmpty) {

    }

    @Override
    public void onStatusLoading(boolean statusLoading) {

    }

    @Override
    public void onStatusError(boolean statusError, int code, String msg) {

    }

    @Override
    public void onStatusNetworkError(boolean statusNetworkError, String msg) {

    }

    @Override
    public void onLoadComplete() {
        if (adapter.isLoading())
            adapter.loadMoreComplete();
        if (!getPresenter().isHasMore()) {
            adapter.loadMoreEnd();
        }
    }

    public void addData(List<Object> datas) {
        if (datas == null || datas.isEmpty()) {
            getPresenter().setHasMore(false);
            return;
        }
        if (getPresenter().isFirstPage()) {
            adapter.setNewData(datas);
        } else {
            adapter.addData(datas);
        }
        //判断加入的数据长度，如果比默认的少则没有更多内容了。这里前提是没自定义每页长度，使用默认值
        if (datas.size() < BaseListPresenter.PAGESIZE_DEFULT) {
            getPresenter().setHasMore(false);
            adapter.loadMoreEnd();
        }


    }
}
