package com.generator.common;
import java.io.Serializable;

/**
 * 请求返回模板类
 *
 * @Author wangjp
 * @param <T>
 */
public class ResultModel<T> implements Serializable {

    private static final long serialVersionUID = 4480899038005612787L;

    /** 返回编码 ,默认是xxxxxx,成功000000*/
    private String            retCode          = "000000";

    /** 返回消息 ,默认是unkown,成功是OK*/
    private String            retMsg           = "success";

    /** 返回或请求类 */
    private T                 result;

    public ResultModel() {
        super();
    }

    public ResultModel(T result) {
        super();
        this.result = result;
    }

    public ResultModel(String retCode, String retMsg) {
        super();
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public ResultModel(String retCode, String retMsg, T result) {
        super();
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.result = result;
    }

    public String getRetCode() {

        return retCode;
    }

    public void setRetCode(String retCode) {

        this.retCode = retCode;
    }

    public String getRetMsg() {

        return retMsg;
    }

    public void setRetMsg(String retMsg) {

        this.retMsg = retMsg;
    }

    public T getResult() {

        return result;
    }

    public void setResult(T result) {

        this.result = result;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("-BaseModel [retCode=");
        builder.append(retCode);
        builder.append(", retMsg=");
        builder.append(retMsg);
        builder.append(", result=");
        builder.append(result);
        builder.append("]");
        return builder.toString();
    }

}