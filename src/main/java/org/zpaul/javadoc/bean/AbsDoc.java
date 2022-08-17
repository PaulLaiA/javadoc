package org.zpaul.javadoc.bean;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

public abstract class AbsDoc implements Serializable {
	protected String className;

	protected String classInfo;

	protected int modifierSpecifier;

	protected Map<String, AnnotationDesc> annotations;

	protected CommentDoc comment;

	protected Map<String, CommentDoc> tags;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(String classInfo) {
		this.classInfo = classInfo;
	}

	public int getModifierSpecifier() {
		return modifierSpecifier;
	}

	public void setModifierSpecifier(int modifierSpecifier) {
		this.modifierSpecifier = modifierSpecifier;
	}

	public Map<String, AnnotationDesc> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Map<String, AnnotationDesc> annotations) {
		this.annotations = annotations;
	}

	public CommentDoc getComment() {
		return comment;
	}

	public void setComment(CommentDoc comment) {
		this.comment = comment;
	}

	public Map<String, CommentDoc> getTags() {
		return tags;
	}

	public void setTags(Map<String, CommentDoc> tags) {
		this.tags = tags;
	}

	public boolean hasAnnotation(String annotationClassName) {
		return StringUtils.isNotBlank(annotationClassName) && annotations != null && annotations.containsKey(annotationClassName);
	}

	public AnnotationDesc getAnnotation(String annotationClassName) {
		if (StringUtils.isNotBlank(annotationClassName) && this.annotations != null) {
			return this.annotations.get(annotationClassName);
		}
		return null;
	}

	public String getAnnotationValue(String annotationClassName, String key) {
		if (StringUtils.isNotBlank(annotationClassName) && this.annotations != null) {
			AnnotationDesc annotationDocVO = this.annotations.get(annotationClassName);
			return annotationDocVO.getValue(key);
		}
		return null;
	}

	public String[] getAnnotationValues(String annotationClassName, String key) {
		if (StringUtils.isNotBlank(annotationClassName) && this.annotations != null) {
			AnnotationDesc annotationDocVO = this.annotations.get(annotationClassName);
			return annotationDocVO.getValues(key);
		}
		return null;
	}

	public CommentDoc getTag(String tagKey) {
		if (StringUtils.isBlank(tagKey) || this.tags == null) {
			return null;
		}
		if (tagKey.charAt(0) != '@') {
			tagKey = "@" + tagKey;
		}
		return this.tags.get(tagKey);
	}

}