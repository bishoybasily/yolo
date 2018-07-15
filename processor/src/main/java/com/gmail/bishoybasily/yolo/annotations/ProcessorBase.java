package com.gmail.bishoybasily.yolo.annotations;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.util.Set;

public abstract class ProcessorBase extends AbstractProcessor {

    protected Filer filer;
    protected Types types;
    protected Elements elements;
    protected Messager messager;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public final synchronized void init(ProcessingEnvironment environment) {
        this.filer = environment.getFiler();
        this.messager = environment.getMessager();
        this.elements = environment.getElementUtils();
        this.types = environment.getTypeUtils();
    }

    @Override
    public final boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return process(new Extractor(roundEnv, filer, types, elements, messager));
    }

    public abstract boolean process(Extractor extractor);

    protected String upperFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    protected String lowerFirstLetter(String input) {
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }

    protected void flush(String packageName, TypeSpec typeSpec) {
        try {
            JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
        } catch (IOException e) {
//			e.printStackTrace();
        }
    }

}
