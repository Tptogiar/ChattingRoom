package server.model.pool;

import server.model.server.SvrStatus;
import server.model.server.UserSvr;
import server.model.unit.JDBCUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Tptogiar
 * @Descripiton: 数据库连接池
 * @creat 2021/05/16-20:38
 */

//TODO 这个数据库连接池也还没写
public class MyDataSource  {

    private static Connection connection;

    public static Connection getConnection(){
        return connection;
    }

    static {
        try {
            Class.forName(SvrStatus.dataBaseDriverClass);
            connection = DriverManager.getConnection(SvrStatus.dataBaseUrl, SvrStatus.dataBaseUser, SvrStatus.dataBasePassword);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
