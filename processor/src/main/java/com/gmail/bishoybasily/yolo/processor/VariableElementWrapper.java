package com.gmail.bishoybasily.yolo.processor;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


public class VariableElementWrapper extends ElementWrapper<VariableElement> {

    public VariableElementWrapper(VariableElement element, Filer filer, Types types, Elements elements, Messager messager) {
        super(element, filer, types, elements, messager);
    }


}
