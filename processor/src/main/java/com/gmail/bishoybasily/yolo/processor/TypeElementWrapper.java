package com.gmail.bishoybasily.yolo.processor;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class TypeElementWrapper extends ElementWrapper<TypeElement> implements Comparable<TypeElementWrapper> {

    protected ExecutableElementWrapper constructor;
    protected List<VariableElementWrapper> constructorDependencies;
    protected List<VariableElementWrapper> fields;
    protected List<ExecutableElementWrapper> methods;

    public TypeElementWrapper(TypeElement element, Filer filer, Types types, Elements elements, Messager messager) {
        super(element, filer, types, elements, messager);

        this.constructor = element.getEnclosedElements()
                .stream()
                .filter(e -> e.getKind() == ElementKind.CONSTRUCTOR)
                .map(ExecutableElement.class::cast)
                .map(executableElement -> new ExecutableElementWrapper(executableElement, filer, types, elements, messager))
                .findFirst()
                .get();

        this.constructorDependencies = element.getEnclosedElements()
                .stream()
                .filter(e -> e.getKind() == ElementKind.CONSTRUCTOR)
                .map(ExecutableElement.class::cast)
                .findFirst()
                .get()
                .getParameters()
                .stream()
                .filter(e -> e.getKind() == ElementKind.PARAMETER)
                .map(VariableElement.class::cast)
                .map(variableElement -> new VariableElementWrapper(variableElement, filer, types, elements, messager))
                .collect(Collectors.toList());

        this.fields = element.getEnclosedElements()
                .stream()
                .filter(e -> e.getKind() == ElementKind.FIELD)
                .map(VariableElement.class::cast)
                .map(variableElement -> new VariableElementWrapper(variableElement, filer, types, elements, messager))
                .collect(Collectors.toList());

        this.methods = element.getEnclosedElements()
                .stream()
                .filter(e -> e.getKind() == ElementKind.METHOD)
                .map(ExecutableElement.class::cast)
                .map(executableElement -> new ExecutableElementWrapper(executableElement, filer, types, elements, messager))
                .sorted()
                .collect(Collectors.toList());

    }

    public final List<ExecutableElementWrapper> methods(Class<? extends Annotation> a) {
        return methods
                .stream()
                .filter(e -> e.annotatedWith(a))
                .sorted()
                .collect(Collectors.toList());
    }

    public final List<VariableElementWrapper> fields(Class<? extends Annotation> a) {
        return fields
                .stream()
                .filter(e -> e.annotatedWith(a))
                .collect(Collectors.toList());
    }

    public ExecutableElementWrapper getConstructor() {
        return constructor;
    }

    public List<VariableElementWrapper> getConstructorDependencies() {
        return constructorDependencies;
    }

    public List<VariableElementWrapper> getFields() {
        return fields;
    }

    public List<ExecutableElementWrapper> getMethods() {
        return methods;
    }

    @Override
    public int compareTo(TypeElementWrapper o) {
        return constructorDependencies.size() - o.constructorDependencies.size();
    }


}
