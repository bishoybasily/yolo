package com.yolo.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;

public class ClassNames {

	public static final ClassName
			CONTEXT_CLASS = ClassName.get("android.content", "Context"),
			INTENT_CLASS = ClassName.get("android.content", "Intent"),
			INTENT_SERVICE_CLASS = ClassName.get("android.app", "IntentService");


	public static TypeName CLASS(TypeMirror typeMirror) {
		return ClassName.get(typeMirror);
	}




}
