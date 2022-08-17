package org.zpaul.javadoc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.zpaul.MarkdownBudiler;
import org.zpaul.javadoc.bean.ClassDoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JavaDocStockClientTest {

	private final String base = "C:\\Users\\paulL\\SoftwareDevelopment\\Java\\InterviewQuestions\\";

	private final String sourceCode = base + "template-web-ssm\\src\\main\\java\\org\\dpaul\\template\\springboot\\pojo";
	private final String buildDir = base + "out\\artifacts\\Demo_template_web_ssm_main_war_exploded\\";

	private final String jsonPath = "C:\\Users\\paulL\\Desktop\\temp1\\javadoc.json";

	@Test
	public void demo() throws IOException {
		final Map<String, ClassDoc> classDoc = MarkdownBudiler.genClassDoc(sourceCode, buildDir);
		JSONArray array = new JSONArray(1024);
		for (ClassDoc value : classDoc.values()) {
			array.add(JSON.toJSON(value));
		}
		IOUtils.write(array.toJSONString(), Files.newOutputStream(Paths.get(jsonPath)), Charsets.UTF_8);
	}

}