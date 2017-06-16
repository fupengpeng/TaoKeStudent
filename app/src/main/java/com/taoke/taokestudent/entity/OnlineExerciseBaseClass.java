package com.taoke.taokestudent.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * 在线做题当前基础分类(保存各级的id)
 */
public class OnlineExerciseBaseClass extends SugarRecord implements Parcelable {
    private String xueduan;
    private String nianji;
    private String kemu;
    private String banben;
    private String mokuai;
    // 保存时间
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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
     *
     * @param baseClass
     */
    public void copy(OnlineExerciseBaseClass baseClass) {
        this.xueduan = baseClass.xueduan;
        this.nianji = baseClass.nianji;
        this.kemu = baseClass.kemu;
        this.banben = baseClass.banben;
        this.mokuai = baseClass.mokuai;
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
    }

    public OnlineExerciseBaseClass() {
    }

    protected OnlineExerciseBaseClass(Parcel in) {
        this.xueduan = in.readString();
        this.nianji = in.readString();
        this.kemu = in.readString();
        this.banben = in.readString();
        this.mokuai = in.readString();
    }

    public static final Creator<OnlineExerciseBaseClass> CREATOR = new Creator<OnlineExerciseBaseClass>() {
        @Override
        public OnlineExerciseBaseClass createFromParcel(Parcel source) {
            return new OnlineExerciseBaseClass(source);
        }

        @Override
        public OnlineExerciseBaseClass[] newArray(int size) {
            return new OnlineExerciseBaseClass[size];
        }
    };
}
