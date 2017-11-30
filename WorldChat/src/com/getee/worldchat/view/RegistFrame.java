package com.getee.worldchat.view;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JPasswordField;

import com.getee.worldchat.control.PackMessage;
import com.getee.worldchat.model.MessHelp;
import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.PictureBath;
import com.getee.worldchat.model.User;


public class RegistFrame extends JFrame{
    private JTextField textField;//用户名*
    private JTextField textField_1;//昵称*
    private JRadioButton menButton ;//男按钮*
    private JRadioButton womenButton;//女按钮
    private JComboBox comboBox;//下拉头像
    private JCheckBox chckbxNewCheckBox;//已阅读条款
    private JButton button;//完成注册按钮
    private JPasswordField passwordField;//密码*
    private JPasswordField passwordField_1;
    private ImageIcon headImg;//头像图片
    private String headImgBath;//头像图片路径*
    private String sex;//性别*
    
    private LoginFrame login;
    
    private ObjectOutputStream out;
    private ObjectInputStream in;//链接*
    //private ObjectReaderStream ors;
    

    public static void main(String[] args) {
        new RegistFrame();
    }
    public RegistFrame(ObjectOutputStream out,ObjectInputStream in,LoginFrame login) {
        this();
        this.in=in;
        this.out=out;
        this.login=login;
        this.sex="男";
    }
    /**
     * Create the application.
     */
    public RegistFrame() {
        setTitle("注册账号");
        this.setIconImage(PictureBath.ICON.getImage());//设置图标
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
        menButton.addActionListener((ActionEvent e)->{
            sex="男";
        });
        
        womenButton = new JRadioButton("女");
        womenButton.setBounds(395, 276, 63, 29);
        getContentPane().add(womenButton);
        womenButton.addActionListener((ActionEvent e)->{
            sex="女";
        });
        
        ButtonGroup buGroup = new ButtonGroup();//使按键之间添加单选按钮组
        buGroup.add(menButton);
        buGroup.add(womenButton);
        
        headImg = new ImageIcon("source/头像1.jpg");//大头像处
        headImgBath="source/头像1.jpg";
        headImg.setImage(headImg.getImage().getScaledInstance(208,260,Image.SCALE_DEFAULT));
        JLabel label_3 = new JLabel(headImg);
        label_3.setBounds(15, 48, 208, 260);
        getContentPane().add(label_3);

        comboBox = new JComboBox(PictureBath.PHOTO);//头像下拉列表,以及添加头像名
//        comboBox.setToolTipText("");
        comboBox.setBounds(325, 316, 231, 36);
        getContentPane().add(comboBox);

        comboBox.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED)
                {
                    String str=(String)comboBox.getSelectedItem();
                    headImgBath="source/"+str+".jpg";
                    headImg = new ImageIcon(headImgBath);
                    label_3.setIcon(headImg);
                    
                }
                
            }

        });
        
       
        JLabel lblNewLabel_2 = new JLabel("头像");
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
            else
            {
                sendRegist();
            }
        });

        passwordField = new JPasswordField();
        passwordField.setBounds(325, 152, 231, 31);
        getContentPane().add(passwordField);
        
        passwordField_1 = new JPasswordField();//密码确认框
        passwordField_1.setBounds(325, 211, 231, 31);
        getContentPane().add(passwordField_1);
        passwordField_1.addMouseListener(new MouseAdapter() {
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
        });
        
        //JLabel label_4 = new JLabel("用户名已注册");//使用弹窗提醒

    }
    
    public void sendRegist()//把注册窗口显示出来，再自动填装用户名
    {
        User user=new User();
        user.setIdNum(textField.getText().trim());
        user.setNiname(textField_1.getText().trim());
        user.setPassword(String.valueOf(passwordField.getPassword()));
        user.setSex(sex);
        user.setPhoto(headImgBath);
        user.setSpeakword("贫僧是来画圆的。");
        //if(user.getIdNum().equals("")) 账户非法
        try {
            MessageBox writeMessage=PackMessage.packRegist(user);
            out.writeObject(writeMessage);
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            MessageBox readMessage=(MessageBox)in.readObject();
            int type=readMessage.getType();
            if(type==MessHelp.ISFALSE)
            {
                JOptionPane.showMessageDialog(this, "用户名已存在!","温馨提示",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                this.setVisible(false);
                this.login.setVisible(true);
                login.getTextField().setText(user.getIdNum());
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //String idNum=textField.getText().trim();
        //System.out.println(user);
    }
}
