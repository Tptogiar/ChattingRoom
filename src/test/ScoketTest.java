package test;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/05/16-11:01
 */


public class ScoketTest {

    @Test
    public void service() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(555);
        Socket accept = serverSocket.accept();
        System.out.println("接受到来自客户端的连接");
        Thread.sleep(5000);
        OutputStream outputStream = accept.getOutputStream();
        System.out.println("output");
        Thread.sleep(5000);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
        System.out.println("objectOutput");
        Thread.sleep(5000);
        InputStream inputStream = accept.getInputStream();
        System.out.println("\ninput");
        Thread.sleep(5000);
        ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
        System.out.println("ObjectInput");
        Thread.sleep(50000);
    }


    @Test
    public void client() throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 555);
//        Thread.sleep(5000);
        OutputStream outputStream = socket.getOutputStream();
        System.out.println("output");
//        Thread.sleep(5000);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("objectOutput");
//        Thread.sleep(5000);
        InputStream inputStream = socket.getInputStream();
        System.out.println("\ninput");
//        Thread.sleep(5000);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        System.out.println("objectInput");
    }
}
