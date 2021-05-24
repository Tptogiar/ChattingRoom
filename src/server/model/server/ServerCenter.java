package server.model.server;

import common.TipType;
import common.inter.InterType;
import common.inter.Response;
import common.inter.containtype.MessageCon;
import server.controller.SvrEventList;
import server.model.entity.Connect;
import server.model.entity.User;
import server.model.factory.ResponseAsmFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Tptogiar
 * @Descripiton 负责服务端的事务
 * @creat 2021/05/19-21:22
 */


public class ServerCenter {
    static ConnectSvr connSvr=new ConnectSvr();


    public static void login(User user, Connect conn) throws IOException {
        TipType loginResult = UserSvr.getInstance().checkLogin(user);
        Response response = ResponseAsmFactory.assembleResp(InterType.LOGIN, loginResult,user);
        //如果当前客户端登录成功了，就将于此客户端的连接加进缓存ConnectSvr.conns里
        //以方便下次连接使用,并将connect与用户进行绑定，否则只发送response不绑定user
        if(TipType.LOGIN_SUCCEED.equals(loginResult)){
            int userID = user.getUserID();
            ConnectSvr.conns.put(userID,conn);
            ConnectSvr.sendRepLoginSucceed(response,conn);
        }else{
            ConnectSvr.sendResponse(response,conn);

        }
    };

    public static void register(User user,Connect conn) throws IOException {
        TipType registerRusult = UserSvr.getInstance().addUser(user);
        Response response = ResponseAsmFactory.assembleResp(InterType.REGISTER, registerRusult);
        ConnectSvr.sendResponse(response,conn);
        //更新服务端的用户列表
        updateAllUserForServer();
    }


