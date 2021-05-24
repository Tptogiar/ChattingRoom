package common;

/**
 * @author Tptogiar
 * @Descripiton: 用来描述服务端给客户端的提示类型
 * @creat 2021/05/18-19:21
 */


public enum  TipType {

    //
    LOGIN_SUCCEED("登录成功"),
    LOGIN_USERNAME_NOT_EXIST("用户名不存在"),
    LOGIN_WRONG_USERNAME_PASSWORD("用户名或密码错误"),
    LOGIN_WRONG_UNKNOW("登录错误（未知错误）"),
    LOGIN_WRONG_HAD_LOGIN("该用户已登录"),
    LOGIN_WORONG_ISBLOCK("抱歉，您已被系统加入黑名单"),
    //
    //
    REGISTER_SUCCEED("注册成功"),
    REGISTER_USERNAME_EXIST("该用户名已经存在"),
    REGISTER_WRONG_UNKNOW("登录错误（未知错误）"),


    CORRECT_USER_INFO_USERNAME_EXIST("该用户名已经存在"),
    CORRECT_USER_INFO_SUCCEED("修改成功"),
    CORRECT_USER_INFO_PASSWORD_WORING("原始密码不正确");





    private String tipText;
    private TipType(String tipText){
        this.tipText=tipText;
    }


    public String getTipText() {
        return tipText;
    }
}
