package com.bigkoo.katafoundation.fragment;

import com.bigkoo.kataframework.mvppresenter.BaseDetailPresenter;

/**
 * Created by sai on 2018/3/18.
 */
public abstract class BaseDetailFragment<P extends BaseDetailPresenter> extends BaseDataFragment<P>{

    @Override
    protected void initData() {
        getPresenter().onLoadData();
    }

}
