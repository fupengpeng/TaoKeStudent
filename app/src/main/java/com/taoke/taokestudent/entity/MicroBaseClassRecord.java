package com.taoke.taokestudent.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.taoke.taokestudent.common.Consts;

import java.util.List;

/**
 * 微课视频基础分类记录(保存各级的id)
 */
public class MicroBaseClassRecord extends SugarRecord {
    /* 记录类型：微课视频/习题微课/我的课程 */
    @Column(name = "bcType", unique = true)
    private String bcType;
    private String xueduan;
    private String nianji;
    private String kemu;
    private String banben;
    private String mokuai;
    private String dataType;

    public String getBcType() {
        return bcType;
    }

    public void setBcType(String bcType) {
        this.bcType = bcType;
    }

    public String getXueduan() {
        return xueduan;
    }

    public void setXueduan(String xueduan) {
        this.xueduan = xueduan;
    }

    public String getNianji() {
        return nianji;
    }

    public void setNianji(String nianji) {
        this.nianji = nianji;
    }

    public String getKemu() {
        return kemu;
    }

    public void setKemu(String kemu) {
        this.kemu = kemu;
    }

    public String getBanben() {
        return banben;
    }

    public void setBanben(String banben) {
        this.banben = banben;
    }

    public String getMokuai() {
        return mokuai;
    }

    public void setMokuai(String mokuai) {
        this.mokuai = mokuai;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取基础分类
     *
     * @return
     */
    public static MicroLessonVideoBaseClass getMicroLessonVideoBaseClass(String bcType) {
        // 查询保存的记录
        List<MicroBaseClassRecord> records = MicroBaseClassRecord.find(MicroBaseClassRecord.class,
                "bcType=?", bcType);
        if (records != null && records.size() > 0) {
            MicroLessonVideoBaseClass baseClass = new MicroLessonVideoBaseClass();
            baseClass.setXueduan(records.get(0).getXueduan());
            baseClass.setNianji(records.get(0).getNianji());
            baseClass.setKemu(records.get(0).getKemu());
            baseClass.setBanben(records.get(0).getBanben());
            baseClass.setMokuai(records.get(0).getMokuai());
            baseClass.setDataType(records.get(0).getDataType());
            return baseClass;
        }

        return null;
    }

    /**
     * 保存基础分类
     *
     * @param baseClass
     */
    public static void saveMicroLessonVideoBaseClass(String bcType, MicroLessonVideoBaseClass baseClass) {
        if (bcType == null || baseClass == null) {
            return;
        }

        MicroBaseClassRecord record = new MicroBaseClassRecord();
        record.bcType = bcType;
        record.xueduan = baseClass.getXueduan();
        record.nianji = baseClass.getNianji();
        record.kemu = baseClass.getKemu();
        record.banben = baseClass.getBanben();
        record.mokuai = baseClass.getMokuai();
        record.dataType = baseClass.getDataType();
        record.save();
    }
}
