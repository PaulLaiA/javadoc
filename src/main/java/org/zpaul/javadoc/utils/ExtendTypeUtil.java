package org.zpaul.javadoc.utils;

import cn.hutool.core.util.ReUtil;
import com.sun.javadoc.ClassDoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExtendTypeUtil {

	public static HashMap<String, List<String>> readExtendTypes(ClassDoc classDoc) {
		HashMap<String, List<String>> result = new HashMap<>();
		try {
			String s = classDoc.superclassType().toString();
			String s1 = Optional.ofNullable(ReUtil.get("\\<([^\\[\\]]*)\\>", s, 0)).orElse("");
			s = s.replace(s1, "");
			s1 = s1.substring(1, s1.length() - 1);
			final List<String> collect = Arrays.stream(s1.split(",")).map(String::trim).collect(Collectors.toList());
			result.put(s, collect);
		} catch (Exception e) {
			result = new HashMap<>();
		}
		return result;
	}
}