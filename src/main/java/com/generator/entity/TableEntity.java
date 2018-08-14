package com.generator.entity;

import java.util.List;
import com.generator.vo.TableInfo;

public class TableEntity extends TableInfo
{
    /**
     * 列集合
     */
    private List<ColumnEntity> columns;
    
    public List<ColumnEntity> getColumns()
    {
        return columns;
    }
    
    public void setColumns(List<ColumnEntity> columns)
    {
        this.columns = columns;
    }
}
