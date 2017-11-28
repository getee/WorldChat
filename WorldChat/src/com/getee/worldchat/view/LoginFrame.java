package com.getee.worldchat.view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import com.getee.worldchat.model.PictureBath;

public class LoginFrame extends JFrame {
    private JTextField textField;
    private JPasswordField passwordField;
    private JLabel label_1;
    private JButton button;
    private JButton button_1;
    
    public static void main(String[] args) {
        new LoginFrame();      
    }
    public LoginFrame(){
        setTitle("WorldChat");
        this.setIconImage(PictureBath.ICON.getImage());//设置图标
        this.setBounds(100, 100, 567, 465);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        getContentPane().setLayout(null);
        
        ImageIcon title = new ImageIcon("source/start.png");
        title.setImage(title.getImage().getScaledInstance(545,186,Image.SCALE_DEFAULT));
        JLabel lblNewLabel = new JLabel(title);
        lblNewLabel.setBounds(0, 0, 545, 186);
        //lblNewLabel.setVisible(true);
        getContentPane().add(lblNewLabel);
        
        textField = new JTextField();//账号框
        textField.setBounds(218, 201, 252, 37);
        getContentPane().add(textField);
        textField.setColumns(10);
        //textField.setVisible(true);
        
        passwordField = new JPasswordField();//密码框
        passwordField.setBounds(218, 265, 252, 37);
        getContentPane().add(passwordField);
        //passwordField.setVisible(true);
        
        JLabel lblNewLabel_1 = new JLabel("账号：");
        lblNewLabel_1.setBounds(159, 201, 54, 37);        
        getContentPane().add(lblNewLabel_1);
        
        JLabel label = new JLabel("密码：");
        label.setBounds(159, 265, 54, 37);
        getContentPane().add(label);
        
        ImageIcon head = new ImageIcon("source/qq.png");//头像图片
        head.setImage(head.getImage().getScaledInstance(134,145,Image.SCALE_DEFAULT));
        label_1 = new JLabel(head);
        label_1.setBounds(10, 201, 134, 145);
        getContentPane().add(label_1);
        
        button = new JButton("登陆");
        button.setBounds(218, 325, 101, 29);
        getContentPane().add(button);
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new FriendsFrame();
                
            }
            
        });
        
        button_1 = new JButton("注册");
        button_1.setBounds(369, 325, 101, 29);
        getContentPane().add(button_1);
              
    }
}
