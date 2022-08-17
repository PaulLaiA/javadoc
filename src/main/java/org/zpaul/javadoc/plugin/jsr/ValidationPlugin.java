package org.zpaul.javadoc.plugin.jsr;

import org.apache.commons.lang3.StringUtils;
import org.zpaul.javadoc.bean.*;
import org.zpaul.javadoc.plugin.IJavaDocPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author genx
 * @date 2020/3/7 23:13
 */
public class ValidationPlugin implements IJavaDocPlugin {
	/**
	 * Bean Validation
	 * jakarta.validation-api
	 */
	private final String JAVAX_VALIDATION = "javax.validation.constraints.";
	/**
	 * Hibernate Validator 是 Bean Validation 的参考实现 . Hibernate Validator 提供了 JSR 303 规范中所有内置 constraint 的实现，除此之外还有一些附加的 constraint。
	 */
	private final String ORG_HIBERNATE_VALIDATOR = "org.hibernate.validator.constraints.";

	@Override
	public void handle(JavaDoc javaDocVO) {
		Set<String> limits;
		for (ClassDoc classDoc : javaDocVO.getClassDocs().values()) {

			for (TypeDoc field : classDoc.getFields().values()) {
				//字段上的注解
				limits = readValidAnnoaation(field);
				if (limits != null) {
					field.addLimits(limits);
				}
			}

			for (MethodDoc method : classDoc.getMethods().values()) {
				if (method.getReturnType() != null) {
					//方法上的注解
					limits = readValidAnnoaation(method);
					if (limits != null) {
						method.getReturnType().addLimits(limits);
					}
				}

				if (method.getParams() != null) {
					for (TypeDoc param : method.getParams()) {
						limits = readValidAnnoaation(param);
						if (limits != null) {
							param.addLimits(limits);
						}
					}
				}
			}

		}

	}

	public Set<String> readValidAnnoaation(TypeDoc doc) {
		if (doc != null && doc.getAnnotations() != null) {
			Set<String> set = new HashSet();
			for (AnnotationDesc annotation : doc.getAnnotations().values()) {

				if (annotation.getQualifiedName().startsWith(JAVAX_VALIDATION) || annotation.getQualifiedName()
				                                                                            .startsWith(ORG_HIBERNATE_VALIDATOR)) {
					try {
						ValidAnnotation validAnnotation = ValidAnnotation.valueOf(annotation.getName());
						if (validAnnotation != null) {
							set.add(parse(validAnnotation, annotation));
						}
					} catch (IllegalArgumentException e) {
						//未定义在 ValidAnnotation 枚举中
						set.add(annotation.getText());
					}
				}
			}
			return set;
		}
		return null;
	}

	public Set<String> readValidAnnoaation(MethodDoc doc) {
		if (doc != null && doc.getAnnotations() != null) {
			Set<String> set = new HashSet();
			for (AnnotationDesc annotation : doc.getAnnotations().values()) {

				if (annotation.getQualifiedName().startsWith(JAVAX_VALIDATION) || annotation.getQualifiedName()
				                                                                            .startsWith(ORG_HIBERNATE_VALIDATOR)) {
					try {
						ValidAnnotation validAnnotation = ValidAnnotation.valueOf(annotation.getName());
						if (validAnnotation != null) {
							set.add(parse(validAnnotation, annotation));
						}
					} catch (IllegalArgumentException e) {
						//未定义在 ValidAnnotation 枚举中
						set.add(annotation.getText());
					}
				}
			}
			return set;
		}
		return null;
	}

	private String parse(ValidAnnotation validAnnotation, AnnotationDesc annotation) {
		StringBuilder sb = new StringBuilder();
		sb.append(validAnnotation.getName());
		if (validAnnotation.getKeys() != null && validAnnotation.getKeys().length > 0) {
			sb.append("(");
			String[] values;
			String key;
			for (int i = 0; i < validAnnotation.getKeys().length; i++) {
				key = validAnnotation.getKeys()[i];
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(key).append(": ");
				values = annotation.getValues(key);
				if (values != null) {
					if (values.length > 1) {
						sb.append("[");
					}
					sb.append(StringUtils.join(values, ","));
					if (values.length > 1) {
						sb.append("]");
					}
				}
			}

			sb.append(")");
		}
		return sb.toString();
	}

	enum ValidAnnotation {
		// javax.validation.constraints. 下的注解

		Null("元素必须为 null"),
		NotNull("元素必须不为 null"),
		NotBlank("字符串不能为空"),
		AssertTrue("元素必须为 true"),
		AssertFalse("元素必须为 false"),
		Min("元素必须是一个数字，其值必须大于等于指定的最小值", "value"),
		Max("元素必须是一个数字，其值必须小于等于指定的最大值", "value"),
		DecimalMin("元素必须是一个数字，其值必须大于等于指定的最小值", "value"),
		DecimalMax("元素必须是一个数字，其值必须小于等于指定的最大值", "value"),
		Size("对象长度必须在指定的范围内", "min", "max"),
		Digits("元素必须是一个数字，其值必须在可接受的范围内", "integer", "fraction"),
		Past("元素必须是一个过去的日期"),
		Future("元素必须是一个将来的日期"),
		Pattern("元素必须符合指定的正则表达式", "regexp"),
		Email("元素必须是电子邮箱地址", "regexp"),
		NotEmpty("字符串的必须非空"),

		// org.hibernate.validator.constraints. 下的注解
		Length("字符串的长度必须在指定的范围内", "min", "max"),
		Range("元素必须在合适的范围内", "min", "max"),
		URL("元素必须是网址", "protocol", "host", "port", "regexp");

		private final String name;
		private final String[] keys;

		ValidAnnotation(String name, String... keys) {
			this.name = name;
			this.keys = keys;
		}


		public String getName() {
			return name;
		}

		public String[] getKeys() {
			return keys;
		}
	}

}