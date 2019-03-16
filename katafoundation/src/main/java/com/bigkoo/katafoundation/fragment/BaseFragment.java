package com.bigkoo.katafoundation.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by sai on 2018/3/18.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResID(), container, false);

        initView();
        initListener();

        return rootView;
    }

    public <T extends View> T findViewById(int id){
        return rootView.findViewById(id);
    }

    protected abstract void initData();

    protected abstract void initView();

    protected void initListener() {
    }

    protected abstract int getLayoutResID();

    public void showToast(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int msgId) {
        Toast.makeText(getActivity().getApplicationContext(), msgId, Toast.LENGTH_SHORT).show();
    }

}