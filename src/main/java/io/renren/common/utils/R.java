package io.renren.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        //module:us:修改返回字段名称和内容
        put("result", Constant.Result.SUCCESS.getValue());
        put("message", Constant.Message.SUCCESS.getValue());
    }

    public static R error() {
        //module:us:修改返回字段名称和内容
        return error(Constant.Result.FAIL.getValue(), Constant.Message.UNKNOWN.getValue());
    }

    public static R error(String msg) {
        //module:us:修改返回字段名称和内容
        return error(Constant.Result.FAIL.getValue(), msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        //module:us:修改返回字段名称
        r.put("result", code);
        r.put("message", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        //module:us:修改返回字段名称
        r.put("message", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        //module:us:修改返回字段名称
        r.put("data", map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    //module:us:返回值方法
    public static R error(int code, String msg, Object obj) {
        R r = new R();
        r.put("result", code);
        r.put("message", msg);
        r.put("data", obj);
        return r;
    }

    //module:us:返回值方法
    public static R ok(Object obj) {
        R r = new R();
        r.put("data", obj);
        return r;
    }

}
