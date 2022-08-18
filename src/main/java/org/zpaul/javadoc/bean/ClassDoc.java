package org.zpaul.javadoc.bean;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ClassDoc extends AbsDoc {

	private final Map<String, TypeDoc> fields = new LinkedHashMap();
	private List<TypeVariableDoc> typeParameters;
	private HashMap<String, List<String>> interfaceTypes;

	public List<TypeVariableDoc> getTypeParameters() {
		return typeParameters;
	}

	public void setTypeParameters(List<TypeVariableDoc> typeParameters) {
		this.typeParameters = typeParameters;
	}

	public Map<String, TypeDoc> getFields() {
		return fields;
	}

	public void setFields(Map<String, TypeDoc> fields) {
		if (fields != null) {
			this.fields.putAll(fields);
		}
	}

	public void putField(String name, TypeDoc field) {
		this.fields.put(name, field);
	}


	public HashMap<String, List<String>> getInterfaceTypes() {
		return interfaceTypes;
	}

	public void setInterfaceTypes(HashMap<String, List<String>> interfaceTypes) {
		this.interfaceTypes = interfaceTypes;
	}

}