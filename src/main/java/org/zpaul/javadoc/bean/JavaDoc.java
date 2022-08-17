package org.zpaul.javadoc.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JavaDoc implements Serializable {

	private Map<String, ClassDoc> classDocs = new HashMap(2048);

	private Map<String, ClassDoc> includeClassDocs = new HashMap(2048);


	public Map<String, ClassDoc> getClassDocs() {
		return classDocs;
	}

	public void setClassDocs(Map<String, ClassDoc> classDocs) {
		this.classDocs = classDocs;
	}

	public ClassDoc getClassDoc(String className) {
		ClassDoc doc = this.classDocs.get(className);
		if (doc == null) {
			doc = this.includeClassDocs.get(className);
		}
		return doc;
	}

	public Map<String, ClassDoc> getIncludeClassDocs() {
		return includeClassDocs;
	}

	public void setIncludeClassDocs(Map<String, ClassDoc> includeClassDocs) {
		this.includeClassDocs = includeClassDocs;
	}
}