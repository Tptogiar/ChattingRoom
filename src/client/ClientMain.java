package client;

import client.model.server.ClientInterfaceEvent;
import client.model.server.ClientStatus;
import common.Tip;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;


/**
 * @author Tptogiar
 * @Descripiton: 客户端程序入口
 * @creat 2021/05/15-09:16
 */


public class ClientMain extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception{
            primaryStage.close();
            checkConnection();
        }


        public static void main(String[] args) {
            launch(args);
        }


        /**
         * @Author: Tptogiar
         * @Description: 检查服务器是否在线
         * @Date: 2021/5/15-15:42
         */
        public void checkConnection(){
            try {
                Socket socket = new Socket(ClientStatus.host, ClientStatus.port);
                ClientStatus.socket=socket;
                ClientStatus.init(socket);
                ClientInterfaceEvent.interfaceLogin();
//                ClientEventList.mainInterfaces();
            } catch (IOException e) {
                Tip.error("服务器链接失败，请关闭后重试");
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}





