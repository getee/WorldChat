package com.getee.worldchat.view;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.text.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JButton;

import com.getee.worldchat.control.DBOperation;
import com.getee.worldchat.model.MessHelp;
import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.PictureBath;
import com.getee.worldchat.model.User;









import javax.swing.JTabbedPane;


public class FriendsFrame extends JFrame{
  //为了能记录所有和我聊过天的好友信息(打开过聊天界面的好友信息)，<对方Id号,界面>
    private Map<String,OneChatFrame>  allFrames=new HashMap<>();
    //需要打开frame;   接受者解析from  \   发送者解析to
    
    
    private JTextField textField;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JButton btnNewButton_2;
    private JTree tree;
    private User user;
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JTabbedPane tabbedPane;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_1;
    
    public static void main(String[] args) {
        User admin =DBOperation.select("3");
        new FriendsFrame(admin);
    }
    public FriendsFrame(ObjectOutputStream out,ObjectInputStream in,User user) {
        this(user);
        this.in=in;
        this.out=out;
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==1&&e.getClickCount()==2) {
                    TreePath  path=tree.getSelectionPath();
                    DefaultMutableTreeNode lastNode=(DefaultMutableTreeNode)path.getLastPathComponent();
                    if(lastNode.isLeaf()) {
                        //上面是解析用户双击之后判断是不是双击的某一个用户名上的这个Node

                        String groupName=path.toString().substring(path.toString().indexOf(",")+1,path.toString().lastIndexOf(",")).trim();//获取分组名

                        String niName=lastNode.toString();//用户昵称行(昵称+id)
                        
                        String idNum=niName.substring(niName.lastIndexOf("[")+1,niName.length()-1);//用户id
                        
                        for(String firendNum:allFrames.keySet())
                        {
                            if(firendNum.equals(idNum))
                            {
                                allFrames.get(firendNum).setVisible(true);
                                return;
                            }
                        }
                        User chatUser=new User();
                        for(User u:user.getFriends().get(groupName)){//遍历该分组好友列表，查找好友
                            if(u.getIdNum().equals(idNum)){
                                chatUser=u;
                                break;
                            }
                        }
                        OneChatFrame   chat=new OneChatFrame(out,in,FriendsFrame.this,user,chatUser);
                        //OneChatFrame   chat=new OneChatFrame(FriendsFrame.this.user,chatUser);
                        chat.setVisible(true);//让聊天界面显示出来
                        allFrames.put(idNum, chat);
                    }
                }
            }
        });
        new ReadThread(in).start();
    }
    /*
     * 进行好友列表显示
     */
    /**
     * @wbp.parser.constructor
     */
    public FriendsFrame(User user) {
        this.user=user;

        this.setIconImage(PictureBath.ICON.getImage());//设置图标
        this.setBounds(100, 100, 354, 739);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        ImageIcon head = new ImageIcon(user.getPhoto());//头像图片
        head.setImage(head.getImage().getScaledInstance(107,88,Image.SCALE_DEFAULT));
        getContentPane().setLayout(null);
        JLabel lblNewLabel = new JLabel(head);
        lblNewLabel.setBounds(15, 15, 107, 88);
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel(user.getNiname());
        lblNewLabel_1.setBounds(137, 15, 160, 35);
        getContentPane().add(lblNewLabel_1);
        
        textField = new JTextField(user.getSpeakword());
        textField.setBounds(137, 69, 160, 35);
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setForeground(Color.BLACK);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 112, 332, 35);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        ImageIcon tong = new ImageIcon("source/icon/tong.png");//通讯录图标
        tong.setImage(tong.getImage().getScaledInstance(116,35,Image.SCALE_DEFAULT));
        btnNewButton = new JButton(tong);
        btnNewButton.setBounds(0, 0, 116, 35);
        panel.add(btnNewButton);
        
        ImageIcon qun = new ImageIcon("source/icon/qun.png");//群聊图标
        qun.setImage(qun.getImage().getScaledInstance(116,35,Image.SCALE_DEFAULT));
        btnNewButton_1 = new JButton(qun);
        btnNewButton_1.setBounds(114, 0, 116, 35);
        panel.add(btnNewButton_1);
        
        ImageIcon near = new ImageIcon("source/icon/near.png");//最近图标
        near.setImage(near.getImage().getScaledInstance(116,35,Image.SCALE_DEFAULT));
        btnNewButton_2 = new JButton(near);
        btnNewButton_2.setBounds(228, 0, 104, 35);
        panel.add(btnNewButton_2);
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 118, 332, 525);
        getContentPane().add(tabbedPane);
        
        scrollPane = new JScrollPane();
        tabbedPane.addTab("New tab", null, scrollPane, null);

        
        scrollPane_1 = new JScrollPane();
        tabbedPane.addTab("New tab", null, scrollPane_1, null);
        
        JScrollPane scrollPane_2 = new JScrollPane();
        tabbedPane.addTab("New tab", null, scrollPane_2, null);
        

        //System.out.println("=======================================================================");

        
        
        DefaultMutableTreeNode  root=new DefaultMutableTreeNode("root");
        //定义一个jtree根节点，所有的好友分组和好友都在这个根节点上往上放
        System.out.println(user);
        Map<String, HashSet<User>>  allFriends=user.getFriends();
         
        Set<String>  allGroupNames=allFriends.keySet();//获取所有的分组名
        
        for(String groupName:allGroupNames) {
            DefaultMutableTreeNode  group=new DefaultMutableTreeNode(groupName);//构造出每个组名的对应的TreeNode对象
            HashSet<User>  friendsOfGroup=allFriends.get(groupName);
            for(User u:friendsOfGroup) {
                DefaultMutableTreeNode  friend=new DefaultMutableTreeNode(u.getNiname()+"  ["+u.getIdNum()+"]");
                group.add(friend);
            }
            
            root.add(group);
        }

        tree = new JTree(root);
        
        tree.setRootVisible(false);
        scrollPane.setViewportView(tree);
        
        ImageIcon add = new ImageIcon("source/icon/add.png");//通讯录图标
        add.setImage(add.getImage().getScaledInstance(333,44,Image.SCALE_DEFAULT));
        JButton btnNewButton_3 = new JButton(add);
        btnNewButton_3.setBounds(0, 639, 333, 44);
        getContentPane().add(btnNewButton_3);
    }
    /*
     * 只对服务器传递消息进行read处理
     */
    class ReadThread extends Thread{
        private ObjectInputStream in;
        private MessageBox news;
        public ReadThread(ObjectInputStream in){
            this.in=in;
        }
        public void run(){
            while(true){
                try {
                    news=(MessageBox) in.readObject();
                    int type=news.getType();
                    if(type==MessHelp.REONEFROM){//个人发送信息返回
                        processOneFrom();
                    }
                    else if(type==MessHelp.REONETO){//个人接收信息返回
                        processOneTo();
                    }
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        /* from自己，所以主要处理，要to的人选
         * 处理个人消息返回process
         */
        private void processOneFrom(){
            String frindID=news.getTo().getIdNum();//你发送的id
            OneChatFrame one=allFrames.get(frindID);
            if(one==null){//如果没有该窗口就新建一个
                one=new OneChatFrame(out,in,FriendsFrame.this,user,news.getTo());
                allFrames.put(frindID, one);
            }
            one.setVisible(true);//让聊天界面显示出来
            JTextPane textPane =one.getTextPane();//获取对话框的句柄
            String str=" "+news.getFrom().getNiname()+" \t"+news.getTime()+"\n"+news.getContent()+"\n";
            
            SimpleAttributeSet attrset = new SimpleAttributeSet();//样式类
            StyleConstants.setFontSize(attrset,16);//设置字体大小
            StyleConstants.setForeground(attrset,Color.BLUE);//自己消息是颜色 
            Document docs = textPane.getDocument();//获得文本对象
            try {
                docs.insertString(docs.getLength(),str , attrset);//对文本进行追加(文本末位处,String,样式)
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        /*  to自己，所以主要处理，要from的人选
         * 处理个人消息接收
         */
        private void processOneTo(){//个人接收信息返回
            String frindID=news.getFrom().getIdNum();//发送信息的id
            OneChatFrame one=allFrames.get(frindID);
            if(one==null){//如果没有该窗口就新建一个
                one=new OneChatFrame(out,in,FriendsFrame.this,user,news.getFrom());
                allFrames.put(frindID, one);
            }
            one.setVisible(true);//让聊天界面显示出来
            JTextPane textPane =one.getTextPane();//获取对话框的句柄
            String str=" "+news.getFrom().getNiname()+" \t"+news.getTime()+"\n"+news.getContent()+"\n";

            SimpleAttributeSet attrset = new SimpleAttributeSet();//样式类
            StyleConstants.setFontSize(attrset,16);//设置字体大小
            StyleConstants.setForeground(attrset,Color.GREEN);//别人消息是颜色 
            Document docs = textPane.getDocument();//获得文本对象
            try {
                docs.insertString(docs.getLength(),str , attrset);//对文本进行追加(文本末位处,String,样式)
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            
        }
    }
}
