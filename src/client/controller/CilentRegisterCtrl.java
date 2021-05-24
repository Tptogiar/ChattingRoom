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
 * @creat 2021/05/18-00:17
 */


public class CilentRegisterCtrl implements Initializable {
    public TextField userName;

    public Button register;
    public PasswordField passwordAgain;
    public PasswordField password;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleButtonClicks(ActionEvent actionEvent) {
        if(actionEvent.getSource()==register){
            if(userName.getText().isEmpty() || password.getText().isEmpty()
                || password.getText().isEmpty()){
                Tip.error("输入错误，请重新输入");
                return;
            }
            if (userName.getText().length()>15 || userName.getText().length()<1){
                Tip.error("用户名长度不能小于1且需小于15");
                return;
            }
            if(password.getText().length()>20 || password.getText().length()<6){
                Tip.error("密码长度不能小于6且不能大于20");
                return;
            }
            if(! passwordAgain.getText().equals(password.getText())){
                Tip.error("两次输入的密码不一致");
                return;
            }
            ClientInterfaceEvent.sendRegister(userName.getText(),password.getText());
        }



    }
}
