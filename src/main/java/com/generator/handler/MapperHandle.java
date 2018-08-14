package com.generator.handler;

import com.generator.entity.MapperEntity;

/**
 * 
 * @Description: 公用逻辑处理
 * @author Jack
 * @date 2017年9月30日 上午11:36:38
 *
 */
public class MapperHandle extends BaseHandler<MapperEntity>
{
    
    public MapperHandle(String ftlName, MapperEntity info, String fileBasePath)
    {
        this.ftlName = ftlName;
        this.info = info;
        this.savePath = fileBasePath + "mapper/" + info.getFileName() + ".xml";
    }
    
    @Override
    public void combileParams(MapperEntity info)
    {
        this.param.put("entityName", info.getEntityName());
        this.param.put("packageName", info.getPackageName());
        this.param.put("table", info.getTableEntity());
    }
    
}
