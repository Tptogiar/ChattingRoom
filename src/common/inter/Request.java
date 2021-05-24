package common.inter;

import java.io.Serializable;

/**
 * @author Tptogiar
 * @Descripiton: 封装客户端对服务端的请求
 * @creat 2021/05/15-21:37
 */


public class Request implements Serializable {


    private InterType interType;
    private Contain contain;


    public Request () {

    }

    public Request (InterType interType, Contain contain) {
        this.interType = interType;
        this.contain=contain;
    }



    public InterType getReqType() {
        return interType;
    }

    public Contain getContain() {
        return contain;
    }
}
