package org.zpaul;

import org.zpaul.javadoc.JavaDocReader;
import org.zpaul.javadoc.bean.ClassDoc;
import org.zpaul.javadoc.bean.JavaDoc;

import java.io.File;
import java.util.*;

public class MarkdownBudiler {
	private final static String classes = "WEB-INF\\classes";
	private final static String lib = "WEB-INF\\lib";

	public static void genMarkdown(String sourceCode, String buildDir, String outputDir) {
		genMarkdown(sourceCode, buildDir + classes, buildDir + lib, outputDir);
	}

	public static void genMarkdown(String sourceCode, String buildClass, String lib, String outputDir) {
		final Map<String, ClassDoc> classDoc = genClassDoc(sourceCode, buildClass, lib);

	}

	public static Map<String, ClassDoc> genClassDoc(String sourceCode, String buildDir) {
		return genClassDoc(sourceCode, buildDir + classes, buildDir + lib);
	}

	public static Map<String, ClassDoc> genClassDoc(String sourceCode, String buildClass, String lib) {
		File sourceCodePath = new File(sourceCode);
		List<String> compilePathList = new ArrayList<>();
		compilePathList.add(buildClass);
		File libDir = new File(lib);
		for (File file : Objects.requireNonNull(libDir.listFiles())) {
			compilePathList.add(file.getAbsolutePath());
		}
		JavaDoc javaDoc = JavaDocReader.read(Collections.singletonList(sourceCodePath), compilePathList);
		return javaDoc.getClassDocs();
	}
}