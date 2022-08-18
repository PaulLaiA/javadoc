package org.zpaul.javadoc.bean;


import lombok.Data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
public class TypeDoc extends AbsDoc {


	private String name;

	private int dimension = 1;

	private TypeParameterizedDoc[] parameters;

	private Set<String> limits;

	public static TypeDoc ofVoid() {
		TypeDoc typeDoc = new TypeDoc();
		typeDoc.setName("");
		typeDoc.setDimension(1);
		typeDoc.setClassName("void");
		typeDoc.setClassInfo("void");
		return typeDoc;
	}

	public void addLimit(String limit) {
		addLimits(Collections.singletonList(limit));
	}

	public void addLimits(Collection<String> limits) {
		if (this.limits == null) {
			this.limits = new HashSet();
		}
		this.limits.addAll(limits);
	}

//	@Override
//	public String toString() {
//		String classInfo = "";
//		if (this.getClassInfo().equals(this.getClassName())) {
//			classInfo = getStr(this.getClassInfo());
//		} else {
//			String replace = this.classInfo.replace(this.getClassName(), "");
//			replace = replace.substring(1, replace.length() - 1);
//			replace = getStr(replace);
//			classInfo = getStr(this.getClassName()) + "<" + replace + ">";
//		}
//
//		return String.format("|%s|%s|%s|%s|", this.name, this.getComment().getText(), classInfo, "");
//	}

//	private String getStr(String str) {
//		try {
//			str = str.substring(str.lastIndexOf(".") + 1);
//		} catch (Exception ignored) {
//		}
//		return str;
//	}
}