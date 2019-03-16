package com.bigkoo.katafoundation.activity;

import android.os.Bundle;

import com.bigkoo.kataframework.mvppresenter.BaseDataPresenter;
import com.bigkoo.kataframework.utils.TUtil;

/**
 * Created by Sai on 2018/3/20.
 */
public abstract class BaseDataActivity<P extends BaseDataPresenter> extends BaseActivity {
    private P presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPresenter();
        super.onCreate(savedInstanceState);
    }

    protected void initPresenter() {
        presenter =  TUtil.getT(this, 0);
        presenter.setVM(this,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public P getPresenter() {
        return presenter;
    }

//    public RxManager getRxManage() {
//        return presenter.getRxManage();
//    }

}
