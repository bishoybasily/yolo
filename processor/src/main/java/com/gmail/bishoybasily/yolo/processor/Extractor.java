package com.gmail.bishoybasily.yolo.processor;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class Extractor {

    private RoundEnvironment roundEnvironment;

    private Filer filer;
    private Types types;
    private Elements elements;
    private Messager messager;

    public Extractor(RoundEnvironment roundEnvironment, Filer filer, Types types, Elements elements, Messager messager) {
        this.roundEnvironment = roundEnvironment;
        this.filer = filer;
        this.types = types;
        this.elements = elements;
        this.messager = messager;

    }

    public List<TypeElementWrapper> classes(Class<? extends Annotation> a, ElementKind... kinds) {
        return roundEnvironment.getElementsAnnotatedWith(a)
                .stream()
                .filter(e -> Arrays.asList(kinds).contains(e.getKind()))
                .map(TypeElement.class::cast)
                .map(typeElement -> new TypeElementWrapper(typeElement, filer, types, elements, messager))
                .sorted()
                .collect(Collectors.toList());
    }


}
