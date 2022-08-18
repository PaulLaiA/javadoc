package org.zpaul.javadoc.plugin.lombok;

import org.zpaul.javadoc.bean.ClassDoc;
import org.zpaul.javadoc.bean.JavaDoc;
import org.zpaul.javadoc.plugin.IJavaDocPlugin;

public class LomBokPlugin implements IJavaDocPlugin {

	private final int PUBLIC_MODIFIER = 1;

	private final String LOMBOK_DATA = "lombok.Data";

	private final String LOMBOK_GETTER = "lombok.Getter";

	private final String LOMBOK_SETTER = "lombok.Setter";

	@Override
	public void handle(JavaDoc javaDocVO) {
		for (ClassDoc classDoc : javaDocVO.getClassDocs().values()) {
			handle(classDoc);
		}
	}

	public void handle(ClassDoc classDoc) {
		boolean addGet = classDoc.hasAnnotation(LOMBOK_DATA) || classDoc.hasAnnotation(LOMBOK_GETTER);
		boolean addSet = classDoc.hasAnnotation(LOMBOK_DATA) || classDoc.hasAnnotation(LOMBOK_SETTER);

//		for (TypeDoc field : classDoc.getFields().values()) {
////			if (!Modifier.isTransient(field.getModifierSpecifier())
////			    && !Modifier.isStatic(field.getModifierSpecifier())) {
//////				if (addGet || field.hasAnnotation(LOMBOK_GETTER)) {
//////					this.addGetMethod(classDoc, field);
//////				}
////
//////				if (!Modifier.isFinal(field.getModifierSpecifier())) {
//////					if (addSet || field.hasAnnotation(LOMBOK_SETTER)) {
//////						this.addSetMethod(classDoc, field);
//////					}
//////				}
////
////			}
//		}

	}

//	private void addGetMethod(ClassDoc classDoc, TypeDoc field) {
//		MethodDoc methodDoc = new MethodDoc();
//		methodDoc.setMethodName("get" + CoreUtil.upperCase(field.getName()));
//		methodDoc.setModifierSpecifier(PUBLIC_MODIFIER);
//		methodDoc.setParams(null);
//		methodDoc.setReturnType(field.copy());
//		classDoc.putMethod(methodDoc.toString(), methodDoc);
//	}

//	private void addSetMethod(ClassDoc classDoc, TypeDoc field) {
//		if (!Modifier.isFinal(field.getModifierSpecifier())) {
//			MethodDoc methodDoc = new MethodDoc();
//			methodDoc.setMethodName("set" + CoreUtil.upperCase(field.getName()));
//			methodDoc.setModifierSpecifier(PUBLIC_MODIFIER);
//			List<TypeDoc> params = new ArrayList(1);
//			params.add(field.copy());
//			methodDoc.setParams(params);
//			methodDoc.setReturnType(TypeDoc.ofVoid());
//			classDoc.putMethod(methodDoc.toString(), methodDoc);
//		}
//	}
}