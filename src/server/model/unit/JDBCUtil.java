package server.model.unit;


import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/05/16-21:45
 */


public class JDBCUtil {


    public static void closeResource(Connection conn, Statement statement, ResultSet resultSet){
        try{
            if(conn!=null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(statement!=null){
                statement.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(resultSet!=null){
                resultSet.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * @Author: Tptogiar
     * @Description: 在网上找了一下好像要直接执行sql脚本
     * 得用Spring框架内提供的工具，或是用其他数据库框架
     * 既然JDBC没有封装好的直接执行sql脚本的工具，
     * 就自己写一个，这里把sql脚本文件用
     * 正则根据“;”把每一条语句拿出来给调用者直接使用
     * @Date: 2021/5/18-19:14
     */
    public static List<String> runInitSql(){
        try {
            File sqlFile = new File("src\\resources\\initDataBase.sql");
            FileReader fileReader = new FileReader(sqlFile);
            StringBuffer stringBuffer = new StringBuffer();
            int len=0;
            char[] buffer=new char[1024];
            while ((len=fileReader.read(buffer))!=-1){
                stringBuffer.append(buffer,0,len);
            }
            String[] split = stringBuffer.toString().split(";");

            List<String> result=new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                String trim = split[i].trim();
                if(trim.length()>0){
                    result.add(trim);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
