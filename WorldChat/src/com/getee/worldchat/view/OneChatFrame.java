package com.getee.worldchat.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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

import javax.swing.JLabel;

import com.getee.worldchat.control.DBOperation;
import com.getee.worldchat.control.PackMessage;
import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.PictureBath;
import com.getee.worldchat.model.User;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OneChatFrame extends JFrame {
    private JFrame FriendsFrame;

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
    
    private User myself;//自身的对象
    private User chatUser;//聊天的对象

    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    /*
     * 对话框由服务外界 reader进行控制
     */
    public JTextPane getTextPane() {
        return textPane;
    }
    public void setTextPane(JTextPane textPane) {
        this.textPane = textPane;
    }

    public static void main(String[] args) {
        new OneChatFrame(DBOperation.select("3"),DBOperation.select("2"));
    }
    
    public OneChatFrame(ObjectOutputStream out,ObjectInputStream in,JFrame FriendsFrame,User myself,User chatUser) {
        this(myself,chatUser);
        this.in=in;
        this.out=out;
        this.FriendsFrame=FriendsFrame;
    }
    public OneChatFrame(User myself,User chatUser) {
        this.myself=myself;
        this.chatUser=chatUser;
        //this.FriendsFrame=FriendsFrame;
        setTitle("与"+chatUser.getNiname()+"聊天ing");
        this.setIconImage(PictureBath.ICON.getImage());//设置图标
        setBounds(100, 100, 971, 691);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        this.setVisible(true);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 774, 330);
        contentPane.add(scrollPane);
        
        textPane = new JTextPane();
        //textPane.setText();//"对话框"
        scrollPane.setViewportView(textPane);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 329, 949, 56);
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
        
        ImageIcon three = new ImageIcon("source/icon/c.png");//抖动图标
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
        scrollPane_1.setBounds(0, 384, 774, 196);
        contentPane.add(scrollPane_1);
        
        textPane_1 = new JTextPane();
        //textPane_1.setText("输入框");
        scrollPane_1.setViewportView(textPane_1);
        
        closeButton = new JButton("关闭");
        closeButton.setBackground(Color.GRAY);
        closeButton.setBounds(466, 589, 146, 34);
        contentPane.add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OneChatFrame.this.setVisible(false);
            }
        });
        
        sendButton = new JButton("发送");
        sendButton.setBackground(Color.GRAY);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();//发送消息
            }
        });
        sendButton.setBounds(628, 590, 146, 32);
        contentPane.add(sendButton);
        
        ImageIcon head = new ImageIcon(chatUser.getPhoto());//对方头像图片
        head.setImage(head.getImage().getScaledInstance(175,330,Image.SCALE_DEFAULT));
        JLabel lblNewLabel = new JLabel(head);
        lblNewLabel.setBounds(774, 0, 175, 330);
        contentPane.add(lblNewLabel);
        
        ImageIcon myhead = new ImageIcon(myself.getPhoto());//自己头像图片
        myhead.setImage(myhead.getImage().getScaledInstance(175,239,Image.SCALE_DEFAULT));
        JLabel lblNewLabel_1 = new JLabel(myhead);
        lblNewLabel_1.setBounds(774, 384, 175, 239);
        contentPane.add(lblNewLabel_1);
        
    }
    private void sendMessage(){
        /*
         * textPane_1.getText();
         * 变更为单消息对象的追加可修复，多线程传递时旧数据保存缺失
         */
        String str=textPane_1.getText();//***获取输入框中值，进行分装***
        
        MessageBox m=PackMessage.packOneChat(myself,chatUser,str);
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


}
