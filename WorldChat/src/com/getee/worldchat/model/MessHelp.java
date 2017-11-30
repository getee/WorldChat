package com.getee.worldchat.model;

public interface MessHelp {
    public String IP="localhost";//"172.19.22.114"
    public int PORT=8090;
            
            
    public int REGIST=0;//注册
    public int LOGIN=1;//登陆
    public int ONECHAT=2;//对一人聊天
    public int ALLCHAT=3;//群聊
    public int ADDFRIEND=4;//加好友
    public int SEARCH=5;//搜索好友
    public int UPDATE=6;//更新信息
    
    
    public int ISTRUE=8;//返回客户机，用于客户端确认操作是否成功
    public int ISFALSE=9;//返回客户机，提示操作失败

}
