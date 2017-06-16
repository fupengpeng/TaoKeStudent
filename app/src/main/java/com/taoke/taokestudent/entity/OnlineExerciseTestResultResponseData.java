package com.taoke.taokestudent.entity;

import java.util.List;

/**
 * 在线做题答题结果
 */
public class OnlineExerciseTestResultResponseData {
    /* 用时 */
    private String use_time;
    /* 题目总数 */
    private String zongshu;
    /* 答对数 */
    private String daduishu;
    /* 答错数 */
    private String dacuoshu;
    /* 答对率% */
    private double daduilv;
    /* 试题集 */
    private List<OnlineExerciseQuestion> dataList;

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getZongshu() {
        return zongshu;
    }

    public void setZongshu(String zongshu) {
        this.zongshu = zongshu;
    }

    public String getDaduishu() {
        return daduishu;
    }

    public void setDaduishu(String daduishu) {
        this.daduishu = daduishu;
    }

    public String getDacuoshu() {
        return dacuoshu;
    }

    public void setDacuoshu(String dacuoshu) {
        this.dacuoshu = dacuoshu;
    }

    public double getDaduilv() {
        return daduilv;
    }

    public void setDaduilv(double daduilv) {
        this.daduilv = daduilv;
    }

    public List<OnlineExerciseQuestion> getDataList() {
        return dataList;
    }

    public void setDataList(List<OnlineExerciseQuestion> dataList) {
        this.dataList = dataList;
    }
}
