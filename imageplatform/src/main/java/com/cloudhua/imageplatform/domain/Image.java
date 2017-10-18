package com.cloudhua.imageplatform.domain;

/**
 * Created by yangchao on 2017/8/15.
 * image实体类
 */
public class Image {
    private long imgId;
    private String name;
    private String formatName;
    private long size;
    private long length;
    private long width;
    private String stor;
    private int stat;
    private long ctime;
    private long creator;
    private long towerId;
    private String attr;

    public Image() {};

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getImgId() {
        return imgId;
    }

    public void setImgId(long imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getStor() {
        return stor;
    }

    public void setStor(String stor) {
        this.stor = stor;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public long getTowerId() {
        return towerId;
    }

    public void setTowerId(long towerId) {
        this.towerId = towerId;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
