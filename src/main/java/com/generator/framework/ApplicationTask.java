package com.generator.framework;

public interface ApplicationTask
{
    boolean perform(ApplicationContext context) throws Exception;
    
    boolean hasNext();
    
    void registerNextTask(ApplicationTask nextTask);
    
    ApplicationTask next();
    
}
