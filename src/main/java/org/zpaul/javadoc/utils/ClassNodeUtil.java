package org.zpaul.javadoc.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import org.zpaul.javadoc.bean.*;
import org.zpaul.javadoc.bean.r.ClassNode;
import org.zpaul.javadoc.bean.r.FieldNode;
import org.zpaul.javadoc.contants.RoughlyType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassNodeUtil {

	public static HashMap<RoughlyType, String> type;

	static {
		ClassNodeUtil.type = new HashMap<>();
		ClassNodeUtil.type.put(RoughlyType.Array, "List");
		ClassNodeUtil.type.put(RoughlyType.Map, "Map");
		ClassNodeUtil.type.put(RoughlyType.String, "String");
		ClassNodeUtil.type.put(RoughlyType.Number, "int");
		ClassNodeUtil.type.put(RoughlyType.Boolean, "boolean");
		ClassNodeUtil.type.put(RoughlyType.Date, "date");
		ClassNodeUtil.type.put(RoughlyType.Object, "object");
	}

	public static List<FieldNode> getFieldNodes(ClassDoc doc) {
		final Stream<TypeDoc> docStream = doc
				.getFields()
				.values()
				.parallelStream();
		return docStream.map(FieldNode::of)
		                .sorted(Comparator.comparing(FieldNode::getName))
		                .distinct()
		                .collect(Collectors.toList());
	}

	public static List<ClassNode> getClassNodes(Map<String, ClassDoc> classDocMap, ClassDoc doc, int level) {
		final int level2 = level + 1;
		return doc.getFields().values().parallelStream().filter(k -> {
			          final RoughlyType ct = RoughlyType.assertBaseType(k.getClassInfo());
			          if (ct == RoughlyType.Object) return true;
			          if (ct == RoughlyType.Array) {
				          return Arrays.stream(k.getParameters()).distinct()
				                       .map(TypeParameterizedDoc::getClassName)
				                       .map(ClassNodeUtil::changeType)
				                       .map(ClassNodeUtil::isBaseType)
				                       .count() > 1;
			          }
			          if (ct == RoughlyType.Map) {
				          //TODO: map
			          }
			          return false;
		          })
		          .map(k -> {
			          final RoughlyType ct = RoughlyType.assertBaseType(k.getClassName());
			          if (ct == RoughlyType.Object) return k.getClassName();
			          return k.getParameters()[0].getClassName();
		          })
		          .map(classDocMap::get)
		          .map(k -> ClassNode.of(classDocMap, k, level2))
		          .distinct()
		          .collect(Collectors.toList());
	}

	private static boolean isBaseType(String str) {
		final RoughlyType roughlyType = RoughlyType.assertBaseType(str);
		return roughlyType != RoughlyType.Array && roughlyType != RoughlyType.Map && roughlyType != RoughlyType.Object;
	}

	public static String changeType(String str) {
		return ClassNodeUtil.type.get(RoughlyType.assertBaseType(str));
	}

	public static String getDesc(String... str) {
		return Arrays.stream(str).filter(ObjectUtil::isNotNull).distinct().findFirst().orElse("");
	}

	public static String getTypeDetail(String str) {
		String s = ReUtil.get("\\<([^\\[\\]]*)\\>", str, 0);
		if (s.length() > 2) {
			s = s.substring(1, s.length() - 1);
			return s.substring(s.lastIndexOf(".") + 1);
		}
		return s;
	}

	/**
	 * @param doc
	 * @param type     true: field | false: class
	 *
	 * @return
	 */
	public static String getDesc(AbsDoc doc, boolean type) {
		String remark = Optional.ofNullable(doc.getComment())
		                        .map(CommentDoc::getText)
		                        .orElse("");
		String annotationVal = "";
		try {
			AnnotationDesc annotation = doc.getAnnotation(type ? "io.swagger.annotations.ApiModelProperty" : "io.swagger.annotations.ApiModel");
			annotationVal = annotation.getData().get("value")[0];
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return remark.length() > annotationVal.length() ? remark : annotationVal;

	}
}