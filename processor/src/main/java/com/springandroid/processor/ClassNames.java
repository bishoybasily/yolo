package com.springandroid.processor;

import com.squareup.javapoet.ClassName;

public interface ClassNames {

	ClassName
			CONTEXT_CLASS = ClassName.get("android.content", "Context"),
			INTENT_CLASS = ClassName.get("android.content", "Intent"),
			INTENT_SERVICE_CLASS = ClassName.get("android.app", "IntentService");

}
