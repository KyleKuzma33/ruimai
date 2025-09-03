/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.wong.question.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据封装类
 *
 * 通常用于 Web 接口返回统一的数据结构，
 * 继承自 HashMap<String, Object>，可以像 Map 一样使用 put 存储额外数据。
 *
 * 格式：
 * {
 *   "code": 200,           // 状态码
 *   "msg": "success",      // 提示消息
 *   "data": {...},         // 返回的数据
 *   "count": 100           // 记录总数（分页时常用）
 * }
 *
 * @author Mark
 */
@Data
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据总数（通常用于分页返回）
	 */
	private Integer count;

	/**
	 * 状态码：200 表示成功，500 表示失败
	 */
	private Integer code;

	/**
	 * 返回消息
	 */
	private String msg;

	/**
	 * 返回的数据对象
	 */
	private Object data;

	/** 成功状态码 */
	private static int SC_OK = 200;

	/** 系统异常状态码 */
	private static int SC_INTERNAL_SERVER_ERROR = 500;

	// ===== 构造方法 =====

	public R() {}

	public R(Integer code, String msg, Object data, Integer count) {
		this.count = count;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public R(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public R(Integer code, Object data) {
		this.code = code;
		this.data = data;
	}

	public R(Integer code, Object data, Integer count) {
		this.count = count;
		this.code = code;
		this.data = data;
	}

	// ===== 错误返回 =====

	/**
	 * 默认错误返回（500，未知异常）
	 */
	public static R error() {
		new R(SC_INTERNAL_SERVER_ERROR, null, null);
		R r = new R();
		r.put("code", SC_INTERNAL_SERVER_ERROR);
		r.put("msg", "未知异常，请联系管理员");
		r.put("data", null);
		return r;
	}

	/**
	 * 错误返回（500，自定义消息）
	 */
	public static R error(String msg) {
		R r = new R(SC_INTERNAL_SERVER_ERROR, msg);
		r.put("code", SC_INTERNAL_SERVER_ERROR);
		r.put("msg", msg);
		r.put("data", null);
		return r;
	}

	/**
	 * 错误返回（自定义状态码、消息）
	 */
	public static R error(Integer code, String msg) {
		R r = new R(code, msg);
		r.put("code", code);
		r.put("msg", msg);
		r.put("data", null);
		return r;
	}

	/**
	 * 错误返回（带数据）
	 */
	public static R error(String msg, Object data) {
		R r = new R(SC_INTERNAL_SERVER_ERROR, msg, data, null);
		r.put("code", SC_INTERNAL_SERVER_ERROR);
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}

	/**
	 * 错误返回（自定义状态码、消息、数据）
	 */
	public static R error(Integer code, String msg, Object data) {
		R r = new R(code, msg, data, null);
		r.put("code", code);
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}

	// ===== 成功返回 =====

	/**
	 * 成功返回（只返回消息）
	 */
	public static R success(String msg) {
		R r = new R(SC_OK, msg);
		r.put("code", SC_OK);
		r.put("msg", msg);
		r.put("data", null);
		return r;
	}

	/**
	 * 成功返回（带消息和数据）
	 */
	public static R success(String msg, Object data) {
		R r = new R(SC_OK, msg, data, null);
		r.put("code", SC_OK);
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}

	/**
	 * 成功返回（JSONObject 数据）
	 */
	public static R success(JSONObject jsonObject) {
		R r = new R(SC_OK, "", jsonObject, null);
		r.put("code", SC_OK);
		r.put("msg", null);
		r.put("data", jsonObject);
		return r;
	}

	/**
	 * 成功返回（JSONArray 数据）
	 */
	public static R success(JSONArray jsonArray) {
		R r = new R(SC_OK, "", jsonArray, null);
		r.put("code", SC_OK);
		r.put("msg", null);
		r.put("data", jsonArray);
		return r;
	}

	/**
	 * 成功返回（任意对象）
	 */
	public static R success(Object object) {
		R r = new R(SC_OK, "", object, null);
		r.put("code",SC_OK);
		r.put("msg", null);
		r.put("data", object);
		return r;
	}

	/**
	 * 成功返回（Map 数据，带总数 count，一般用于分页）
	 */
	public static R success(Map<String, Object> map, int count) {
		R r = new R(SC_OK, "", map, count);
		r.put("code", SC_OK);
		r.put("msg", null);
		r.put("count", count);
		r.put("data", map);
		return r;
	}

	/**
	 * 成功返回（无任何数据）
	 */
	public static R success() {
		R r = new R(SC_OK, "", "", null);
		return r;
	}

	// ===== 其他方法 =====

	/**
	 * 向返回对象中添加键值对
	 */
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	@Override
	public String toString() {
		return "R{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}

	/**
	 * 判断是否成功
	 */
	public boolean isSuccess() {
		return this.code == 200;
	}
}
