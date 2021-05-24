package common.inter.containtype;

import common.inter.Contain;
import common.inter.InterType;
import org.omg.CORBA.OBJ_ADAPTER;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


/**
 * @author Tptogiar
 * @Descripiton: 封装客户端发送给服务端的查询请求内容以及
 * 服务端的响应内容
 * @creat 2021/05/16-18:10
 */


public class EnquiryCon implements Contain , Serializable {



    private ArrayList keyObjects=new ArrayList();

    private ArrayList resultObject=new ArrayList();

    /**
     * @Author  Tptogiar
     * @Description  参数interType用来标记这个是查询请求的contain
     * 还是查询结果的contain
     * @Date  2021/5/19-14:11
     */
    public EnquiryCon(InterType interType,Object... objects) {
        if(InterType.REQUEST.equals(interType)){
            Collections.addAll(keyObjects, objects);
        }
        if(InterType.RESPONSE.equals(interType)){
            Collections.addAll(resultObject, objects);
        }
    }

    public ArrayList getKeyObjects() {
        return keyObjects;
    }

    public ArrayList getResultObject() {
        return resultObject;
    }
}
