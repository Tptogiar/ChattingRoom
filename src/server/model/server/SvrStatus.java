package server.model.server;

import server.model.pool.MyDataSource;
import server.model.pool.MyThreadPool;
import server.model.unit.JDBCUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @author Tptogiar
 * @Descripiton: 记录当前服务器的状态
 * @creat 2021/05/15-16:03
 */


public class SvrStatus {


    public static Properties config=new Properties();
    public static String userName;
    public static String password;
    public static int port;
    public static String dataBaseUser;
    public static String dataBasePassword;
    public static String dataBaseUrl;
    public static String dataBaseDriverClass;
    public static String dataBaseName;

    public static ServerSocket svrScoket;

    public static ConnectSvr connSvr=new ConnectSvr();

    public static MyThreadPool myThreadPool=new MyThreadPool(1,4,10);

    static {
        try {
            serverConfig();
            svrScoket=new ServerSocket(port);
            initDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @Author: Tptogiar
     * @Description: 配置服务器
     * @Date: 2021/5/19-16:32
     */
    public static void serverConfig() throws IOException {
        config.load(SvrStatus.class.getResourceAsStream("/config/serverConfig.properties"));
        userName =config.getProperty("guaguaUser");
        password =config.getProperty("guaguaPassword");
        port= Integer.parseInt(config.getProperty("port"));

        dataBaseUser =config.getProperty("dataBaseUser");
        dataBasePassword=config.getProperty("dataBasePassword");
        dataBaseUrl=config.getProperty("dataBaseUrl");
        dataBaseDriverClass =config.getProperty("driverClass");

        dataBaseName=config.getProperty("dataBaseName");
    }




    /**
     * @Author: Tptogiar
     * @Description: 开辟一个线程来监听客户端的连接
     * @Date: 2021/5/15-21:49
     */
    public static void listenClientsRequest(){

        SvrStatus.myThreadPool.submit(null,new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        Socket accept = SvrStatus.svrScoket.accept();
                        connSvr.addConnect(accept);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @Author: Tptogiar
     * @Description: 数据库的初始化
     * @Date: 2021/5/19-16:30
     */
    public static void initDataBase() throws SQLException {
        String creatDataBase="create database  if not exists "+SvrStatus.dataBaseName;
        String useDataBase="USE `"+SvrStatus.dataBaseName+"`";
        PreparedStatement preparedStatement = MyDataSource.getConnection().prepareStatement(creatDataBase);
        preparedStatement.addBatch(useDataBase);
        preparedStatement.execute();
        List strings = JDBCUtil.runInitSql();
        for (int i = 0; i < strings.size(); i++) {
            preparedStatement.addBatch((String) strings.get(i));
        }
        preparedStatement.executeBatch();

        //将所有用户置为离线状态
        ServerCenter.setAllOffonline();
    }




}
