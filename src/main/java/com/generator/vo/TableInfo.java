package com.generator.vo;

import java.util.List;

public class TableInfo
{
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 主键列名称
     */
    private String primaryKey;
    /**
     * 列集合
     */
    private List<ColumnInfo> columnList;
    
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
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getPrimaryKey()
    {
        return primaryKey;
    }
    
    public void setPrimaryKey(String primaryKey)
    {
        this.primaryKey = primaryKey;
    }
    
    public List<ColumnInfo> getColumnList()
    {
        return columnList;
    }
    
    public void setColumnList(List<ColumnInfo> columnList)
    {
        this.columnList = columnList;
    }
}
