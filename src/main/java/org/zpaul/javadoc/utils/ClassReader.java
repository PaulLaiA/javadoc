package org.zpaul.javadoc.utils;

import com.sun.javadoc.Parameter;
import com.sun.javadoc.Tag;
import org.zpaul.javadoc.bean.ClassDoc;
import org.zpaul.javadoc.bean.MethodDoc;
import org.zpaul.javadoc.bean.TypeDoc;
import org.zpaul.javadoc.bean.TypeVariableDoc;

import java.util.*;


public class ClassReader {

	private final JavaDocBuilder.JavaDocEnv env;

	public ClassReader(JavaDocBuilder.JavaDocEnv env) {
		this.env = env;
	}


	public void read() {
		for (com.sun.javadoc.ClassDoc classDoc : env.getClassDocMap().values()) {
			read(classDoc);
		}
	}


	public ClassDoc read(com.sun.javadoc.ClassDoc classDoc) {
		ClassDoc classDocVO = env.get(classDoc.qualifiedTypeName());
		if (classDocVO != null) {
			return classDocVO;
		}
		classDocVO = new ClassDoc();
		classDocVO.setClassName(classDoc.qualifiedTypeName());
		env.add(classDocVO);

		try {

			tryExtendFromParent(classDocVO, classDoc);

			classDocVO.setModifierSpecifier(classDoc.modifierSpecifier());

			classDocVO.setInterfaceTypes(InterfaceTypeUtil.readInterfaceTypes(classDoc));
			classDocVO.setExtendTypes(ExtendTypeUtil.readExtendTypes(classDoc));

			classDocVO.setComment(CommentUtil.read(classDoc.inlineTags()));

			classDocVO.setTags(CommentUtil.readTagWithMap(classDoc.tags()));

			classDocVO.setAnnotations(AnnotationUtil.readAnnotationMap(classDoc));

			if (classDoc.typeParameters() != null && classDoc.typeParameters().length > 0) {
				List<TypeVariableDoc> typeVariableVOList = new ArrayList(classDoc.typeParameters().length);
				for (com.sun.javadoc.TypeVariable typeVariable : classDoc.typeParameters()) {
					typeVariableVOList.add(readTypeVariable(typeVariable));
				}
				classDocVO.setTypeParameters(typeVariableVOList);
			}

			classDocVO.setFields(readFields(classDoc));

//			classDocVO.setMethods(readMethods(classDoc));


		} catch (Exception e) {
			e.printStackTrace();
		}
		for (com.sun.javadoc.ClassDoc innerClassDoc : classDoc.innerClasses(true)) {
			read(innerClassDoc);
		}
		return classDocVO;
	}

	private void tryExtendFromParent(ClassDoc classDocVO, com.sun.javadoc.ClassDoc classDoc) {
		if (classDoc.superclass() != null && !Object.class.getName().equals(classDoc.superclass()
		                                                                            .qualifiedTypeName())) {
			ClassDoc parentVO = read(classDoc.superclass());

			classDocVO.setFields(parentVO.getFields());

//			classDocVO.setMethods(parentVO.getMethods());
		}
	}


	private TypeVariableDoc readTypeVariable(com.sun.javadoc.TypeVariable typeVariable) {
		TypeVariableDoc typeVariableVO = new TypeVariableDoc();
		typeVariableVO.setName(typeVariable.typeName());
		typeVariableVO.setDescription(typeVariable.toString());
		return typeVariableVO;
	}

	private Map<String, TypeDoc> readFields(com.sun.javadoc.ClassDoc classDoc) {
		com.sun.javadoc.FieldDoc[] fieldDocs = classDoc.fields(false);
		Map<String, TypeDoc> fields = new TreeMap();
		for (com.sun.javadoc.FieldDoc fieldDoc : fieldDocs) {
			TypeDoc typeDoc = readType(fieldDoc);
			fields.put(typeDoc.getName(), typeDoc);
		}
		return fields;
	}


	private Map<String, MethodDoc> readMethods(com.sun.javadoc.ClassDoc classDoc) {
		com.sun.javadoc.MethodDoc[] methodDocs = classDoc.methods(false);
		Map<String, MethodDoc> methods = new TreeMap();
		for (com.sun.javadoc.MethodDoc methodDoc : methodDocs) {
			MethodDoc m = readMethod(methodDoc);
			methods.put(m.toString(), m);
		}
		return methods;
	}


	private MethodDoc readMethod(com.sun.javadoc.MethodDoc methodDoc) {
		MethodDoc methodDocVO = new MethodDoc();
		methodDocVO.setMethodName(methodDoc.name());
		methodDocVO.setModifierSpecifier(methodDoc.modifierSpecifier());
		methodDocVO.setComment(CommentUtil.read(methodDoc.inlineTags()));
		methodDocVO.setAnnotations(AnnotationUtil.readAnnotationMap(methodDoc));
		methodDocVO.setTags(CommentUtil.readTagWithMap(methodDoc.tags()));

		List<TypeDoc> params = new ArrayList<>(methodDoc.parameters().length);
		Map<String, Tag[]> paramCommentMap = new HashMap(16);
		for (com.sun.javadoc.ParamTag paramTag : methodDoc.paramTags()) {
			paramCommentMap.put(paramTag.parameterName(), paramTag.inlineTags());
		}
		for (Parameter parameter : methodDoc.parameters()) {
			params.add(readType(parameter.type(), parameter.name(), paramCommentMap.get(parameter.name()), parameter.annotations(), null, 0));
		}
		methodDocVO.setParams(params);

		methodDocVO.setReturnType(readType(methodDoc.returnType(), "", methodDoc.tags("return"), null, null, 0));

		methodDocVO.setThrowExpections(CoreUtil.readThrowExpections(methodDoc));

		return methodDocVO;
	}

	public TypeDoc readType(com.sun.javadoc.FieldDoc fieldDoc) {
		return readType(fieldDoc.type(), fieldDoc.name(), fieldDoc.inlineTags(), fieldDoc.annotations(), fieldDoc.tags(), fieldDoc.modifierSpecifier());
	}

	public TypeDoc readType(com.sun.javadoc.Type type, String name, Tag[] inlineTags, com.sun.javadoc.AnnotationDesc[] annotations, Tag[] tags, int modifierSpecifier) {
		onTypeReaded(type);

		TypeDoc typeDoc = new TypeDoc();
		typeDoc.setClassInfo(type.toString());
		typeDoc.setClassName(type.qualifiedTypeName());
		typeDoc.setName(name);
		typeDoc.setComment(CommentUtil.read(inlineTags));
		typeDoc.setDimension(type.dimension().length() / 2 + 1);
		typeDoc.setAnnotations(AnnotationUtil.readAnnotationMap(annotations));
		typeDoc.setTags(CommentUtil.readTagWithMap(tags));
		typeDoc.setParameters(CoreUtil.readParameteres(type));
		typeDoc.setModifierSpecifier(modifierSpecifier);
		return typeDoc;
	}

	private void onTypeReaded(com.sun.javadoc.Type type) {
		if (type != null && type.asClassDoc() != null) {
			read(type.asClassDoc());
		}
	}

}