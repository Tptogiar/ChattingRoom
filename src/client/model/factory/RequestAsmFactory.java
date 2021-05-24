package client.model.factory;

import client.model.server.ClientStatus;
import common.inter.containtype.EnquiryCon;
import common.inter.containtype.MessageCon;
import common.inter.containtype.UserCon;
import common.inter.InterType;
import common.inter.Request;
import server.model.entity.User;

import java.io.File;
import java.time.LocalDateTime;

/**
 * @author Tptogiar
 * @Descripiton: 将所需信息包装成Request并交给发送单元发送出去
 *
 * @creat 2021/05/15-23:35
 */


public class RequestAsmFactory {


    public static Request assembleRes(InterType interType, Object ...objects){
        if(InterType.LOGIN.equals(interType)){
            return login((String) objects[0],(String) objects[1]);
        }
        else if(InterType.LOGIN_COMPLETE.equals(interType)){
            return loginComplete((Integer)objects[0]);
        }
        else if(InterType.REGISTER.equals(interType)){
            return register((String)objects[0],(String)objects[1]);
        }
//        else if(InterType.ENQUIRY_CONTACTS.equals(interType)){
//            return enquiryContacts((User)objects[0]);
//        }
//        else if(InterType.ENQUIRY_ONLINE_USERS.equals(interType)){
//            return enquiryOnlines();
//        }
        else if(InterType.MESSAGE_TEXT.equals(interType)){
            return messageText((int)objects[0],(String)objects[1],(int)objects[2],(String)objects[3], (LocalDateTime) objects[4]);
        }
        else if(InterType.MESSAGE_TEXT_ONLINE.equals(interType)){
            return messageTextToOnline((int)objects[0],(String)objects[1],(int)objects[2],(String)objects[3], (LocalDateTime) objects[4]);
        }
        else if (InterType.MESSAGE_FILE.equals(interType)){
            return messageFile((int)objects[0],(String)objects[1],(int)objects[2],(byte[])objects[3],(String)objects[4]);
        }
        else if(InterType.CORRECT_INFO.equals(interType)){
            return correctInfo((int)objects[0],(String)objects[1],(String)objects[2],(String)objects[3]);
        }


        return null;
    }




    /**
     * @Author Tptogiar
     * @Description 告知服务端本客户端登录完成了
     * @Date 2021/5/20-22:27
     * @param
     */
    private static Request loginComplete(Integer userID) {
        UserCon userCon = new UserCon(userID);
        return new Request(InterType.LOGIN_COMPLETE,userCon);
    }


    /**
     * @Author: Tptogiar
     * @Description: 登录请求
     * @Date: 2021/5/16-17:47
     */
    private static Request login(String userName, String password){
        //包装request的内容Contain
        UserCon userCon = new UserCon(userName, password);
        Request request = new Request(InterType.LOGIN, userCon);
        return request;
    }

    /**
     * @Author: Tptogiar
     * @Description: 查询连续人请求
     * @Date: 2021/5/16-17:48
     */
//    private static Request enquiryContacts(User user) {
//        EnquiryCon enquiryCon = new EnquiryCon(InterType.REQUEST,user);
//        Request request = new Request(InterType.ENQUIRY_CONTACTS, enquiryCon);
//        return request;
//    }

    /**
     * @Author: Tptogiar
     * @Description: 注册请求
     * @Date: 2021/5/17-21:12
     */
    private static Request register(String userName, String password) {
        UserCon userCon = new UserCon(userName, password);
        Request request = new Request(InterType.REGISTER, userCon);
        return request;
    }

    /**
     * @Author: Tptogiar
     * @Description: 组装查询在线联系人的请求
     * @Date: 2021/5/19-14:08
     */
//    private static Request enquiryOnlines() {
//        int userID = ClientStatus.user.getUserID();
//        EnquiryCon enquiryCon = new EnquiryCon(InterType.REQUEST,userID);
//        Request request = new Request(InterType.ENQUIRY_ONLINE_USERS, enquiryCon);
//        return request;
//    }


    /**
     * @Author Tptogiar
     * @Description 给客户端发送文本消息
     * @Date 2021/5/19-17:13
     */
    private static Request messageText(int sendUserID, String sendUserName, int receiverUserID, String messageText, LocalDateTime transferTime) {
        MessageCon messageCon = new MessageCon(sendUserID,sendUserName,receiverUserID, messageText,transferTime);
        return new Request(InterType.MESSAGE_TEXT, messageCon);
    }

    /**
     * @Author Tptogiar
     * @Description 公共频道消息请求
     * @Date 2021/5/23-1:38
     */
    private static Request messageTextToOnline(int sendUserID, String sendUserName, int receiverUserID, String messageText, LocalDateTime transferTime) {
        MessageCon messageCon = new MessageCon(sendUserID,sendUserName,receiverUserID, messageText,transferTime);
        return new Request(InterType.MESSAGE_TEXT_ONLINE, messageCon);
    }


    /**
     * @Author Tptogiar
     * @Description 文件传输请求
     * @Date 2021/5/23-1:38
     */
    private static Request messageFile(int sendID, String sendUserName, int receiverUserID, byte[] fileBytes, String fileName) {
        MessageCon messageCon = new MessageCon(sendID,sendUserName,receiverUserID,fileBytes,fileName);
        return new Request(InterType.MESSAGE_FILE,messageCon);
    }


    /**
     * @Author Tptogiar
     * @Description 修改用户信息
     * @Date 2021/5/23-19:26
     */
    private static Request correctInfo(int userID, String newUserName, String password, String newPassword) {
        UserCon userCon = new UserCon(userID,newUserName,password, newPassword);
        return new Request(InterType.CORRECT_INFO,userCon);
    }


}
