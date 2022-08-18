package org.zpaul.javadoc.bean.r;

import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.List;

@Data
public class MarkdownNode {

	private String name;
	private String desc;
	private List<ClassNode> nodes;

	public List<String> printMarkdown() {
		System.out.println(JSONUtil.toJsonPrettyStr(this));
		return null;
	}
}