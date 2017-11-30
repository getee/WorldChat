package com.getee.worldchat.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Map;

import com.getee.worldchat.model.User;

public class DBOperation {
    /**
     * 由服务器类调用
     * 对用户的基本信息进行 验证，保存，或修改
     * @return
     */
    public static void main(String[] args) {
//        User admin=new User("366","666","666","666","666");
//        
//        DBOperation.creat(admin);

       // User admin=select("3");
       //out.println(DBOperation.addFriend(admin, "2", "开黑"));
//        System.out.println(admin);
//        System.out.println(select("266"));
        
    }
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
    public static boolean addFriend(User user,String addID,String callName)//添加好友信息(自己类，添加的好友id,分组名)
    {

        User friend=select(addID);//查找该好友
        if(friend==null) return false;//不存在该好友
        Map<String,HashSet<User>> itm=user.getFriends();
        HashSet<User> its=itm.get(callName);//取出该小组中的所有成员集合
        if(its==null){
            its=new HashSet<>();
        }
        its.add(friend);
        itm.put(callName, its);
        
        user.setFriends(itm);//自己信息修改
        if(!edit(user)) return false;
        
        Map<String,HashSet<User>> fm=friend.getFriends();
        HashSet<User> fs=fm.get(callName);//取出该小组中的所有成员集合
        if(fs==null){
            fs=new HashSet<>();
        }
        fs.add(user);
        fm.put(callName, fs);
        
        friend.setFriends(fm);
        if(!edit(friend)) return false;
        return true;
        
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
    
    /*
     * 修改信息----需要在修改界面处判断修改后的id是否被注册(或阻止ID修改)----
     */
    public static boolean edit(User user)//可做保存数据用途
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
