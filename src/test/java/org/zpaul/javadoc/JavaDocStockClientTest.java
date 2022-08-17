package org.zpaul.javadoc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.zpaul.javadoc.bean.ClassDoc;
import org.zpaul.javadoc.bean.JavaDoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JavaDocStockClientTest {

	@Test
	public void demo() throws IOException {

		final String s = "C:\\Users\\paulL\\SoftwareDevelopment\\Java\\InterviewQuestions";
		File sourceCodePath = new File(s + "\\template-web-ssm\\src\\main\\java\\org\\dpaul\\template\\springboot\\pojo");
		List<String> compilePathList = new ArrayList<>();
		compilePathList.add(s + "\\out\\artifacts\\Demo_template_web_ssm_main_war_exploded\\WEB-INF\\classes");
		File libDir = new File(s + "\\out\\artifacts\\Demo_template_web_ssm_main_war_exploded\\WEB-INF\\lib");
		for (File file : Objects.requireNonNull(libDir.listFiles())) {
			compilePathList.add(file.getAbsolutePath());
		}
		JavaDoc javaDoc = JavaDocReader.read(Collections.singletonList(sourceCodePath), compilePathList);
		JSONArray array = new JSONArray(1024);
		for (ClassDoc value : javaDoc.getClassDocs().values()) {
			if (value.getClassName().startsWith("org.dpaul.template.springboot.pojo"))
				array.add(JSON.toJSON(value));
		}
		IOUtils.write(array.toJSONString(), Files.newOutputStream(Paths.get("C:\\Users\\paulL\\Desktop\\temp1\\javadoc.json")), Charsets.UTF_8);
	}

}