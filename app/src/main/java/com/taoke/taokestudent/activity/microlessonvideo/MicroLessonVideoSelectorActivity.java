package com.taoke.taokestudent.activity.microlessonvideo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.adapter.BaseClassSelectorAdapter;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.MicorLessonVideoBaseClassItem;
import com.taoke.taokestudent.entity.MicroDataTypeItem;
import com.taoke.taokestudent.entity.MicroLessonVideoBaseClass;
import com.taoke.taokestudent.presenter.microlessonvideo.MicroLessonVideoSelectorActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 微课视频科目筛选器
 */
public class MicroLessonVideoSelectorActivity extends BaseActivity {
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
    /* 分类 */
    @BindView(R.id.gv_fenlei)
    GridView gvFenlei;
    /* 分类区域 */
    @BindView(R.id.ll_data_type)
    LinearLayout llDataType;

    /* 当前分类 */
    private MicroLessonVideoBaseClass baseClass;

    /* 基础分类Adatper */
    private List<BaseClassSelectorAdapter> baseClassAdapters;
    /* 基础分类数据源 */
    private List<List<String>> baseClassDataSources;
    /* 基础分类原始数据 */
    private List<List<MicorLessonVideoBaseClassItem>> baseClassSourceDatas;
    /* 基础分类选中项 */
    private List<Integer> baseClassSelectedPositons;

    /* 分类Adapter */
    private BaseClassSelectorAdapter typeAdapter;
    /* 分类数据源 */
    private List<String> typeList;
    /* 分类原始数据 */
    private List<MicroDataTypeItem> typeItems;
    /* 分类选中项 */
    private int typePosition;

    /* 主导器 */
    private MicroLessonVideoSelectorActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_micro_lesson_video_selector);
        presenter = new MicroLessonVideoSelectorActivityPresenter(this);
        // 获取基础分类
        baseClass = getIntent().getParcelableExtra(Consts.IntentExtraKey.MICRO_BASE_CLASS);
        // 初始化界面
        initViews();
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        // 绑定数据
        bindData();
        // 分类为微课视频时隐藏分类区域
        if(Consts.MICRO_DATA_TYPE_EXERCISE.equals(baseClass.getDataType())){
            llDataType.setVisibility(View.GONE);
        }

        // 加载基础分类数据
        presenter.loadBaseClass("1", Consts.BaseClassLevel.XDLV);
        // 加载分类数据
        presenter.loadDataType();
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

        // 分类
        typeList = new ArrayList<>();
        typeAdapter = new BaseClassSelectorAdapter(this, typeList);
        gvFenlei.setAdapter(typeAdapter);
    }

    /**
     * 设置分类数据
     *
     * @param datas
     */
    public void setDataType(List<MicroDataTypeItem> datas) {
        typeItems = datas;
        typeList.clear();
        for (int i = 0; i < datas.size(); i++) {
            typeList.add(datas.get(i).getName());

            if (("" + datas.get(i).getType()).equals(baseClass.getDataType())) {
                typePosition = i;
            }
        }
        typeAdapter.setSelectedPosition(typePosition);
        typeAdapter.notifyDataSetChanged();
    }

    /**
     * 设置基础分类数据
     *
     * @param datas 基础分类数据
     * @param level 分类级别
     */
    public void setBaseData(List<MicorLessonVideoBaseClassItem> datas, String level) {
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
    private void setBaseClassDatas(int position, List<MicorLessonVideoBaseClassItem> datas) {
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
        // 设置分类
        typePosition = 0;
        typeAdapter.setSelectedPosition(0);
        typeAdapter.notifyDataSetChanged();
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
        // 设置分类
        typePosition = 0;
        typeAdapter.setSelectedPosition(0);
        typeAdapter.notifyDataSetChanged();
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
        // 设置分类
        typePosition = 0;
        typeAdapter.setSelectedPosition(0);
        typeAdapter.notifyDataSetChanged();
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
        // 设置分类
        typePosition = 0;
        typeAdapter.setSelectedPosition(0);
        typeAdapter.notifyDataSetChanged();
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
        // 设置分类
        typePosition = 0;
        typeAdapter.setSelectedPosition(0);
        typeAdapter.notifyDataSetChanged();
    }

    /**
     * 选择分类项
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.gv_fenlei)
    public void onFenleiItemClick(AdapterView<?> parent, View view, int position, long id) {
        baseClass = null;
        if (position == typePosition) {
            return;
        }
        typePosition = position;
        typeAdapter.setSelectedPosition(position);
        typeAdapter.notifyDataSetChanged();
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
        baseClass = new MicroLessonVideoBaseClass();
        baseClass.setXueduan("" + baseClassSourceDatas.get(0).get(baseClassSelectedPositons.get(0)).getId());
        baseClass.setNianji("" + baseClassSourceDatas.get(1).get(baseClassSelectedPositons.get(1)).getId());
        baseClass.setKemu("" + baseClassSourceDatas.get(2).get(baseClassSelectedPositons.get(2)).getId());
        baseClass.setBanben("" + baseClassSourceDatas.get(3).get(baseClassSelectedPositons.get(3)).getId());
        baseClass.setMokuai("" + baseClassSourceDatas.get(4).get(baseClassSelectedPositons.get(4)).getId());
        baseClass.setDataType(typeItems.get(typePosition).getType());
        // 回传
        Intent intent = new Intent();
        intent.putExtra(Consts.IntentExtraKey.MICRO_BASE_CLASS, baseClass);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
