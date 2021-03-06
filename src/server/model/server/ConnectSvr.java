package server.model.server;

import common.Tip;
import common.inter.InterType;
import common.inter.containtype.UserCon;
import common.inter.Request;
import common.inter.Response;
import server.model.entity.Connect;
import server.model.entity.User;
import server.model.factory.RequestDivFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author Tptogiar
 * @Descripiton: 管理当前服务端与客户端的连接
 * @creat 2021/05/15-19:34
 */


public class ConnectSvr {

    public static HashMap<Integer,Connect> conns=new HashMap<>();

    public ConnectSvr() {
    }



    public  void addConnect(Socket accept) throws IOException {
        Connect conn=new Connect(accept);
        InterType submitStatus = SvrStatus.myThreadPool.submit(conn,new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        //TODO 这里需要处理读到不属于Request内容时不中断监听  不能while简单往外提
                        Request request = (Request) conn.getObjInStm().readObject();
                        RequestDivFactory.divideRes(request, conn);
                    }
                } catch (IOException e) {
                    /*如果这里的connect抛出异常，就认为是客户端掉线了，
                     * 所以在此处进行客户端的掉线处理
                     * 即把user表内对应的user的isOnline设置为0
                     * 当然如果该客户端还没登录成功（user和connect还没绑定，User为null）
                     * 就不需要进行掉线处理了，本来就还没上线
                     * */

                    User user = conn.getUser();
                    if ("Connection reset".equals(e.getMessage())) {
                        if (conn.isAllow()){
                            //线程池里的worker要除掉一个
                            SvrStatus.myThreadPool.clientShutdown();
                        }
                        if (user == null) {
                            return;
                        }
                        try {
                            ServerCenter.markUserAsOffline(user);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    } else {
                        e.printStackTrace();
                    }
                }
                catch (ClassCastException e){
                    //在这里捕获乱入的Object(非Request类型)，并骚做处理
                    Tip.info("有非法连接介入");
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        //将服务器对于本次连接的响应状况返回给客户端
        Response response = new Response(submitStatus, null);
        conn.getObjOutStm().writeObject(response);

    }


    /**
     * @Author Tptogiar
     * @Description 当发送的是登录请求时，将此时登录的用户与给连接绑定起来
     * 服务端得意记录每一连接与用户的对应关系
     * @Date 2021/5/20-21:21
     */
    public static void sendRepLoginSucceed(Response response, Connect conn) throws IOException {
        UserCon contain = (UserCon) response.getContain();
        User user = contain.getUser();
        conn.setUser(user);
        conn.getObjOutStm().writeObject(response);
    }

    /**
     * @Author Tptogiar
     * @Description 发送其他Response
     * @Date 2021/5/20-21:22
     */
    public static void sendResponse(Response response,Connect conn) throws IOException {
        conn.getObjOutStm().writeObject(response);
    }





}
