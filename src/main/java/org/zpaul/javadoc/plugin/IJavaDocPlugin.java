package org.zpaul.javadoc.plugin;

import org.zpaul.javadoc.bean.JavaDoc;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author genx
 * @date 2020/3/1 16:09
 */
public interface IJavaDocPlugin {

	/**
	 * 用于调整优先级
	 *
	 * @return
	 */
	default int getOrder() {
		return 0;
	}

	void handle(JavaDoc javaDocVO);

}