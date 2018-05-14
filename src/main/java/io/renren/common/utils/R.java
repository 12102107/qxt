package io.renren.common.utils;

import java.util.HashMap;

/**
 * 返回数据
 *
 * @author chenshun
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
    
    private static final long serialVersionUID = 1L;
    private static final String RESULT = "result";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";

    public R() {
        put(RESULT, Constant.Result.SUCCESS.getValue());
        put(MESSAGE, Constant.Message.SUCCESS.getValue());
    }

    public static R error() {
        return error(Constant.Result.FAIL.getValue(), Constant.Message.UNKNOWN.getValue());
    }

    public static R error(String msg) {
        return error(Constant.Result.FAIL.getValue(), msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put(RESULT, code);
        r.put(MESSAGE, msg);
        return r;
    }

    public static R error(int code, String msg, Object obj) {
        R r = new R();
        r.put(RESULT, code);
        r.put(MESSAGE, msg);
        r.put(DATA, obj);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put(MESSAGE, msg);
        return r;
    }

    public static R ok(Object obj) {
        R r = new R();
        r.put("data", obj);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
