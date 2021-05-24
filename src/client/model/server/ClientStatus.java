package client.model.server;

import client.controller.MessageCellCtrl;
import common.inter.containtype.MessageCon;
import server.model.entity.User;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Tptogiar
 * @Descripiton: 记录当前客户端的状态
 * @creat 2021/05/15-11:42
 */


public class ClientStatus {

    public static Properties config;


    //CurrentObjectInputStream
    public static ObjectInputStream curObjInStm;
    //CurrentObjectOutputStream
    public static ObjectOutputStream curObjOutStm;
    public static Socket socket;

    public static int port;
    public static String host;

    public static User user;
    //用来指定默认的文件存放位置
    public static String fileDir = "src\\UserData\\";
    public static HashMap<Integer, ArrayList<MessageCon>> chattingRecrodBuffer=new HashMap<>();


    static {
        config=new Properties();
        try {
            config.load(ClientStatus.class.getResourceAsStream("/config/clientConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        port = Integer.parseInt(config.getProperty("port"));
        host=config.getProperty("host");
    }

    /**
     * @Author: Tptogiar
     * @Description: 想获取对象输出流在获取对象输入流，服务端也是，
     * 如果都客户端都先获取对象输入流的话会陷入相互等待
     * @Date: 2021/5/16-11:12
     */
    public static void init(Socket socket) throws IOException {
        curObjOutStm=new ObjectOutputStream(socket.getOutputStream());
        curObjInStm=new ObjectInputStream(socket.getInputStream());
    }






}
