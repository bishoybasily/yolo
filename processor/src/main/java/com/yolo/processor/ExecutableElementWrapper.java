package com.yolo.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class ExecutableElementWrapper extends ElementWrapper<ExecutableElement> implements Comparable<ExecutableElementWrapper> {

	protected Set<VariableElementWrapper> params;
	private TypeElementWrapper parent;

	public ExecutableElementWrapper(ExecutableElement element, Filer filer, Types types, Elements elements, Messager messager) {
		super(element, filer, types, elements, messager);

		this.params = element.getParameters()
				.stream()
				.filter(e -> e.getKind() == ElementKind.PARAMETER)
				.map(VariableElement.class::cast)
				.map(variableElement -> new VariableElementWrapper(variableElement, filer, types, elements, messager).setParent(this))
				.collect(Collectors.toSet());
	}

	public TypeName returnTypeName() {
		return ClassName.get(e.getReturnType());
	}

	@Override
	public int compareTo(ExecutableElementWrapper o) {
		return params.size() - o.params.size();
	}
}
