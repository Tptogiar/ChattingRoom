package client.controller;

import client.model.server.ClientInterfaceEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import javax.tools.Tool;
import java.io.File;

/**
 * @author Tptogiar
 * @Descripiton: 文件接收界面
 * @creat 2021/05/23-10:32
 */


public class FileReceiveCtrl {


    public AnchorPane root;
    public Button chose;
    public Label path;
    public Button save;
    public Label sendUserName;
    private byte[] fileBytes;
    File saveFile;


    public void init(File file, String sendUserNameText, byte[] fileBytes) {

        sendUserName.setText(sendUserNameText);

        this.saveFile=new File("src\\UserData\\"+file.getName());
        this.fileBytes=fileBytes;

        String absolutePath = saveFile.getAbsolutePath();
        path.setText(absolutePath);
        Tooltip.install(path,new Tooltip(absolutePath));
    }

    public void handleButtonClicked(ActionEvent actionEvent) {
        if (actionEvent.getSource()==chose){
            File dir=ClientInterfaceEvent.choseDir();
            if (dir==null){
                return;
            }
            saveFile=new File(dir.getAbsolutePath()+"\\"+saveFile.getName());
            Tooltip.install(path,new Tooltip(saveFile.getAbsolutePath()));
        }

        else if (actionEvent.getSource()==save){
            ClientInterfaceEvent.saveMessageFile(saveFile,fileBytes);
        }




    }
}
