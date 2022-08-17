package org.zpaul.javadoc.plugin;

import org.zpaul.javadoc.bean.JavaDoc;

public interface IJavaDocPlugin {

	default int getOrder() {
		return 0;
	}

	void handle(JavaDoc javaDocVO);

}