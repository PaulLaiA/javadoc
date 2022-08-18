package org.zpaul.javadoc.bean.r;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.zpaul.javadoc.bean.TypeDoc;
import org.zpaul.javadoc.contants.RoughlyType;
import org.zpaul.javadoc.utils.ClassNodeUtil;

@Data
@NoArgsConstructor
public class FieldNode {
	private String name;
	private String type;
	private String typeDetail;
	private String desc;
	private String memo;

	public FieldNode(String name, String type, String desc) {
		this.name = name;
		this.type = type;
		this.desc = desc;
	}

	public static FieldNode of(TypeDoc doc) {
		FieldNode fieldNode = new FieldNode();
		fieldNode.setName(doc.getName());
		fieldNode.setType(ClassNodeUtil.changeType(doc.getClassName()));
		if (fieldNode.getType().equals(ClassNodeUtil.type.get(RoughlyType.Array)))
			fieldNode.setTypeDetail(ClassNodeUtil.getTypeDetail(doc.getClassInfo()));
		else if (fieldNode.getType().equals(ClassNodeUtil.type.get(RoughlyType.Object)))
			fieldNode.setTypeDetail(doc.getClassInfo().substring(doc.getClassInfo().lastIndexOf(".") + 1));
		else
			fieldNode.setTypeDetail("");
		fieldNode.setDesc(ClassNodeUtil.getDesc(doc.getComment().getText()));
		return fieldNode;
	}
}