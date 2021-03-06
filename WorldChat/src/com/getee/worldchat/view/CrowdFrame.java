package com.getee.worldchat.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JList;

import com.getee.worldchat.control.DBGroup;
import com.getee.worldchat.control.DBOperation;
import com.getee.worldchat.control.PackMessage;
import com.getee.worldchat.model.GroupLevel;
import com.getee.worldchat.model.MessHelp;
import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.PictureBath;
import com.getee.worldchat.model.User;

public class CrowdFrame extends JFrame {

    private JFrame friendsFrame;

    private JPanel contentPane;
    private JTextPane textPane;//对话框
    private JTextPane textPane_1;//输入框
    private JButton fontButton;
    private JButton emojiButton;
    private JButton quakeButton;
    private JButton closeButton;    
    private JButton sendButton;
    private JButton shotButton;
    private JButton voiceButton;
    private JScrollPane scrollPane_2;
    private JList list;//群列表
    
    private User myself;//自身的对象
    private User chatGroup;//聊天的对象

    private ObjectOutputStream out;
    private ObjectInputStream in;


    public static void main(String[] args) {
        new CrowdFrame(DBOperation.select("1"),DBGroup.select("666"));
    }
    
    public CrowdFrame(ObjectOutputStream out,ObjectInputStream in,FriendsFrame friendsFrame,User myself,User chatGroup) {
        this(myself,chatGroup);
        this.in=in;
        this.out=out;
        this.friendsFrame=friendsFrame;
        list.addMouseListener(new MouseAdapter() {
            /*
             * 在群中进行单个聊天
             * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
        private Map<String,OneChatFrame>  allFraes=new HashMap<>();//群中个人聊天记录
            public void mouseClicked(MouseEvent e) {
                User user=myself;//好友更新后需要再更新
                if(user.getFriends().size()==0) return ;
                if(e.getButton()==1&&e.getClickCount()==2) {
                    User uTo=(User) list.getSelectedValue();
                    String uID=uTo.getIdNum();
                        if(friendsFrame.getAllFrames().get(uID)!=null)
                        {
                            friendsFrame.getAllFrames().get(uID).setVisible(true);
                            return;
                        }
                    OneChatFrame   chat=new OneChatFrame(out,in,friendsFrame,user,uTo);
                    //OneChatFrame   chat=new OneChatFrame(FriendsFrame.this.user,chatUser);
                    chat.setVisible(true);//让聊天界面显示出来
                    friendsFrame.getAllFrames().put(uID, chat);
                    
                    
                }
            }
        });
        
    }

    /**
     * @wbp.parser.constructor
     */
    public CrowdFrame(User myself,User chatGroup) {
        this.myself=myself;
        this.chatGroup=chatGroup;
        setTitle(chatGroup.getNiname());
        this.setIconImage(PictureBath.ICON.getImage());//设置图标
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 971, 691);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        this.setVisible(true);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 683, 330);
        contentPane.add(scrollPane);
        
        textPane = new JTextPane();
        textPane.setText("");
        scrollPane.setViewportView(textPane);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 329, 683, 56);
        contentPane.add(panel);
        panel.setLayout(null);
        
        ImageIcon one = new ImageIcon("source/icon/a.png");//字体图标
        one.setImage(one.getImage().getScaledInstance(64,55,Image.SCALE_DEFAULT));
        fontButton = new JButton(one);
        fontButton.setBounds(0, 0, 64, 55);
        panel.add(fontButton);
        
        ImageIcon two = new ImageIcon("source/icon/b.png");//表情图标
        two.setImage(two.getImage().getScaledInstance(64,55,Image.SCALE_DEFAULT));
        emojiButton = new JButton(two);
        emojiButton.setBounds(62, 0, 64, 55);
        panel.add(emojiButton);
        
        ImageIcon three = new ImageIcon("source/icon/f.png");//上传图标
        three.setImage(three.getImage().getScaledInstance(64,55,Image.SCALE_DEFAULT));
        quakeButton = new JButton(three);
        quakeButton.setBounds(124, 0, 64, 55);
        panel.add(quakeButton);
        
        ImageIcon four = new ImageIcon("source/icon/d.png");//语音图标
        four.setImage(four.getImage().getScaledInstance(64,55,Image.SCALE_DEFAULT));
        voiceButton = new JButton(four);
        voiceButton.setBounds(185, 0, 64, 55);
        panel.add(voiceButton);
        
        ImageIcon five = new ImageIcon("source/icon/e.png");//截图图标
        five.setImage(five.getImage().getScaledInstance(64,55,Image.SCALE_DEFAULT));
        shotButton = new JButton(five);
        shotButton.setBounds(247, 0, 64, 55);
        panel.add(shotButton);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(0, 384, 683, 196);
        contentPane.add(scrollPane_1);
        
        textPane_1 = new JTextPane();
        textPane_1.setText("");
        scrollPane_1.setViewportView(textPane_1);
        
        closeButton = new JButton("关闭");
        closeButton.setBackground(Color.GRAY);
        closeButton.setBounds(375, 586, 146, 34);
        contentPane.add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CrowdFrame.this.setVisible(false);
            }
        });
        
        sendButton = new JButton("发送");
        sendButton.setBackground(Color.GRAY);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();//发送消息
            }
        });
        sendButton.setBounds(537, 587, 146, 32);
        contentPane.add(sendButton);
        
        
        ImageIcon head = new ImageIcon(chatGroup.getPhoto());//对方头像图片
        head.setImage(head.getImage().getScaledInstance(264,170,Image.SCALE_DEFAULT));
        JLabel lblNewLabel = new JLabel(head);
        lblNewLabel.setBounds(685, 0, 264, 170);
        contentPane.add(lblNewLabel);
        
        scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(685, 252, 264, 368);
        contentPane.add(scrollPane_2);
        System.out.println(chatGroup.getFriends());
        Set<User> suu=chatGroup.getFriends().get(GroupLevel.NOMAL);
        
        System.out.println(suu);
        User[] strGroup=suu.toArray(new User[]{});//Set2Array  遍历所有群的好友
        list = new JList<User>(strGroup);
        list.setForeground(Color.ORANGE);
        list.setFont(new Font("华文楷体", Font.PLAIN, 24));

        scrollPane_2.setViewportView(list);
    }
    private void sendMessage(){
        String str=textPane_1.getText();//***获取输入框中值，进行分装***
        
        MessageBox m=PackMessage.packAllChat(myself,chatGroup,str);
        try {
            out.writeObject(m);
            out.flush();
            textPane_1.setText("");//输入框清空
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        textPane.setText(textPane.getText()+"\n"+str); 
    }

    public JTextPane getTextPane() {
        return textPane;
    }
    
    
}
