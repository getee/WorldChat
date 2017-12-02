package com.getee.worldchat.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class User implements Serializable {
    /**
     * 存储用户的基本信息,#代表群不需要的信息
     */
    private String idNum;//账号名
    private String password;//密码    #(或是验证消息)
    private String niname;//昵称
    private String sex;//性别         #
    private String speakword;//个性签名
    private String photo;//头像路径
    private Map<String,HashSet<User>> friends;//列表:<分组名,分组中好友>
            //# <群管理，成员>
    
    private Set<User> groups;//群聊列表:<群id名>仅当在群聊中说话时启动查看,添加了哪些群
    

    //差一个toString
    public Set<User> getGroups() {
        return groups;
    }
    public void setGroups(Set<User> groups) {
        this.groups = groups;
    }
    
    public User(String idNum, String password, String niname, String sex,
            String speakword) {
        this();
        this.idNum = idNum;
        this.password = password;
        this.niname = niname;
        this.sex = sex;
        this.speakword = speakword;
                
    }

    public User()
    {
        this.friends = new TreeMap<>();//好友不能直接构造
        this.groups = new HashSet<>();//群不能直接构造
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
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

    public Map<String, HashSet<User>> getFriends() {
        return friends;
    }
    public void setFriends(Map<String, HashSet<User>> friends) {
        this.friends = friends;
    }
    
    public Set<User> allFriendsId()//遍历出所有好友
    {
        Set<User> allF = new HashSet<>();
        if(friends==null) return null;
        Set<String> key= friends.keySet();
        if(key==null) return null;
        for(String n:key){
            Set<User> t=friends.get(n);//获取到一个分组中的所有好友
            for(User i:t){
                allF.add(i);
            }
        }
        return allF;
    }
    @Override
    public String toString() {
        String friendsName="";
        Set<User> allF = allFriendsId();
        if(allF!=null){
            for(User t : allF){
                friendsName=friendsName+"、"+t.getIdNum();
            }
        }
        String groupsName="";
        for(User t : groups){
            friendsName=friendsName+t.getIdNum();
        }
        return "User [idNum=" + idNum + ", password=" + password + ", niname="
                + niname + ", sex=" + sex + ", speakword=" + speakword
                + ", photo=" + photo + ", friends=" + friendsName + ", groups="
                + groupsName + "]";
    }

    

}
