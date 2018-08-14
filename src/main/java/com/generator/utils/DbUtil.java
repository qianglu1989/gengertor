package com.generator.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description: 数据库连接工具类.
 * @author Jack
 * @date 2017年9月26日 下午7:18:55
 *
 */
public class DbUtil
{
    
    private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);
    
    /**
     * 加载驱动
     */
    static
    {
        try
        {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);
            
            logger.info("加载驱动成功：{}", driverName);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Description: getConn:获取连接
     * @author Jack
     * @date 2017年10月9日 下午8:46:41
     *
     * @return
     */
    public static Connection getConn()
    {
        String jdbcUrl = "jdbc:mysql://10.4.89.151:3306/ecejcallcenter?useUnicode=yes&amp;characterEncoding=UTF8&amp;allowMultiQueries=true";
        String userName = "usecct";
        String password = "PjQ0zBxxduxjaksi0";
        return getConn(jdbcUrl, userName, password);
    }
    
    /**
     * 
     * @Description: 获取连接
     * @author Jack
     * @date 2017年10月9日 下午8:47:00
     *
     * @return
     */
    public static Connection getConn(String jdbcUrl, String userName, String password)
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(jdbcUrl, userName, password);
        }
        catch(SQLException e)
        {
            logger.error("数据连接异常", e);
            e.printStackTrace();
        }
        return conn;
    }
    
    /**
     * 
     * @Description: 关闭连接
     * @author Jack
     * @date 2017年9月26日 下午7:23:34
     *
     * @param conn
     * @param stat
     * @param resultSet
     */
    public static void closeReso(Connection conn, Statement stat, ResultSet resultSet)
    {
        try
        {
            if(conn != null)
                conn.close();
            if(stat != null)
                stat.close();
            if(resultSet != null)
                resultSet.close();
            logger.info("关闭资源成功。。。");
        }
        catch(SQLException e)
        {
            logger.error("关闭连接异常", e);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        System.out.println(getConn());
    }
}
