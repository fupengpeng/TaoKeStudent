package com.taoke.taokestudent.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 微课视频列表项
 */
public class MicroLessonVideoListItem implements Parcelable {
    /* ID */
    private String id;
    /* 名称 */
    private String name;
    /* 价格 */
    private String price;
    /* 数量 */
    private String clanum;
    /* 图片地址 */
    private String pic;
    /* 资源类型 */
    private String type;
    /* 课程ID，无用 */
    private String kecheng_id;
    /* 是否购买 1：购买 2：未购买 */
    private String payly;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getClanum() {
        return clanum;
    }

    public void setClanum(String clanum) {
        this.clanum = clanum;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKecheng_id() {
        return kecheng_id;
    }

    public void setKecheng_id(String kecheng_id) {
        this.kecheng_id = kecheng_id;
    }

    public String getPayly() {
        return payly;
    }

    public void setPayly(String payly) {
        this.payly = payly;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.clanum);
        dest.writeString(this.pic);
        dest.writeString(this.type);
        dest.writeString(this.kecheng_id);
        dest.writeString(this.payly);
    }

    public MicroLessonVideoListItem() {
    }

    protected MicroLessonVideoListItem(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.clanum = in.readString();
        this.pic = in.readString();
        this.type = in.readString();
        this.kecheng_id = in.readString();
        this.payly = in.readString();
    }

    public static final Parcelable.Creator<MicroLessonVideoListItem> CREATOR = new Parcelable.Creator<MicroLessonVideoListItem>() {
        @Override
        public MicroLessonVideoListItem createFromParcel(Parcel source) {
            return new MicroLessonVideoListItem(source);
        }

        @Override
        public MicroLessonVideoListItem[] newArray(int size) {
            return new MicroLessonVideoListItem[size];
        }
    };
}
