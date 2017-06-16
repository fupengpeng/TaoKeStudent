package com.taoke.taokestudent.fragment.onlineexercise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.MainActivity;
import com.taoke.taokestudent.activity.onlineexercise.OnlineExerciseChapterActivity;
import com.taoke.taokestudent.activity.onlineexercise.OnlineExercsieSelectorActivity;
import com.taoke.taokestudent.activity.personalcenter.RegisterActivity;
import com.taoke.taokestudent.adapter.OnlineExerciseUnitListAdapter;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.OnlineExerciseBaseClass;
import com.taoke.taokestudent.entity.OnlineExerciseBaseClassItem;
import com.taoke.taokestudent.fragment.BaseFragment;
import com.taoke.taokestudent.presenter.onlineexercise.OnlineExerciseFragmentPresenter;
import com.taoke.taokestudent.util.InfoUtils;
import com.taoke.taokestudent.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 在线做题Fragment
 */
public class OnlineExerciseFragment extends BaseFragment {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 题目数量 */
    @BindView(R.id.et_online_question_num)
    EditText etOnlineQuestionNum;
    /* 知识点章节列表 */
    @BindView(R.id.lv_online_unit_list)
    ListView lvOnlineUnitList;
    /* 空数据时的提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;

    /* 在线做题当前分类 */
    private OnlineExerciseBaseClass baseClass;

    /* 数据源 */
    private List<OnlineExerciseBaseClassItem> list;
    /* Adapter */
    private OnlineExerciseUnitListAdapter adapter;

    /* 主导器 */
    private OnlineExerciseFragmentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载界面
        View view = super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_online_exercise);
        presenter = new OnlineExerciseFragmentPresenter(this);
        // 初始化
        init();
        // 标题
        tvTitleText.setText(getString(R.string.online_exercise));
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        // 绑定数据
        bindData();
        // 加载分类数据
        presenter.loadClass();
    }

    /**
     * 设置分类数据
     *
     * @param baseClass 分类数据
     */
    public void setClassData(OnlineExerciseBaseClass baseClass) {
        this.baseClass = baseClass;
        // 保存分类
        OnlineExerciseBaseClass.deleteAll(OnlineExerciseBaseClass.class);
        this.baseClass.setTime(System.currentTimeMillis());
        this.baseClass.save();
        // 加载单元列表
        presenter.loadChapterListDatas(baseClass.getMokuai());
    }

    /**
     * 设置单元列表数据
     *
     * @param datas 单元列表数据
     */
    public void setUnitListDatas(List<OnlineExerciseBaseClassItem> datas) {
        // 清空现有数据
        list.clear();

        // 判断是否存在数据
        if (datas == null || datas.size() == 0) {
            // 显示提示
            tvDataEmpty.setVisibility(View.VISIBLE);
            // 刷新数据
            adapter.notifyDataSetChanged();
            return;
        }

        // 隐藏提示
        tvDataEmpty.setVisibility(View.GONE);

        // 刷新数据
        list.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        list = new ArrayList<>();
        adapter = new OnlineExerciseUnitListAdapter(getActivity(), list);
        lvOnlineUnitList.setAdapter(adapter);
    }

    /**
     * 打开章节界面
     */
    @OnItemClick(R.id.lv_online_unit_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 验证题目数量
        String questionNum = etOnlineQuestionNum.getText().toString().trim();
        if (TextUtils.isEmpty(questionNum)) {
            InfoUtils.showInfo(getActivity(), getString(R.string.input_question_num));
            UIUtils.showInputMethod(getActivity(), etOnlineQuestionNum, true);
            return;
        }
        int num = Integer.parseInt(questionNum);
        if (num < 1) {
            InfoUtils.showInfo(getActivity(), getString(R.string.question_num_min_1));
            UIUtils.showInputMethod(getActivity(), etOnlineQuestionNum, true);
            return;
        }
        if (num > 20) {
            InfoUtils.showInfo(getActivity(), getString(R.string.question_num_max_20));
            UIUtils.showInputMethod(getActivity(), etOnlineQuestionNum, true);
            return;
        }

        // 打开章节界面
        Intent intent = new Intent(getActivity(), OnlineExerciseChapterActivity.class);
        intent.putExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_UNIT_ID, "" + list.get(position).getId());
        intent.putExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_UNIT_NAME, list.get(position).getName());
        intent.putExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_QUESTION_NUM, num);
        startActivity(intent);
    }

    /**
     * 打开侧滑菜单
     */
    @OnClick(R.id.iv_menu)
    public void openLeftMenu() {
        ((MainActivity) getActivity()).openLeftMenu();
    }

    /**
     * 筛选
     */
    @OnClick(R.id.iv_selector)
    public void showSelector() {
        Intent intent = new Intent(getContext(), OnlineExercsieSelectorActivity.class);
        intent.putExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_BASE_CLASS, baseClass);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                OnlineExerciseBaseClass baseClass = data.getParcelableExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_BASE_CLASS);
                setClassData(baseClass);
            }
        }
    }

}
