package com.getee.worldchat.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JLabel;

import com.getee.worldchat.model.MessHelp;
import com.getee.worldchat.model.MessageBox;
import com.getee.worldchat.model.User;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class AddGroupFrame extends JFrame {

    private JPanel contentPane;
    private JPanel panel ;//查找面板
    private JTextField searchField;
    private JLabel lblNewLabel;//头像
    private JTextPane txtpnid;//昵称+id
    private JButton btnNewButton;//+
    private JButton btnNewButton_1;//不加
    private FriendsFrame ff;//自己的信息
    
    
    private User user;//自己的信息
    private User addUser;//对方的信息

    
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AddGroupFrame();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public AddGroupFrame(ObjectOutputStream out,ObjectInputStream in,User user,FriendsFrame ff) {
        this();
        this.out=out;
        this.in=in;
        this.user=user;
        this.ff=ff;
    }

    /**
     * Create the frame.
     */
    public AddGroupFrame() {
        this.setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 592, 374);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        searchField = new JTextField();
        searchField.setText("请输入你要添加的账号id...");
        searchField.setBounds(118, 25, 267, 43);
        contentPane.add(searchField);
        searchField.setColumns(10);
        searchField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                searchField.setText("");
            }

        });

        JButton button = new JButton("查找");
        button.setBounds(400, 25, 107, 43);
        contentPane.add(button);
        button.addActionListener((ActionEvent e)->{
            searchFriend(searchField.getText().trim());//进行搜索
                //JOptionPane.showMessageDialog(AddFriendFrame.this, "用户名不存在!","温馨提示",JOptionPane.ERROR_MESSAGE);

        });
        
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(15, 99, 540, 204);
        contentPane.add(panel);
        panel.setVisible(false);
        panel.setLayout(null);
        
        lblNewLabel = new JLabel("头像");
        lblNewLabel.setBounds(51, 15, 160, 174);
        //lblNewLabel.setVisible(false);
        panel.add(lblNewLabel);
        
        btnNewButton = new JButton("+群");
        btnNewButton.setBounds(241, 127, 114, 62);
        //btnNewButton.setVisible(false);
        panel.add(btnNewButton);
        btnNewButton.addActionListener((ActionEvent e)->{
            //String groupName=JOptionPane.showInputDialog(AddGroupFrame.this,"请输入分组名:\n","title",JOptionPane.PLAIN_MESSAGE);
            sendAdd();
            
        });
        
        btnNewButton_1 = new JButton("不加了");
        btnNewButton_1.setBounds(370, 127, 101, 62);
        //btnNewButton_1.setVisible(false);
        btnNewButton_1.addActionListener((ActionEvent e)->{
               AddGroupFrame.this.setVisible(false);
        });
        panel.add(btnNewButton_1);
        
        
        txtpnid = new JTextPane();
        txtpnid.setText("昵称+id");
        txtpnid.setBounds(241, 15, 229, 105);
        panel.add(txtpnid);
    }
    public void sendAdd(){

            MessageBox m=new MessageBox();
            m.setFrom(user);
            m.setTo(addUser);
            m.setType(MessHelp.ADDGROUP);
            try {
                out.writeObject(m);
                out.flush();
              } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
    }
    public void searchFriend(String str){
        MessageBox m=new MessageBox();
        m.setContent(str);
        m.setType(MessHelp.LOOKGROUP);
        m.setFrom(user);
        try {
            out.writeObject(m);
            out.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
    }
    
    public void setAddUser(User addUser) {
        this.addUser = addUser;
    }


    public JTextPane getTxtpnid() {
        return txtpnid;
    }


    public JPanel getPanel() {
        return panel;
    }


    public JLabel getLblNewLabel() {
        return lblNewLabel;
    }

}
