package com.taoke.taokestudent.activity.onlineexercise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.adapter.BaseClassSelectorAdapter;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.OnlineExerciseBaseClass;
import com.taoke.taokestudent.entity.OnlineExerciseBaseClassItem;
import com.taoke.taokestudent.presenter.onlineexercise.OnlineExerciseSelectorActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 在线做题筛选器
 */
public class OnlineExercsieSelectorActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 学段 */
    @BindView(R.id.gv_xueduan)
    GridView gvXueduan;
    /* 年级 */
    @BindView(R.id.gv_nianji)
    GridView gvNianji;
    /* 学科 */
    @BindView(R.id.gv_xueke)
    GridView gvXueke;
    /* 版本 */
    @BindView(R.id.gv_banben)
    GridView gvBanben;
    /* 模块 */
    @BindView(R.id.gv_mokuai)
    GridView gvMokuai;

    /* 当前分类 */
    private OnlineExerciseBaseClass baseClass;

    /* 基础分类Adatper */
    private List<BaseClassSelectorAdapter> baseClassAdapters;
    /* 基础分类数据源 */
    private List<List<String>> baseClassDataSources;
    /* 基础分类原始数据 */
    private List<List<OnlineExerciseBaseClassItem>> baseClassSourceDatas;
    /* 基础分类选中项 */
    private List<Integer> baseClassSelectedPositons;

    /* 主导器 */
    private OnlineExerciseSelectorActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_online_exercise_selector);
        presenter = new OnlineExerciseSelectorActivityPresenter(this);
        // 获取基础分类
        baseClass = getIntent().getParcelableExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_BASE_CLASS);
        // 初始化界面
        initViews();
        // 标题
        tvTitleText.setText(getString(R.string.select_knowledge));
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        // 绑定数据
        bindData();
        // 加载基础分类数据
        presenter.loadBaseClass(Consts.BASE_CLASS_XUEDUAN_FID, Consts.BaseClassLevel.XDLV);
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        // 基础分类
        baseClassAdapters = new ArrayList<>();
        baseClassDataSources = new ArrayList<>();
        baseClassSourceDatas = new ArrayList<>();
        baseClassSelectedPositons = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            baseClassDataSources.add(new ArrayList<String>());
            baseClassAdapters.add(new BaseClassSelectorAdapter(this, baseClassDataSources.get(i)));
            baseClassSourceDatas.add(null);
            baseClassSelectedPositons.add(0);
        }
        gvXueduan.setAdapter(baseClassAdapters.get(0));
        gvNianji.setAdapter(baseClassAdapters.get(1));
        gvXueke.setAdapter(baseClassAdapters.get(2));
        gvBanben.setAdapter(baseClassAdapters.get(3));
        gvMokuai.setAdapter(baseClassAdapters.get(4));
    }

    /**
     * 设置基础分类数据
     *
     * @param datas 基础分类数据
     * @param level 分类级别
     */
    public void setBaseData(List<OnlineExerciseBaseClassItem> datas, String level) {
        // 学段
        if (Consts.BaseClassLevel.XDLV.equals(level)) {
            setBaseClassDatas(0, datas);
            // 加载年级
            presenter.loadBaseClass("" + datas.get(baseClassSelectedPositons.get(0)).getId(), Consts.BaseClassLevel.NJLV);
        }
        // 年级
        if (Consts.BaseClassLevel.NJLV.equals(level)) {
            setBaseClassDatas(1, datas);
            // 加载学科
            presenter.loadBaseClass("" + datas.get(baseClassSelectedPositons.get(1)).getId(), Consts.BaseClassLevel.XKLV);
        }
        // 学科
        if (Consts.BaseClassLevel.XKLV.equals(level)) {
            setBaseClassDatas(2, datas);
            // 加载版本
            presenter.loadBaseClass("" + datas.get(baseClassSelectedPositons.get(2)).getId(), Consts.BaseClassLevel.BBLV);
        }
        // 版本
        if (Consts.BaseClassLevel.BBLV.equals(level)) {
            setBaseClassDatas(3, datas);
            // 加载模块
            presenter.loadMokuai("" + datas.get(baseClassSelectedPositons.get(3)).getId(), Consts.BaseClassLevel.MKLV);
        }
        // 模块
        if (Consts.BaseClassLevel.MKLV.equals(level)) {
            setBaseClassDatas(4, datas);
        }
    }

    /**
     * 设置基础分类数据
     */
    private void setBaseClassDatas(int position, List<OnlineExerciseBaseClassItem> datas) {
        baseClassSourceDatas.set(position, datas);
        baseClassDataSources.get(position).clear();

        // id
        String id = null;
        if (baseClass != null) {
            switch (position) {
                case 0:
                    id = baseClass.getXueduan();
                    break;
                case 1:
                    id = baseClass.getNianji();
                    break;
                case 2:
                    id = baseClass.getKemu();
                    break;
                case 3:
                    id = baseClass.getBanben();
                    break;
                case 4:
                    id = baseClass.getMokuai();
                    break;
            }
        }


        for (int i = 0; i < datas.size(); i++) {
            baseClassDataSources.get(position).add(datas.get(i).getName());

            if (id != null && ("" + datas.get(i).getId()).equals(id)) {
                baseClassSelectedPositons.set(position, i);
            }
        }

        if (id == null) {
            baseClassSelectedPositons.set(position, 0);
        }

        baseClassAdapters.get(position).setSelectedPosition(baseClassSelectedPositons.get(position));
        baseClassAdapters.get(position).notifyDataSetChanged();
    }

    /**
     * 选择学段项
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.gv_xueduan)
    public void onXueduanItemClick(AdapterView<?> parent, View view, int position, long id) {
        baseClass = null;
        if (position == baseClassSelectedPositons.get(0)) {
            return;
        }
        baseClassSelectedPositons.set(0, position);
        baseClassAdapters.get(0).setSelectedPosition(position);
        baseClassAdapters.get(0).notifyDataSetChanged();
        // 加载年级
        presenter.loadBaseClass("" + baseClassSourceDatas.get(0).get(position).getId(), Consts.BaseClassLevel.NJLV);
    }

    /**
     * 选择年级项
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.gv_nianji)
    public void onNianjiItemClick(AdapterView<?> parent, View view, int position, long id) {
        baseClass = null;
        if (position == baseClassSelectedPositons.get(1)) {
            return;
        }
        baseClassSelectedPositons.set(1, position);
        baseClassAdapters.get(1).setSelectedPosition(position);
        baseClassAdapters.get(1).notifyDataSetChanged();
        // 加载学科
        presenter.loadBaseClass("" + baseClassSourceDatas.get(1).get(position).getId(), Consts.BaseClassLevel.XKLV);
    }

    /**
     * 选择学科项
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.gv_xueke)
    public void onXuekeItemClick(AdapterView<?> parent, View view, int position, long id) {
        baseClass = null;
        if (position == baseClassSelectedPositons.get(2)) {
            return;
        }
        baseClassSelectedPositons.set(2, position);
        baseClassAdapters.get(2).setSelectedPosition(position);
        baseClassAdapters.get(2).notifyDataSetChanged();
        // 加载版本
        presenter.loadBaseClass("" + baseClassSourceDatas.get(2).get(position).getId(), Consts.BaseClassLevel.BBLV);
    }

    /**
     * 选择版本项
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.gv_banben)
    public void onBanbenItemClick(AdapterView<?> parent, View view, int position, long id) {
        baseClass = null;
        if (position == baseClassSelectedPositons.get(3)) {
            return;
        }
        baseClassSelectedPositons.set(3, position);
        baseClassAdapters.get(3).setSelectedPosition(position);
        baseClassAdapters.get(3).notifyDataSetChanged();
        // 加载模块
        presenter.loadMokuai("" + baseClassSourceDatas.get(3).get(position).getId(), Consts.BaseClassLevel.MKLV);
    }

    /**
     * 选择模块项
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.gv_mokuai)
    public void onMokuaiItemClick(AdapterView<?> parent, View view, int position, long id) {
        baseClass = null;
        if (position == baseClassSelectedPositons.get(4)) {
            return;
        }
        baseClassSelectedPositons.set(4, position);
        baseClassAdapters.get(4).setSelectedPosition(position);
        baseClassAdapters.get(4).notifyDataSetChanged();
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

    /**
     * 确定
     */
    @OnClick(R.id.btn_submit)
    public void submit() {
        // 获取选中项id
        baseClass = new OnlineExerciseBaseClass();
        baseClass.setXueduan("" + baseClassSourceDatas.get(0).get(baseClassSelectedPositons.get(0)).getId());
        baseClass.setNianji("" + baseClassSourceDatas.get(1).get(baseClassSelectedPositons.get(1)).getId());
        baseClass.setKemu("" + baseClassSourceDatas.get(2).get(baseClassSelectedPositons.get(2)).getId());
        baseClass.setBanben("" + baseClassSourceDatas.get(3).get(baseClassSelectedPositons.get(3)).getId());
        baseClass.setMokuai("" + baseClassSourceDatas.get(4).get(baseClassSelectedPositons.get(4)).getId());
        // 回传
        Intent intent = new Intent();
        intent.putExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_BASE_CLASS, baseClass);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
