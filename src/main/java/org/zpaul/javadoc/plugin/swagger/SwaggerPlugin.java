package org.zpaul.javadoc.plugin.swagger;

import org.zpaul.javadoc.bean.*;
import org.zpaul.javadoc.plugin.IJavaDocPlugin;

public class SwaggerPlugin implements IJavaDocPlugin {


	private final String API_MODEL_PROPERTY = "io.swagger.annotations.ApiModelProperty";

	private final String API_OPERATION = "io.swagger.annotations.ApiOperation";

	@Override
	public void handle(JavaDoc javaDocVO) {
		for (ClassDoc classDoc : javaDocVO.getClassDocs().values()) {
			handle(classDoc);
		}
	}

	public void handle(ClassDoc classDoc) {
		AnnotationDesc annotationDocVO;
		for (TypeDoc field : classDoc.getFields().values()) {
			annotationDocVO = field.getAnnotation(API_MODEL_PROPERTY);
			if (annotationDocVO != null) {

				String comment = annotationDocVO.getValue("value");

				boolean required = "true".equals(annotationDocVO.getValue("required"));

				if (field.getComment() == null) {
					field.setComment(CommentDoc.of(comment));
				}

				if (required) {
					field.addLimit("required");
				}
			}
		}

//		for (MethodDoc method : classDoc.getMethods().values()) {
//			annotationDocVO = method.getAnnotation(API_OPERATION);
//			if (annotationDocVO != null) {
//				String comment = annotationDocVO.getValue("value");
//				if (method.getComment() == null) {
//					method.setComment(CommentDoc.of(comment));
//				}
//			}
//		}

	}
}