    /**
     * @Author Tptogiar
     * @Description 当有用户登录时，向其他用户发送有新用户上线提醒
     * @Date 2021/5/21-22:44
     */
    public static void updateOnlineUsersToClient() throws IOException {
        List resultSet = UserSvr.getInstance().enquiryOnlines();
        Response response = ResponseAsmFactory.assembleResp(InterType.UPDATE_ONLINE_USERS, resultSet);
        Iterator<Map.Entry<Integer, Connect>> iterator = ConnectSvr.conns.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Connect> next = iterator.next();
            Connect connect = next.getValue();
            //判断该连接是否还可以使用（该用户是否还在线），如果不在线了，则删除该连接
            Socket socket = connect.getSocket();
//            if(socket.isOutputShutdown() && socket.isInputShutdown()){
                ConnectSvr.sendResponse(response,connect);
//            }else{
//                ConnectSvr.conns.remove(connect);
//            }
        }
    }

    /**
     * @Author Tptogiar
     * @Description 客户端登录完成后给其相应的响应
     * @Date 2021/5/20-23:07
     */
    public static void loginComplete(int userID) throws IOException {
        //拿到该用户对应的连接
        Connect connnect = ConnectSvr.conns.get(userID);
        //将当前在线用户反馈给该用户
        List onlineUsers = UserSvr.getInstance().enquiryOnlines();
        Response response = ResponseAsmFactory.assembleResp(InterType.LOGIN_COMPLETE, onlineUsers);
        ConnectSvr.sendResponse(response,connnect);
        //更新服务端的用户列表
        updateAllUserForServer();
        //向其他用户发送有新用户上线提醒
        updateOnlineUsersToClient();
        //该用户登录后，向该用户发送属于他的消息记录
        List<MessageCon> messageByUsers = MessageSvr.getInstance().getMessageByUser(userID);
        Response responseChattingRecord = ResponseAsmFactory.assembleResp(InterType.CHATTING_RECORDS, messageByUsers);
        ConnectSvr.sendResponse(responseChattingRecord,connnect);
    }

    /**
     * @Author Tptogiar
     * @Description 处理客户端发过来的文本消息
     * @Date 2021/5/21-20:19
     */
    public static void messageText(MessageCon messageCon) throws IOException {
        MessageSvr.getInstance().addMessageTextTODataBase(messageCon);
        messageTextToUserIfOnline(messageCon);
    }


    /**
     * @Author Tptogiar
     * @Description 将文本消息发给指定的用户的客户端（如果在线的话）
     * @Date 2021/5/21-19:22
     */
    public static void messageTextToUserIfOnline(MessageCon messageCon) throws IOException {
        int receiverUserID = messageCon.getReceiverUserID();
        User receiver = new User(receiverUserID);
        boolean online = UserSvr.getInstance().isOnline(receiver);
        if(online){
            Response response = ResponseAsmFactory.assembleResp(InterType.MESSAGE_TEXT, messageCon);
            Connect connect = ConnectSvr.conns.get(receiverUserID);
            ConnectSvr.sendResponse(response,connect);
        }
    }


    /**
     * @Author Tptogiar
     * @Description 加载服务端的用户列表
     * @Date 2021/5/22-10:35
     */
    public static void updateAllUserForServer() throws IOException {
        List<User> users = UserSvr.getInstance().enquiryAllusers();
        SvrEventList.displayAllUsers(users);
    }


    /**
     * @Author Tptogiar
     * @Description 将所用用户置为离线
     * @Date 2021/5/22-13:13
     */
    public static void setAllOffonline() {
        UserSvr.getInstance().setAllOffline();
    }

    /**
     * @Author Tptogiar
     * @Description 标记指定用户为离线状态
     * @Date 2021/5/22-13:17
     */
    public static void markUserAsOffline(User user) throws IOException {
        UserSvr.getInstance().markAsOffOnline(user);
        updateAllUserForServer();
        //在向在线用户更行在线用户列表之前先将已下线的用户从在线用户连接里面删掉
        int offlineUserID = user.getUserID();
        ConnectSvr.conns.remove(offlineUserID);
        updateOnlineUsersToClient();
    }

    /**
     * @Author Tptogiar
     * @Description 将指定用户强制下线
     * @Date 2021/5/22-15:32
     * @return
     */
    public static User forceOffOnline(User user, InterType interType) throws IOException {
        Response response = ResponseAsmFactory.assembleResp(interType);
        int userID = user.getUserID();
        Connect connect = ConnectSvr.conns.get(userID);
        //如果用户未上线，则不需要下线操作了
        if (connect==null){
            return user;
        }
        ConnectSvr.sendResponse(response,connect);
        ConnectSvr.conns.remove(userID);
        markUserAsOffline(user);
        User userByID = UserSvr.getInstance().getUserByID(user);
        return userByID;
    }


    /**
     * @Author Tptogiar
     * @Description 给指定用户发送提醒
     * @Date 2021/5/22-15:32
     */
    public static void sendTip(User user, String text) throws IOException {
        Response response = ResponseAsmFactory.assembleResp(InterType.POPUP_TIP, text);
        int userID = user.getUserID();
        Connect connect = ConnectSvr.conns.get(userID);
        ConnectSvr.sendResponse(response,connect);
    }


    /**
     * @Author Tptogiar
     * @Description 群发给所有用户
     * @Date 2021/5/22-23:18
     */
    public static void messageTextToOnline(MessageCon messageCon) throws IOException {
        MessageSvr.getInstance().addMessageTextTODataBase(messageCon);
        Response response = ResponseAsmFactory.assembleResp(InterType.MESSAGE_TEXT, messageCon);
        Iterator<Map.Entry<Integer, Connect>> iterator = ConnectSvr.conns.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Connect> next = iterator.next();
            Connect connect = next.getValue();
            ConnectSvr.sendResponse(response,connect);
        }
    }

    /**
     * @Author Tptogiar
     * @Description 文件处理
     * @Date 2021/5/23-1:53
     */
    //TODO 虽然文件可能比较大，出入数据库影响查询效率，
    // 但是这条文件发送记录还是可以记录下来的
    public static void messageFile(MessageCon messageCon) throws IOException {
        int receiverUserID = messageCon.getReceiverUserID();
        Connect connect = ConnectSvr.conns.get(receiverUserID);
        Response response = ResponseAsmFactory.assembleResp(InterType.MESSAGE_FILE, messageCon);
        ConnectSvr.sendResponse(response,connect);
    }

    /**
     * @Author Tptogiar
     * @Description 删除指定用户
     * @Date 2021/5/23-15:21
     */
    public static void deleteUser(User user) throws IOException {
        forceOffOnline(user,InterType.FORCE_OFFLINE_DELETE);
        int update = UserSvr.getInstance().deleteUser(user);
        updateAllUserForServer();
    }

    /**
     * @Author Tptogiar
     * @Description 将指定用户拉入黑名单
     * @Date 2021/5/23-15:21
     * @return
     */
    public static User addUserToBlockList(User user) throws IOException {
        forceOffOnline(user,InterType.FORCE_OFFLINE_TO_BLACKLIST);
        int update = UserSvr.getInstance().addUserToBlackList(user);
        User userByID = UserSvr.getInstance().getUserByID(user);
        updateAllUserForServer();
        return userByID;
    }

    /**
     * @Author Tptogiar
     * @Description 从黑明单中移除
     * @Date 2021/5/23-15:40
     * @return
     */
    public static User unlockInBlockList(User user) throws IOException {
        int update = UserSvr.getInstance().unlockInBlackList(user);
        User userByID = UserSvr.getInstance().getUserByID(user);
        updateAllUserForServer();
        return userByID;
    }


    /**
     * @Author Tptogiar
     * @Description 对修改用户信息进行相应
     * @Date 2021/5/23-19:33
     */
    public static void correctInfo(User user) throws IOException {
        TipType correctResult = UserSvr.getInstance().correctUserInfo(user);
        Response response = ResponseAsmFactory.assembleResp(InterType.CORRECT_INFO, correctResult,user);
        int userID = user.getUserID();
        Connect connect = ConnectSvr.conns.get(userID);
        ConnectSvr.sendResponse(response,connect);
    }
}
