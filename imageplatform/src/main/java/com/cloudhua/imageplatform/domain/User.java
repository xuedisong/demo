package com.cloudhua.imageplatform.domain;

/**
 * Created by yangchao on 2017/8/20.
 * 用户类
 */
public class User {

    public static final int USER_DELETE = 0;

    public static final int USER_NORMAL = 1;

    public static final int USER_LOCK = 1 << 1;

    private long uid;

    private String name;

    private String nickName;

    private String email;

    private String phone;

    private String password;

    private int position;

    private int status = 1;

    private long order;

    private String attr;

    private long ctime;

    private long mtime;

    public User() {
    }

    public User(long uid, String name, String nickName, String email, String phone, String password, int position, int status, long order, String attr, long ctime, long mtime) {
        this.uid = uid;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.position = position;
        this.status = status;
        this.order = order;
        this.attr = attr;
        this.ctime = ctime;
        this.mtime = mtime;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getMtime() {
        return mtime;
    }

    public void setMtime(long mtime) {
        this.mtime = mtime;
    }

}
