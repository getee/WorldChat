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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.text.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JButton;

import com.getee.worldchat.control.DBGroup;
import com.getee.worldchat.control.DBOperation;
import com.getee.worldchat.model.MessHelp;
import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.PictureBath;
import com.getee.worldchat.model.User;














import javax.swing.JTabbedPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JList;


public class FriendsFrame extends JFrame{
  //为了能记录所有和我聊过天的好友信息(打开过聊天界面的好友信息)，<对方Id号,界面>
    private Map<String,OneChatFrame>  allFrames=new HashMap<>();
    
    public Map<String, OneChatFrame> getAllFrames() {
        return allFrames;
    }
    //需要打开frame;   接受者解析from  \   发送者解析to
    private Map<String,CrowdFrame>  allGroup=new HashMap<>();
    
    
    
    private DefaultMutableTreeNode root;

    private AddFriendFrame  addFrame;
    private AddGroupFrame  addGroupFrame;
    private JTextField textField;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JButton btnNewButton_2;
    private JTree tree;
    private User user;
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JTabbedPane tabbedPane;//控制面板
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_1;
    
    private JButton addFriendButton;//加好友
    private JButton addGroupButton;//加群
    private JList list;
    
    public JTree getTree() {
        return tree;
    }
    public static void main(String[] args) {
        User admin =DBGroup.select("666"); //DBOperation.select("1");
        //User admin =DBOperation.select("1");
        new FriendsFrame(admin);
    }
    public FriendsFrame(ObjectOutputStream out,ObjectInputStream in,User user) {
        this(user);
        this.in=in;
        this.out=out;
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                User user=FriendsFrame.this.user;//好友更新后需要再更新
                if(user.getFriends().size()==0) return ;
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
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                User user=FriendsFrame.this.user;//好友更新后需要再更新
                if(user.getGroups().size()==0) return ;
                if(e.getButton()==1&&e.getClickCount()==2) {
                    String groupName=list.getSelectedValue().toString();
                    
                    if(allGroup.get(groupName)!=null){
                            allGroup.get(groupName).setVisible(true);
                            return;
                    }
                    User g=(User)list.getSelectedValue();
                    String groupID=g.getIdNum();
                    MessageBox m=new MessageBox();
                    m.setType(MessHelp.SELECTGROUP);
                    m.setContent(groupID);//传递groupId号查找
                    m.setFrom(user);
                    try {
                        out.writeObject(m);
                        out.flush();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                                /*
                                 * 没必要定义Set<User>  ,因为群的更改不是相互的
                                 * (User)list.getSelectedValue(),此处是 旧Group,需要标记从服务器读取
                                 */
                    
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
        btnNewButton.addActionListener((ActionEvent e)->{
            tabbedPane.setSelectedIndex(0);
        });
        panel.add(btnNewButton);
        
        ImageIcon qun = new ImageIcon("source/icon/qun.png");//群聊图标
        qun.setImage(qun.getImage().getScaledInstance(116,35,Image.SCALE_DEFAULT));
        btnNewButton_1 = new JButton(qun);
        btnNewButton_1.setBounds(114, 0, 116, 35);
        btnNewButton_1.addActionListener((ActionEvent e)->{
            tabbedPane.setSelectedIndex(1);
        });
        panel.add(btnNewButton_1);
        
        ImageIcon near = new ImageIcon("source/icon/near.png");//最近图标
        near.setImage(near.getImage().getScaledInstance(116,35,Image.SCALE_DEFAULT));
        btnNewButton_2 = new JButton(near);
        btnNewButton_2.setBounds(228, 0, 104, 35);
        btnNewButton_2.addActionListener((ActionEvent e)->{
            tabbedPane.setSelectedIndex(2);
        });
        panel.add(btnNewButton_2);
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 118, 332, 525);
        getContentPane().add(tabbedPane);
        
        scrollPane = new JScrollPane();
        tabbedPane.addTab("New tab", null, scrollPane, null);

        
        scrollPane_1 = new JScrollPane();
        tabbedPane.addTab("New tab", null, scrollPane_1, null);
        
        
        
        
        
        User[] strGroup=user.getGroups().toArray(new User[]{});//Set2Array  遍历所有群
        list = new JList<User>(strGroup);
        list.setForeground(Color.ORANGE);
        list.setFont(new Font("华文行楷", Font.PLAIN, 24));
        
        scrollPane_1.setViewportView(list);
        
        JScrollPane scrollPane_2 = new JScrollPane();
        tabbedPane.addTab("New tab", null, scrollPane_2, null);
        
        
        
        
        
        
        
        //System.out.println("=======================================================================");

        root=new DefaultMutableTreeNode("root");
        
        //定义一个jtree根节点，所有的好友分组和好友都在这个根节点上往上放
        System.out.println(user);
        Map<String, HashSet<User>>  allFriends=user.getFriends();
         
        Set<String>  allGroupNames=allFriends.keySet();//获取所有的分组名
        if(allGroupNames.size()==0){
            DefaultMutableTreeNode  dmt=new DefaultMutableTreeNode("我的设备");//设置默认分组
            root.add(dmt);

        }
        else{
            for(String groupName:allGroupNames) {
                DefaultMutableTreeNode  group=new DefaultMutableTreeNode(groupName);//构造出每个组名的对应的TreeNode对象
                HashSet<User>  friendsOfGroup=allFriends.get(groupName);
                for(User u:friendsOfGroup) {
                    DefaultMutableTreeNode  friend=new DefaultMutableTreeNode(u.getNiname()+"  ["+u.getIdNum()+"]");
                    group.add(friend);
                }
                
                root.add(group);
            }
        }

        tree = new JTree(root);

        tree.setRootVisible(false);
        scrollPane.setViewportView(tree);
        
        
        addFriendButton = new JButton("加好友");
        addFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(addFrame==null)
                    addFrame=new AddFriendFrame(out,in,user,FriendsFrame.this);
                else
                {
                    addFrame.setVisible(true);
                    addFrame.getPanel().setVisible(false);
                }
            }
        });
        addFriendButton.setBounds(0, 639, 160, 44);
        getContentPane().add(addFriendButton);
        
        addGroupButton = new JButton("加群");
        addGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        addGroupButton.setBounds(158, 639, 174, 44);
        getContentPane().add(addGroupButton);
    }
    
    public void reUpdata(){//更新界面
        tree.updateUI();
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
                    else if(type==MessHelp.RESEARCH){//个人查找信息返回
                        processAddFriend();
                    }else if(type==MessHelp.READDFRIEND){//个人查找好友信息返回
                        processReAdd();
                    }
//                    else if(type==MessHelp.REALLFROM){//个人->群发送信息返回
//                        processAllFrom();
//                    }
                    else if(type==MessHelp.REALLTO){//群->个人接收信息返回
                        processAllTo();
                    }
                    else if(type==MessHelp.REGROUP){//群->个人接收信息返回
                        processRegroup();
                    }
                    else if(type==MessHelp.RELOOKGROUP){//个人添加群的查找
                        //processLookFriend();//重用显示界面
                    }
                    else if(type==MessHelp.READDGROUP){//个人添加群
                        //processAddGroup();
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
            String frindID=news.getTo().getIdNum();//你发送给的id
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
        private void processAddFriend(){
          if(news.getTo()==null){
              JOptionPane.showMessageDialog(addFrame, "用户名不存在!","温馨提示",JOptionPane.ERROR_MESSAGE);
              return ;
          }
          //======================================显示添加的好友信息================================================
          addFrame.getPanel().setVisible(true);
          User u=news.getTo();
          ImageIcon head = new ImageIcon(u.getPhoto());//头像图片
          head.setImage(head.getImage().getScaledInstance(160,174,Image.SCALE_DEFAULT));
          addFrame.getLblNewLabel().setIcon(head);
          addFrame.getTxtpnid().setText("");
          
          SimpleAttributeSet attrset = new SimpleAttributeSet();//样式类
          StyleConstants.setFontSize(attrset,22);//设置字体大小
          StyleConstants.setForeground(attrset,Color.BLUE);//别人昵称
          Document docs = addFrame.getTxtpnid().getDocument();//获得文本对象
          try {
              docs.insertString(docs.getLength(),u.getNiname()+" " , attrset);//对文本进行追加(文本末位处,String,样式)
          } catch (BadLocationException e) {
              e.printStackTrace();
          }
          StyleConstants.setForeground(attrset,Color.RED);//别人id是颜色 
          docs = addFrame.getTxtpnid().getDocument();//获得文本对象
          try {
              docs.insertString(docs.getLength(),"("+u.getIdNum()+")\n" , attrset);//对文本进行追加(文本末位处,String,样式)
          } catch (BadLocationException e) {
              e.printStackTrace();
          }
          StyleConstants.setFontSize(attrset,20);//设置字体大小
          StyleConstants.setForeground(attrset,Color.GRAY);//别人个性签名颜色 
          docs = addFrame.getTxtpnid().getDocument();//获得文本对象
          try {
              docs.insertString(docs.getLength(),""+u.getSpeakword()+"\n" , attrset);//对文本进行追加(文本末位处,String,样式)
          } catch (BadLocationException e) {
              e.printStackTrace();
          }
          //======================================确认添加好友================================================
          addFrame.setAddUser(u);

        }
        private void processReAdd(){//接收到add好友，自身调用
            String str=news.getContent();
            if(str.equals("true")){
                JOptionPane.showMessageDialog(null, "添加成功!","提示",JOptionPane.INFORMATION_MESSAGE);
                user=news.getFrom();//刷新user的值

                //实现重绘
 
                SwingUtilities.invokeLater(new Runnable() {  
                    public void run() { 
                        root.removeAllChildren();//删除原有的子节点
                        Map<String, HashSet<User>>  allFriends=user.getFriends();
                        //root=new DefaultMutableTreeNode("root");
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
                            tree.updateUI();                   
                    }  
                });
                
                

                
            }
            else{
                JOptionPane.showMessageDialog(null, "添加失败!","提示",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        /*private void processAllFrom(){//更新自己的界面
            String groupID=news.getTo().getIdNum();//你发送的群id
            CrowdFrame one=allGroup.get(groupID);
            if(one==null){//如果没有该窗口就新建一个
                one=new CrowdFrame(out,in,FriendsFrame.this,user,news.getTo());
                allGroup.put(groupID, one);
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
        */
        private void processAllTo(){//接收信息的群界面
            String groupID=news.getTo().getIdNum();//发送信息的id==========所有人都是解析to用户
            CrowdFrame one=allGroup.get(groupID);
            if(one==null){//如果没有该窗口就新建一个
                one=new CrowdFrame(out,in,FriendsFrame.this,user,news.getTo());
                allGroup.put(groupID, one);
            }
            one.setVisible(true);//让聊天界面显示出来
            JTextPane textPane =one.getTextPane();//获取对话框的句柄
            String str=" "+news.getFrom().getNiname()+" \t"+news.getTime()+"\n"+news.getContent()+"\n";

            SimpleAttributeSet attrset = new SimpleAttributeSet();//样式类
            StyleConstants.setFontSize(attrset,16);//设置字体大小
            if(user.getIdNum().equals(news.getFrom().getIdNum()))
            {
                StyleConstants.setForeground(attrset,Color.BLUE);//自己消息是颜色
            }
            else
            {
                StyleConstants.setForeground(attrset,Color.GREEN);//别人消息是颜色 
            }
            Document docs = textPane.getDocument();//获得文本对象
            try {
                docs.insertString(docs.getLength(),str , attrset);//对文本进行追加(文本末位处,String,样式)
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        private void processRegroup(){
            
            User group=news.getTo();
            CrowdFrame   chat=new CrowdFrame(out,in,FriendsFrame.this,user,group);
            chat.setVisible(true);//让聊天界面显示出来
            allGroup.put(group.getIdNum(), chat);
            
        }
        
        
        
        
    }//Thread
}



/*
 * 设置List图片
 */
class   CellRenderer   extends   JLabel   implements   ListCellRenderer  
{  
      /*类CellRenderer继承JLabel并实作ListCellRenderer.由于我们利用JLabel易于插图的特性，因此CellRenderer继承了JLabel  
        *可让JList中的每个项目都视为是一个JLabel.  
        */  
        CellRenderer()  
        {  
                setOpaque(true);  
        }  
        /*从这里到结束：实作getListCellRendererComponent()方法*/  
        public   Component   getListCellRendererComponent(JList   list,  
                                                        Object   value,  
                                                        int   index,  
                                                        boolean   isSelected,  
                                                        boolean   cellHasFocus)  
        {        
                /*我们判断list.getModel().getElementAt(index)所返回的值是否为null,例如上个例子中，若JList的标题是"你玩过哪  
                  *些数据库软件"，则index>=4的项目值我们全都设为null.而在这个例子中，因为不会有null值，因此有没有加上这个判  
                  *断并没有关系.  
                  */  
                if   (value   !=   null)  
                {  
                        setText(value.toString());  
                        //setIcon(new   ImageIcon(""+(index+1)+".jpg")); // 设置图片
                }  
                if   (isSelected)   {  
                        setBackground(list.getSelectionBackground());  
                        setForeground(list.getSelectionForeground());  
                }  
                else   {  
                        //设置选取与取消选取的前景与背景颜色.  
                        setBackground(list.getBackground());  
                        setForeground(list.getForeground());  
                }  
                return   this;  
        }          
}

