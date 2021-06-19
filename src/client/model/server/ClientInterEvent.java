package client.model.server;

import client.model.factory.ResponseDivFactory;
import client.model.factory.RequestAsmFactory;
import client.model.util.FileUtil;
import common.Tip;
import common.inter.InterType;
import common.inter.Request;
import common.inter.Response;
import server.model.server.ServerCenter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Tptogiar
 * @Descripiton 负责客户端与服务端的交互事件，
 * 将于服务器交互的部分同界面事件ClientEventList分离
 * @creat 2021/05/16-12:53
 */


public class ClientInterEvent {

    /**
     * @Author Tptogiar
     * @Description 监听服务端发送回来的响应
     * @Date 2021/5/20-8:49
     */
    public static void listenToServer(){
        ThreadSvr.executeNewTask(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        Response response = (Response) ClientStatus.curObjInStm.readObject();
                        ResponseDivFactory.responseDivide(response);
                    }
                } catch (IOException e) {
                    if("Connection reset".equals(e.getMessage())){
                        Tip.froceOffline("与服务器失去联系，其关闭后重试");
                    }else{
                        e.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * @Author Tptogiar
     * @Description 发送登录请求
     * @Date 2021/5/17-23:31
     */
    public static void sendLogin(String userName, String password) {
        ThreadSvr.executeNewTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = RequestAsmFactory.assembleRes(InterType.LOGIN, userName, password);
                    ClientStatus.curObjOutStm.writeObject(request);
                } catch (Exception e) {
                    Tip.froceOffline("连接服务器失败，请关闭后重试");
                }
            }
        });
    }





    /**
     * @Author Tptogiar
     * @Description 向服务器请求初始化信息
     * @Date 2021/5/16-17:19
     */
    public static void init() throws Exception {
        ThreadSvr.executeNewTask(new Runnable() {
            @Override
            public void run() {
                try {
                    loginComplete(ClientStatus.user.getUserID());
//                    enquiryOnlineUsersAndwait();
//                    enquiryContactsAndWait();
//                    enquiryGroupChats();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }


    /**
     * @Author Tptogiar
     * @Description 告知客户端登录完成了
     * @Date 2021/5/20-22:51
     */
    public static void loginComplete(int userID) throws IOException {
        Request request = RequestAsmFactory.assembleRes(InterType.LOGIN_COMPLETE, userID);
        ClientStatus.curObjOutStm.writeObject(request);
    }


    /**
     * @Author Tptogiar
     * @Description 向服务器查询联系人信息
     * @Date 2021/5/16-17:21
     */
//    public static void enquiryContactsAndWait() throws Exception {
//        Request request = RequestAsmFactory.assembleRes(InterType.ENQUIRY_CONTACTS, ClientStatus.user);
//        ClientStatus.curObjOutStm.writeObject(request);
//        Response response = (Response) ClientStatus.curObjInStm.readObject();
//        ResponseDivFactory.responseDivide(response);
//        System.out.println("联系人完毕");
//    }

    /**
     * @Author Tptogiar
     * @Description 向服务器查询群聊信息
     * @Date 2021/5/16-17:24
     */
    public static void enquiryGroupChats(){



    }

    /**
     * @Author Tptogiar
     * @Description 发送注册请求
     * @Date 2021/5/17-23:31
     */
    public static void sendRegister(String userName, String password) {
        ThreadSvr.executeNewTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = RequestAsmFactory.assembleRes(InterType.REGISTER, userName, password);
                    ClientStatus.curObjOutStm.writeObject(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * @Author Tptogiar
     * @Description 发送文本消息
     * @Date 2021/5/19-16:58
     */
    public static void sendTextMessage(int sendUserID, String sendUserName, int receiverUserID, String messageText, LocalDateTime transferTime) {
        ThreadSvr.executeNewTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = RequestAsmFactory.assembleRes(InterType.MESSAGE_TEXT, sendUserID,sendUserName,receiverUserID,messageText,transferTime);
                    ClientStatus.curObjOutStm.writeObject(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




    }

    /**
     * @Author Tptogiar
     * @Description 群发给所有在线用户
     * @Date 2021/5/22-23:15
     */
    public static void sendTextMessageToOnline(int sendUserID, String sendUserName, int receiverUserID, String messageText, LocalDateTime transferTime) {
        ThreadSvr.executeNewTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = RequestAsmFactory.assembleRes(InterType.MESSAGE_TEXT_ONLINE, sendUserID,sendUserName,receiverUserID,messageText,transferTime);
                    ClientStatus.curObjOutStm.writeObject(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * @Author Tptogiar
     * @Description 发生文件
     * @Date 2021/5/23-1:41
     */
    public static void sendFile(int sendID, String sendUserName, int receiverID, File file) {
        ThreadSvr.executeNewTask(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] fileBytes = FileUtil.toByteArray(file);
                    String fileName = file.getName();
                    Request request = RequestAsmFactory.assembleRes(InterType.MESSAGE_FILE,sendID,sendUserName,receiverID,fileBytes,fileName);
                    ClientStatus.curObjOutStm.writeObject(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @Author Tptogiar
     * @Description 修改用户信息
     * @Date 2021/5/23-19:23
     */
    public static void correctInfo(int userID, String newUserName, String password, String newPassword) {
        ThreadSvr.executeNewTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = RequestAsmFactory.assembleRes(InterType.CORRECT_INFO, userID,newUserName,password, newPassword);
                    ClientStatus.curObjOutStm.writeObject(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}