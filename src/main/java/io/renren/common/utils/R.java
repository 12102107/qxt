package io.renren.common.utils;

import org.apache.http.HttpStatus;

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
//		put("code", 0);
//		put("msg", "success");
		put("result", Constant.Result.SUCCESS.getValue());
		put("message", Constant.Message.SUCCESS.getValue());
	}

	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
//		r.put("code", code);
//		r.put("msg", msg);
		r.put("result", code);
		r.put("message", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		//r.put("msg", msg);
		r.put("message", msg);
		return r;
	}

	public static R ok(Object obj) {
		R r = new R();
		r.put("data", obj);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
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
