package client.controller;

import client.model.server.ClientInterfaceEvent;
import client.model.server.ClientStatus;
import common.Tip;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import server.model.entity.User;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/05/23-18:27
 */


public class UserProfileCtrl {


    public TextArea description;
    public TextField userName;
    public Button save;
    public PasswordField password;
    public PasswordField newPassword;
    public AnchorPane root;

    public void init(User user) {
        String userNameText = user.getUserName();
        String userPasswordText = user.getUserPassword();
        String descriptionText = user.getDescription();
        userName.setText(userNameText);
        password.setText(userPasswordText);
        newPassword.setText(userPasswordText);
        description.setText(descriptionText);
    }

    public void handldButtonClicked(ActionEvent actionEvent) {
        if (actionEvent.getSource()==save){
            String newUserName = userName.getText();
            String passwordText = password.getText();
            String newPasswordText = newPassword.getText();


            if(newUserName.isEmpty()){
                Tip.error("用户名不能为空");
                return;
            }
            if(passwordText.isEmpty() || newPasswordText.isEmpty()){
                Tip.error("密码不能为空");
                return;
            }
            if(newPasswordText.length()>20 || newPasswordText.length()<6){
                Tip.error("密码长度不能小于6且不能大于20");
                return;
            }
            ClientInterfaceEvent.correctInfo(ClientStatus.user.getUserID(),newUserName,passwordText,newPasswordText);

        }



    }
}
