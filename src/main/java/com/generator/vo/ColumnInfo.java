package com.generator.vo;

public class ColumnInfo
{
    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 是否主键 紧支持单一主键
     */
    private boolean isPrimaryKey;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 字段长度
     */
    private int len;
    /**
     * 字段精度
     */
    private int precision;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public boolean isPrimaryKey()
    {
        return isPrimaryKey;
    }
    
    public void setPrimaryKey(boolean isPrimaryKey)
    {
        this.isPrimaryKey = isPrimaryKey;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public int getLen()
    {
        return len;
    }
    
    public void setLen(int len)
    {
        this.len = len;
    }
    
    public int getPrecision()
    {
        return precision;
    }
    
    public void setPrecision(int precision)
    {
        this.precision = precision;
    }
}
