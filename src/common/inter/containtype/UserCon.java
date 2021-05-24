package common.inter.containtype;

import common.TipType;
import common.inter.Contain;
import common.inter.InterType;
import server.model.entity.User;

import java.io.Serializable;

/**
 * @author Tptogiar
 * @Descripiton: 封装客户端给服务端发送的用户相关的请求内容
 * 以及服务端的响应内容
 * @creat 2021/05/15-22:02
 */


public class UserCon implements Contain, Serializable {

    private InterType interType;

    private User user;
    private TipType tipType;

    /**
     * @Author: Tptogiar
     * @Description: 登录时，用来传输用户名和密码
     * @Date: 2021/5/17-23:48
     */
    public UserCon(String userName, String password) {
        User user = new User(userName, password);
        this.user=user;
    }

    /**
     * @Author: Tptogiar
     * @Description: 登录成功时，用来传输登录结果和登录的用户
     * @Date: 2021/5/17-23:48
     */
    public UserCon(TipType loginResult, User user){
        this.tipType=loginResult;
        this.user=user;
    }

    public UserCon(int userID,String userName, String password,String newPassword) {
        User user = new User(userID,userName, password,newPassword);
        this.user=user;
    }


    /**
     * @Author: Tptogiar
     * @Description: 注册成功时，用来传输注册结果
     * @Date: 2021/5/17-23:49
     */
    public UserCon(TipType registerResult) {
        this.tipType=registerResult;
    }

    public UserCon(int userID) {
        User user = new User(userID);
        this.user=user;
    }

    public User getUser() {
        return user;
    }


    public TipType getTipType() {
        return tipType;
    }



}
