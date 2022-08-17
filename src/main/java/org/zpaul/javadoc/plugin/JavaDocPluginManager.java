package org.zpaul.javadoc.plugin;

import org.zpaul.javadoc.bean.JavaDoc;
import org.zpaul.javadoc.plugin.jsr.ValidationPlugin;
import org.zpaul.javadoc.plugin.lombok.LomBokPlugin;
import org.zpaul.javadoc.plugin.swagger.SwaggerPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author genx
 * @date 2020/3/7 22:01
 */
public class JavaDocPluginManager {

	private static final List<IJavaDocPlugin> list = new ArrayList(8);

	static {
		list.add(new LomBokPlugin());

		list.add(new ValidationPlugin());

		list.add(new SwaggerPlugin());
	}

	public static void handle(JavaDoc javaDocVO) {
		for (IJavaDocPlugin javaDocPlugin : list) {
			javaDocPlugin.handle(javaDocVO);
		}
	}
}