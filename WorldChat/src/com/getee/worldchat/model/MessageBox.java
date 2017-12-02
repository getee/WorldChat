package com.getee.worldchat.model;

import java.io.Serializable;

import javax.swing.text.SimpleAttributeSet;

public class MessageBox implements Serializable {
    private String content;//内容
    private User from;
    private User to;
    private int type;//来自MessType接口,固定格式
    private String time;//发送时间
    private SimpleAttributeSet attrset;//样式只在聊天对话框中可以体现
    /*
     * 储存传递数据的类
     */
    public MessageBox(String content, User from, User to, int type, String time) {
        this.content = content;
        this.from = from;
        this.to = to;
        this.type = type;
        this.time = time;
    }
    public MessageBox() {
        
    }
    @Override
    public String toString() {
        String toStr="null";
        if(to!=null) toStr=to.getIdNum();
        String fromStr="null";
        if(from!=null) fromStr=from.getIdNum();

        return "MessageBox [content=" + content + ", from=" + toStr + ", to="
                + fromStr + ", type=" + type + ", time=" + time + "]";
    }
    public SimpleAttributeSet getAttrset() {
        return attrset;
    }
    public void setAttrset(SimpleAttributeSet attrset) {
        this.attrset = attrset;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public User getFrom() {
        return from;
    }
    public void setFrom(User from) {
        this.from = from;
    }
    public User getTo() {
        return to;
    }
    public void setTo(User to) {
        this.to = to;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

}
