package com.getee.worldchat.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.getee.worldchat.model.User;

public class DBGroup {
    /*
     * 由服务器类调用
     * 加群操作，查找，创建，把群当做一个user
     * 应该定义一个上层接口，其子实现分为用户，和群
     */

    public static void main(String[] args) {
//        User group=new User();
//        group.setIdNum("666");
//        group.setNiname("大理寺");
//        group.setPhoto("source/头像7.jpg");
//        group.setSpeakword("悔罪净化灵魂， 劳动重塑自我！");
//        creat(group);
        User u=DBOperation.select("1");
        addGroup(u, "666");

    }
    public static User select(String idNum)//查找账户。。。。添加群
    {
        File file=new File("groupinfo/"+idNum+".wcg");//群信息后缀不同
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
    /*
     * 显示出群中的所有人数
     */
    
    /*
     * 用户要加群时调用
     */
    public static boolean addGroup(User user,String addID)//添加群信息(自己类，添加的群id)
    {

        User group=select(addID);//查找该群
        if(group==null) return false;//不存在该群
        Set<String> its=user.getGroups();
        its.add(addID);
        if(DBOperation.edit(user))return false;        //保存用户添加的群

        String level="nomal";
        Map<String,HashSet<User>> gm=group.getFriends();//取出该群中的所有成员集合
        HashSet<User> gs=gm.get(level);//取出该小组中的所有成员集合
        if(gs==null){
            gs=new HashSet<>();
        }
        gs.add(user);
        gm.put(level, gs);
        
        group.setFriends(gm);

        if(!edit(group)) return false;//保存对对群的修改
        return true;
        
    }

    public static boolean creat(User user)//添加注册信息=======在界面出进行new HashMap==
    {
        File file=new File("groupinfo/"+user.getIdNum()+".wcg");
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
        File file=new File("groupinfo/"+user.getIdNum()+".wcg");
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
