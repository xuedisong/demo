package com.cloudhua.imageplatform.domain;

/**
 * Created by yangchao on 2017/8/17.
 * 系统配置项类
 */
public class Sysconfig {

    private long id;

    private String key;

    private String value;

    private String attr;

    public Sysconfig() {
    }

    public Sysconfig(long id, String key, String value, String attr) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.attr = attr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
