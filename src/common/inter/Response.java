package common.inter;

import java.io.Serializable;

/**
 * @author Tptogiar
 * @Descripiton: 封装服务端对客户端的回应
 * @creat 2021/05/15-21:56
 */


public class Response implements Serializable {

    private InterType interType;
    private Contain contain;



    public Response() {
    }

    public Response(InterType interType, Contain contain) {
        this.interType = interType;
        this.contain = contain;
    }





    public InterType getInterType() {
        return interType;
    }

    public void setInterType(InterType interType) {
        this.interType = interType;
    }

    public Contain getContain() {
        return contain;
    }

    public void setContain(Contain contain) {
        this.contain = contain;
    }
}
