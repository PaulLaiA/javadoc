package org.zpaul.javadoc.contants;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum RoughlyType {
	Unknow,
	Array,
	Map,
	String,
	Number,
	Boolean,
	Date,
	Object,


	;


	private static final Map<String, RoughlyType> BASE_TYPE_MAP = new HashMap(8);

	static {
		BASE_TYPE_MAP.put("byte", RoughlyType.String);
		BASE_TYPE_MAP.put("java.lang.Byte", RoughlyType.String);

		BASE_TYPE_MAP.put("short", RoughlyType.String);
		BASE_TYPE_MAP.put("java.lang.Short", RoughlyType.String);

		BASE_TYPE_MAP.put("int", RoughlyType.Number);
		BASE_TYPE_MAP.put("java.lang.Integer", RoughlyType.Number);

		BASE_TYPE_MAP.put("long", RoughlyType.Number);
		BASE_TYPE_MAP.put("java.lang.Long", RoughlyType.Number);

		BASE_TYPE_MAP.put("float", RoughlyType.Number);
		BASE_TYPE_MAP.put("java.lang.Float", RoughlyType.Number);

		BASE_TYPE_MAP.put("double", RoughlyType.Number);
		BASE_TYPE_MAP.put("java.lang.Double", RoughlyType.Number);

		BASE_TYPE_MAP.put("boolean", RoughlyType.Boolean);
		BASE_TYPE_MAP.put("java.lang.Boolean", RoughlyType.Boolean);

		BASE_TYPE_MAP.put("char", RoughlyType.String);
		BASE_TYPE_MAP.put("java.lang.Character", RoughlyType.String);

		BASE_TYPE_MAP.put("java.lang.String", RoughlyType.String);

		BASE_TYPE_MAP.put("java.util.List", RoughlyType.Array);

		BASE_TYPE_MAP.put("java.util.Map", RoughlyType.Map);


	}

	public static RoughlyType assertBaseType(String className) {
		return Optional.ofNullable(BASE_TYPE_MAP.get(className)).orElse(RoughlyType.Object);
	}
}