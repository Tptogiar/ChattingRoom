package server.model.pool;

/**
 * @author Tptogiar
 * @Descripiton: 自定义线程池
 * @creat 2021/05/16-09:35
 */

//TODO 这个服务端的线程池也还没完善，只是让程序先跑起来而已
public class ThreadPool {




    public static void addNewTask(Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.start();
    }











}
