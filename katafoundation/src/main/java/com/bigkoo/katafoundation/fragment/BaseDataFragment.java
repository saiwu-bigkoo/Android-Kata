package com.bigkoo.katafoundation.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.kataframework.mvppresenter.BaseDataPresenter;
import com.bigkoo.kataframework.utils.TUtil;

/**
 * Created by Sai on 2018/3/20.
 */

public abstract class BaseDataFragment <P extends BaseDataPresenter> extends BaseLazyFragment{
    private P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    protected void initPresenter() {
        presenter =  TUtil.getT(this, 0);
        presenter.setVM(this,getActivity());
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public P getPresenter() {
        return presenter;
    }

}
