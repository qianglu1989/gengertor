package com.generator.task;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.generator.entity.ConnectionEntity;
import com.generator.framework.AbstractApplicationTask;
import com.generator.framework.ApplicationContext;
import com.generator.framework.Constants;
import com.generator.utils.DbUtil;
import com.generator.utils.StringUtil;
import com.generator.vo.ColumnInfo;
import com.generator.vo.TableInfo;

/**
 * 
 * @Description: 得到当前连接中所有表信息
 * @author Jack
 * @date 2017年10月10日 下午4:57:08
 *
 */
public class InitTask extends AbstractApplicationTask
{
    private static final Logger logger = LoggerFactory.getLogger(InitTask.class);
    
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception
    {
        // 连接数据库
        Connection conn = null;
        ResultSet tableRs = null;
        ResultSet columnRs = null;
        ResultSet primaryRs = null;
        
        ConnectionEntity connEntity = (ConnectionEntity) context.getAttribute("database.conn");
        String jdbcUrl = "jdbc:mysql://{0}:{1}/{2}?useUnicode=yes&amp;characterEncoding={3}&amp;allowMultiQueries=true";
        jdbcUrl = MessageFormat.format(jdbcUrl, connEntity.getIp(), connEntity.getPort(), connEntity.getDbName(), connEntity.getChartSet());
        String userName = connEntity.getUserName();
        String password = connEntity.getPwd();
        conn = DbUtil.getConn(jdbcUrl, userName, password);
        DatabaseMetaData dbMetaData = conn.getMetaData();
        String schemaName = "";
        
        // 获取表的结果集
        tableRs = dbMetaData.getTables(null, schemaName, Constants.EMPTY_STR, new String[]{"TABLE"});
        Map<String, TableInfo> allTableMaps = new HashMap<String, TableInfo>();
        while(tableRs.next())
        {
            String primaryKey = "";
            String tableName = tableRs.getString("TABLE_NAME").toLowerCase();
            String tableRemark = tableRs.getString("REMARKS");
            String tableType = tableRs.getString("TABLE_TYPE");
            TableInfo tableInfo = new TableInfo();
            {
                tableInfo.setName(tableName);
                tableInfo.setRemark(tableRemark);
                tableInfo.setType(tableType);
            }
            // 获取主键
            primaryRs = dbMetaData.getPrimaryKeys(null, schemaName, tableName);
            while(primaryRs.next())
            {
                if(!StringUtil.isEmpty(primaryKey))
                    primaryKey += ",";
                primaryKey += primaryRs.getString("COLUMN_NAME").toLowerCase();// 列名
            }
            if(StringUtil.isEmpty(primaryKey))
            {
                logger.error("{}主键值不能为空", tableName);
                return false;
            }
            tableInfo.setPrimaryKey(primaryKey);
            
            // 获取列
            columnRs = dbMetaData.getColumns(null, schemaName, tableName, Constants.EMPTY_STR);
            List<ColumnInfo> columnList = new ArrayList<ColumnInfo>();
            while(columnRs.next())
            {
                String columnName = columnRs.getString("COLUMN_NAME").toLowerCase();
                String columnType = columnRs.getString("TYPE_NAME").toLowerCase();
                String columnRemark = columnRs.getString("REMARKS");
                int len = columnRs.getInt("COLUMN_SIZE");
                // 字段类型精度
                int precision = columnRs.getInt("DECIMAL_DIGITS");
                if(columnName == null || "".equals(columnName))
                {
                    continue;
                }
                
                ColumnInfo columnInfo = new ColumnInfo();
                {
                    columnInfo.setName(columnName);
                    columnInfo.setType(columnType);
                    columnInfo.setRemark(columnRemark);
                    columnInfo.setLen(len);
                    columnInfo.setPrecision(precision);
                    columnInfo.setPrimaryKey(("," + primaryKey + ",").contains("," + columnName + ","));
                }
                columnList.add(columnInfo);
            }
            tableInfo.setColumnList(columnList);
            allTableMaps.put(tableName, tableInfo);
        }
        context.setAttribute("allTableMaps", allTableMaps);
        return true;
    }
    
}
