package server.controller;

import common.Tip;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.model.server.SvrStatus;

import java.io.IOException;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/05/15-10:56
 */


public class LoginCtrl {


    public Button btn;
    public TextField userName;
    public PasswordField password;


    public void handleButtonClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource()==btn){
            String userNameText = userName.getText();
            String passwordText = password.getText();
            if(userNameText.isEmpty() || passwordText.isEmpty()){
                Tip.error("输入有误，请重新输入");
                return;
            }
            if(! SvrStatus.userName.equals(userNameText)|| ! SvrStatus.password.equals(passwordText)){
                Tip.error("密码错误请重试");
            }else{
                SvrEventList.activateServer();
            }
        }
    }
}
