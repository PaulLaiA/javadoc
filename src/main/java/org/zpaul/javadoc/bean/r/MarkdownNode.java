package org.zpaul.javadoc.bean.r;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.zpaul.javadoc.bean.ClassDoc;
import org.zpaul.javadoc.bean.CommentDoc;
import org.zpaul.javadoc.utils.ClassNodeUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MarkdownNode {

	private String name;
	private String desc;
	private List<ClassNode> nodes;

	public MarkdownNode(ClassDoc classDoc, Map<String, ClassDoc> classDocMap) {
		this.name = classDoc.getClassName();
		this.desc = ClassNodeUtil.getDesc(classDoc,false);
		this.nodes = classDoc.getExtendTypes().values().stream()
		                     .flatMap(Collection::stream)
		                     .map(classDocMap::get)
		                     .map(k -> ClassNode.of(classDocMap, k, 1))
		                     .collect(Collectors.toList());
	}

	public List<String> printMarkdown() {
		return null;
	}

}