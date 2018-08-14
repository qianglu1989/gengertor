package com.generator.entity;

import com.generator.vo.ColumnInfo;

public class ColumnEntity extends ColumnInfo
{
    /**
     * 对应属性
     */
    private String propType;
    
    /**
     * 属性名称
     */
    private String propName;
    
    public String getPropType()
    {
        return propType;
    }
    
    public void setPropType(String propType)
    {
        this.propType = propType;
    }
    
    public String getPropName()
    {
        return propName;
    }
    
    public void setPropName(String propName)
    {
        this.propName = propName;
    }
}
