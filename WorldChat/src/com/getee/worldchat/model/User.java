package com.getee.worldchat.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class User implements Serializable {
    /**
     * 存储用户的基本信息
     */
    private String idNum;//账号名
    private String password;//密码
    private String niname;//昵称
    private String sex;//性别
    private String speakword;//个性签名
    private Map<String,String> friends;//列表:<好友账号,分组名>
    
    public User(String idNum, String password, String niname, String sex,
            String speakword) {
        this.idNum = idNum;
        this.password = password;
        this.niname = niname;
        this.sex = sex;
        this.speakword = speakword;
        this.friends = new TreeMap<>();//好友不能直接构造
    }
    public User()
    {
        
    }
    public String getIdNum() {
        return idNum;
    }
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNiname() {
        return niname;
    }
    public void setNiname(String niname) {
        this.niname = niname;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSpeakword() {
        return speakword;
    }
    public void setSpeakword(String speakword) {
        this.speakword = speakword;
    }
    public Map<String, String> getFriends() {
        return friends;
    }
    public void setFriends(Map<String, String> friends) {
        this.friends = friends;
    }
    @Override
    public String toString() {
        return "User [idNum=" + idNum + ", password=" + password + ", niname="
                + niname + ", sex=" + sex + ", speakword=" + speakword
                + ", friends=" + friends + "]";
    }
    

}
