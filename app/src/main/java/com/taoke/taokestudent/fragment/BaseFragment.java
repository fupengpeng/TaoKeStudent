package com.taoke.taokestudent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoke.taokestudent.common.DataEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 */
public class BaseFragment extends Fragment {
    //用于解除绑定
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layoutResId) {
        View view = inflater.inflate(layoutResId, container, false);
        //绑定ButterKnife
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    /**
     * 发送事件
     */
    public void postEvent(int type, Object data) {
        EventBus.getDefault().post(new DataEvent(type, data));
    }

    /**
     * 显示Fragment
     *
     * @param containerId
     * @param oldFragment
     * @param newFragment
     */
    public void showFragment(int containerId, BaseFragment oldFragment, BaseFragment newFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (!newFragment.isAdded()) {
            transaction.add(containerId, newFragment);
        }
        if (oldFragment != null) {
            transaction.hide(oldFragment);
        }
        transaction.show(newFragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 打开Activity
     *
     * @param cls 目标class
     */
    public void startActivity(Class<?> cls) {
        getActivity().startActivity(new Intent(getActivity(), cls));
    }

    /**
     * 打开Activity
     *
     * @param cls         目标class
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        getActivity().startActivityForResult(new Intent(getActivity(), cls), requestCode);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除绑定
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
