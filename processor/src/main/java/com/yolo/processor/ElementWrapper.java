package com.yolo.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;

@Getter
@EqualsAndHashCode(of = {"e"})
public abstract class ElementWrapper<E extends Element> {

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

	public <P extends ElementWrapper> P getParent() {
		throw new UnsupportedOperationException();
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

	public final String packageReference() {
		return elements.getPackageOf(e).getQualifiedName().toString();
	}

}
