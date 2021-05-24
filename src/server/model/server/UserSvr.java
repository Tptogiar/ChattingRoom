package server.model.server;

import common.TipType;
import server.dao.UserDAO;
import server.dao.impl.UserImpl;
import server.model.entity.User;
import server.model.pool.MyDataSource;
import java.sql.Connection;
import java.util.List;

/**
 * @author Tptogiar
 * @Descripiton: 管理当前的注册的用户
 * @creat 2021/05/15-19:36
 */


public class UserSvr {


    /**
     * @Author: Tptogiar
     * @Description: 考虑到后面使用数据连接池后
     * connection都是从数据库动态获取的，所以这里的connection
     * 没有写成静态的
     * 为了避免每次都手写一个new，所以将new的过程封装在getIntance()里面
     * @Date: 2021/05/16-14:03
     */
    private static UserDAO userImpl =new UserImpl();
    Connection connection=MyDataSource.getConnection();

//    private static UserSvr userSvr=new UserSvr();

    private UserSvr(){
    }

    public static UserSvr getInstance(){
        return new UserSvr();
    }

    /**
     * @Author: Tptogiar
     * @Description: 登录请先检查用户名是否存在
     * @Date: 2021/5/18-23:27
     */
    public TipType checkLogin(User user){
        /*判断用户名是否存在*/
        long countByUserName = userImpl.countByUserName(connection, user);
        if(countByUserName ==0){
            return TipType.LOGIN_USERNAME_NOT_EXIST;
        }

        /*判断用户名密码是否正确*/
        long countByUserNameAndPassword = userImpl.login(connection, user);
        if(countByUserNameAndPassword==0){
            return TipType.LOGIN_WRONG_USERNAME_PASSWORD;
        }
        //判断是否被加入黑名单
        boolean isBlock = isBlock(user);
        if(isBlock){
            return TipType.LOGIN_WORONG_ISBLOCK;
        }

        //在把user传回去之前，告知客户端当前登录的用户的userID
        int userID = getUserID(user);
        user.setUserID(userID);
        boolean isOnline = isOnline(user);

        /*判断该用户是否已经在线*/
        if(isOnline){
            return TipType.LOGIN_WRONG_HAD_LOGIN;
        }

        /*标记当前用户为在线状态*/
        int markAsOnline = markAsOnline(user);

        return TipType.LOGIN_SUCCEED;
    }


    /**
     * @Author Tptogiar
     * @Description 查询在线用户（给客户端使用）
     * @Date 2021/5/23-16:29
     */
    public List enquiryOnlines() {
        return userImpl.enquiryOnlines(connection);
    }


    /**
     * @Author: Tptogiar
     * @Description: 注册新的用户
     * @Date: 2021/5/18-0:08
     * @return
     */
    public TipType addUser(User user){
        long countByUserName = userImpl.countByUserName(connection, user);
        if (countByUserName>=1){
            return TipType.REGISTER_USERNAME_EXIST;
        }
        int updata = userImpl.insertUser(connection, user);
        if (updata==1){
            return TipType.REGISTER_SUCCEED;
        }
        return TipType.REGISTER_WRONG_UNKNOW;
    }


    /**
     * @Author: Tptogiar
     * @Description: 将对应用户标记为上线状态
     * 只在上面登录方法里面被调用，所以设置成private的
     * @Date: 2021/5/19-13:38
     */
    private int markAsOnline(User user){
        int update = userImpl.markOnline(connection, user);
        return update;
    }


    /**
     * @Author: Tptogiar
     * @Description: 将对应用户标记为离线状态
     * @Date: 2021/5/19-13:39
     */
    public int markAsOffOnline(User user){
       int update= userImpl.markAsOffOnline(connection,user);
       return update;
    }


    public int setAllOffline(){
        int update = userImpl.setAllOffline(connection);
        return update;
    }

    /**
     * @Author Tptogiar
     * @Description 判断指定用户是否在线
     * @Date 2021/5/20-9:17
     */
    public boolean isOnline(User user){
        return userImpl.isOnline(connection, user);
    }

    /**
     * @Author Tptogiar
     * @Description 获取指定用户的ID
     * @Date 2021/5/20-9:26
     */
    public int getUserID(User user){
        return userImpl.getUserID(connection,user);
    }

    /**
     * @Author Tptogiar
     * @Description 查询所有用户信息(给服务端使用）
     * @Date 2021/5/23-15:22
     */
    public List<User> enquiryAllusers(){
        return userImpl.enquiryAllUsers(connection);
    }

    /**
     * @Author Tptogiar
     * @Description 删除指定用户
     * @Date 2021/5/23-15:22
     */
    public int deleteUser(User user){
        return userImpl.deleteUser(connection,user);
    }

    /**
     * @Author Tptogiar
     * @Description 拉入黑名单
     * @Date 2021/5/23-15:38
     */
    public int addUserToBlackList(User user){
        return userImpl.addUserToBlackList(connection,user);
    }

    /**
     * @Author Tptogiar
     * @Description 从黑名单中移除
     * @Date 2021/5/23-15:39
     */
    public int unlockInBlackList(User user){
        return userImpl.unlockInBlackList(connection,user);
    }


    /**
     * @Author Tptogiar
     * @Description 查询指定用户是否在黑名单
     * @Date 2021/5/23-16:24
     */
    public boolean isBlock(User user){
        return userImpl.isBlock(connection, user);
    }


    /**
     * @Author Tptogiar
     * @Description 根据id查询指定用户信息
     * @Date 2021/5/23-16:30
     */
    public User getUserByID(User user){
        return userImpl.getUserByID(connection,user);
    }


    /**
     * @Author Tptogiar
     * @Description 更改用户信息
     * @Date 2021/5/23-19:41
     */
    public TipType correctUserInfo(User user){
        /*判断用户名密码是否正确*/
        long countByUserNameAndPassword = userImpl.login(connection, user);
        if(countByUserNameAndPassword==0){
            return TipType.CORRECT_USER_INFO_PASSWORD_WORING;
        }
        int update = userImpl.correctUserInfo(connection, user);
        user.setUserPassword(user.getNewPassword());
        return TipType.CORRECT_USER_INFO_SUCCEED;
    }




}
