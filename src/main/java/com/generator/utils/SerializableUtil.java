package com.generator.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.generator.entity.ConnectionEntity;

/**
 * 
 * @Description: 序列化工具类
 * @author Jack
 * @date 2017年10月9日 下午3:57:00
 *
 */
public class SerializableUtil<T>
{
    private static final Logger logger = LoggerFactory.getLogger(SerializableUtil.class);
    
    /**
     * 
     * @Description: 序列化
     * @author Jack
     * @date 2017年10月9日 下午4:05:28
     *
     * @param pathname
     *            文件全路径
     * @param t
     *            序列化对象
     * @return
     */
    public static <T> boolean Serializer(String pathname, T t)
    {
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathname));
            out.writeObject(t);
            out.flush();
            out.close();
        }
        catch(FileNotFoundException e)
        {
            logger.error("序列化失败,{}文件不存在", pathname);
            return false;
        }
        catch(Exception e)
        {
            logger.error("序列化失败", e);
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @Description: 反序列化
     * @author Jack
     * @date 2017年10月9日 下午4:05:47
     *
     * @param pathname
     *            文件全路径
     * @param t
     *            反序列化对象
     * @return
     */
    public static Object Deserialize(String pathname)
    {
        try
        {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(pathname));
            Object obj = in.readObject();
            in.close();
            return obj;
        }
        catch(ClassNotFoundException e)
        {
            logger.error("反序列化失败,无法找到类", e);
        }
        catch(FileNotFoundException e)
        {
            logger.error("反序列化失败,{}文件不存在", pathname);
        }
        catch(IOException e)
        {
            // logger.error("反序列化失败", e);
        }
        return null;
    }
    
    public static void main(String[] args)
    {
        FileUtil.writeToFile("G:/templates/conn.s", "");
        ConnectionEntity entity = new ConnectionEntity();
        {
            entity.setKey(UUID.randomUUID().toString().replaceAll("-", ""));
            entity.setName("测试链接");
            entity.setChartSet("utf-8");
            entity.setIp("127.0.0.1");
            entity.setPort("3306");
            entity.setName("jack");
            entity.setPwd("123456");
            entity.setSqlType(1);
        }
        SerializableUtil.Serializer("G:/templates/conn.s", entity);
        
        ConnectionEntity entity2 = (ConnectionEntity) SerializableUtil.Deserialize("G:/templates/conn.s");
        System.out.println(entity2.getKey());
    }
}
