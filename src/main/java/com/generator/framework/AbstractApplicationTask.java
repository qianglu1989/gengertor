package com.generator.framework;

public abstract class AbstractApplicationTask implements ApplicationTask
{
    
    private ApplicationTask nextTask = null;
    
    private boolean hasNext = false;
    
    protected abstract boolean doInternal(ApplicationContext context) throws Exception;
    
    @Override
    public boolean perform(ApplicationContext context) throws Exception
    {
        return doInternal(context);
    }
    
    @Override
    public boolean hasNext()
    {
        return this.hasNext;
    }
    
    @Override
    public void registerNextTask(ApplicationTask nextTask)
    {
        this.nextTask = nextTask;
        this.hasNext = !(null == nextTask);
        
    }
    
    @Override
    public ApplicationTask next()
    {
        return this.nextTask;
    }
    
}
