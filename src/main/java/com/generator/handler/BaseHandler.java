package com.generator.handler;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.generator.utils.DateUtil;
import com.generator.utils.FileUtil;
import com.generator.utils.FreeMarkerUtil;

public abstract class BaseHandler<T>
{
    private static final Logger logger = LoggerFactory.getLogger(BaseHandler.class);
    protected String ftlName;
    protected String savePath;
    protected Map<String, Object> param = new HashMap<String, Object>();
    protected T info;
    
    public String generaterTemplateContent()
    {
        // String temp = FileUtil.readFileToString(this.getClass().getClassLoader().getResource("").getPath() + ftlName);
        InputStream in = null;
        try
        {
            Resource resource = new ClassPathResource(ftlName);
            in = resource.getInputStream();
            return FreeMarkerUtil.getProcessValue(param, IOUtils.toString(in, "utf-8"));
        }
        catch(Exception e)
        {
            logger.error("解析模版报错", e);
            return "";
        }
        finally
        {
            IOUtils.closeQuietly(in);
        }
    }
    
    /**
     * 
     * 保存到文件
     * 
     * @param str
     */
    public void saveToFile(String str)
    {
        FileUtil.writeToFile(savePath, str);
    }
    
    /**
     * 组装参数
     * 
     * @param param
     */
    public abstract void combileParams(T info);
    
    /**
     * 
     * 设置一些公共的参数.
     */
    public void beforeGenerate()
    {
        String time = DateUtil.formatDataToStr(new Date(), "yyyy年MM月dd日");
        param.put("author", System.getProperty("user.name"));
        param.put("time", time);
    }
    
    /**
     * 生成文件
     */
    public void execute()
    {
        String str = null;
        combileParams(info);
        beforeGenerate();
        str = generaterTemplateContent();
        saveToFile(str);
    }
    
}
