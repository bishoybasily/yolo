package com.yolo.processor;

import lombok.Getter;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TypeElementWrapper extends ElementWrapper<TypeElement> implements Comparable<TypeElementWrapper> {

	protected ExecutableElementWrapper constructor;
	protected List<VariableElementWrapper> dependencies;
	protected List<ExecutableElementWrapper> methods;

	public TypeElementWrapper(TypeElement element, Filer filer, Types types, Elements elements, Messager messager) {
		super(element, filer, types, elements, messager);

		this.constructor = element.getEnclosedElements()
				.stream()
				.filter(e -> e.getKind() == ElementKind.CONSTRUCTOR)
				.map(ExecutableElement.class::cast)
				.map(executableElement -> new ExecutableElementWrapper(executableElement, filer, types, elements, messager).setParent(this))
				.findFirst()
				.get();

		this.dependencies = element.getEnclosedElements()
				.stream()
				.filter(e -> e.getKind() == ElementKind.CONSTRUCTOR)
				.map(ExecutableElement.class::cast)
				.findFirst()
				.get()
				.getParameters()
				.stream()
				.filter(e -> e.getKind() == ElementKind.PARAMETER)
				.map(VariableElement.class::cast)
				.map(variableElement -> new VariableElementWrapper(variableElement, filer, types, elements, messager).setParent(this.constructor))
				.collect(Collectors.toList());

		this.methods = element.getEnclosedElements()
				.stream()
				.filter(e -> e.getKind() == ElementKind.METHOD)
				.map(ExecutableElement.class::cast)
				.map(executableElement -> new ExecutableElementWrapper(executableElement, filer, types, elements, messager).setParent(this))
				.sorted()
				.collect(Collectors.toList());

	}

	public final List<ExecutableElementWrapper> methodsAnnotatedWith(Class<? extends Annotation> a) {
		return methods
				.stream()
				.filter(e -> e.annotatedWith(a))
				.sorted()
				.collect(Collectors.toList());
	}

	@Override
	public int compareTo(TypeElementWrapper o) {
		return dependencies.size() - o.dependencies.size();
	}
}
