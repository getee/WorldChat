package com.getee.worldchat.frame;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPasswordField;


public class RegistFrame extends JFrame{
    private JTextField textField;//用户名
    private JTextField textField_1;//昵称
    private JRadioButton menButton ;//男按钮
    private JRadioButton womenButton;//女按钮
    private JComboBox comboBox;//下拉头像
    private JCheckBox chckbxNewCheckBox;//已阅读条款
    private JButton button;//完成注册按钮
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    

    public static void main(String[] args) {
        new RegistFrame();
    }

    /**
     * Create the application.
     */
    public RegistFrame() {
        setTitle("注册账号");
        this.setBounds(100, 100, 739, 625);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("用户名");
        lblNewLabel.setBounds(254, 40, 56, 36);
        getContentPane().add(lblNewLabel);
        
        textField = new JTextField();
        textField.setBounds(325, 40, 231, 36);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("昵  称");
        lblNewLabel_1.setBounds(254, 91, 63, 43);
        getContentPane().add(lblNewLabel_1);
        
        JLabel label = new JLabel("密  码");
        label.setBounds(254, 149, 63, 36);
        getContentPane().add(label);
        
        JLabel label_1 = new JLabel("确认密码");
        label_1.setBounds(238, 206, 72, 36);
        getContentPane().add(label_1);
        
        
        textField_1 = new JTextField();
        textField_1.setBounds(325, 99, 231, 35);
        getContentPane().add(textField_1);
        textField_1.setColumns(10);
        
        JLabel label_5 = new JLabel("密码不一致");
        label_5.setVisible(false);
        label_5.setFont(new Font("SimSun", Font.PLAIN, 18));
        label_5.setForeground(Color.RED);
        label_5.setBounds(571, 214, 114, 28);
        getContentPane().add(label_5);
        
        JLabel label_2 = new JLabel("性  别");
        label_2.setBounds(254, 272, 63, 36);
        getContentPane().add(label_2);
        
        menButton = new JRadioButton("男",true);
        menButton.setBounds(325, 276, 63, 29);
        getContentPane().add(menButton);
        
        womenButton = new JRadioButton("女");
        womenButton.setBounds(395, 276, 63, 29);
        getContentPane().add(womenButton);
        
        ButtonGroup buGroup = new ButtonGroup();//使按键之间添加单选按钮组
        buGroup.add(menButton);
        buGroup.add(womenButton);
        

        comboBox = new JComboBox();//头像下拉列表
//        comboBox.setToolTipText("");
        comboBox.setBounds(325, 316, 231, 36);
        getContentPane().add(comboBox);
        
        JLabel lblNewLabel_2 = new JLabel("头  像");
        lblNewLabel_2.setBounds(254, 323, 56, 36);
        getContentPane().add(lblNewLabel_2);
        
        chckbxNewCheckBox = new JCheckBox("已阅读并同意相关条款");
        chckbxNewCheckBox.setBounds(254, 377, 241, 29);
        getContentPane().add(chckbxNewCheckBox);
        chckbxNewCheckBox.addActionListener(comboBox);
        
        button = new JButton("完成注册");
        button.setBounds(254, 417, 231, 55);
        getContentPane().add(button);
        button.addActionListener((ActionEvent e)->{
            if(!chckbxNewCheckBox.isSelected())
            {
                JOptionPane.showMessageDialog(RegistFrame.this, "用户名一旦注册无法更改!","相关条款",JOptionPane.INFORMATION_MESSAGE);
            }
        });
//        button.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(!chckbxNewCheckBox.isSelected())
//                {
//                    System.out.println("ddd");
//                    JOptionPane.showMessageDialog(RegistFrame.this, "用户名长度不够!","温馨提示",JOptionPane.ERROR_MESSAGE);
//                }
//            } 
//        });
        
        
        JLabel label_3 = new JLabel("头像");
        label_3.setBounds(15, 48, 208, 260);
        getContentPane().add(label_3);
        
        
        passwordField = new JPasswordField();
        passwordField.setBounds(325, 152, 231, 31);
        getContentPane().add(passwordField);
        
        passwordField_1 = new JPasswordField();//密码确认框
        passwordField_1.setBounds(325, 211, 231, 31);
        getContentPane().add(passwordField_1);
        passwordField_1.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
            }
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
            }
            @Override
            public void mouseExited(MouseEvent e) {
                String pass=String.valueOf(passwordField.getPassword());
                String passWord=String.valueOf(passwordField_1.getPassword());
                if(!pass.equals(passWord)){
                    button.setEnabled(false);
                    label_5.setVisible(true);
                }
                else
                {
                    button.setEnabled(true);
                    label_5.setVisible(false);
                }
                
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
        
        //JLabel label_4 = new JLabel("用户名已注册");//使用弹窗提醒
        
        
    }
    public void sendRegist()
    {
        
    }
}
