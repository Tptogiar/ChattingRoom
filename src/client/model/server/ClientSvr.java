package client.model.server;

import client.model.factory.RequestAsmFactory;
import client.model.util.FileUtil;
import common.Tip;
import common.TipType;
import common.inter.InterType;
import common.inter.Request;
import common.inter.containtype.MessageCon;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.model.entity.User;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSvr {


    public static void loginResult(TipType loginResult, User user){
        if (TipType.LOGIN_WRONG_USERNAME_PASSWORD.equals(loginResult)
                || TipType.LOGIN_USERNAME_NOT_EXIST.equals(loginResult)
                || TipType.LOGIN_WRONG_HAD_LOGIN.equals(loginResult)
                || TipType.LOGIN_WORONG_ISBLOCK.equals(loginResult)){

            ClientInterfaceEvent.popTip(loginResult);
            return;
        }
        if(TipType.LOGIN_SUCCEED.equals(loginResult)){
            try {
                ClientInterfaceEvent.loginSucceed();
                //将此用户设置为客户端的当前用户
                ClientStatus.user= user;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
    }


//    public static void contactEnquiry(List resultList) throws IOException {
//        ClientInterfaceEventList.displayOnlineUsers(resultList);
//        System.out.println("ClientSvr     "+"处理服务器的联系人查询相应完毕");
//    }


    public static void regesterRep(TipType registerResult){
        if(TipType.REGISTER_SUCCEED.equals(registerResult)){
            ClientInterfaceEvent.registerSucceed(registerResult);
            return;
        }
        if (TipType.REGISTER_USERNAME_EXIST.equals(registerResult)){
            ClientInterfaceEvent.popTip(registerResult);
            return;
        }
    }

    /**
     * @Author Tptogiar
     * @Description 更新在线用户列表
     * @Date 2021/5/23-13:58
     */
    public static void updateOnlineUsers(List resultList) throws IOException {
        ClientInterfaceEvent.displayOnlineUsers(resultList);
    }


    /**
     * @Author Tptogiar
     * @Description 将一条或多天消息记录添加到缓存中
     * @Date 2021/5/23-13:57
     */
    public static void messageText(MessageCon...messageCons) throws IOException {
        int index=0;
        for (int i = 0; i < messageCons.length; i++) {
            MessageCon messageCon = messageCons[i];
            index=messageCon.getSendUserID();
            //判断应该展示在那个聊天窗口中
            if(ClientStatus.user.getUserID() == index){
                //如果这条消息时自己发送的，则应该展示在接受者对应的聊天窗体中,
                // 否则还是现实在发送者的聊天窗体中（即不改变getUserCellCtrl的值）
                index=messageCon.getReceiverUserID();
            }
            //如果是公共频道，则那公共频道的userCellCtrl
            if (messageCon.getReceiverUserID()==ClientInterfaceEvent.PUBLIC){
                index=ClientInterfaceEvent.PUBLIC;
            }
            //如果此时索引位置为空，则给他建立一个新的用来缓存messageCon的数组
            if(ClientStatus.chattingRecrodBuffer.get(index)==null){
                ClientStatus.chattingRecrodBuffer.put(index,new ArrayList<MessageCon>());
            }
            ClientStatus.chattingRecrodBuffer.get(index).add(messageCon);
        }
        //未来消息的及时性，加入缓存后就要准备展示了，
        //至于能不能立即展示除了，就要看对方上线了没有
        ClientInterfaceEvent.readyForMessage(index);
    }


    /**
     * @Author Tptogiar
     * @Description 告知服务端登录完成后服务端返回的响应
     * @Date 2021/5/20-23:25
     */
    public static void loginComplete(List resultList) throws IOException {
        ClientInterfaceEvent.displayOnlineUsers(resultList);
    }

    /**
     * @Author Tptogiar
     * @Description 客户端被服务的强制下线
     * @Date 2021/5/22-16:31
     */
    public static void forceOffline(String text) {
        Tip.froceOffline(text);
    }


    /**
     * @Author Tptogiar
     * @Description 弹窗提醒
     * @Date 2021/5/22-16:49
     */
    public static void popupTip(String messageText) {
        Tip.info(messageText);
    }





    /**
     * @Author Tptogiar
     * @Description 接收文件
     * @Date 2021/5/23-10:30
     * @param sendUserID
     * @param sendUserName
     * @param receiverUserID
     * @param fileBytes
     * @param fileName
     */
    public static void  messageFile(int sendUserID, String sendUserName, int receiverUserID, byte[] fileBytes, String fileName) throws IOException {
        ClientInterfaceEvent.displayFileReceiveWin(sendUserName,fileBytes,fileName);
    }

    /**
     * @Author Tptogiar
     * @Description 保存接收到的文件
     * @Date 2021/5/23-11:02
     * @param saveFile
     * @param fileBytes
     */
    public static void saveMessageFile(File saveFile, byte[] fileBytes){
        FileUtil.getFile(fileBytes,saveFile.getPath());
    }

    public static void correctUserInfo(TipType correctResult, User user) {
        if (TipType.CORRECT_USER_INFO_USERNAME_EXIST.equals(correctResult)
            || TipType.CORRECT_USER_INFO_PASSWORD_WORING.equals(correctResult)){

            ClientInterfaceEvent.popTip(correctResult);
            return;
        }
        if(TipType.CORRECT_USER_INFO_SUCCEED.equals(correctResult)){
            try {
                //将此用户设置为客户端的当前用户
                ClientStatus.user= user;
                Tip.info("用户信息修改成功");
                ClientInterfaceEvent.mainCtrl.userProfileCtrl.init(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
    }

    /**
     * @Author Tptogiar
     * @Description 客户端成功连接服务端后执行的步骤
     * @Date 2021/6/19-1:15
     * @param socket
     */
    public static void connectSucceed(Socket socket) throws IOException {
        ClientStatus.socket=socket;
        ClientStatus.init(socket);
        ClientInterEvent.listenToServer();
    }

    /**
     * @Author Tptogiar
     * @Description 服务器允许连接/当前在线用户较多/服务器繁忙
     * @Date 2021/6/19-1:31
     * @param interType
     */
    public static void serverResponseForConnection(InterType interType) throws IOException {
        if(interType.equals(InterType.SERVER_ALLOW_CONNECT)){
            ClientInterfaceEvent.interfaceLogin();
            return;
        }
        Tip.froceOffline(interType.getTypeName());
    }
}
