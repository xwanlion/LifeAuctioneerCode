package com.github.xwanlion.lifeauctioneer.conf;

import com.github.xwanlion.lifeauctioneer.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Msg {
	OK("OK", 0),
	ERR_NULL_PARAM("ERR_NULL_PARAM", 1),
	FOLDER_CREATE_FAIL("FOLDER_CREATE_FAIL", 1),
	ERR_UNKONW("ERR_UNKONW", 9999);

	private String name;
	private int index;

	public final static String CODE_KEY = "code";
	public final static String MSG_KEY = "msg";
	public final static String DATA_KEY = "data";

	private Msg(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static Map<String, Object> success() {
		return Msg.toMap(OK.index, OK.name);
	}

	public static Map<String, Object> success(String extraKey, String extraMsg) {
		Map<String, Object> map = Msg.toMap(OK.index, OK.name);
		map.put(extraKey, extraMsg);
		return map;
	}

	public static <T> Map<String, Object> success(String extraKey, List<T> extraMsg) {
		Map<String, Object> map = Msg.toMap(OK.index, OK.name);
		map.put(extraKey, extraMsg);
		return map;
	}

	public static <T> Map<String, Object> success(String extraKey, Map<String, T> extraMsg) {
		Map<String, Object> map = Msg.toMap(OK.index, OK.name);
		map.put(extraKey, extraMsg);
		return map;
	}

	public static Map<String, Object> success(Map<String, Object> data) {
		Map<String, Object> map = Msg.toMap(OK.index, OK.name);
		map.put(DATA_KEY, data);
		return map;
	}

	public static Map<String, Object> success(Long data) {
		Map<String, Object> map = Msg.toMap(OK.index, OK.name);
		map.put(DATA_KEY, data);
		return map;
	}

	public static boolean isSuccess(Map<String, Object> map) {
		Integer code = (Integer) map.get(CODE_KEY);
		if (code == null) return false;

		return code == OK.index;

	}

	public static boolean isError(Map<String, Object> map) {
		return !Msg.isSuccess(map);

	}

	public static Map<String, Object> error() {
		return Msg.error(ERR_UNKONW.name);
	}

	public static Map<String, Object> error(String msg) {
		return Msg.toMap(ERR_UNKONW.index, msg);
	}

	public static Map<String, Object> toMap(int code, String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE_KEY, code);
		map.put(MSG_KEY, msg);
		return map;
	}

	public Map<String, Object> toMap(String extraMsg) {
		StringBuffer gname = new StringBuffer(this.name);
		if (extraMsg != null && extraMsg.length() > 0) {
			gname.append(":");
			gname.append(extraMsg);
		}

		return Msg.toMap(this.index, gname.toString());

	}

	public Map<String, Object> toMap() {
		return this.toMap(null);
	}

	public String toJson() {
		Map<String, Object> map = Msg.toMap(this.index, this.name);
		return JsonUtils.toJsonString(map);
	}

	public String toJson(String extraMsg) {
		StringBuffer gname = new StringBuffer(this.name);
		if (extraMsg != null && extraMsg.length() > 0) {
			gname.append(":");
			gname.append(extraMsg);
		}

		Map<String, Object> map = Msg.toMap(this.index, gname.toString());
		return JsonUtils.toJsonString(map);

	}

	public int index() {
		return this.index;
	}

	@Override
	public String toString() {
		return this.name;
	}

}