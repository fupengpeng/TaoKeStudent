package com.taoke.taokestudent.common;

/**
 * EventBus传递的数据
 */
public class DataEvent {
    /* 类型 */
    private int type;
    /* 数据 */
    private Object data;

    public DataEvent() {
    }

    public DataEvent(int type) {
        this.type = type;
    }

    public DataEvent(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
