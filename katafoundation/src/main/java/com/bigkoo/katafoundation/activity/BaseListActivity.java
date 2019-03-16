package com.bigkoo.katafoundation.activity;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.bigkoo.katafoundation.R;
import com.bigkoo.kataframework.mvppresenter.BaseListPresenter;

/**
 * Created by sai on 2018/3/18.
 */

public abstract class BaseListActivity<P extends BaseListPresenter> extends BaseDataActivity<P>{
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

    }


    protected abstract BaseQuickAdapter getAdapter();

}
