package com.getee.worldchat.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;

import com.getee.worldchat.model.User;

public class DBOperation {
    /**
     * 对用户的基本信息进行 验证，保存，或修改
     * @return
     */
    /*public static void main(String[] args) {
        //User admin=new User("266","166","666","666","666");
        User admin=DBOperation.select("266");
        //DBOperation.creat(admin);
        System.out.println(DBOperation.addFriend(admin, "166", "r厦"));
        
    }*/
    public static User login(String idNum,String password)//验证登录
    {
//        File file=new File("userinfo/"+idNum+".wc");
//        if(!file.exists())return null;
        User user=select(idNum);//查询出账户信息
        if(user==null)return null;
        if(user.getPassword().equals(password))return user;//返回登录的用户所有信息
        return null;
        
    }
    public static User select(String idNum)//查找账户。。。。添加好友
    {
        File file=new File("userinfo/"+idNum+".wc");
        if(!file.exists())return null;
        
        ObjectInputStream obj = null;
        try {
            obj=new ObjectInputStream(new FileInputStream(file));
            User user=(User)obj.readObject();//把查找到的账户返回
            return user;//返回查找的用户所有信息
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                if(obj!=null)
                    obj.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
    public static User addFriend(User user,String addID,String groupName)//添加好友信息(自己类，添加的好友id,分组名)
    {
        //Map<String,String> friend=user.getFriends();
        //friend.put(addID, groupName);
        user.getFriends().put(addID, groupName);
        //user.setFriends(friend);
        for( String t:user.getFriends().values())
        {
            System.out.println(t);
        }
        if(edit(user))//将信息保存进文件
            return user;
        else
            return null;
        
    }

    public static boolean creat(User user)//添加注册信息=======在界面出进行new HashMap==
    {
        File file=new File("userinfo/"+user.getIdNum()+".wc");
        if(file.exists()) return false;
        else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return edit(user);
    }
    public static boolean edit(User user)//修改信息----需要在修改界面处判断修改后的id是否被注册(或阻止ID修改)----
    {
        File file=new File("userinfo/"+user.getIdNum()+".wc");
        ObjectOutputStream obj = null;
        try {
            obj=new ObjectOutputStream(new FileOutputStream(file));
            obj.writeObject(user);
            obj.flush();
            
            return true;
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                if(obj!=null)
                    obj.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return false;
    }
    //public static boolean delete(User user)//账号不删除
    
}
