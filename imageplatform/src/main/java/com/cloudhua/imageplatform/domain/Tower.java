package com.cloudhua.imageplatform.domain;

/**
 * Created by yangchao on 2017/8/15.
 * 杆塔／线路类
 */
public class Tower {
    private long towerId;
    private String name;
    private long parentId;
    private int status;
    private long typeId;
    private long owerId;
    private long longitude;
    private long latitude;
    private String attr;

    public Tower() {}

    public long getTowerId() {
        return towerId;
    }

    public void setTowerId(long towerId) {
        this.towerId = towerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public long getOwerId() {
        return owerId;
    }

    public void setOwerId(long owerId) {
        this.owerId = owerId;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
