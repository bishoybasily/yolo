package com.gmail.bishoybasily.yolo.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

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

    public final String annotationString(Class<? extends Annotation> aClass, String key) {

        Optional<AnnotationValue> value = getAnnotationValue(aClass, key);

        if (value.isPresent())
            return value
                    .map(AnnotationValue::getValue)
                    .map(o -> (String) o)
                    .get();
        else return null;

    }

    public final List<TypeMirror> annotationTypeMirrors(Class<? extends Annotation> aClass, String key) {

        Optional<AnnotationValue> value = getAnnotationValue(aClass, key);

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

    public final String packageReference() {
        return elements.getPackageOf(e).getQualifiedName().toString();
    }

    private Optional<AnnotationValue> getAnnotationValue(Class<? extends Annotation> aClass, String key) {
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

    public E getE() {
        return e;
    }

    public Filer getFiler() {
        return filer;
    }

    public Types getTypes() {
        return types;
    }

    public Elements getElements() {
        return elements;
    }

    public Messager getMessager() {
        return messager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementWrapper<?> that = (ElementWrapper<?>) o;

        return e != null ? e.equals(that.e) : that.e == null;
    }

    @Override
    public int hashCode() {
        return e != null ? e.hashCode() : 0;
    }
}
