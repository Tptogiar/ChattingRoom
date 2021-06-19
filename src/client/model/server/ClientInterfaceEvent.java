package client.model.server;

import client.ClientMain;
import client.controller.*;
import common.Tip;
import common.TipType;
import common.inter.containtype.MessageCon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.model.entity.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author Tptogiar
 * @Descripiton  客户端的界面事件汇总,只为界面事件服务
 * @creat 2021/05/16-00:09
 */


public class ClientInterfaceEvent {

    static Stage loginStage=null;
    static Stage mianStage=null;
    static Stage register=null;

    static MainCtrl mainCtrl=null;
    static HashMap<Integer, UserCellCtrl> userCellCtrlIndex=new HashMap<>();

    //有别于其他userCell，其代表公共频道
    public static final int PUBLIC =0;

    static Stage fileReceiveStage=null;
    static Stage userProfielStage;


    static {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    userProfielStage=new Stage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @Author  Tptogiar
     * @Description  加载登录界面
     * @Date  2021/5/18-20:02
     */
    public static void interfaceLogin() throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    loginStage = new Stage();
                    FXMLLoader clientLoginLoader = new FXMLLoader(ClientMain.class.getResource("../resources/fxml/client/ClientLogin.fxml"));
                    AnchorPane load = (AnchorPane)clientLoginLoader.load();
                    loginStage.setScene(new Scene(load));
                    loginStage.setResizable(false);
                    loginStage.show();
                    loginStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                            confirm.setTitle("退出？");
                            confirm.setHeaderText("确认退出？");
                            Optional<ButtonType> result = confirm.showAndWait();
                            if (result.get() == ButtonType.OK){
                                System.exit(0);
                            } else {
                                event.consume();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });






    }

    /**
     * @Author  Tptogiar
     * @Description  客户端点击登录按钮后界面事件
     * @Date  2021/5/16-0:16
     */
    public static void sendLogin(String id, String password) throws Exception {
        ClientInterEvent.sendLogin(id,password);
    }

    /**
     * @Author  Tptogiar
     * @Description  接收到服务器发送回来的 登录成功 后执行的界面事件
     * @Date  2021/5/16-15:38
     */
    public static void loginSucceed()  {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    loginStage.close();
                    interfaceMainAndPublic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * @Author  Tptogiar
     * @Description  登录成功后加载主界面,private只在内部调用即可
     * @Date  2021/5/17-23:25
     */
    public static void interfaceMainAndPublic() throws Exception {
        mianStage = new Stage();
        FXMLLoader clientMainLoader = new FXMLLoader(ClientMain.class.getResource("../resources/fxml/client/ClientMain.fxml"));
        AnchorPane load = (AnchorPane)clientMainLoader.load();
        mainCtrl = (MainCtrl) clientMainLoader.getController();
        mianStage.setScene(new Scene(load));
        FXMLLoader publicchatLoader = new FXMLLoader(ClientMain.class.getResource("../resources/fxml/client/ClientChatWin.fxml"));
        publicchatLoader.load();
        //加载公共频道，并作为mainCtrl的属性，且添加索引userCellCtrlIndex中
        ClientChatWinCtrl publicChatCtrl = (ClientChatWinCtrl) publicchatLoader.getController();
        publicChatCtrl.init("公共频道");
        UserCellCtrl userCellCtrl = new UserCellCtrl();
        userCellCtrl.init(publicChatCtrl);
        mainCtrl.publicChatWin =publicChatCtrl.root;
        userCellCtrlIndex.put(PUBLIC,userCellCtrl);

        mianStage.setResizable(false);
        mianStage.show();
        /**
         * @Author Tptogiar
         * @Description 监听窗口关闭
         * @Date 2021/5/22-15:18
         */
        mianStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("退出？");
                confirm.setHeaderText("确认退出？");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.get() == ButtonType.OK){
                    System.exit(0);
                } else {
                    event.consume();
                }
            }
        });
        ClientInterEvent.init();
    }

    /**
     * @Author  Tptogiar
     * @Description  客户端点击注册后的界面事件
     * @Date  2021/5/18-20:04
     */
    public static void sendRegister(String id, String password) {
        ClientInterEvent.sendRegister(id,password);
    }

    /**
     * @Author  Tptogiar
     * @Description  加载注册界面
     * @Date  2021/5/18-0:29
     */
    public static void interfaceRegister() throws IOException {
        register=new Stage();
        FXMLLoader clientRegisterLoader = new FXMLLoader(ClientMain.class.getResource("../resources/fxml/client/CilentRegister.fxml"));
        AnchorPane load = (AnchorPane)clientRegisterLoader.load();
        register.setScene(new Scene(load));
        register.setResizable(false);
        register.show();
    }
    /**
     * @Author  Tptogiar
     * @Description  接受到服务端反馈的 注册成功 后的提醒
     * 相比于其他普通提醒，由于还需要额外的操作：关闭注册窗口
     * 所以没有整合到提醒popTip函数里
     * @Date  2021/5/17-23:26
     * @param registerResult
     */
    public static void registerSucceed(TipType registerResult) {
        Tip.info(registerResult.getTipText());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    register.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * @Author  Tptogiar
     * @Description  弹出来自服务端的各种提醒
     * @Date  2021/5/18-19:56
     */
    public static void popTip(TipType tipType){
        Tip.info(tipType.getTipText());
    }



    /**
     * @Author  Tptogiar
     * @Description  加载聊天列表的用户卡片，private只在内部调用即可
     * @Date  2021/5/19-0:44
     */
    private static UserCellCtrl interfaceUserCell() throws IOException {
        FXMLLoader userCellLoader = new FXMLLoader(ClientMain.class.getResource("../resources/fxml/client/UserCell.fxml"));
        AnchorPane load = (AnchorPane)userCellLoader.load();
        UserCellCtrl userCellCtrl = (UserCellCtrl) userCellLoader.getController();
        return userCellCtrl;
    }



    /**
     * @Author Tptogiar
     * @Description 将在线用户展现在界面上，并将它们对应的控制器储存起来给后面使用
     * @Date  2021/5/19-0:16
     */
    public static void displayOnlineUsers(List onlineUsers) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    mainCtrl.chatList.getChildren().clear();
                    for (Object onlineUser : onlineUsers) {
                        User user = (User) onlineUser;
                        int userID = user.getUserID();


                        UserCellCtrl userCellCtrl = interfaceUserCell();

                        //加载出这个user对应的聊天窗体
                        ClientChatWinCtrl clientChatWinCtrl = interfaceChatWin(user);

                        //将这张卡片与对应的用户和聊天窗体绑定起来
                        userCellCtrl.init(user,clientChatWinCtrl);
                        //将控制器储存起来
                        userCellCtrlIndex.put(userID,userCellCtrl);
                        mainCtrl.chatList.getChildren().add(userCellCtrl.root);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @Author Tptogiar
     * @Description 想聊天列表中添加联系人
     * @Date 2021/5/22-18:00
     */
    public static void displayNewUser(User user){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                        int userID = user.getUserID();
                        UserCellCtrl userCellCtrl = interfaceUserCell();
                        //加载出这个user对应的聊天窗体
                        ClientChatWinCtrl clientChatWinCtrl = interfaceChatWin(user);
                        //将这张卡片与对应的用户和聊天窗体绑定起来
                        userCellCtrl.init(user,clientChatWinCtrl);
                        //将控制器储存起来
                        userCellCtrlIndex.put(userID,userCellCtrl);
                        mainCtrl.chatList.getChildren().add(userCellCtrl.root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * @Author Tptogiar
     * @Description 展示聊天对话界面
     * @Date  2021/5/19-15:47
     */
    public static ClientChatWinCtrl interfaceChatWin(User user) throws Exception {
        FXMLLoader chatWinLoader = new FXMLLoader(ClientMain.class.getResource("../resources/fxml/client/ClientChatWin.fxml"));
        mainCtrl.chatContain.getChildren().clear();
        AnchorPane load = (AnchorPane) chatWinLoader.load();
        ClientChatWinCtrl clientChatWinCtrl = (ClientChatWinCtrl) chatWinLoader.getController();
        clientChatWinCtrl.init(user);
        return clientChatWinCtrl;
    }

    /**
     * @Author Tptogiar
     * @Description 发送文本消息
     * @Date 2021/5/19-16:58
     */
    public static void sendTextMessage(int sendUserID,String sendUserName,int receiverUserID ,String messageText, LocalDateTime transferTime) {
        ClientInterEvent.sendTextMessage(sendUserID,sendUserName,receiverUserID,messageText,transferTime);
    }

    /**
     * @Author Tptogiar
     * @Description 将客户端接收到的消息展示在对应聊天窗里面
     * @Date 2021/5/20-12:48
     */
    public static void displayMessage(UserCellCtrl userCellCtrl,MessageCon messageCon) throws IOException {
        //TODO 此处加载服务端返回的聊天记录时，由于聊天记录的对方还没有上线，所以
        //对应的userCellCtrl还没有被加载出来，所以会报空指针问题，所以需要先去实现加载将联系过的用户
        //或先将消息记录存起来，等消息记录对应的用户上线了再加载出来
        //所以这里暂时先将null return掉
        if(userCellCtrl==null){
            return;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    userCellCtrl.getClientChatWinCtrl().displayNewMessage(messageCon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static MessageCellCtrl interfaceMessageCell() throws IOException {
        FXMLLoader messageCellLoader = new FXMLLoader(ClientMain.class.getResource("../resources/fxml/client/MessageCell.fxml"));
        AnchorPane load = (AnchorPane) messageCellLoader.load();
        return (MessageCellCtrl) messageCellLoader.getController();
    }

    /**
     * @Author Tptogiar
     * @Description 打开user对应的聊天窗口
     * @Date 2021/5/20-13:28
     */
    public static void displayChatWin(ClientChatWinCtrl clientChatWinCtrl, int userID) throws IOException {
        mainCtrl.chatContain.getChildren().clear();
        mainCtrl.chatContain.getChildren().add(clientChatWinCtrl.root);
        readyForMessage(userID);
    }

    /**
     * @Author Tptogiar
     * @Description 将聊天记录从缓存ClientStatus.chattingRecrodBuffer中拿出来后添加到对应的聊天窗体中
     * @Date 2021/5/23-14:04
     * @param userID
     */
    public static void readyForMessage(int userID) throws IOException {
        UserCellCtrl userCellCtrl = ClientInterfaceEvent.userCellCtrlIndex.get(userID);
        //如果此时消息所对应的用户还没有上线，则缓存中的messageCon不展示，也不清楚，等着下次
        if (userCellCtrl==null){
            return;
        }
        ArrayList<MessageCon> messageCons = ClientStatus.chattingRecrodBuffer.get(userID);
        //如果缓存中还没内容
        if(messageCons==null){
            return;
        }
        for (int i = 0; i < messageCons.size(); i++) {
            displayMessage(userCellCtrl,messageCons.get(i));
        }
        messageCons.clear();
    }



    /**
     * @Author Tptogiar
     * @Description 展示公共聊天窗口
     * @Date 2021/5/22-22:21
     */
    public static void displayPublicChatWin() {
        mainCtrl.chatContain.getChildren().clear();
        mainCtrl.chatContain.getChildren().add(mainCtrl.publicChatWin);

    }

    /**
     * @Author Tptogiar
     * @Description 发送给所有在线用户
     * @Date 2021/5/22-23:14
     */
    public static void sendTextMessageToOnline(int sendUserID, String sendUserName, int receiverUserID, String messageText, LocalDateTime transferTime) {
        ClientInterEvent.sendTextMessageToOnline(sendUserID,sendUserName,receiverUserID,messageText,transferTime);

    }

    /**
     * @Author Tptogiar
     * @Description 选择需要传输的文件
     * @Date 2021/5/23-1:18
     * @param sendID
     * @param sendUserName
     * @param receiverID
     */
    public static void choseFile(int sendID, String sendUserName, int receiverID) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择传输的文件");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter( "All files","*.*"));
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            ClientInterEvent.sendFile(sendID,sendUserName,receiverID,file);
        }
    }





    /**
     * @Author Tptogiar
     * @Description 加载文件保存界面
     * @Date 2021/5/23-10:35
     * @return
     */
    public static FileReceiveCtrl interfaceFilereceive(File file, String sendUserName, byte[] fileBytes) throws IOException {
        FXMLLoader fileReceiveLoader = new FXMLLoader(ClientMain.class.getResource("../resources/fxml/client/FileReceive.fxml"));
        AnchorPane load = (AnchorPane)fileReceiveLoader.load();
        FileReceiveCtrl controller = (FileReceiveCtrl) fileReceiveLoader.getController();
        controller.init(file,sendUserName,fileBytes);
        return controller;
    }

    /**
     * @Author Tptogiar
     * @Description 展示文件接收窗
     * @Date 2021/5/23-10:43
     * @param sendUserName
     * @param fileBytes
     * @param fileName
     */
    public static void displayFileReceiveWin(String sendUserName, byte[] fileBytes, String fileName) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    fileReceiveStage = new Stage();
                    File defaultFilePath = new File(ClientStatus.fileDir+fileName);
                    fileReceiveStage.setScene(new Scene(interfaceFilereceive(defaultFilePath,sendUserName,fileBytes).root));
                    fileReceiveStage.setResizable(false);
                    fileReceiveStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * @Author Tptogiar
     * @Description 保存收到的文件
     * @Date 2021/5/23-11:09
     */
    public static void saveMessageFile(File saveFile, byte[] fileBytes) {
        ClientSvr.saveMessageFile(saveFile,fileBytes);
        fileReceiveStage.close();
    }


    /**
     * @Author Tptogiar
     * @Description 选择目录
     * @Date 2021/5/23-13:45
     */
    public static File choseDir() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("请选择目录");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = directoryChooser.showDialog(new Stage());
        if (file!=null){
            return file;
        }
        return null;
    }



    /**
     * @Author Tptogiar
     * @Description 加载用户信息界面
     * @Date 2021/5/23-18:26
     * @return
     */
    public static UserProfileCtrl interfaceUserProfile() throws IOException {
        FXMLLoader userProfileLoader = new FXMLLoader(ClientMain.class.getResource("../resources/fxml/client/UserProfile.fxml"));
        AnchorPane load = (AnchorPane)userProfileLoader.load();
        UserProfileCtrl controller = (UserProfileCtrl) userProfileLoader.getController();
        return controller;
    }

    /**
     * @Author Tptogiar
     * @Description 展示用户信息界面
     * @Date 2021/5/23-18:31
     */
    public static void displayUserProfile() throws IOException {
        UserProfileCtrl userProfileCtrl = interfaceUserProfile();
        mainCtrl.setUserProfileCtrl(userProfileCtrl);
        userProfileCtrl.init(ClientStatus.user);
        userProfielStage.setScene(new Scene(userProfileCtrl.root));
        userProfielStage.show();
        /**
         * @Author Tptogiar
         * @Description 添加焦点监听，失去焦点就把该窗口关闭
         * @Date 2021/5/26-14:15
         */
        userProfielStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                userProfielStage.close();
            }
        });
    }

    /**
     * @Author Tptogiar
     * @Description 修改用户信息
     * @Date 2021/5/23-19:24
     */
    public static void correctInfo(int userID, String newUserName, String password, String newPassword) {
        ClientInterEvent.correctInfo(userID,newUserName,password,newPassword);
    }
}
