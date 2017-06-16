package com.taoke.taokestudent.entity;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.InputStreamReader;
import java.util.Date;

/**
 * 用户
 */
public class User {
    /* Uid */
    private String uid;
    /* 昵称 */
    private String nickname;
    /* 真是姓名 */
    private String realname;
    /* 班级 */
    private String banji;
    /* 淘课币 */
    private String cash;
    /* 注册类型 作业与考试时使用，注册方式为4的才能使用该功能*/
    private String reg_type;
    /* 年卡截止时间 截至时间前收费的项目全免费 */
    private String stop_time;
    /* 性别  1:男 2:女*/
    private String sex;
    /* 头像地址 */
    private String head_image;
    /* 头像 */
    private Bitmap headImage;
    /* 是否免费 */
    private boolean isFree;

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public Bitmap getHeadImage() {
        return headImage;
    }

    public void setHeadImage(Bitmap headImage) {
        this.headImage = headImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getBanji() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getReg_type() {
        return reg_type;
    }

    public void setReg_type(String reg_type) {
        this.reg_type = reg_type;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
        // 判断是否处于免费期
        if (TextUtils.isEmpty(stop_time)) {
            isFree = false;
        } else {
            long mill = Long.parseLong(stop_time);
            isFree = System.currentTimeMillis() <= mill;
        }
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }
}
