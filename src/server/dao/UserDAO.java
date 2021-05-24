package server.dao;

import server.model.entity.User;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: Tptogiar
 * @Description: User的DAO接口
 * @Date: 2021/5/16-14:03
 */
public interface UserDAO {


    int insertUser(Connection conn, User user);


    void delUser(Connection conn,User user);


    long login(Connection conn, User user);



    long countByUserName(Connection conn, User user);


    List enquiryOnlines(Connection conn);


    int markOnline(Connection conn, User user);

    int markAsOffOnline(Connection conn, User user);

    int setAllOffline(Connection conn);

    int getUserID(Connection conn, User user);

    Boolean isOnline(Connection connection, User user);

    List enquiryAllUsers(Connection connection);


    int deleteUser(Connection connection, User user);

    int addUserToBlackList(Connection connection, User user);

    int unlockInBlackList(Connection connection, User user);

    Boolean isBlock(Connection conn, User user);

    User getUserByID(Connection connection, User user);

    int correctUserInfo(Connection connection, User user);
}
