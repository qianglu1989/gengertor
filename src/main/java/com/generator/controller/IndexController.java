package com.generator.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.generator.entity.ConnectionEntity;
import com.generator.framework.Application;
import com.generator.framework.ApplicationContext;
import com.generator.framework.SimpleApplicationContext;
import com.generator.task.EntityTask;
import com.generator.task.InitTask;
import com.generator.task.MapperTask;
import com.generator.utils.DbUtil;
import com.generator.utils.FileUtil;
import com.generator.utils.MessageUtil;
import com.generator.utils.SerializableUtil;
import com.generator.utils.StringUtil;
import com.generator.vo.TableInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @Description: http://www.cnblogs.com/java-zhao/p/5348113.html
 * @author Jack
 * @date 2017年10月10日 下午1:12:46
 *
 */
@RestController
@RequestMapping("/")
@Api(value = "代码生成器")
public class IndexController
{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private static ApplicationContext context = new SimpleApplicationContext();
    private String path = System.getProperty("java.io.tmpdir") +File.separator+ "project-generator/conn.s";
    private String generatorpath = System.getProperty("java.io.tmpdir") +File.separator+ "project-generator/file/generator/";
    
    @ApiIgnore()
    @GetMapping("/")
    public ModelAndView index() throws IOException
    {
        File file = new File(path);
        if(!file.exists())
            FileUtil.writeToFile(path, "");
        context.setAttribute("generator.path", generatorpath);
        return new ModelAndView("index");
    }
    
    /**
     * 
     * @Description: 得到所有Connection链接
     * @author Jack
     * @date 2017年10月9日 下午3:26:37
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "得到所有Connection链接")
    @GetMapping("/getConns")
    public ModelMap getConns()
    {
        try
        {
            List<ConnectionEntity> list = (List<ConnectionEntity>) SerializableUtil.Deserialize(path);
            return MessageUtil.makeModelMap(200, list);
        }
        catch(Exception e)
        {
            logger.error("初始化连接对象出错", e);
            return MessageUtil.makeModelMap(500, "初始化连接对象出错");
        }
    }
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "添加连接")
    @PostMapping("/addConn")
    public ModelMap addConn(@RequestBody @ApiParam(name = "添加连接", value = "传入json格式", required = true) ConnectionEntity conn)
    {
        List<ConnectionEntity> list = (List<ConnectionEntity>) SerializableUtil.Deserialize(path);
        list = list == null ? new ArrayList<>() : list;
        conn.setKey(UUID.randomUUID().toString().replaceAll("-", ""));
        list.add(conn);
        boolean result = SerializableUtil.Serializer(path, list);
        if(result)
            return MessageUtil.makeModelMap(200, conn);
        else
            return MessageUtil.makeModelMap(500, "添加失败");
    }
    
    /**
     * 
     * @Description: 删除链接
     * @author Jack
     * @date 2017年10月9日 下午3:28:02
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "删除链接")
    @ApiImplicitParam(name = "key", value = "删除链接", required = true, paramType = "query", dataType = "String")
    @GetMapping("/delConn")
    public ModelMap delConn(String key)
    {
        try
        {
            List<ConnectionEntity> list = (List<ConnectionEntity>) SerializableUtil.Deserialize(path);
            List<ConnectionEntity> filter_list = list.stream().filter(x -> x.getKey().equals(key)).collect(Collectors.toList());
            list.removeAll(filter_list);
            boolean result = SerializableUtil.Serializer(path, list);
            if(result)
                return MessageUtil.makeModelMap(200, "删除成功");
            else
                return MessageUtil.makeModelMap(500, "删除失败");
        }
        catch(Exception e)
        {
            logger.error("删除失败", e);
            return MessageUtil.makeModelMap(500, "删除失败");
        }
    }
    
    /**
     * 
     * @Description: 测试Connection链接
     * @author Jack
     * @date 2017年10月9日 下午3:26:06
     *
     * @return
     */
    @ApiOperation(value = "测试链接", notes = "测试Connection链接")
    @PostMapping("/getConnTest")
    public ModelMap getConnTest(@RequestBody @ApiParam(name = "用户对象", value = "传入json格式", required = true) ConnectionEntity entity)
    {
        String jdbcUrl = "jdbc:mysql://{0}:{1}/{2}?useUnicode=yes&amp;characterEncoding={3}&amp;allowMultiQueries=true";
        jdbcUrl = MessageFormat.format(jdbcUrl, entity.getIp(), entity.getPort(), entity.getDbName(), entity.getChartSet());
        String userName = entity.getUserName();
        String password = entity.getPwd();
        Connection conn = DbUtil.getConn(jdbcUrl, userName, password);
        if(conn != null)
            return MessageUtil.makeModelMap(200, "连接成功");
        else
            return MessageUtil.makeModelMap(500, "连接失败");
    }
    
