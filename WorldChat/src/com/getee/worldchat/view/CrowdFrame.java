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

import java.awt.Color;

import javax.swing.JList;

import com.getee.worldchat.model.PictureBath;

public class CrowdFrame extends JFrame {

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
    private JList list;


    public static void main(String[] args) {
        new CrowdFrame();
    }

    public CrowdFrame() {
        setTitle("与XX聊天ing");
        this.setIconImage(PictureBath.ICON.getImage());//设置图标
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        textPane.setText("对话框");
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
        textPane_1.setText("输入框");
        scrollPane_1.setViewportView(textPane_1);
        
        closeButton = new JButton("关闭");
        closeButton.setBackground(Color.GRAY);
        closeButton.setBounds(375, 586, 146, 34);
        contentPane.add(closeButton);
        
        sendButton = new JButton("发送");
        sendButton.setBackground(Color.GRAY);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        sendButton.setBounds(537, 587, 146, 32);
        contentPane.add(sendButton);
        
        JLabel lblNewLabel = new JLabel("广告区招租");
        lblNewLabel.setBounds(685, 0, 264, 170);
        contentPane.add(lblNewLabel);
        
        scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(685, 252, 264, 368);
        contentPane.add(scrollPane_2);
        
        list = new JList();
        scrollPane_2.setViewportView(list);
    }
}