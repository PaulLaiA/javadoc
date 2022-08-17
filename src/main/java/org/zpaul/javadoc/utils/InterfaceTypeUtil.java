package org.zpaul.javadoc.utils;

import com.sun.javadoc.ClassDoc;

import java.util.*;

public class InterfaceTypeUtil {

	public static HashMap<String, List<String>> readInterfaceTypes(ClassDoc classDoc) {
		HashMap<String, List<String>> interfaceTypes = new HashMap<>();
		Set<String> processed = new HashSet(16);
		processed.add(Object.class.getName());
		readInterfaceTypes(interfaceTypes, classDoc, processed);
		return interfaceTypes;
	}

	private static void readInterfaceTypes(HashMap<String, List<String>> interfaceTypes, ClassDoc classDoc, Set<String> processed) {
		if (classDoc == null || processed.contains(classDoc.qualifiedTypeName())) {
			return;
		}
		processed.add(classDoc.qualifiedTypeName());

		for (ClassDoc type : classDoc.interfaces()) {
			interfaceTypes.put(type.qualifiedTypeName(), new ArrayList<>());
			if (classDoc.interfaceTypes() != null && classDoc.interfaceTypes().length != 0)
				try {
					Arrays.stream(classDoc.interfaceTypes())
					      .map(k -> k.asParameterizedType().typeArguments())
					      .forEach(k -> Arrays.stream(k)
					                          .forEach(a -> interfaceTypes.get(type.qualifiedTypeName())
					                                                      .add(a.qualifiedTypeName())));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			readInterfaceTypes(interfaceTypes, type, processed);
		}

	}

}