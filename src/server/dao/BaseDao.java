package server.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tptogiar
 * @Descripiton: 数据库的通用增删查改操作
 * @creat 2021/05/16-13:33
 */


public class BaseDao<T> {

    //记录每个子类对应的类型
    private Class<T> clazz=null;

    {
        //子类在继承BaseDAO时已经指明了类型，这里拿到类型并给到clazz,即把子类的类型记录起来，方便后面复用
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        clazz= (Class<T>)paramType.getActualTypeArguments()[0];
    }


    /**
     * @Author: Tptogiar
     * @Description: 通用的修改操作
     * @Date: 2021/5/16-21:43
     */
    public int update(Connection conn, String sql, Object... args) {// sql中占位符的个数与可变形参的长度相同！
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);// 小心参数声明错误！！
            }
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            JDBCUtil.closeResource(conn,preparedStatement,null);
        }
        return 0;
    }


    /**
     * @Author: Tptogiar
     * @Description: 查询指定一条数据的通用操作
     * @Date: 2021/5/16-22:27
     */
    public T getForOne(Connection conn, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }


            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();

            if (resultSet.next()) {
                T instance = clazz.newInstance();

                //遍历该表的栏，并给对应的类对象的属性赋值
                for (int i = 0; i < columnCount; i++) {
                    Object columValue = resultSet.getObject(i + 1);
                    //最好用别名，避免表中的列名与属性名不一致的情况
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    //利用反射拿到记录在clazz中的类型的属性
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(instance, columValue);
                }
                return instance;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//           JDBCUtil.closeResource(conn,preparedStatement,resultSet);
        }
        return null;
    }


    /**
     * @Author: Tptogiar
     * @Description: 当查询结果有多条时的通用操作
     * @Date: 2021/5/16-23:25
     */
    public List<T> getForList(Connection conn, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();

            int columnCount = metaData.getColumnCount();
            //储存查询结果的集合
            ArrayList<T> list = new ArrayList<T>();
            while (resultSet.next()) {
                T t = clazz.newInstance();

                for (int i = 0; i < columnCount; i++) {

                    Object columValue = resultSet.getObject(i + 1);

                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //用反射拿到该类的属性，并赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            JDBCUtil.closeResource(conn,preparedStatement,resultSet);
        }
        return null;
    }


    /**
     * @Author: Tptogiar
     * @Description: 用于特殊的查询
     * @Date: 2021/5/16-22:55
     */
    public static  <E> E getValue(Class E ,Connection conn,String sql,Object...args){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            for(int i = 0;i < args.length;i++){
                preparedStatement.setObject(i + 1, args[i]);

            }

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return (E) resultSet.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
//            JDBCUtil.closeResource(conn,preparedStatement,resultSet);
        }
        return null;
    }

}
