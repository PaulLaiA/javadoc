package org.zpaul.javadoc.filter;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;

public interface IJavaDocFilter {

	boolean accept(ClassDoc classDoc);

	boolean accept(MethodDoc methodDoc);

}