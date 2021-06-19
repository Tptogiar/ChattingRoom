package client.model.factory;

import client.model.server.ClientSvr;
import common.TipType;
import common.inter.Contain;
import common.inter.containtype.EnquiryCon;
import common.inter.containtype.MessageCon;
import common.inter.containtype.UserCon;
import common.inter.InterType;
import common.inter.Response;
import server.model.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tptogiar
 * @Descripiton: 将服务端相应的数据接收并交给对应的处理单元处理
 * @creat 2021/05/15-23:36
 */


public class ResponseDivFactory {


    public static void responseDivide(Response response) throws IOException {
        InterType interType = response.getInterType();
        Contain contain = response.getContain();
        if(InterType.LOGIN.equals(interType)){
            login(contain);
        }
        else if(InterType.LOGIN_COMPLETE.equals(interType)){
            loginComplete(contain);
        }
        else if(InterType.REGISTER.equals(interType)){
            regesterRep(contain);
        }
        else if(InterType.UPDATE_ONLINE_USERS.equals(interType)){
            updateOnlineUsers(contain);
        }
//        else if(InterType.ENQUIRY_CONTACTS.equals(interType)){
//            contactEnquiry(contain);
//        }
        else if(InterType.MESSAGE_TEXT.equals(interType)){
            messageText(contain);
        }
        else if(InterType.CHATTING_RECORDS.equals(interType)){
            chattingRecords(contain);
        }
        else if(InterType.FORCE_OFFLINE.equals(interType)
            || InterType.FORCE_OFFLINE_TO_BLACKLIST.equals(interType)
            || InterType.FORCE_OFFLINE_DELETE.equals(interType)){

            forceOffline(interType);
        }
        else if(InterType.POPUP_TIP.equals(interType)){
            popupTip(contain);
        }
        else if(InterType.MESSAGE_TEXT_ONLINE.equals(interType)){
            messageText(contain);
        }
        else if(InterType.MESSAGE_FILE.equals(interType)){
            messageFile(contain);
        }
        else if(InterType.CORRECT_INFO.equals(interType)){
            correctUserInfo(contain);
        }
        else if(InterType.SERVER_ALLOW_CONNECT.equals(interType)
            || InterType.SERVER_ONLINES_BUSY.equals(interType)
            || InterType.SERVER_TASKS_BUSY.equals(interType)){
            serverResponseForConnection(interType);
        }
    }


    /**
     * @Author Tptogiar
     * @Description 告知服务端登录完成后服务端返回的响应
     * @Date 2021/5/20-23:24
     */
    private static void loginComplete(Contain contain) throws IOException {
        EnquiryCon enquiryCon = (EnquiryCon) contain;
        ArrayList resultObject = enquiryCon.getResultObject();
        List resultList = (List) resultObject.get(0);
        ClientSvr.loginComplete(resultList);
    }


    /**
     * @Author: Tptogiar
     * @Description: 处理服务器返回的登录相应
     * @Date: 2021/5/16-18:18
     */
    private static void login(Contain contain){
        UserCon userRespContain = (UserCon) contain;
        TipType loginResult = userRespContain.getTipType();
        User user = userRespContain.getUser();
        ClientSvr.loginResult(loginResult,user);
    }


    /**
     * @Author: Tptogiar
     * @Description: 处理返回的查询联系人相应
     * @Date: 2021/5/16-18:18
     */
//    private static void contactEnquiry(Contain contain) throws IOException {
//        EnquiryCon enquiryCon = (EnquiryCon) contain;
//        ArrayList resultObject = enquiryCon.getResultObject();
//        List resultList = (List) resultObject.get(0);
//        ClientSvr.contactEnquiry(resultList);
////        ClientInterfaceEventList.displayOnlineUsers(resultList);
////        System.out.println("ResponseDivFactory     "+"处理服务器的联系人查询相应完毕");
//    }

    /**
     * @Author: Tptogiar
     * @Description: 对服务器发回来的注册相应进行处理
     * @Date: 2021/5/17-20:12
     */
    private static void regesterRep(Contain contain) {
        UserCon userRespContain = (UserCon) contain;
        TipType registerResult = userRespContain.getTipType();
        ClientSvr.regesterRep(registerResult);
    }

    /**
     * @Author: Tptogiar
     * @Description 更新在线用户
     * @Date: 2021/5/19-14:20
     */
    private static void updateOnlineUsers(Contain contain) throws IOException {
        EnquiryCon enquiryCon = (EnquiryCon) contain;
        ArrayList resultObject = enquiryCon.getResultObject();
        List resultList = (List) resultObject.get(0);
        ClientSvr.updateOnlineUsers(resultList);
    }

    /**
     * @Author Tptogiar
     * @Description 对服务端发送回来的文本消息进行处理
     * @Date 2021/5/20-9:41
     */
    private static void messageText(Contain contain) throws IOException {
        MessageCon messageCon = (MessageCon) contain;
        ClientSvr.messageText(messageCon);
    }

    /**
     * @Author Tptogiar
     * @Description 对聊天记录响应进行处理
     * @Date 2021/5/21-21:36
     */
    private static void chattingRecords(Contain contain) throws IOException {
        EnquiryCon enquiryCon = (EnquiryCon) contain;
        List<MessageCon> messageCons = (List<MessageCon>) enquiryCon.getResultObject().get(0);
        for (int i = 0; i < messageCons.size(); i++) {
            messageText(messageCons.get(i));
        }
    }

    /**
     * @Author Tptogiar
     * @Description 客户端被服务的强制下线
     * @Date 2021/5/22-16:31
     * @param interType
     */
    private static void forceOffline(InterType interType) {
        ClientSvr.forceOffline(interType.getTypeName());
    }



    /**
     * @Author Tptogiar
     * @Description 服务端发来的弹窗提醒
     * @Date 2021/5/22-16:48
     */
    private static void popupTip(Contain contain) {
        MessageCon messageCon = (MessageCon) contain;
        String messageText = messageCon.getMessageText();
        ClientSvr.popupTip(messageText);
    }

    /**
     * @Author Tptogiar
     * @Description 文件接收
     * @Date 2021/5/23-9:46
     */
    private static void messageFile(Contain contain) throws IOException {
        MessageCon messageCon = (MessageCon) contain;

        int sendUserID = messageCon.getSendUserID();
        String sendUserName = messageCon.getSendUserName();
        int receiverUserID = messageCon.getReceiverUserID();
        byte[] fileBytes = messageCon.getFileBytes();
        String fileName = messageCon.getFileName();
        ClientSvr.messageFile(sendUserID,sendUserName,receiverUserID,fileBytes,fileName);
    }



    private static void correctUserInfo(Contain contain) {
        UserCon userCon = (UserCon) contain;
        TipType correctResult = userCon.getTipType();
        User user = userCon.getUser();
        ClientSvr.correctUserInfo(correctResult,user);
    }


    /**
     * @Author Tptogiar
     * @Description 服务器允许连接/当前在线用户较多/服务器繁忙
     * 为将界面事件与业务逻辑分离，所以不直接调用ClientInterfaceEvent内的函数，而是通过ClientSvr调用
     * @Date 2021/6/19-1:21
     * @param interType
     */
    private static void serverResponseForConnection(InterType interType) throws IOException {
        ClientSvr.serverResponseForConnection(interType);
    }

}
