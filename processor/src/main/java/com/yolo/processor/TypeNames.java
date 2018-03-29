package com.yolo.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;

public class TypeNames {

	public static final TypeName
			CONTEXT_CLASS = CLASS("android.content", "Context"),
			INTENT_CLASS = CLASS("android.content", "Intent"),
			INTENT_SERVICE_CLASS = CLASS("android.app", "IntentService");

	public static TypeName CLASS(TypeMirror typeMirror) {
		return ClassName.get(typeMirror);
	}

	public static TypeName CLASS(String pkg, String nam) {
		return ClassName.get(pkg, nam);
	}

	public static TypeName CLASS(Class<?> cls) {
		return CLASS(cls.getPackage().getName(), cls.getSimpleName());
	}

	public static TypeName CLASS(String name) {
		return ClassName.bestGuess(name);
	}

}
