package com.generator.utils;

import java.util.ArrayList;
import org.springframework.ui.ModelMap;

import com.generator.common.QueryResult;
import com.generator.common.ResultModel;

/**
 * 
 * @Description: MessageUtil
 * @author Jack
 * @date 2017年2月23日 下午4:35:41
 *
 */
public class MessageUtil
{
    
    /** 返回消息 */
    private static final String MESSAGE = "message";
    /** 返回数据 */
    private static final String DATA = "data";
    /** 返回结果状态 */
    private static final String RESULT_CODE = "resultCode";
    
    public static ModelMap makeModelMap(Integer resultCode)
    {
        return makeModelMap(resultCode, "");
    }
    
    public static ModelMap makeModelMap(Integer resultCode, String msg)
    {
        return makeModelMap(resultCode, msg, new ArrayList<Object>(0));
    }
    
    public static ModelMap makeModelMap(Integer resultCode, Object data)
    {
        return makeModelMap(resultCode, "", data);
    }
    
    public static ModelMap makeModelMap(Integer resultCode, String msg, Object data)
    {
        
        ModelMap model = new ModelMap();
        model.addAttribute(DATA, data);
        model.addAttribute(RESULT_CODE, resultCode);
        model.addAttribute(MESSAGE, msg);
        return model;
    }
    
    /**
     * 
     * @Description: 生成分页对象
     * @author Jack
     * @date 2017年3月20日 上午9:56:52
     *
     * @param queryResult
     * @return
     */
    public static <T> ModelMap makeModeMap(QueryResult<T> queryResult)
    {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("total", queryResult.getTotal());
        modelMap.addAttribute("rows", queryResult.getlist());
        return modelMap;
    }
    
    /***
     * 生成返回对象
     * 
     * @param <T>
     * 
     * 
     * @param returnCode
     * @param result
     * @return
     */
    public static <T> ModelMap makeModeMap(ResultModel<T> result)
    {
        ModelMap model = new ModelMap();
        model.addAttribute(DATA, result.getResult());
        model.addAttribute(RESULT_CODE, result.getRetCode());
        model.addAttribute(MESSAGE, result.getRetMsg());
        return model;
    }
    
}
