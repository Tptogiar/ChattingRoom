package server;

import javafx.application.Application;
import javafx.stage.Stage;
import server.controller.SvrEventList;
import server.model.pool.MyDataSource;
import server.model.server.SvrStatus;

import java.sql.SQLException;

/**
 * @author Tptogiar
 * @Descripiton: 服务端程序入口
 * @creat 2021/05/15-09:16
 */


public class ServerMain extends Application {
    static {

        try {
            SvrStatus.initDataBase();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    @Override
        public void start(Stage primaryStage) throws Exception{
        //调试时可以将下面登录验证interfaceLogin()和activateServer()中的loginStage.close()注释，打开activateServer()，
        //正常使用则相反
            SvrEventList.interfaceLogin();
//            SvrEventList.activateServer();
        }

        public static void main(String[] args) {
            launch(args);
        }
}
