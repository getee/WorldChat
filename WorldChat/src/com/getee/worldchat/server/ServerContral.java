package com.getee.worldchat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.getee.worldchat.model.MessHelp;
import com.getee.worldchat.model.MessageBox;

public class ServerContral {
    private ServerSocket ser;
    
    public static void main(String[] args) {
        new ServerContral().index();
    }
    public void index()
    {
        try {
            ser=new ServerSocket(MessHelp.PORT);
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
    class ClientSolveThread extends Thread{
        private ObjectOutputStream  out;
        private ObjectInputStream in;
        public ClientSolveThread(ObjectOutputStream out, ObjectInputStream in) {
            this.out = out;
            this.in = in;
        }
        public void run(){
            while(true)
            {
                try {
                    MessageBox news=(MessageBox)in.readObject();
                    System.out.println(news);
                    int type=news.getType();
                    if(type==MessHelp.REGIST){
                        
                    }else if(type==MessHelp.LOGIN){
                        
                    }//。。。
                    
                    
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
    }

}
