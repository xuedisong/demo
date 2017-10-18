package com.cloudhua.imageplatform.domain;

/**
 * Created by yangchao on 2017/8/20.
 * 用户职务类
 */
public class Position {

    private long position;

    private String name;

    private String attr;

    public Position() {
    }

    public Position(long position, String name, String attr) {
        this.position = position;
        this.name = name;
        this.attr = attr;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
