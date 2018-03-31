package com.yolo.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode(of = {"e"})
public class ElementWrapper<E extends Element> {

	protected E e;

	protected Filer filer;
	protected Types types;
	protected Elements elements;
	protected Messager messager;

	public ElementWrapper(E e, Filer filer, Types types, Elements elements, Messager messager) {
		this.e = e;
		this.filer = filer;
		this.types = types;
		this.elements = elements;
		this.messager = messager;
	}

	public final String name() {
		return e.getSimpleName().toString();
	}

	public final TypeName typeName() {
		return ClassName.get(e.asType());
	}

	public boolean annotatedWith(Class<? extends Annotation> a) {
		return e.getAnnotation(a) != null;
	}

	public final List<TypeMirror> annotationTypeMirrors(Class<? extends Annotation> aClass, String key) {

		Optional<AnnotationValue> value = getAnnotationValues(aClass, key);

		if (value.isPresent())
			return value
					.map(AnnotationValue::getValue)
					.map(o -> (List<AnnotationValue>) o)
					.get()
					.stream()
					.map(AnnotationValue::getValue)
					.map(TypeMirror.class::cast)
					.collect(Collectors.toList());
		else return Collections.emptyList();

	}


	public final String annotationString(Class<? extends Annotation> aClass, String key) {

		Optional<AnnotationValue> value = getAnnotationValues(aClass, key);

		if (value.isPresent())
			return value
					.map(AnnotationValue::getValue)
					.map(o -> (String) o)
					.get();
		else return null;

	}

	public final String packageReference() {
		return elements.getPackageOf(e).getQualifiedName().toString();
	}

	private Optional<AnnotationValue> getAnnotationValues(Class<? extends Annotation> aClass, String key) {
		return e.getAnnotationMirrors()
				.stream()
				.filter(annotationMirror -> elements.getTypeElement(aClass.getName()).asType().equals(annotationMirror.getAnnotationType()))
				.findFirst()
				.map(AnnotationMirror::getElementValues)
				.map(Map::entrySet)
				.map(Collection::stream)
				.map(o -> o.collect(Collectors.toMap(e -> e.getKey().getSimpleName().toString(), Map.Entry::getValue)))
				.map(o -> o.get(key));
	}

}
