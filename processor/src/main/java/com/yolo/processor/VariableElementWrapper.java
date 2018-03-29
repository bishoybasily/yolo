package com.yolo.processor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@Getter
@Setter
@Accessors(chain = true)
public class VariableElementWrapper extends ElementWrapper<VariableElement> {

	public VariableElementWrapper(VariableElement element, Filer filer, Types types, Elements elements, Messager messager) {
		super(element, filer, types, elements, messager);
	}


}
