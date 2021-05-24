package server.dao.impl;

import server.dao.BaseDao;
import server.dao.UserDAO;
import server.model.entity.User;

import java.sql.Connection;
import java.util.List;

/**
 * @author Tptogiar
 * @Descripiton: 对表user的操作的实现类
 * 考虑到后面使用数据库连接池后connection都不是固定的，而是动态的从连接池里面获取的
 * 所以Connection都是从参数传进来的
 * @creat 2021/05/16-14:03
 */


public class UserImpl extends BaseDao<User> implements UserDAO {


    /**
     * @Author: Tptogiar
     * @Description: 向数据库中添加一个客户端用户
     * @Date: 2021/5/16-14:17
     */
    @Override
    public int insertUser(Connection conn, User user) {
        //先检查表中是否存在相同的用户名
        String userName = user.getUserName();
        String password = user.getUserPassword();
        String sql="insert into user(userName,userPassword) value(?,?)";
        int update = update(conn, sql,userName,password);
        return update;
    }

    /**
     * @Author: Tptogiar
     * @Description: 将User从数据库中删除
     * @Date: 2021/5/16-14:17
     */
    @Override
    public void delUser(Connection conn, User user) {

    }


    /**
     * @Author: Tptogiar
     * @Description: 检查密码是否正确
     * @Date: 2021/5/16-14:22
     * @return
     */
    @Override
    public long login(Connection conn, User user) {
        String password = user.getUserPassword();
        String userName = user.getUserName();
        String sql = "select count(*) from user where userName<=> ? and userPassword<=> ?";
        long count = getCount(conn, sql, userName,password);
        return count;
    }


    /**
     * @Author: Tptogiar
     * @Description: 查询指定用户名是否存在的查询操作
     * 返回查询到的个数让调用者进行判断
     * @Date: 2021/5/19-13:34
     */
    @Override
    public long countByUserName(Connection conn, User user) {
        String userName = user.getUserName();
        String userNameExistSql="select count(*) from user where userName <=>?";
        long userExistCount = getCount(conn, userNameExistSql, userName);
        return userExistCount;
    }




    /**
     * @Author: Tptogiar
     * @Description: 把查询数据库中某条数据是否存在的逻辑单独拿出来，以便复用
     * @Date: 2021/5/18-0:11
     */
    public long getCount(Connection conn, String sql, Object...objects){
        Long value = getValue(Long.class,conn, sql, objects);
        if(value == null){
            return 0;
        }
        return value;
    }

    /**
     * @Author: Tptogiar
     * @Description: 查询在线用户
     * @Date: 2021/5/19-0:03
     * @return
     */
    @Override
    public List enquiryOnlines(Connection conn) {
        String sql="select userName, userID,isOnline from user where isOnline= 1";
        return getForList(conn, sql);
    }



    /**
     * @Author: Tptogiar
     * @Description: 将对应用户标记为上线状态
     * @Date: 2021/5/19-13:31
     */
    @Override
    public int markOnline(Connection conn, User user) {
        String sql="update user set isOnline = 1 where userID= ? ";
        int userID = user.getUserID();
        int update = update(conn, sql, userID);
        return update;
    }
    /**
     * @Author: Tptogiar
     * @Description: 将对应用户标记为离线状态
     * @Date: 2021/5/19-13:31
     */
    @Override
    public int markAsOffOnline(Connection conn, User user) {
        String sql="update user set isOnline = 0 where userID =? ";
        int userID = user.getUserID();
        int update = update(conn, sql, userID);
        return update;
    }



    /**
     * @Author: Tptogiar
     * @Description: 服务器启动是把所有用户置为离线状态
     * @Date: 2021/5/19-16:18
     */
    @Override
    public int setAllOffline(Connection conn) {
        String sql="update user set isOnline =0";
        int update = update(conn, sql);
        return update;
    }

    @Override
    public int getUserID(Connection conn, User user){
        String userName = user.getUserName();
        String sql="select userID from user where userName = ?";
        return getValue(Integer.class, conn, sql, userName);
    }


    /**
     * @Author Tptogiar
     * @Description 判断指定用户是否应登录(在用户登录时，
     * 只传
     * @Date 2021/5/20-0:05
     * @return
     */
    @Override
    public Boolean isOnline(Connection conn, User user) {
        int userID = user.getUserID();
        String sql="select isOnline from user where userID = ?";
        return getValue(Long.class, conn, sql,userID);
    }


    /**
     * @Author Tptogiar
     * @Description 查询所有用户信息
     * @Date 2021/5/22-10:40
     */
    @Override
    public List enquiryAllUsers(Connection connection){
        String sql="select * from user";
        return getForList(connection, sql);
    }

    /**
     * @Author Tptogiar
     * @Description 删除指定用户
     * @Date 2021/5/23-15:23
     */
    @Override
    public int deleteUser(Connection connection, User user) {
        int userID = user.getUserID();
        String sql="delete from user where userID = ?";
        return update(connection,sql,userID);
    }

    /**
     * @Author Tptogiar
     * @Description 拉入黑名单
     * @Date 2021/5/23-15:37
     */
    @Override
    public int addUserToBlackList(Connection connection, User user) {
        int userID = user.getUserID();
        String sql="update user set isBlock = 1 where userID = ?";
        return update(connection,sql,userID);
    }


    /**
     * @Author Tptogiar
     * @Description 从黑名单中移除
     * @Date 2021/5/23-15:37
     */
    @Override
    public int unlockInBlackList(Connection connection, User user) {
        int userID = user.getUserID();
        String sql="update user set isBlock = 0 where userID = ?";
        return update(connection,sql,userID);
    }

    /**
     * @Author Tptogiar
     * @Description 在用户之前，客户端还不知他爱服务端的id，所以
     * 传过来的是userName,所有得用userName查询
     * @Date 2021/5/23-16:59
     */
    @Override
    public Boolean isBlock(Connection conn, User user) {
        String userName = user.getUserName();
        String sql="select isBlock from user where userName = ?";
        return getValue(Long.class, conn, sql,userName);
    }

    @Override
    public User getUserByID(Connection connection, User user) {
        int userID = user.getUserID();
        String sql="select * from user where userID = ?";
        return getForOne(connection,sql,userID);
    }

    @Override
    public int correctUserInfo(Connection connection, User user){
        String newUserName = user.getUserName();
        String newPassword = user.getNewPassword();
        int userID = user.getUserID();
        String sql="update user set userName = ? ,userPassword = ? where userID =?";
        return update(connection,sql,newUserName,newPassword,userID);
    }

}
