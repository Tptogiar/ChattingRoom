package common.util;

import sun.rmi.server.LoaderHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Tptogiar
 * @Descripiton: 时间工具类
 * @creat 2021/05/21-12:25
 */


public class TimeUtil {

    /**
     * @Author Tptogiar
     * @Description 将LocalDateTime转换为比较好辨识的格式
     * @Date 2021/5/21-12:28
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime){
        DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("yyyy年MM月dd日  HH:mm");
        String formatTime = localDateTimeFormat.format(localDateTime.plusYears(1));
        return formatTime;
    }





}
