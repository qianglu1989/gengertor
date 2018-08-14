package com.generator.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ecej.nove.utils.common.PropertyConfigUtils;
import com.generator.framework.AbstractApplicationTask;
import com.generator.framework.ApplicationContext;
import com.generator.framework.Constants;
import com.generator.handler.BaseHandler;
import com.generator.handler.EntityHandler;
import com.generator.utils.StringUtil;
import com.generator.vo.ColumnInfo;
import com.generator.vo.EntityInfo;
import com.generator.vo.TableInfo;

/**
 * 
 * @Description: 实体生成类
 * @author Jack
 * @date 2017年9月27日 上午10:09:00
 *
 */
public class EntityTask extends AbstractApplicationTask
{
    private static final Logger logger = LoggerFactory.getLogger(EntityTask.class);
    
    private static String packageName = "";
    private static String ENTITY_FTL = "templates/ftl/Entity.ftl";
    
    @SuppressWarnings("unchecked")
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception
    {
        logger.info("实体类信息初始化");
        
        packageName = (String) context.getAttribute("packageName");
        packageName = StringUtil.isEmpty(packageName) ? "com.ecej.demo" : packageName;
        String fileBasePath = (String) context.getAttribute("generator.path");
        
        Boolean result = combineInfo(context);
        if(result)
        {
            List<EntityInfo> entityInfos = (List<EntityInfo>) context.getAttribute("entityInfos");
            entityInfos.forEach(x -> {
                BaseHandler<EntityInfo> handler = new EntityHandler(ENTITY_FTL, x, fileBasePath);
                handler.execute();
            });
            logger.info("实体类生成完毕");
        }
        return result;
    }
    
    /**
     * 
     * @Description: 组装信息
     * @author Jack
     * @date 2017年9月27日 上午10:19:33
     *
     */
    @SuppressWarnings("unchecked")
    private Boolean combineInfo(ApplicationContext context)
    {
        logger.info("实体类信息组装信息开始");
        
        Boolean result = true;
        
        try
        {
            // 获取表和实体的映射集合
            Map<String, String> table2Entities = (Map<String, String>) context.getAttribute("tableName.to.entityName");
            Map<String, TableInfo> tableInfos = (Map<String, TableInfo>) context.getAttribute("tableInfos");
            
            List<EntityInfo> entityInfos = new ArrayList<>();
            table2Entities.forEach((tableName, entityName) -> {
                
                EntityInfo entityInfo = new EntityInfo();
                TableInfo tableInfo = tableInfos.get(tableName);
                
                Set<String> imports = new HashSet<>();
                Map<String, String> propTypes = new LinkedHashMap<>();
                Map<String, String> propRemarks = new LinkedHashMap<>();
                Map<String, String> propName2ColumnNames = new LinkedHashMap<>();
                
                entityInfo.setTableName(tableName);
                entityInfo.setEntityName(entityName);
                entityInfo.setClassName(entityName + Constants.ENTITY_SUFFIX);
                entityInfo.setEntityPackage(packageName);
                
                List<ColumnInfo> columns = tableInfo.getColumnList();
                columns.forEach(x -> {
                    String fieldName = x.getName();
                    String fieldType = x.getType();
                    
                    // 生成字段对应属性名
                    String propName = StringUtil.convertFieldName2PropName(fieldName);
                    String propType = PropertyConfigUtils.getProperty(fieldType);
                    if(null == propType)
                    {
                        logger.error("{}对应类型为{}", fieldType, propType);
                    }
                    if(x.isPrimaryKey())
                        entityInfo.setPrimaryKey(fieldName);
                    propTypes.put(propName, propType);
                    propRemarks.put(propName, x.getRemark());
                    propName2ColumnNames.put(propName, x.getName().toLowerCase());
                });
                // 获取此实体所有的类型
                Collection<String> types = propTypes.values();
                
                for(String type : types)
                {
                    if(!StringUtil.isEmpty(PropertyConfigUtils.getProperty(type)))
                    {
                        imports.add(PropertyConfigUtils.getProperty(type));
                    }
                }
                entityInfo.setPropTypes(propTypes);
                entityInfo.setPropRemarks(propRemarks);
                entityInfo.setPropNameColumnNames(propName2ColumnNames);
                entityInfo.setImports(imports);
                entityInfo.setPackageClassName(entityInfo.getEntityPackage() + "." + entityInfo.getClassName());
                entityInfos.add(entityInfo);
            });
            context.setAttribute("entityInfos", entityInfos);
        }
        catch(Exception e)
        {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    
}
