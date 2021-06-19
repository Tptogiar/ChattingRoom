package test;

import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/06/19-14:01
 */


public class TestObjectStream {


    @Test
    public void testObjectStream() throws IOException {

        Socket socket = new Socket("localhost", 555);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream.writeObject("hong");


    }









}
