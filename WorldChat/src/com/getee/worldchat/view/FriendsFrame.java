package com.getee.worldchat.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JButton;

import com.getee.worldchat.control.DBOperation;
import com.getee.worldchat.model.PictureBath;
import com.getee.worldchat.model.User;



import javax.swing.JTabbedPane;


public class FriendsFrame extends JFrame{
  //为了能记录所有和我聊过天的好友信息(打开过两天界面的好友信息)，我们定义一个集合存储这些资料
    private Map<String,OneChatFrame>  allFrames=new HashMap<>();
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
        User admin =DBOperation.select("266");
        new FriendsFrame(admin);
    }
    public FriendsFrame(ObjectOutputStream out,ObjectInputStream in,User user) {
        this(user);
        this.in=in;
        this.out=out;
        
    }
    /*
     * 进行好友列表显示
     */
    public FriendsFrame(User user) {
        this();
        //System.out.println("===============");
        this.user=user;
        
        
        DefaultMutableTreeNode  root=new DefaultMutableTreeNode("root");
        //定义一个jtree根节点，所有的好友分组和好友都在这个根节点上往上放
        System.out.println(user);
        Map<String, HashSet<User>>  allFriends=user.getFriends();
         
        Set<String>  allGroupNames=allFriends.keySet();//获取所有的分组名
        
        for(String groupName:allGroupNames) {
            DefaultMutableTreeNode  group=new DefaultMutableTreeNode(groupName);//构造出每个组名的对应的TreeNode对象
            HashSet<User>  friendsOfGroup=allFriends.get(groupName);
            for(User u:friendsOfGroup) {
                DefaultMutableTreeNode  friend=new DefaultMutableTreeNode(u.getNiname());
                group.add(friend);
            }
            
            root.add(group);
        }

        tree = new JTree(root);
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==1&&e.getClickCount()==2) {
                    TreePath  path=tree.getSelectionPath();
                    DefaultMutableTreeNode lastNode=(DefaultMutableTreeNode)path.getLastPathComponent();
                    if(lastNode.isLeaf()) {
                        //上面是解析用户双击之后判断是不是双击的某一个用户名上的这个Node
                        String username=lastNode.toString();
                        String num=username.substring(username.lastIndexOf("[")+1,username.length()-1);
                        //
                        User your=new User();
                        your.setNiname(num);
                        for(String firendNum:allFrames.keySet())
                        {
                            if(firendNum.equals(num))
                            {
                                allFrames.get(firendNum).setVisible(true);
                                return;
                            }
                        }
//                        OneChatFrame   chat=new OneChatFrame(FriendsFrame.this.user,your,FriendsFrame.this.in,FriendsFrame.this.out);
//                        chat.setVisible(true);//让聊天界面显示出来
//                        allFrames.put(num, chat);
                    }
                }
            }
        });
        tree.setRootVisible(false);
        scrollPane.setViewportView(tree);
    }

    public FriendsFrame() {
        this.setIconImage(PictureBath.ICON.getImage());//设置图标
        this.setBounds(100, 100, 354, 739);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        ImageIcon head = new ImageIcon("source/qq.png");//头像图片
        head.setImage(head.getImage().getScaledInstance(107,88,Image.SCALE_DEFAULT));
        getContentPane().setLayout(null);
        JLabel lblNewLabel = new JLabel(head);
        lblNewLabel.setBounds(15, 15, 107, 88);
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("用户名");
        lblNewLabel_1.setBounds(137, 15, 160, 35);
        getContentPane().add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(137, 69, 160, 35);
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setForeground(Color.LIGHT_GRAY);
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
        
        
        
    }
}
