package com.generator.framework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @Description: 当前应用程序上下文
 * @author Jack
 * @date 2017年9月26日 上午11:34:57
 *
 */
public abstract class ApplicationContext
{
    protected Map<String, Object> ctx = new ConcurrentHashMap<>();
    
    public Map<String, Object> getCtx()
    {
        return ctx;
    }
    
    public void setCtx(Map<String, Object> ctx)
    {
        this.ctx = ctx;
    }
    
    public abstract void setAttribute(String key, Object obj);
    
    public abstract Object getAttribute(String key);
    
}
