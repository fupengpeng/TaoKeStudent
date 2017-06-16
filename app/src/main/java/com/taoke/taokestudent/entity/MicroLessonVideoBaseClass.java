package com.taoke.taokestudent.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 微课视频当前基础分类(保存各级的id)
 */
public class MicroLessonVideoBaseClass implements Parcelable {
    private String xueduan;
    private String nianji;
    private String kemu;
    private String banben;
    private String mokuai;
    private String dataType;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
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

    /**
     * 拷贝数据
     * @param baseClass
     */
    public void copy(MicroLessonVideoBaseClass baseClass){
        this.xueduan = baseClass.xueduan;
        this.nianji = baseClass.nianji;
        this.kemu = baseClass.kemu;
        this.banben = baseClass.banben;
        this.mokuai = baseClass.mokuai;
        this.dataType = baseClass.dataType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.xueduan);
        dest.writeString(this.nianji);
        dest.writeString(this.kemu);
        dest.writeString(this.banben);
        dest.writeString(this.mokuai);
        dest.writeString(this.dataType);
    }

    public MicroLessonVideoBaseClass() {
    }

    protected MicroLessonVideoBaseClass(Parcel in) {
        this.xueduan = in.readString();
        this.nianji = in.readString();
        this.kemu = in.readString();
        this.banben = in.readString();
        this.mokuai = in.readString();
        this.dataType = in.readString();
    }

    public static final Parcelable.Creator<MicroLessonVideoBaseClass> CREATOR = new Parcelable.Creator<MicroLessonVideoBaseClass>() {
        @Override
        public MicroLessonVideoBaseClass createFromParcel(Parcel source) {
            return new MicroLessonVideoBaseClass(source);
        }

        @Override
        public MicroLessonVideoBaseClass[] newArray(int size) {
            return new MicroLessonVideoBaseClass[size];
        }
    };
}
