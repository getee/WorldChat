package com.getee.worldchat.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import com.getee.worldchat.control.PackMessage;
import com.getee.worldchat.model.MessHelp;
import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.PictureBath;
import com.getee.worldchat.model.User;




public class LoginFrame extends JFrame {
    private AllButtonListener  listener;//内部类监听对象

    private JTextField textField;
    private JPasswordField passwordField;
    private JLabel label_1;
    private JButton button;
    private JButton button_1;
    private Socket link;//持有链接
    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    {
        listener=new AllButtonListener();
    }
    public static void main(String[] args) {
        //JFrame.setDefaultLookAndFeelDecorated(true);   
        SwingUtilities.invokeLater(new Runnable() {   
            public void run() {   
                new LoginFrame(); 
                //要使窗口透明，您可以使用 AWTUtilities.setWindowOpacity(Window, float) 方法   
                //AWTUtilities.setWindowOpacity(this, 0.9f);   
            }   
        }); 
       // new LoginFrame();      
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
//        PackMessage pm= new PackMessage(){
//                public MessageBox packLogin(String idNum,String password,int messType){return null;}//登录消息
//        };
        button = new JButton("登陆");
        button.setBounds(218, 325, 101, 29);
        getContentPane().add(button);
        button.addActionListener(listener);
        
        button_1 = new JButton("注册");
        button_1.setBounds(369, 325, 101, 29);
        getContentPane().add(button_1);
        button_1.addActionListener(listener);
    }
    public JTextField getTextField() {//用于自动填充账户名
        return textField;
    }
    public void setTextField(JTextField textField) {
        this.textField = textField;
    }
    class AllButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){

              try {
                  link=new Socket(MessHelp.IP,MessHelp.PORT);//链接
                  out=new ObjectOutputStream(link.getOutputStream());
                  in=new ObjectInputStream(link.getInputStream());
              } catch (UnknownHostException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
              } catch (IOException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
              }

            if(e.getSource()==button){
                User user = null;//等服务器流验证
                try {
                    String idNum=textField.getText().trim();
                    String password=String.valueOf(passwordField.getPassword());
                    MessageBox writeMessage=PackMessage.packLogin(idNum, password);
                    out.writeObject(writeMessage);//将登录数据传递到服务器
                    out.flush();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                try {
                    MessageBox readMessage=(MessageBox)in.readObject();//从服务器读取的 包
                    int type=readMessage.getType();
                    if(type==MessHelp.ISFALSE)
                    {
                        JOptionPane.showMessageDialog(LoginFrame.this, "用户名或密码错误!","温馨提示",JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        user=readMessage.getTo();
                        new FriendsFrame(out,in,user);
                        LoginFrame.this.setVisible(false);
                    }
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
            else if(e.getSource()==button_1)
            {
                new RegistFrame(out,in,LoginFrame.this);
                LoginFrame.this.setVisible(false); 
            }
        }
    }
}
