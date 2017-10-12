package com.zblog.core.plugin;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class JMap extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	private JMap() {
	}

	private JMap(String key, Object value) {
		super();
		put(key, value);
	}

	private JMap(Map<String, ?> map) {
		super();
		putAll(map);
	}

	public static JMap create() {
		return new JMap();
	}

	public static JMap create(String key, Object value) {
		return new JMap(key, value);
	}

	public static JMap create(Map<String, ?> map) {
		return new JMap(map);
	}

	public JMap set(String key, Object value) {
		put(key, value);

		return this;
	}

	public JMap setAll(Map<String, ?> map) {
		putAll(map);

		return this;
	}

	public <T> T getAs(String key) {
		return (T) super.get(key);
	}

	public <T> T getAs(String key, T defaults) {
		T renVal = (T) super.get(key);
		if (renVal == null)
			renVal = defaults;

		return renVal;
	}

	public <T> T get(String key, Class<T> clazz) {
		return (T) super.get(key);
	}

	public String getStr(String key) {
		return getStr(key, null);
	}

	public <T> T putOrGet(String key, T val) {
		if(containsKey(key))
			return getAs(key);

		put(key,val);

		return val;
	}

	public String getStr(String key, String defaults) {
		Object value = get(key);
		if (value == null)
			return defaults;

		return value.toString();
	}

	public int getInt(String key) {
		return getInt(key, 0);
	}

	public int getInt(String key, int defaults) {
		Object value = get(key);
		if (value == null)
			return defaults;

		return Integer.parseInt(value.toString());
	}

	public boolean getBool(String key) {
		return getBool(key, false);
	}

	public boolean getBool(String key, boolean defaults) {
		Object value = get(key);
		if (value == null)
			return defaults;

		return Boolean.parseBoolean(value.toString());
	}

	public long getLong(String key, long defaults) {
		Object value = get(key);
		if (value == null)
			return defaults;

		return Long.parseLong(value.toString());
	}

	public long getLong(String key) {
		return getLong(key, 0);
	}

}
