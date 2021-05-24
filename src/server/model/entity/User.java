package server.model.entity;

import java.io.Serializable;

/**
 * @author Tptogiar
 * @Descripiton: 用户类
 * @creat 2021/05/15-19:37
 */


public class User implements Serializable {

    private String userName;
    private String userPassword;
    private int userID;
    private boolean isOnline;
    private String description;
    private boolean isBlock;
    private String newPassword;



    public User() {
    }

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User(int userID) {
        this.userID = userID;
    }

    public User(int userID, String userName, String password,String newPassword) {
        this.userID=userID;
        this.userName=userName;
        this.userPassword=password;
        this.newPassword=newPassword;
    }

    public String getUserName() {
        return userName;
    }


    public User(int userID,String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    /**
     * @Author Tptogiar
     * @Description 这里只是客户端传输服务端User，服务端验证User后获取
     * 传过来的密码用的，
     * 在其他时候服务端传回给客户端的User是不包含密码的，只有userID和userName
     * @Date 2021/5/19-21:13
     */
    public String getUserPassword() {
        return userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getDescription() {
        return description;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public String getNewPassword() {
        return newPassword;
    }


    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
