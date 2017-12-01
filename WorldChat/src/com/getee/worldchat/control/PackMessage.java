package com.getee.worldchat.control;

import com.getee.worldchat.model.MessHelp;
import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.User;
/*
 * 该类专门用于打包发送消息
 */
public class PackMessage {
    /*
     * 静态方法用于直接打包产生一个MessageBox
     */
    public static MessageBox packLogin(String idNum,String password){
        User user=new User();
        user.setIdNum(idNum);
        user.setPassword(password);
        
        MessageBox m=new MessageBox();
        m.setType(MessHelp.LOGIN);
        m.setFrom(user);
        return m;
        
    }
    public static MessageBox packRegist(User user){//注册消息
         MessageBox m=new MessageBox();
         m.setType(MessHelp.REGIST);
         m.setFrom(user);
         return m;
    }
    
    public static MessageBox packTrue(User user){//服务器的to的用户消息
        MessageBox m=new MessageBox();
        m.setType(MessHelp.ISTRUE);
        m.setTo(user);
        return m;
   }
    public static MessageBox packTrue(){//服务器的返回正确确认消息
        MessageBox m=new MessageBox();
        m.setType(MessHelp.ISTRUE);
        return m;
   }
   public static MessageBox packFalse(){//服务器的返回失败确认消息
        MessageBox m=new MessageBox();
        m.setType(MessHelp.ISFALSE);
        return m;
   }
    public static MessageBox packOneChat(User from,User to,String str){
        MessageBox m=new MessageBox();
        m.setFrom(from);
        m.setTo(to);
        m.setContent(str);
        m.setType(MessHelp.ONECHAT);
        return m;
    }//对一聊天消息
    public static MessageBox packAllChat(){return null;}//对多聊天消息

}
