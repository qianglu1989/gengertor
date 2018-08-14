package com.generator.entity;

/**
 * 
 * @Description: Mapper实体
 * @author Jack
 * @date 2017年9月30日 上午11:49:49
 *
 */
public class MapperEntity
{
    private String entityName;
    
    private String packageName;
    
    private TableEntity tableEntity;
    
    private String fileName;

    public String getEntityName()
    {
        return entityName;
    }
    
    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }
    
    public String getPackageName()
    {
        return packageName;
    }
    
    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }
    
    public TableEntity getTableEntity()
    {
        return tableEntity;
    }
    
    public void setTableEntity(TableEntity tableEntity)
    {
        this.tableEntity = tableEntity;
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
}
