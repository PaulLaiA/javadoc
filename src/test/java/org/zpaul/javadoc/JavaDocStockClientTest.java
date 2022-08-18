package org.zpaul.javadoc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.zpaul.javadoc.bean.ClassDoc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;

public class JavaDocStockClientTest {

	@Test
	public void demo() {
		String base = "C:\\Users\\paulL\\SoftwareDevelopment\\Java\\Demo\\";
		String sourceCode = base + "template-web-ssm\\src\\main\\java\\org\\dpaul\\template\\springboot\\pojo";
		String buildDir = base + "out\\artifacts\\template_web_ssm\\";

		final Map<String, ClassDoc> classDoc = MarkdownBudiler.genClassDoc(sourceCode, buildDir);
		MarkdownBudiler.genClassDocMd(classDoc, "org.dpaul.template.springboot.pojo.dto.IBase");
		JSONArray array = new JSONArray(1024);
		for (ClassDoc value : classDoc.values()) {
			array.add(JSONUtil.parse(value));
		}
		String jsonPath = "C:\\Users\\paulL\\Desktop\\temp1\\javadoc.json";
		FileUtil.writeString(array.toStringPretty(),
		                     Paths.get(jsonPath).toFile(),
		                     StandardCharsets.UTF_8.toString());
	}

}