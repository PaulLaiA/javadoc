package org.zpaul.javadoc.bean;

import java.io.Serializable;

public class TypeParameterizedDoc implements Serializable {

	private String text;

	private String className;

	private int dimension = 1;


	private TypeParameterizedDoc[] parameters;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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
}