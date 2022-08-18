package org.zpaul.javadoc.bean.r;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.zpaul.javadoc.bean.ClassDoc;
import org.zpaul.javadoc.utils.ClassNodeUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@NoArgsConstructor
public class ClassNode {

	private int level;
	private String className;
	private String desc;

	private List<FieldNode> fields;
	private List<ClassNode> link;

	public ClassNode(int level) {
		this.level = level;
	}

	public ClassNode(int level, String className, String desc) {
		this.level = level;
		this.className = className;
		this.desc = desc;
	}

	public static ClassNode of(Map<String, ClassDoc> classDocMap, ClassDoc doc, int level) {
		final ClassNode classNode = new ClassNode(level, doc.getClassName(), Optional
				.ofNullable(doc.getComment().getText()).orElse(""));
		classNode.setFields(ClassNodeUtil.getFieldNodes(doc));
		classNode.setLink(ClassNodeUtil.getClassNodes(classDocMap, doc, level));
		return classNode;
	}
}