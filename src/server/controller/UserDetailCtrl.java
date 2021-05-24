package server.controller;

import common.Tip;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.model.entity.User;
import server.model.server.ServerCenter;

import java.io.IOException;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/05/22-11:58
 */


public class UserDetailCtrl {


    public AnchorPane root;
    public Label userID;
    public Label userName;
    public Label userPassword;
    public Label description;
    public Button forceOffOnline;
    public Button sendTip;
    public ImageView userPhoto;
    public Button toBlockList;
    public Button deleteUser;
    public Button unlockInBlockList;
    private User user;
    private Stage sendStage=new Stage();
    private Stage ownStage;




    public void initUser(User user, Stage userDetailStage) throws IOException {
        this.user=user;

        int userIDInt = user.getUserID();
        String userNameText = user.getUserName();
        String userPasswordText = user.getUserPassword();
        String descriptionText = user.getDescription();

        userID.setText(String.valueOf(userIDInt));
        userName.setText(userNameText);
        userPassword.setText(userPasswordText);
        description.setText(descriptionText);
        PopupTipCtrl popupTipCtrl = SvrEventList.interfacePopupTip(user);
        sendStage.setResizable(false);
        sendStage.setScene(new Scene(popupTipCtrl.root));

        ownStage=userDetailStage;
    }


    public void initUser(User user) throws IOException {
        this.user=user;
        int userIDInt = user.getUserID();
        String userNameText = user.getUserName();
        String userPasswordText = user.getUserPassword();
        String descriptionText = user.getDescription();

        userID.setText(String.valueOf(userIDInt));
        userName.setText(userNameText);
        userPassword.setText(userPasswordText);
        description.setText(descriptionText);
        PopupTipCtrl popupTipCtrl = SvrEventList.interfacePopupTip(user);
        sendStage.setResizable(false);
        sendStage.setScene(new Scene(popupTipCtrl.root));
    }



    public void deleteUserHandle(ActionEvent actionEvent) throws IOException {
        SvrEventList.deleteUser(user);
        ownStage.close();
    }

    public void toBlockListHandle(ActionEvent actionEvent) throws IOException {
        if (user.isBlock()){
            Tip.info("该用户已经加入黑名单");
            return;
        }
        User newUser = SvrEventList.addUserToBlockList(this.user);
        initUser(newUser);
    }


    public void unlockInBlockListHandle(ActionEvent actionEvent) throws IOException {
        if (! user.isBlock()){
            Tip.info("该用户未在黑名单");
            return;
        }
        User newUser = SvrEventList.unlockInBlockList(this.user);
        initUser(newUser);
    }

    public void sendTipHandle(ActionEvent actionEvent) {
        if (! user.isOnline()){
            Tip.info("用户未上线，不能发送即时提醒");
            return;
        }
        sendStage.show();
    }

    public void forceOffOnlineHandle(ActionEvent actionEvent) throws IOException {
        if (! user.isOnline()){
            Tip.info("用户未上线");
            return;
        }
        sendStage.close();
        User newUser = SvrEventList.forceOffOnline(this.user);
        initUser(newUser);
    }

}