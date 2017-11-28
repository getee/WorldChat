package com.getee.worldchat.control;

import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.User;
/*
 * 该类用于子类重写，打包发送消息
 */
public abstract class PackMessage {
    public MessageBox packLogin(String idNum,String password,int messType){return null;}//登录消息
    public MessageBox packRegist(User user,int messType){return null;}//注册消息
    public MessageBox packOneChat(){return null;}//对一聊天消息
    public MessageBox packAllChat(){return null;}//对多聊天消息

}
