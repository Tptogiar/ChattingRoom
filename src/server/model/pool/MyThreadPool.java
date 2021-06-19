package server.model.pool;

import common.inter.InterType;
import server.model.entity.Connect;

import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Tptogiar
 * @Descripiton: 自定义的简易线程池
 * 不区分核心线程和非核心线程
 * @creat 2021/06/08-21:57
 */


public class MyThreadPool {

    private final static List<Runnable>tasks= Collections.synchronizedList(new LinkedList<>());
    private volatile int curThread=0;
    private volatile int corePoolSize;
    private volatile int maximumPoolSize;
    private volatile int tasksSize;


    public MyThreadPool(int corePoolSize,int maximumPoolSize, int tasksSize) {
        this.corePoolSize=corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.tasksSize = tasksSize;
    }


    public InterType submit(Connect connect, Runnable task){
        if(tasks.size()>tasksSize){
            return InterType.SERVER_TASKS_BUSY;
            //TODO 拒绝策略
        }else {
            tasks.add(task);
            return execTask(connect,task);
        }
    }


    private InterType execTask(Connect connect, Runnable task) {
        //核心现场，服务端的主线程
        if(curThread<corePoolSize){
            Myworker myworker = new Myworker(tasks);
            myworker.start();
            curThread++;
            return null;
        }
        //服务端的非核心线程，一个非核心线程对应一个客户端
        else if(curThread<maximumPoolSize){
            //TODO 此处单纯的new worker有点小问题
            // 因为在设定里面一个worker是对应一个客户端的
            // 而当客户端关掉之后，这里的work却没有销毁
            //有时间再改了

            Myworker myworker = new Myworker(tasks);
            myworker.start();
            curThread++;
            return InterType.SERVER_ALLOW_CONNECT;
        }else  {
            connect.setAllow(false);
            return InterType.SERVER_ONLINES_BUSY;
        }
    }

    /**
     * @Author Tptogiar
     * @Description 客户端关掉之后这里的线程数要减少
     * TODO 但是单单减少也是不行的，worker要进行销毁
     * 有时间再改了，先这样
     * @Date 2021/6/19-0:58
     */
    public void clientShutdown(){
        curThread--;
    }

//    private static class Mytask implements Runnable{
//        @Override
//        public void run() {
//
//        }
//
//    }










    private static class Myworker extends Thread{

        //记录其是第几个worker
        private int number;
        private List<Runnable> tasks;
        private static int count=0;

        public Myworker(List<Runnable> tasks) {
            count++;
            this.tasks = tasks;
            number=count;
        }

        @Override
        public void run() {
            while(tasks.size()>0){
                Runnable firstTask = tasks.remove(0);
                firstTask.run();
            }
        }
    }












}
