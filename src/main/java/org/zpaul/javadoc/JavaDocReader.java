package org.zpaul.javadoc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import org.zpaul.javadoc.bean.JavaDoc;
import org.zpaul.javadoc.utils.FileUtil;
import org.zpaul.javadoc.utils.JavaDocBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaDocReader {

	private static RootDoc root = null;

	public synchronized static JavaDoc read(File sourceDir, List<String> compilePaths) {
		return read(Collections.singletonList(sourceDir), compilePaths);
	}

	public synchronized static JavaDoc read(List<File> sourceDirs, List<String> compilePaths) {
		ClassDoc[] classes = readWithClassDocs(sourceDirs, compilePaths);
		return new JavaDocBuilder(classes).build();
	}

	public synchronized static ClassDoc[] readWithClassDocs(List<File> sourceDirs, List<String> compilePaths) {
		javadocExecute(sourceDirs, compilePaths);
		return root.classes();
	}

	private static void javadocExecute(List<File> sourceDirs, List<String> compilePaths) {
		List<String> commandList = new ArrayList<>(2048);
		commandList.add("-doclet");
		commandList.add(Doclet.class.getName());
		commandList.add("-encoding");
		commandList.add("utf-8");
		commandList.add("-classpath");
		commandList.add(String.join(";", compilePaths));

		List<File> list = new ArrayList(1024);
		for (File sourceDir : sourceDirs) {
			if (sourceDir.exists()) {
				if (sourceDir.isDirectory()) {
					list.addAll(FileUtil.listFiles(sourceDir, file ->
							file.isDirectory() || file.getName().toLowerCase().endsWith(".java"), false));
				} else if (sourceDir.getName().toLowerCase().endsWith(".java")) {
					list.add(sourceDir);
				}
			}

		}

		if (list.size() == 0) {
			throw new IllegalArgumentException("源文件不能为空");
		}

		for (File file : list) {
			commandList.add(file.getAbsolutePath());
		}
		com.sun.tools.javadoc.Main.execute(commandList.toArray(new String[commandList.size()]));
	}

	public static class Doclet {
		public static LanguageVersion languageVersion() {
			return LanguageVersion.JAVA_1_5;
		}

		public static boolean start(RootDoc root) {
			JavaDocReader.root = root;

			return true;
		}
	}
}