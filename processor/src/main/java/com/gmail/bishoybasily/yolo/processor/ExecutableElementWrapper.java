package com.gmail.bishoybasily.yolo.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ExecutableElementWrapper extends ElementWrapper<ExecutableElement> implements Comparable<ExecutableElementWrapper> {

    protected List<VariableElementWrapper> params;

    public ExecutableElementWrapper(ExecutableElement element, Filer filer, Types types, Elements elements, Messager messager) {
        super(element, filer, types, elements, messager);

        this.params = element.getParameters()
                .stream()
                .filter(e -> e.getKind() == ElementKind.PARAMETER)
                .map(VariableElement.class::cast)
                .map(variableElement -> new VariableElementWrapper(variableElement, filer, types, elements, messager))
                .collect(Collectors.toList());
    }

    public TypeName returnTypeName() {
        return ClassName.get(e.getReturnType());
    }

    @Override
    public int compareTo(ExecutableElementWrapper o) {
        return params.size() - o.params.size();
    }
}
