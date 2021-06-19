package server.controller;

import common.Tip;
import common.inter.InterType;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.ServerMain;
import server.model.entity.User;
import server.model.server.ServerCenter;
import server.model.server.SvrStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Tptogiar
 * @Descripiton: 有时候一个界面事件可能需要涉及到不同的界面，
 * 用到不同的界面控制器，如果界面事件分散在各个控制器类内则需要
 * 共享控制器，事件显得零散，故将所有控制器集中于此，事件也将在这里执行
 * @creat 2021/05/15-17:16
 */


public class SvrEventList {

    public static MainCtrl mainCtrl;
    public static LoginCtrl loginCtrl;

    private static Stage loginStage;
    private static Stage svrStage;
    /**
     * @Author: Tptogiar
     * @Description: 登录服务器
     * @Date: 2021/5/15-17:25
     */
    public static void interfaceLogin() throws IOException {
        loginStage = new Stage();
        FXMLLoader svrMainLoader = new FXMLLoader(ServerMain.class.getResource("../resources/fxml/server/ServerLogin.fxml"));
        AnchorPane load = (AnchorPane)svrMainLoader.load();
        SvrEventList.loginCtrl = svrMainLoader.getController();
        loginStage.setScene(new Scene(load));
        loginStage.setResizable(false);
        loginStage.show();
    }

    /**
     * @Author: Tptogiar
     * @Description: 检查用户名即密码是否正确
     * @Date: 2021/5/15-18:21
     */
    public static void checkLogin(String userName, String psw) throws IOException {
        if(! SvrStatus.userName.equals(userName) || ! SvrStatus.password.equals(psw)){
            Tip.error("密码错误请重试");
        }else{
            activateServer();
        }
    }




    /**
     * @Author: Tptogiar
     * @Description: 登录成功后启动服务器
     * @Date: 2021/5/15-17:25
     */
    public static void activateServer() throws IOException {
        svrStage = new Stage();
        FXMLLoader svrMainLoader = new FXMLLoader(ServerMain.class.getResource("../resources/fxml/server/ServerMain.fxml"));
        AnchorPane load = (AnchorPane)svrMainLoader.load();
        SvrEventList.mainCtrl = svrMainLoader.getController();
        svrStage.setScene(new Scene(load));
        svrStage.setResizable(false);
        svrStage.show();
        //监听窗口关闭
        svrStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
//        loginStage.close();
        ServerCenter.updateAllUserForServer();
        SvrStatus.listenClientsRequest();
    }


    /**
     * @Author Tptogiar
     * @Description 将所用用户展示在控制面板上
     * @Date 2021/5/22-10:47
     * @return
     */
    public static void displayAllUsers(List<User> users) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    mainCtrl.userContain.getChildren().clear();
                    for (int i = 0; i < users.size(); i++) {
                        UserCellCtrl userCellCtrl = interfaceUserCell();
                        userCellCtrl.initUser(users.get(i));
                        mainCtrl.userContain.getChildren().add(userCellCtrl.root);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * @Author Tptogiar
     * @Description 加载用户卡片
     * @Date 2021/5/22-11:13
     */
    public static UserCellCtrl interfaceUserCell() throws IOException {
        FXMLLoader userCellLoader = new FXMLLoader(ServerMain.class.getResource("../resources/fxml/server/UserCell.fxml"));
        userCellLoader.load();
        return (UserCellCtrl) userCellLoader.getController();
    }



    /**
     * @Author Tptogiar
     * @Description 加载用户详细信息界面
     * @Date 2021/5/22-14:55
     * @param user
     * @param userDetailStage
     */
    public static UserDetailCtrl interfaceUserDetail(User user, Stage userDetailStage) throws IOException {
        FXMLLoader userDetailLoader = new FXMLLoader(ServerMain.class.getResource("../resources/fxml/server/UserDetail.fxml"));
        userDetailLoader.load();
        UserDetailCtrl controller = (UserDetailCtrl) userDetailLoader.getController();
        controller.initUser(user,userDetailStage);
        return controller;
    }

    /**
     * @Author Tptogiar
     * @Description 将用户强制下线
     * @Date 2021/5/22-15:31
     * @return
     */
    public static User forceOffOnline(User user) throws IOException {
        return ServerCenter.forceOffOnline(user, InterType.FORCE_OFFLINE);
    }

    /**
     * @Author Tptogiar
     * @Description 给用户发送提醒
     * @Date 2021/5/22-15:31
     */
    public static void sendTip(User user, String text) throws IOException {
        ServerCenter.sendTip(user,text);
    }


    /**
     * @Author Tptogiar
     * @Description 加载弹窗提醒发送窗
     * @Date 2021/5/22-16:09
     * @return
     */
    public static PopupTipCtrl interfacePopupTip(User user) throws IOException {
        FXMLLoader popupTipLoader = new FXMLLoader(ServerMain.class.getResource("../resources/fxml/server/PopupTip.fxml"));
        popupTipLoader.load();
        PopupTipCtrl controller = (PopupTipCtrl) popupTipLoader.getController();
        controller.init(user);
        return controller;
    }

    public static void deleteUser(User user) throws IOException {
        ServerCenter.deleteUser(user);
    }

    public static User addUserToBlockList(User user) throws IOException {
        return ServerCenter.addUserToBlockList(user);
    }

    public static User unlockInBlockList(User user) throws IOException {
       return ServerCenter.unlockInBlockList(user);
    }
}
