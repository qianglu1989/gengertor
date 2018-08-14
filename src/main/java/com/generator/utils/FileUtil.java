package com.generator.utils;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil
{
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    
    public static String readFileToString(String file)
    {
        return readFileToString(file, "UTF-8");
    }
    
    public static String readFileToString(String file, String encoding)
    {
        try
        {
            if(StringUtil.isEmpty(encoding))
            {
                encoding = "UTF-8";
            }
            String content = org.apache.commons.io.FileUtils.readFileToString(new File(file), encoding);
            return content;
        }
        catch(IOException ex)
        {
            logger.error("读取文件出错", ex);
        }
        return "";
    }
    
    public static boolean writeToFile(String file, String data)
    {
        return writeToFile(file, data, "UTF-8");
    }
    
    public static boolean writeToFile(String file, String data, String encoding)
    {
        try
        {
            if(encoding == null || "".equals(encoding))
            {
                encoding = "UTF-8";
            }
            org.apache.commons.io.FileUtils.writeStringToFile(new File(file), data, encoding);
            return true;
        }
        catch(IOException ex)
        {
            logger.error("写文件出错", ex);
        }
        return false;
    }
    
    public static void deleteAll(File file)
    {
        try
        {
            if(file.exists())
            {
                if(file.isFile() || file.list().length == 0)
                {
                    file.delete();
                }
                else
                {
                    File[] files = file.listFiles();
                    for(int i = 0; i < files.length; i++)
                    {
                        deleteAll(files[i]);
                        files[i].delete();
                    }
                    
                    if(file.exists()) // 如果文件本身就是目录 ，就要删除目录
                        file.delete();
                }
            }
        }
        catch(Exception e)
        {
            logger.error("删除文件出错", e);
        }
    }
}
