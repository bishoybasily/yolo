package com.springandroid.processor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

public class Extractor {

	private RoundEnvironment roundEnvironment;

	public Extractor(RoundEnvironment roundEnvironment) {
		this.roundEnvironment = roundEnvironment;
	}

	public Set<TypeElement> classesAnnotatedWith(Class<? extends Annotation> a) {
		return roundEnvironment.getElementsAnnotatedWith(a)
				.stream()
				.filter(elem -> elem.getKind() == ElementKind.CLASS)
				.map(TypeElement.class::cast)
				.collect(Collectors.toSet());
	}

	public Set<ExecutableElement> methodsAnnotatedWith(TypeElement typeElement, Class<? extends Annotation> a) {
		return typeElement.getEnclosedElements()
				.stream()
				.filter(e -> e.getKind() == ElementKind.METHOD && e.getAnnotation(a) != null)
				.map(ExecutableElement.class::cast)
				.collect(Collectors.toSet());
	}


}
