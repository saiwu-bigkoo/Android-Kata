package com.bigkoo.katafoundation.fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.bigkoo.katafoundation.R;
import com.bigkoo.kataframework.mvppresenter.BaseListPresenter;

/**
 * Created by sai on 2018/3/18.
 */

public abstract class BaseListFragment<P extends BaseListPresenter> extends BaseDataFragment<P> {
    protected RecyclerView recyclerView;
    protected BaseQuickAdapter adapter;

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = getAdapter();
        adapter.bindToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.disableLoadMoreIfNotFullPage();
    }


    @Override
    protected void initData() {
        getPresenter().onLoadData();
    }


    protected abstract BaseQuickAdapter getAdapter();

}
