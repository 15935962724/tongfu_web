package com.tongfu.util;

import com.alibaba.fastjson.JSON;

/**
 * 响应结果生成工具
 */
public class ResultUtil {

    /**
     * 返回成功，传入返回体具体出參
     *
     * @param object
     * @return
     */
    public static String success(Object object) {
        Result result = new Result ();
        result.setStatus (ResultCode.SUCCESS.getCode ());
        result.setMsg (ResultCode.SUCCESS.getMsg ());
        result.setData (object);
        return JSON.toJSON(result).toString();
    }

    /**
     * 提供给部分不需要出參的接口
     *
     * @return
     */
    public static String success() {
        return success (null);
    }

    /**
     * 自定义错误信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static String error(Integer code, String msg) {
        Result result = new Result ();
        result.setStatus (code);
        result.setMsg (msg);
        result.setData (null);
        return JSON.toJSON(result).toString();
    }

    /**
     * 返回异常信息，在已知的范围内
     *
     * @param resultCode
     * @return
     */
    public static String error(ResultCode resultCode) {
        Result result = new Result ();
        result.setStatus (resultCode.getCode ());
        result.setMsg (resultCode.getMsg ());
        result.setData (null);
        return JSON.toJSON(result).toString();
    }
    
    public static String error(String msg) {
        Result result = new Result ();
        result.setStatus (400);
        result.setMsg (msg);
        result.setData (null);
        return JSON.toJSON(result).toString();
    }
    
}