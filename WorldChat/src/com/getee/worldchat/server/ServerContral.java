package com.getee.worldchat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.getee.worldchat.control.DBOperation;
import com.getee.worldchat.control.PackMessage;
import com.getee.worldchat.model.MessHelp;
import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.User;

public class ServerContral {
    private ServerSocket ser;
    private Map<User,ObjectOutputStream> allClient;//保存各个登录后用户的输出流，进行数据传递
    
    
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
     * ================线程类，处理Socket 
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
                    }//。。。
                    
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
        /*
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
                allClient.put(user, out);//保存数据
                MessageBox m= PackMessage.packTrue(user);
                try {
                    out.writeObject(m);
                    out.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
        
        
        
    }
}
