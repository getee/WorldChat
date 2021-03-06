package com.getee.worldchat.model;

public interface MessHelp {
    public String IP="localhost";//"172.19.22.114"
    public int PORT=8090;
            
    /*
     * 提交给服务器的type号        
     */
    public int REGIST=0;//注册
    public int LOGIN=1;//登陆
    public int ONECHAT=2;//对一人聊天
    public int ALLCHAT=3;//群聊
    public int ADDFRIEND=4;//加好友
    public int SEARCH=5;//搜索好友
    public int SELECTGROUP=6;//选择群
    public int LOOKGROUP=7;//选择群
    public int ADDGROUP=8;//加群
    public int UPDATE=9;//更新信息
    
    /*
     * 服务器填写pack,客户机验证的if
     * 服务器返回客户机,的数据type号
     */
    public int REONEFROM=10;//返回客户机，用于更新==发送de个人==对话窗消息
    public int REONETO=11;//返回客户机，用于更新==接收de个人==对话窗消息
    public int REALLFROM=12;//返回客户机，用于更新群==发送==对话窗消息
    public int REALLTO=13;//返回客户机，用于更新群==接收==对话窗消息***每个人都是to的对象
    
    public int RESEARCH=14;//返回客户机，用于回复添加好友
    public int READDFRIEND=15;//返回客户机，用于回复添加好友
    
    public int REGROUP=16;//返回群信息

    public int ISTRUE=18;//返回客户机，纯粹用于客户端，确认操作是否成功
    public int ISFALSE=19;//返回客户机，纯粹用于客户端，提示操作失败
    
    public int RELOOKGROUP=20;//返回群信息
    public int READDGROUP=21;//返回群信息

}
