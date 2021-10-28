package com.myself.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.myself.common.exception.ExceptionCodeEnum;
import com.myself.common.to.es.SkuHasStockVo;

import java.util.HashMap;
import java.util.List;
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
		put("code", 0);
	}
	
	public static R error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(500, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R error(ExceptionCodeEnum ex){
		R r = new R();
		r.put("code", ex.getCode());
		r.put("msg", ex.getMsg());
		return r;
	}

	public static R error(ExceptionCodeEnum ex, Map data){
		R r = R.error(ex);
		r.put("data",data);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
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

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Integer getCode() {
		return (Integer) this.get("code");
	}

	/**
	 * 复杂类型转换 TypeReference
	 */
	public <T> T getData(TypeReference<T> typeReference){
		// get("data") 默认是map类型 所以再由map转成string再转json
		Object data = get("data");
		return JSON.parseObject(JSON.toJSONString(data), typeReference);
	}

	public R setData(Object data){
		put("data", data);
		return this;
	}
}
