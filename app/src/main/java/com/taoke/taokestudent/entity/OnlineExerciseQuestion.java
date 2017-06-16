package com.taoke.taokestudent.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 在线做题试题
 */
public class OnlineExerciseQuestion implements Parcelable {
    /* ID */
    private String id;
    /* 题干 */
    private String topic;
    /* A */
    private String a;
    /* B */
    private String b;
    /* C */
    private String c;
    /* D */
    private String d;
    /* 组题题干 */
    private String zuti_topic;
    /* 1:多选 2：单选 */
    private String tixing;

    /* 考点 */
    private String kaodian;
    /* 解析 */
    private String analysis_text;
    /* 答案（用户回答） */
    private String daan;
    /* 正确答案 */
    private String answer;
    /* 对错  1：对 2：错 */
    private String duicuo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getZuti_topic() {
        return zuti_topic;
    }

    public void setZuti_topic(String zuti_topic) {
        this.zuti_topic = zuti_topic;
    }

    public String getTixing() {
        return tixing;
    }

    public void setTixing(String tixing) {
        this.tixing = tixing;
    }

    public String getKaodian() {
        return kaodian;
    }

    public void setKaodian(String kaodian) {
        this.kaodian = kaodian;
    }

    public String getAnalysis_text() {
        return analysis_text;
    }

    public void setAnalysis_text(String analysis_text) {
        this.analysis_text = analysis_text;
    }

    public String getDaan() {
        return daan;
    }

    public void setDaan(String daan) {
        this.daan = daan;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDuicuo() {
        return duicuo;
    }

    public void setDuicuo(String duicuo) {
        this.duicuo = duicuo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.topic);
        dest.writeString(this.a);
        dest.writeString(this.b);
        dest.writeString(this.c);
        dest.writeString(this.d);
        dest.writeString(this.zuti_topic);
        dest.writeString(this.tixing);
        dest.writeString(this.kaodian);
        dest.writeString(this.analysis_text);
        dest.writeString(this.daan);
        dest.writeString(this.answer);
        dest.writeString(this.duicuo);
    }

    public OnlineExerciseQuestion() {
    }

    protected OnlineExerciseQuestion(Parcel in) {
        this.id = in.readString();
        this.topic = in.readString();
        this.a = in.readString();
        this.b = in.readString();
        this.c = in.readString();
        this.d = in.readString();
        this.zuti_topic = in.readString();
        this.tixing = in.readString();
        this.kaodian = in.readString();
        this.analysis_text = in.readString();
        this.daan = in.readString();
        this.answer = in.readString();
        this.duicuo = in.readString();
    }

    public static final Parcelable.Creator<OnlineExerciseQuestion> CREATOR = new Parcelable.Creator<OnlineExerciseQuestion>() {
        @Override
        public OnlineExerciseQuestion createFromParcel(Parcel source) {
            return new OnlineExerciseQuestion(source);
        }

        @Override
        public OnlineExerciseQuestion[] newArray(int size) {
            return new OnlineExerciseQuestion[size];
        }
    };
}
