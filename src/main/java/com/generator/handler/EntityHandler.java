package com.generator.handler;

import java.util.Map;
import java.util.Map.Entry;
import com.generator.vo.EntityInfo;

public class EntityHandler extends BaseHandler<EntityInfo>
{
    
    public EntityHandler(String ftlName, EntityInfo info, String fileBasePath)
    {
        this.ftlName = ftlName;
        this.info = info;
        this.savePath = fileBasePath + "po/" + info.getClassName() + ".java";
    }
    
    @Override
    public void combileParams(EntityInfo entityInfo)
    {
        this.param.put("packageStr", entityInfo.getEntityPackage());
        StringBuilder sb = new StringBuilder();
        for(String str : entityInfo.getImports())
        {
            sb.append("import ").append(str).append(";\r\n");
        }
        this.param.put("importStr", sb.toString());
        this.param.put("tableName", entityInfo.getTableName());
        this.param.put("className", entityInfo.getClassName());
        
        // 生成属性，getter,setter方法
        sb = new StringBuilder();
        StringBuilder sbMethods = new StringBuilder();
        Map<String, String> propRemarks = entityInfo.getPropRemarks();
        sb.append("    public static final String TABLE_ALIAS = \"").append(entityInfo.getEntityName()).append("\";\r\n\r\n");
        
        // 获取属性和字段名的对应关系
        Map<String, String> propNameColumnNames = entityInfo.getPropNameColumnNames();
        
        for(Entry<String, String> entry : entityInfo.getPropTypes().entrySet())
        {
            String propName = entry.getKey();
            String propType = entry.getValue();
            String columnName = propNameColumnNames.get(propName);
            
            // 注释、类型、名称
            sb.append("    /**\r\n     *").append(propRemarks.get(propName)).append("    db_column:").append(columnName.toLowerCase()).append("\r\n     */\r\n");
            if(entityInfo.getPrimaryKey().equals(columnName))
            {
                //sb.append("    @Id\r\n");
            }
            sb.append("    private ").append(propType).append(" ").append(propName).append(";\r\n");
            
            sbMethods.append("    public ").append(propType).append(" get").append(propName.substring(0, 1).toUpperCase()).append(propName.substring(1)).append("() {\r\n").append("        return ").append(propName).append(";\r\n").append("    }\r\n").append("    public void set").append(propName.substring(0, 1).toUpperCase()).append(propName.substring(1)).append("(").append(propType).append(" ").append(propName).append(") {\r\n").append("        this.").append(propName).append(" = ").append(propName).append(";\r\n    }\r\n").append("\r\n");
        }
        
        this.param.put("propertiesStr", sb.toString());
        this.param.put("methodStr", sbMethods.toString());
        
    }
    
}