    /**
     * 
     * @Description: 得到所有表
     * @author Jack
     * @date 2017年10月9日 下午8:58:13
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    @ApiIgnore
    @GetMapping("/getTables/{key}")
    public ModelMap getTables(@PathVariable String key)
    {
        try
        {
            List<ConnectionEntity> list = (List<ConnectionEntity>) SerializableUtil.Deserialize(path);
            if(list.size() < 0)
                return MessageUtil.makeModelMap(500, "请先添加数据源");
            List<ConnectionEntity> filter_list = list.stream().filter(x -> x.getKey().equals(key)).collect(Collectors.toList());
            if(filter_list.size() < 1)
                return MessageUtil.makeModelMap(500, "未找到链接对象");
            ConnectionEntity connectionEntity = filter_list.get(0);
            context.setAttribute("database.conn", connectionEntity);
            Application application = new Application(IndexController.class.getSimpleName(), context);
            application.setApplicationName(IndexController.class.getName());
            application.addTask(InitTask.class).start();
            Map<String, TableInfo> allTableMaps = (Map<String, TableInfo>) context.getAttribute("allTableMaps");
            return MessageUtil.makeModelMap(200, allTableMaps);
        }
        catch(Exception e)
        {
            return MessageUtil.makeModelMap(500, "表加载失败");
        }
    }
    
    /**
     * 
     * @Description: 生成开始
     * @author Jack
     * @date 2017年10月9日 下午9:01:31
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @ApiIgnore
    @PostMapping("generator")
    public ModelMap generator(String tables, String packageName)
    {
        try
        {
            File file = new File(generatorpath);
            FileUtil.deleteAll(new File(file.getParent()));
            
            List<String> tableList = StringUtil.splitStr2List(tables, ",");
            // 添加映射关系
            Map<String, String> table2Entity = new HashMap<String, String>();
            for(int i = 0; i < tableList.size(); i++)
            {
                String entityName = StringUtil.convertTableName2EntityName(tableList.get(i));
                table2Entity.put(tableList.get(i), entityName);
            }
            context.setAttribute("tableName.to.entityName", table2Entity);
            // 获取所有表信息
            Map<String, TableInfo> allTableMaps = (Map<String, TableInfo>) context.getAttribute("allTableMaps");
            // 要生成的表信息
            Map<String, TableInfo> tableInfos = new HashMap<String, TableInfo>();
            allTableMaps.forEach((tableName, tableInfo) -> {
                if(tableList.contains(tableName.toLowerCase()))
                {
                    tableInfos.put(tableName, tableInfo);
                }
            });
            context.setAttribute("tableInfos", tableInfos);
            context.setAttribute("packageName", packageName);
            Application application = new Application(IndexController.class.getSimpleName(), context);
            application.setApplicationName(IndexController.class.getName());
            application
                    .addTask(EntityTask.class)
                    .addTask(MapperTask.class).start();
            String file_download_path = (String) context.getAttribute("files.download.path");
            if(!StringUtil.isEmpty(file_download_path))
                return MessageUtil.makeModelMap(200, (Object) file_download_path);
            else
                return MessageUtil.makeModelMap(500, "生成失败");
        }
        catch(Exception e)
        {
            logger.error("生成失败", e);
            return MessageUtil.makeModelMap(500, "生成失败");
        }
    }
    
    /**
     * 
     * @Description: 下载文件
     * @author Jack
     * @date 2017年10月20日 下午1:52:15
     *
     * @param file
     * @param response
     * @return
     */
    @GetMapping("downloadZip")
    public void downloadZip(HttpServletResponse response)
    {
        String file_download_path = (String) context.getAttribute("files.download.path");
        try
        {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file_download_path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            // 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename=generator.zip");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        }
        catch(IOException ex)
        {
            logger.error("下载失败", ex);
        }
        // finally
        // {
        // try
        // {
        // File f = new File(file_download_path);
        // f.delete();
        // }
        // catch(Exception e)
        // {
        // e.printStackTrace();
        // }
        // }
    }
}
