package com.bigkoo.katafoundation.mvpview;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.bigkoo.kataframework.mvpview.BaseListView;

/**
 * Created by sai on 2018/3/18.
 */

public interface BaseListRAMView extends BaseListView {
    void onItemClick(BaseQuickAdapter adapter, View view, int position);
    boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position);
}
