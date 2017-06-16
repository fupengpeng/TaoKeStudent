package com.taoke.taokestudent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoke.taokestudent.R;

/**
 * 消息Fragment
 */
public class MessageFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载界面
        View view = super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_message);

        return view;
    }
}
