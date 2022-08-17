package org.zpaul.javadoc.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public TypeParameterizedDoc[] getParameters() {
		return parameters;
	}

	public void setParameters(TypeParameterizedDoc[] parameters) {
		this.parameters = parameters;
	}

	public Set<String> getLimits() {
		return limits;
	}

	public void setLimits(Set<String> limits) {
		this.limits = limits;
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

	public TypeDoc copy() {
		String str = JSONObject.toJSONString(this);
		return JSONObject.parseObject(str, TypeDoc.class);
	}
}