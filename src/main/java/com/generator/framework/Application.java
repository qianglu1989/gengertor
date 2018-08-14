package com.generator.framework;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.generator.task.InitTask;
import com.generator.utils.ClassUtil;
import com.generator.utils.DateUtil;
import com.generator.utils.ZipUtil;

/**
 * 
 * @Description: 应用程序
 * @author Jack
 * @date 2017年9月26日 上午11:33:31
 *
 */
public class Application
{
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    private String applicationId;
    
    private String applicationName;
    
    private ApplicationContext context;
    
    private Date startTime;
    
    private Date endTime;
    
    private long excuteTime;
    
    public String getApplicationName()
    {
        return applicationName;
    }
    
    public void setApplicationName(String applicationName)
    {
        this.applicationName = applicationName;
    }
    
    /**
     * 所有任务
     */
    private List<ApplicationTask> applicationTasks = new ArrayList<ApplicationTask>();
    
    public Application(String applicationId)
    {
        this.applicationId = applicationId;
        this.context = new SimpleApplicationContext();
    }
    
    public Application(String applicationId, ApplicationContext context)
    {
        this.applicationId = applicationId;
        this.context = context;
    }
    
    /**
     * 
     * @Description: 执行入口
     * @author Jack
     * @date 2017年9月26日 下午12:07:13
     *
     */
    public void start()
    {
        startTask();
    }
    
    private void startTask()
    {
        this.startTime = new Date();
        logger.info("应用程序{}执行开始时间：{}", this.applicationName, this.startTime);
        if(hasTasks())
        {
            ApplicationTask task = applicationTasks.get(0);
            // 是否执行成功
            boolean complate = true;
            try
            {
                complate = task.perform(this.context);
                if(complate && !task.hasNext() && !(task instanceof InitTask))
                {
                    ZipFile(task);
                }
                while(complate && task.hasNext())
                {
                    task = task.next();
                    complate = task.perform(this.context);
                    if(!complate)
                        break;
                    if(!task.hasNext())
                    {
                        ZipFile(task);
                    }
                }
                if(!complate)
                    logger.error("任务执行出错！当前执行任务为：" + task.getClass().getName());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                logger.error("任务{}执行出现异常！", task.getClass().getName(), e);
            }
        }
        else
        {
            logger.error("无法启动应用程序，由于应用程序中没有任务！");
        }
        this.endTime = new Date();
        logger.info("应用程序{}执行结束时间：{}", this.applicationName, this.endTime);
        this.excuteTime = DateUtil.calExcuteSecondTime(startTime, endTime);
        logger.info("应用程序{}执行总耗时(秒)：{},总耗时(毫秒):{}", this.applicationName, this.excuteTime, DateUtil.calExcuteMilliTime(startTime, endTime));
    }

    /**
     * 
     * @Description: 压缩文件
     * @author Jack
     * @date 2017年10月17日 下午6:57:58
     *
     * @param task
     * @throws Exception
     */
    private void ZipFile(ApplicationTask task) throws Exception
    {
        String fileBasePath = (String) context.getAttribute("generator.path");
        ZipUtil.zip((new File(fileBasePath)).getPath(), (new File(fileBasePath)).getParent(), "generator.zip");
        context.setAttribute("files.download.path", (new File(fileBasePath)).getParent() + "/generator.zip");
        logger.info("跳出整个应用程序任务链！跳出之前执行的任务为：" + task.getClass().getName());
    }
    
    private boolean hasTasks()
    {
        return !applicationTasks.isEmpty();
    }
    
    public Application addTask(Class<? extends ApplicationTask> taskClass)
    {
        ApplicationTask task = ClassUtil.newInstance(taskClass.getName(), ApplicationTask.class);
        if(null != task)
        {
            return addApplicationTask(task);
        }
        return this;
    }
    
    private Application addApplicationTask(ApplicationTask task)
    {
        if(null == task)
            return this;
        if(hasTasks())
        {
            ApplicationTask lastTask = applicationTasks.get(applicationTasks.size() - 1);
            lastTask.registerNextTask(task);
        }
        this.applicationTasks.add(task);
        return this;
    }
}
