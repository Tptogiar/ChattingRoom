package server.model.factory;


import common.TipType;
import common.inter.containtype.EnquiryCon;
import common.inter.containtype.MessageCon;
import common.inter.containtype.UserCon;
import common.inter.InterType;
import common.inter.Response;
import server.model.entity.User;
import sun.plugin2.message.Message;

import java.util.List;

/**
 * @author Tptogiar
 * @Descripiton: 将所需要的信息加工为Response，并并交给对应的发送单元发送
 * @creat 2021/05/15-22:44
 */

//ResponseAssembleFactory
public class ResponseAsmFactory {


    public static Response assembleResp(InterType interType, Object... objects){
        if(InterType.LOGIN.equals(interType)){
           return asmLoginRep((TipType) objects[0],(User)objects[1]);
        }
        else if(InterType.LOGIN_COMPLETE.equals(interType)){
            return loginComplete((List) objects[0]);
        }
        else if (InterType.REGISTER.equals(interType)){
            return asmRegisterResp((TipType) objects[0]);
        }
        else if(InterType.UPDATE_ONLINE_USERS.equals(interType)){
            return updateOnlineUsers((List) objects[0]);
        }
        else if(InterType.MESSAGE_TEXT.equals(interType)){
            return messageText((MessageCon)objects[0]);
        }
        else if(InterType.CHATTING_RECORDS.equals(interType)){
            return chattingRecords((List<MessageCon>)objects[0]);
        }
        else if (InterType.FORCE_OFFLINE.equals(interType)
            || InterType.FORCE_OFFLINE_DELETE.equals(interType)
            || InterType.FORCE_OFFLINE_TO_BLACKLIST.equals(interType)){

            return forceOffline(interType);
        }
        else if(InterType.POPUP_TIP.equals(interType)){
            return popupTip((String)objects[0]);
        }
        else if(InterType.MESSAGE_FILE.equals(interType)){
            return messageFile((MessageCon)objects[0]);
        }
        else if(InterType.CORRECT_INFO.equals(interType)){
            return correctUserInfo((TipType)objects[0],(User)objects[1]);
        }
        return null;
    }




    /**
     * @Author: Tptogiar
     * @Description: 对登录的响应
     * @Date: 2021/5/16-19:27
     */
    private static Response asmLoginRep(TipType loginResult, User user){
        UserCon userRespContain = new UserCon(loginResult,user);
        Response response = new Response(InterType.LOGIN, userRespContain);
        return response;
    }

    /**
     * @Author: Tptogiar
     * @Description: 更新在线用户
     * @Date: 2021/5/16-19:28
     */
    private static Response updateOnlineUsers(List resultList){
        EnquiryCon enquiryCon = new EnquiryCon(InterType.RESPONSE, resultList);
        Response response = new Response(InterType.UPDATE_ONLINE_USERS, enquiryCon);
        return  response;
    }


    /**
     * @Author: Tptogiar
     * @Description: 对注册请求的响应
     * @Date: 2021/5/17-23:47
     */
    private static Response asmRegisterResp(TipType registerResult) {
        UserCon userCon = new UserCon(registerResult);
        Response response = new Response(InterType.REGISTER, userCon);
        return response;
    }


    /**
     * @Author Tptogiar
     * @Description 给消息接受者发送消息的响应
     * @Date 2021/5/20-12:36
     */
    private static Response messageText(MessageCon messageCon) {
        return new Response(InterType.MESSAGE_TEXT,messageCon);
    }

    /**
     * @Author Tptogiar
     * @Description 返回聊天记录响应
     * @Date 2021/5/21-21:31
     * @param messageCon
     */
    private static Response chattingRecords(List<MessageCon> messageCon) {
        EnquiryCon enquiryCon = new EnquiryCon(InterType.RESPONSE, messageCon);
        return new Response(InterType.CHATTING_RECORDS,enquiryCon);
    }

    /**
     * @Author Tptogiar
     * @Description 用户登录成功后给其发送在线用户列表
     * @Date 2021/5/22-16:23
     */
    private static Response loginComplete(List resultSet) {
        EnquiryCon enquiryCon = new EnquiryCon(InterType.RESPONSE, resultSet);
        return new Response(InterType.LOGIN_COMPLETE,enquiryCon);
    }

    /**
     * @Author Tptogiar
     * @Description 强制用户下线
     * @Date 2021/5/22-16:24
     * @param interType
     */
    private static Response forceOffline(InterType interType) {
        return new Response(interType,new MessageCon());
    }

    /**
     * @Author Tptogiar
     * @Description 发送弹窗提醒响应
     * @Date 2021/5/22-16:45
     */
    private static Response popupTip(String text) {
        MessageCon messageCon = new MessageCon(text);
        return new Response(InterType.POPUP_TIP,messageCon);
    }

    private static Response messageFile(MessageCon messageCon) {
        return new Response(InterType.MESSAGE_FILE,messageCon);
    }


    /**
     * @Author Tptogiar
     * @Description 修改用户信息的响应
     * @Date 2021/5/23-20:06
     */
    private static Response correctUserInfo(TipType tipType, User user) {
        UserCon userCon = new UserCon(tipType, user);
        return new Response(InterType.CORRECT_INFO,userCon);
    }



}
