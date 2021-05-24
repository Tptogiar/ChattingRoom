package server.model.entity;

import sun.nio.cs.ext.ISO2022_CN;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Tptogiar
 * @Descripiton: 用来描叙当下的一次连接
 * @creat 2021/05/15-19:37
 */


public class Connect {

    private LocalDateTime touchMoment;
    private ObjectInputStream objInStm;
    private ObjectOutputStream objOutStm;
    private User user;
    private Socket socket;
    /**
     * @Author: Tptogiar
     * @Description: 先objectOutput流再输入流，客户端也是，避免相互等待
     * @Date: 2021/5/16-12:25
     */
    public Connect(Socket accept) throws IOException {
        this.socket=accept;
        this.touchMoment=LocalDateTime.now();
        this.objOutStm=new ObjectOutputStream(accept.getOutputStream());
        this.objInStm=new ObjectInputStream(accept.getInputStream());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ObjectInputStream getObjInStm() {
        return objInStm;
    }

    public ObjectOutputStream getObjOutStm() {
        return objOutStm;
    }

    public Socket getSocket() {
        return socket;
    }
}
