package com.generator.entity;

import java.io.Serializable;

public class ConnectionEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * 唯一值
     */
    private String key;
    /**
     * 链接名称
     */
    private String name;
    /**
     * 数据库类型 1-mysql
     */
    private int sqlType;
    /**
     * 链接ip
     */
    private String ip;
    /**
     * 端口
     */
    private String port;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 编码
     */
    private String chartSet;
    
    public String getKey()
    {
        return key;
    }
    
    public void setKey(String key)
    {
        this.key = key;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getSqlType()
    {
        return sqlType;
    }
    
    public void setSqlType(int sqlType)
    {
        this.sqlType = sqlType;
    }
    
    public String getIp()
    {
        return ip;
    }
    
    public void setIp(String ip)
    {
        this.ip = ip;
    }
    
    public String getPort()
    {
        return port;
    }
    
    public void setPort(String port)
    {
        this.port = port;
    }
    
    public String getDbName()
    {
        return dbName;
    }
    
    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getPwd()
    {
        return pwd;
    }
    
    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }
    
    public String getChartSet()
    {
        return chartSet;
    }
    
    public void setChartSet(String chartSet)
    {
        this.chartSet = chartSet;
    }

}
