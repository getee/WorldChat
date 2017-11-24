package com.getee.worldchat.frame;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JButton;

public class FriendsFrame extends JFrame{
    private JTextField textField;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JButton btnNewButton_2;
    private JTree tree;
    
    public static void main(String[] args) {
        new FriendsFrame();
    }
    public FriendsFrame() {      
        this.setBounds(100, 100, 354, 739);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        getContentPane().setLayout(null);
        
        ImageIcon head = new ImageIcon("source/qq.png");//头像图片
        head.setImage(head.getImage().getScaledInstance(107,88,Image.SCALE_DEFAULT));
        JLabel lblNewLabel = new JLabel(head);
        lblNewLabel.setBounds(15, 15, 107, 88);
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("用户名");
        lblNewLabel_1.setBounds(137, 15, 160, 35);
        getContentPane().add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setBounds(137, 69, 160, 35);
        textField.setForeground(Color.LIGHT_GRAY);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 118, 332, 35);
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
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 150, 332, 533);
        getContentPane().add(scrollPane);
        
        
        DefaultMutableTreeNode  crowdRoot=new DefaultMutableTreeNode("我的群");
        tree = new JTree();
        scrollPane.setViewportView(tree);
    }
}
