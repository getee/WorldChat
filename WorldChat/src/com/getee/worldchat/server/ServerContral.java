package com.getee.worldchat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.getee.worldchat.control.DBOperation;
import com.getee.worldchat.control.PackMessage;
import com.getee.worldchat.model.MessHelp;
import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.User;

public class ServerContral {
    private ServerSocket ser;
    private Map<String,ObjectOutputStream> allClient;//<idNum,write>保存各个登录后用户的输出流，进行数据传递
    
    
    public static void main(String[] args) {
        new ServerContral().index();
    }
    public void index()
    {
        try {
            ser=new ServerSocket(MessHelp.PORT);
            allClient=new HashMap<>();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        while(true){
            try {
                Socket c=ser.accept();
                ObjectOutputStream out=new ObjectOutputStream(c.getOutputStream());
                ObjectInputStream in=new ObjectInputStream(c.getInputStream());
                new ClientSolveThread(out,in).start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
            
    }
    
    /*
     * ====线程类，处理Socket 
     */
    class ClientSolveThread extends Thread{
        private ObjectOutputStream  out;
        private ObjectInputStream in;
        
        private MessageBox news;//接收到的消息盒
        
        public ClientSolveThread(ObjectOutputStream out, ObjectInputStream in) {
            this.out = out;
            this.in = in;
        }
        public void run(){
            while(true)
            {
                try {
                    news=(MessageBox)in.readObject();
                    System.out.println(news);//==================================输出预置
                    int type=news.getType();
                    if(type==MessHelp.REGIST){
                        disposeRegist();
                    }else if(type==MessHelp.LOGIN){
                        disposeLogin();
                    }else if(type==MessHelp.ONECHAT)
                    {
                        disposeOneChat();
                    }else if(type==MessHelp.SEARCH)
                    {
                        disposeSearch();
                    }
                    else if(type==MessHelp.ADDFRIEND)
                    {
                        disposeAddFriend();
                    }
                    //。。。
                    
                    /*
                     * 统一流只能读取一次，所以，可以统一后台读取，更新两个Frame。
                     */
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        }
        /*===============================================
         * 处理注册请求
         */
        private void disposeRegist(){
            User messageFrom=news.getFrom();
            if(DBOperation.creat(messageFrom)){
                MessageBox m= PackMessage.packTrue();
                try {
                    out.writeObject(m);
                    out.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }else{
                MessageBox m= PackMessage.packFalse();
                try {
                    out.writeObject(m);
                    out.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        }
        
        /*
         * 处理登录请求
         */
        private void disposeLogin(){
            User messageFrom=news.getFrom();
            User user=DBOperation.select(messageFrom.getIdNum());
            //System.out.println(messageFrom);//===========================================
            //System.out.println(user);
            //System.out.println(user.getPassword()==messageFrom.getPassword());
            if(user!=null && user.getPassword().equals(messageFrom.getPassword()))
            {
                allClient.put(user.getIdNum(), out);//保存每一个用户登录数据和对应的输出流*************
                MessageBox m= PackMessage.packTrue(user);
                try {
                    out.writeObject(m);
                    out.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                willDo(user.getIdNum(),out);
            }
            else
            {
                MessageBox m= PackMessage.packFalse();
                try {
                    out.writeObject(m);
                    out.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        /*
         * 处理个人聊天
         * 发送两份数据报给双方
         */
        private void disposeOneChat(){
            MessageBox m=news;
            m.setTime(new Date().toLocaleString());
            String uTo=m.getTo().getIdNum();
            String uFrom=m.getFrom().getIdNum();
            boolean isOnline=false;
            for(String t: allClient.keySet())
            {
                if(t.equals(uFrom)){
                    m.setType(MessHelp.REONEFROM);//这个是你发送的数据，即将更新你的窗口
                    try {
                        allClient.get(t).writeObject(m);
                        allClient.get(t).flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if(t.equals(uTo)){
                    m.setType(MessHelp.REONETO);//这个是你要接收的数据，即将更新你的窗口=========
                    /*
                     * * * * * * * * * *用户不在线,新建一个线程 循环等待用户上线,则建立临时本本寄存==在线再传递;* * * * * * * * * 
                     */
                    isOnline=true;

                    try {
                        allClient.get(t).writeObject(m);
                        allClient.get(t).flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            if(!isOnline){//建立代办事项
                File f=new File("willdo/"+uTo+".read");
                try {
                    ObjectOutputStream fout=new ObjectOutputStream(new FileOutputStream(f));
                    fout.writeObject(m);
                    fout.flush();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        /*
         * 建立待读取聊天，以便于上线后读取
         */
        private void willDo(String str,ObjectOutputStream out){
            File f=new File("willdo/"+str+".read");
            if(!f.exists())return ;
            try {
                ObjectInputStream fin=new ObjectInputStream(new FileInputStream(f));
                MessageBox m=(MessageBox)fin.readObject();
                m.setType(MessHelp.REONETO);//这个是你要接收的数据，即将更新你的窗口=========
                out.writeObject(m);
                out.flush();
                f.delete();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        /*
         * 处理加好友
         */
        private void disposeSearch(){
            MessageBox m=news;
            String str=news.getContent();
            User u=DBOperation.select(str);
            //if(u!=null){
                m.setTo(u);//更改to信息
            //}
            m.setType(MessHelp.RESEARCH);
            try {
                out.writeObject(m);
                out.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        private void disposeAddFriend(){
            MessageBox m=news;
            boolean is=DBOperation.addFriend(m.getFrom(), m.getTo(), m.getContent());
            MessageBox m2=new MessageBox();
            m2.setType(MessHelp.READDFRIEND);
            if(is){
                m2.setContent("true");
            }else
            {
                m2.setContent("false");
            }
            try {
                out.writeObject(m);
                out.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        
        
        
    }//thread类
}
