package com.generator.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ecej.nove.utils.common.PropertyConfigUtils;
import com.generator.entity.ColumnEntity;
import com.generator.entity.MapperEntity;
import com.generator.entity.TableEntity;
import com.generator.framework.AbstractApplicationTask;
import com.generator.framework.ApplicationContext;
import com.generator.framework.Constants;
import com.generator.handler.BaseHandler;
import com.generator.handler.MapperHandle;
import com.generator.utils.StringUtil;
import com.generator.vo.ColumnInfo;
import com.generator.vo.TableInfo;

/**
 * 
 * @Description: 核心Task
 * @author Jack
 * @date 2017年9月30日 上午11:32:39
 *
 */
public class MapperTask extends AbstractApplicationTask
{
    private static final Logger logger = LoggerFactory.getLogger(MapperTask.class);
    private static String packageName = "";
    private static String ENTITY_FTL = "templates/ftl/Mapper.ftl";
    
    @SuppressWarnings("unchecked")
    @Override
    protected boolean doInternal(ApplicationContext context) throws Exception
    {
        logger.info("Mapper类信息初始化");
        
        packageName = (String) context.getAttribute("packageName");
        packageName = StringUtil.isEmpty(packageName) ? "com.ecej.demo" : packageName;
        String fileBasePath = (String) context.getAttribute("generator.path");
        Boolean result = combineInfo(context);
        if(result)
        {
            List<MapperEntity> mapperEntities = (List<MapperEntity>) context.getAttribute("mapperEntities");
            mapperEntities.forEach(x -> {
                BaseHandler<MapperEntity> handler = new MapperHandle(ENTITY_FTL, x, fileBasePath);
                handler.execute();
            });
            logger.info("Mapper类文件生成完毕");
        }
        return result;
    }
    
    /**
     * 
     * @Description: 信息组装
     * @author Jack
     * @date 2017年9月30日 上午11:53:06
     *
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    private Boolean combineInfo(ApplicationContext context)
    {
        logger.info("Mapper类信息组装信息开始");
        
        Boolean result = true;
        
        try
        {
            // 获取表和实体的映射集合
            Map<String, String> table2Entities = (Map<String, String>) context.getAttribute("tableName.to.entityName");
            Map<String, TableInfo> tableInfos = (Map<String, TableInfo>) context.getAttribute("tableInfos");
            
            List<MapperEntity> mapperEntities = new ArrayList<>();
            
            for(Entry<String, String> entry : table2Entities.entrySet())
            {
                String tableName = entry.getKey();
                String entityName = entry.getValue();
                MapperEntity mapperEntity = new MapperEntity();
                TableInfo tableInfo = tableInfos.get(tableName);
                TableEntity tableEntity = new TableEntity();
                BeanUtils.copyProperties(tableEntity, tableInfo);
                
                List<ColumnInfo> columnInfos = tableInfo.getColumnList();
                List<ColumnEntity> columnEntities = new ArrayList<>();
                for(ColumnInfo columnInfo : columnInfos)
                {
                    ColumnEntity columnEntity = new ColumnEntity();
                    BeanUtils.copyProperties(columnEntity, columnInfo);
                    columnEntity.setPropName(StringUtil.convertFieldName2PropName(columnEntity.getName()));
                    columnEntity.setPropType(PropertyConfigUtils.getProperty(columnEntity.getType()));
                    columnEntities.add(columnEntity);
                }
                tableEntity.setColumns(columnEntities);
                mapperEntity.setEntityName(entityName);
                mapperEntity.setFileName(entityName + Constants.MAPPER_XML_SUFFIX);
                mapperEntity.setPackageName(packageName);
                mapperEntity.setTableEntity(tableEntity);
                mapperEntities.add(mapperEntity);
            }
            context.setAttribute("mapperEntities", mapperEntities);
        }
        catch(Exception e)
        {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    
}
