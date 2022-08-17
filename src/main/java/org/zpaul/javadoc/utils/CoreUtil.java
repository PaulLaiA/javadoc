package org.zpaul.javadoc.utils;

import org.apache.commons.lang3.StringUtils;
import org.zpaul.javadoc.bean.ClassDoc;
import org.zpaul.javadoc.bean.TypeParameterizedDoc;

import java.util.HashMap;
import java.util.Map;


public class CoreUtil {
	private static final String JAVA_DOT = "java.";

	private static final String ITERABLE = "java.lang.Iterable";

	private static final String NUMBER = "java.lang.Number";

	private static final String CHAR_SEQUENCE = "java.lang.CharSequence";

	private static final String DATE = "java.util.Date";

	private static final String MAP = "java.util.Map";

	public static boolean isBaseType(com.sun.javadoc.Type type) {
		return type.asClassDoc() == null || type.qualifiedTypeName().startsWith(JAVA_DOT);
	}

	public static boolean isIterable(ClassDoc classDoc) {
		if (classDoc != null) {
			return classDoc.getInterfaceTypes().containsKey(ITERABLE);
		}
		return false;
	}

	public static boolean isMap(ClassDoc classDoc) {
		if (classDoc != null) {
			return classDoc.getInterfaceTypes().containsKey(MAP);
		}
		return false;
	}


	public static boolean isNumber(ClassDoc classDoc) {
		if (classDoc != null) {
			return classDoc.getInterfaceTypes().containsKey(NUMBER);
		}
		return false;
	}

	public static boolean isString(ClassDoc classDoc) {
		if (classDoc != null) {
			return classDoc.getInterfaceTypes().containsKey(CHAR_SEQUENCE);
		}
		return false;
	}

	public static boolean isDate(ClassDoc classDoc) {
		if (classDoc != null) {
			return classDoc.getMethods().containsKey("before(java.util.Date)") && classDoc.getMethods()
			                                                                              .containsKey("after(java.util.Date)");
		}
		return false;
	}


	public static TypeParameterizedDoc[] readParameteres(com.sun.javadoc.Type type) {
		if (type.asParameterizedType() != null) {
			TypeParameterizedDoc[] array = new TypeParameterizedDoc[type.asParameterizedType().typeArguments().length];
			com.sun.javadoc.Type item;
			for (int i = 0; i < type.asParameterizedType().typeArguments().length; i++) {
				item = type.asParameterizedType().typeArguments()[i];
				TypeParameterizedDoc typeParameterizedDoc = new TypeParameterizedDoc();
				typeParameterizedDoc.setText(item.toString());
				typeParameterizedDoc.setClassName(item.qualifiedTypeName());
				typeParameterizedDoc.setDimension(item.dimension().length() / 2 + 1);
				typeParameterizedDoc.setParameters(readParameteres(item));
				array[i] = typeParameterizedDoc;
			}
			return array;
		}
		return null;
	}


	public static Map<String, String> readThrowExpections(com.sun.javadoc.MethodDoc methodDoc) {
		Map<String, String> throwExpections = new HashMap(8);
		if (methodDoc.thrownExceptions() != null) {
			for (com.sun.javadoc.ClassDoc classDoc : methodDoc.thrownExceptions()) {
				throwExpections.put(classDoc.qualifiedTypeName(), "");
			}
		}

		for (com.sun.javadoc.ThrowsTag throwsTag : methodDoc.throwsTags()) {
			if (throwsTag.exceptionType() != null) {
				throwExpections.put(throwsTag.exceptionType().qualifiedTypeName(), throwsTag.exceptionComment());
			}
		}
		return throwExpections;
	}


	public static String upperCase(String str) {
		if (StringUtils.isNotBlank(str)) {
			char[] ch = str.toCharArray();
			if (ch[0] >= 'a' && ch[0] <= 'z') {
				ch[0] = (char) (ch[0] - 32);
			}
			return new String(ch);
		}
		return "";
	}

	public static String lowCase(String str) {
		if (StringUtils.isNotBlank(str)) {
			char[] ch = str.toCharArray();
			if (ch[0] >= 'A' && ch[0] <= 'Z') {
				ch[0] = (char) (ch[0] + 32);
			}
			return new String(ch);
		}
		return "";
	}

}