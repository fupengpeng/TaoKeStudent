package com.taoke.taokestudent.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 微课视频列表项
 */
public class MicroLessonDetailListItem implements Parcelable {
    /* ID */
    private String id;
    /* 名称 */
    private String name;
    /* url */
    private List<String> urlArr;
    /* 课程名 */
    private String parentName;
    /* 下载状态 */
    private String type;

    /* 是否被选中 */
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

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

    public List<String> getUrlArr() {
        return urlArr;
    }

    public void setUrlArr(List<String> urlArr) {
        this.urlArr = urlArr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.urlArr);
        dest.writeString(this.parentName);
    }

    public MicroLessonDetailListItem() {
    }

    protected MicroLessonDetailListItem(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.urlArr = in.createStringArrayList();
        this.parentName = in.readString();
    }

    public static final Parcelable.Creator<MicroLessonDetailListItem> CREATOR = new Parcelable.Creator<MicroLessonDetailListItem>() {
        @Override
        public MicroLessonDetailListItem createFromParcel(Parcel source) {
            return new MicroLessonDetailListItem(source);
        }

        @Override
        public MicroLessonDetailListItem[] newArray(int size) {
            return new MicroLessonDetailListItem[size];
        }
    };
}
