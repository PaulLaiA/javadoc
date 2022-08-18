package org.zpaul.javadoc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import org.zpaul.javadoc.bean.ClassDoc;
import org.zpaul.javadoc.bean.CommentDoc;
import org.zpaul.javadoc.bean.JavaDoc;
import org.zpaul.javadoc.bean.r.ClassNode;
import org.zpaul.javadoc.bean.r.MarkdownNode;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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

	public static void genClassDocMd(Map<String, ClassDoc> classDocMap, String iName) {
		final List<String> t_md = FileUtil.readLines(
				Objects.requireNonNull(MarkdownBudiler.class.getResource("/template.md")),
				StandardCharsets.UTF_8);
		final List<String> t_md_data = FileUtil.readLines(
				Objects.requireNonNull(MarkdownBudiler.class.getResource("/template.md")),
				StandardCharsets.UTF_8);
		final List<List<String>> collect = classDocMap
				.values()
				.parallelStream()
				.filter(k -> k.getInterfaceTypes().containsKey(iName))
				.collect(Collectors.toList())
				.stream()
				.map(classDoc -> {
					MarkdownNode mdn = new MarkdownNode();
					mdn.setName(classDoc.getClassName());
					mdn.setDesc(Optional.ofNullable(classDoc.getComment()).map(CommentDoc::getText).orElse(""));
					mdn.setNodes(classDoc.getInterfaceTypes().values().stream()
					                     .flatMap(Collection::stream)
					                     .map(classDocMap::get)
					                     .map(k -> ClassNode.of(classDocMap, k, 1))
					                     .collect(Collectors.toList()));
					return mdn;
				}).map(MarkdownNode::printMarkdown)
				.collect(Collectors.toList());

		collect.stream().map(JSONUtil::toJsonPrettyStr).forEach(System.out::println);

	}


}