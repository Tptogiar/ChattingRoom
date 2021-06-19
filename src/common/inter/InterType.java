package common.inter;

/**
 * @author Tptogiar
 * @Descripiton: 定义枚举类，用来表示客户端与服务端交互的类型，
 * 好让客户端和服务端识别并做相应的处理
 * @creat 2021/05/16-11:31
 */


public enum InterType {

    //请求，用来传递Contain的类型
    REQUEST("请求"),
    //相应，用来传递Contain的类型
    RESPONSE("响应"),



    //登录
    LOGIN("登录"),
    LOGIN_COMPLETE("客户端登录完成"),
    //注册
    REGISTER("注册"),
    CORRECT_INFO("修改用户信息"),

    //查询联系人信息
    ENQUIRY_CONTACTS("查询联系人"),
    //查询在线用户
    UPDATE_ONLINE_USERS("查询在线用户"),
    //查询用户群聊信息
    ENQUIRY_GROUPCHATS("查询用户群聊信息"),


    MESSAGE_TEXT("文本消息"),
    MESSAGE_FILE("文件传输"),




    MESSAGE_TEXT_ONLINE("发送给所有在线用户"),
    CHATTING_RECORDS("聊天记录"),
    POPUP_TIP("弹窗提醒"),
    FORCE_OFFLINE("抱歉，您已被强制下线"),
    FORCE_OFFLINE_DELETE("抱歉，您的账户已被系统删除"),
    FORCE_OFFLINE_TO_BLACKLIST("实在抱歉，您已被拉入黑名单"),


    SERVER_ALLOW_CONNECT("服务器允许连接"),
    SERVER_ONLINES_BUSY("当前在线用户较多，暂不予登录，请您骚后再试"),
    SERVER_TASKS_BUSY("服务器繁忙，请您骚后再试");



    private String typeName;

    private InterType(String typeName){
        this.typeName=typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
