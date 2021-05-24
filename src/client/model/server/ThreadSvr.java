package client.model.server;

import java.util.ArrayList;


/**
 * @author Tptogiar
 * @Descripiton: 提供出javafx线程以外的线程服务
 * @creat 2021/05/16-12:27
 */

//TODO 这个客户端线程服务还没完善，只是先让程序跑起来
public class ThreadSvr {

    static ArrayList<Thread> threads=new ArrayList<Thread>();

    public static void executeNewTask(Runnable runnable){
        Thread thread = new Thread(runnable);
        threads.add(thread);
        thread.start();
    }




}
