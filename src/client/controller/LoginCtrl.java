package client.controller;

import client.model.server.ClientInterfaceEvent;
import common.Tip;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/05/15-10:54
 */


public class LoginCtrl implements Initializable {


    public Button login;
    public Button regsiter;
    public TextField userName;
    public PasswordField password;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    public void handleButtonClicks(ActionEvent actionEvent) throws Exception {
        if(actionEvent.getSource()==login){
            if(userName.getText().isEmpty() || password.getText().isEmpty()){
                Tip.error("输入错误，请重新输入");
                return;
            }
            ClientInterfaceEvent.sendLogin(userName.getText(), password.getText());
        }else if(actionEvent.getSource()==regsiter){
            ClientInterfaceEvent.interfaceRegister();
        }


    }


}
