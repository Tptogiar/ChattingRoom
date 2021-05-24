package server.model.factory;

import common.inter.Contain;
import common.inter.containtype.MessageCon;
import common.inter.containtype.UserCon;
import common.inter.InterType;
import common.inter.Request;
import server.model.entity.Connect;
import server.model.entity.User;
import server.model.server.ConnectSvr;
import server.model.server.ServerCenter;

import java.io.IOException;

/**
 * @author Tptogiar
 * @Descripiton: 将客户端发来的请求分解为对应的类型并交由对应的处理单元去处理
 * @creat 2021/05/15-22:26
 */



//RequsetDivedeFactory
public class RequestDivFactory {

//    static UserSvr userSvr=UserSvr.getInstance();
    static ConnectSvr connSvr=new ConnectSvr();

    public static void divideRes(Request request,Connect connect) throws IOException {
        InterType interType = request.getReqType();
        Contain contain = request.getContain();

        if(InterType.LOGIN.equals(interType)){
           login(contain,connect);
        }
        else if(InterType.LOGIN_COMPLETE.equals(interType)){
            loginComplete(contain);
        }
        else if (InterType.REGISTER.equals(interType)){
            register(contain,connect);
        }
//        else if(InterType.ENQUIRY_ONLINE_USERS.equals(interType)){
//            enquiryOnlines(contain);
//        }
        else if(InterType.MESSAGE_TEXT.equals(interType)){
            messageText(contain);
        }
        else if (InterType.MESSAGE_TEXT_ONLINE.equals(interType)){
            messageTextToOnline(contain);
        }
        else if (InterType.MESSAGE_FILE.equals(interType)){
            messageFile(contain);
        }
        else if(InterType.CORRECT_INFO.equals(interType)){
            correctInfo(contain);
        }

    }



    /**
     * @Author Tptogiar
     * @Description 客户端登录完成后给其一些相应的响应
     * @Date 2021/5/20-23:04
     */
    private static void loginComplete(Contain contain) throws IOException {
        UserCon userCon = (UserCon) contain;
        int userID = userCon.getUser().getUserID();
        ServerCenter.loginComplete(userID);
    }


    /**
     * @Author: Tptogiar
     * @Description: 对登录请求的分解
     * @Date: 2021/5/16-19:34
     */
    public static void login(Contain contain, Connect conn) throws IOException {
        UserCon userCon = (UserCon) contain;
        User user = userCon.getUser();
        ServerCenter.login(user,conn);
    }

    /**
     * @Author: Tptogiar
     * @Description: 处理注册请求
     * @Date: 2021/5/17-23:33
     */
    private static void register(Contain contain, Connect conn) throws IOException {
        UserCon userCon = (UserCon) contain;
        User user = userCon.getUser();
        ServerCenter.register(user,conn);
    }

    /**
     * @Author: Tptogiar
     * @Description: 对查询请求进行处理
     * @Date: 2021/5/17-23:30
     */
//    public static void enquiryOnlines(Contain contain) throws IOException {
//        EnquiryCon enquiryCon = (EnquiryCon) contain;
//        ArrayList keyObjects = enquiryCon.getKeyObjects();
//        Integer userID = (Integer) keyObjects.get(0);
//        ServerCenter.enquiryOnlines(userID);
////        Connect connnect = ConnectSvr.conns.get(userID);
////        List resultSet = UserSvr.getInstance().enquiryOnlines();
////        Response response = ResponseAsmFactory.assembleResp(InterType.ENQUIRY_ONLINE_USERS, resultSet);
////        connSvr.sendResponse(response,connnect);
////        System.out.println("                             查询相应完毕"+"    RequestDivFactory");
//    }


    /**
     * @Author Tptogiar
     * @Description 对客户端发来的文本消息进行处理
     * @Date 2021/5/20-0:38
     */
    private static void messageText(Contain contain) throws IOException {
        MessageCon messageCon = (MessageCon) contain;
        ServerCenter.messageText(messageCon);
    }

    /**
     * @Author Tptogiar
     * @Description 群发给所有在线用户
     * @Date 2021/5/22-23:17
     */
    private static void messageTextToOnline(Contain contain) throws IOException {
        MessageCon messageCon = (MessageCon) contain;
        ServerCenter.messageTextToOnline(messageCon);
    }


    /**
     * @Author Tptogiar
     * @Description 处理文件发送请求
     * @Date 2021/5/23-1:51
     * @param contain
     */
    private static void messageFile(Contain contain) throws IOException {
        MessageCon messageCon = (MessageCon) contain;
        ServerCenter.messageFile(messageCon);
    }



    /**
     * @Author Tptogiar
     * @Description 修改用户信息
     * @Date 2021/5/23-19:30
     */
    private static void correctInfo(Contain contain) throws IOException {
        UserCon userCon = (UserCon) contain;
        User user = userCon.getUser();
        ServerCenter.correctInfo(user);



    }

}
