package com.gmail.bishoybasily.yolo.processor.graph;

import com.gmail.bishoybasily.yolo.annotations.Autowired;
import com.gmail.bishoybasily.yolo.annotations.Bean;
import com.gmail.bishoybasily.yolo.annotations.Configuration;
import com.gmail.bishoybasily.yolo.annotations.EnableGraph;
import com.gmail.bishoybasily.yolo.annotations.InjectMembers;
import com.gmail.bishoybasily.yolo.annotations.LazyBean;
import com.gmail.bishoybasily.yolo.annotations.Qualifier;
import com.gmail.bishoybasily.yolo.annotations.Scope;
import com.gmail.bishoybasily.yolo.processor.Annotations;
import com.gmail.bishoybasily.yolo.processor.Extractor;
import com.gmail.bishoybasily.yolo.processor.ProcessorBase;
import com.gmail.bishoybasily.yolo.processor.TypeNames;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(Annotations.ENABLE_GRAPH)
public class ProcessorGraph extends ProcessorBase {

    private static final String PACKAGE_NAME = "com.gmail.bishoybasily.yolo.generated";

    @Override
    public boolean process(Extractor extractor) {

        TypeSpec.Builder graphClass = TypeSpec.classBuilder("Graph")
                .addModifiers(Modifier.PUBLIC);

        FieldSpec.Builder instanceField = FieldSpec.builder(TypeNames.CLASS("Graph"), "instance")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC);
        graphClass.addField(instanceField.build());

        MethodSpec.Builder graphConstructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE);
        graphClass.addMethod(graphConstructor.build());

        MethodSpec.Builder graphGetInstanceFunction = MethodSpec.methodBuilder("getInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addStatement("if (instance == null) { synchronized (Graph.class) { if (instance == null) { instance = new Graph(); } } } return instance")
                .returns(TypeNames.CLASS("Graph"));
        graphClass.addMethod(graphGetInstanceFunction.build());

        extractor.classes(InjectMembers.class, ElementKind.CLASS).forEach(tew -> {

            // injector class
            TypeSpec.Builder injectorClass = TypeSpec.classBuilder("Injector" + tew.name())
                    .addModifiers(Modifier.PUBLIC);

            // inject function
            MethodSpec.Builder injectFunction = MethodSpec.methodBuilder("inject")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addParameter(tew.typeName(), lowerFirstLetter(tew.name()));

            tew.fields(Autowired.class).forEach(vew -> {

                // respect qualifier
                String dependencyName = lowerFirstLetter(vew.name());
                String dependencyQualifierName = vew.annotationString(Qualifier.class, "value");
                if (dependencyQualifierName != null)
                    dependencyName = dependencyQualifierName;

                injectFunction.addStatement("" + lowerFirstLetter(tew.name()) + "." + lowerFirstLetter(vew.name()) + " = " + PACKAGE_NAME + ".Graph.getInstance()." + dependencyName + "()");

            });

            injectorClass.addMethod(injectFunction.build());

            flush(tew.packageReference(), injectorClass.build());

        });

        extractor.classes(Configuration.class, ElementKind.CLASS).forEach(tew -> {

            // graph fields (configurations)
            FieldSpec.Builder configurationField = FieldSpec.builder(tew.typeName(), lowerFirstLetter(tew.name()))
                    .addModifiers(Modifier.PRIVATE);
            graphClass.addField(configurationField.build());

            String configurationScope = tew.annotationString(Scope.class, "value");
            boolean singletonConfiguration = configurationScope == null || configurationScope.equalsIgnoreCase("singleton");

            if (tew.getConstructorDependencies().isEmpty()) {

                if (singletonConfiguration) {

                    MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(lowerFirstLetter(tew.name()))
                            .addModifiers(Modifier.PUBLIC)
                            .addStatement("if ( this." + lowerFirstLetter(tew.name()) + " == null ) this." + lowerFirstLetter(tew.name()) + " = new " + tew.name() + "()")
                            .addStatement("return this." + lowerFirstLetter(tew.name()))
                            .returns(tew.typeName());
                    graphClass.addMethod(graphBeanProviderFunction.build());

                } else {

                    MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(lowerFirstLetter(tew.name()))
                            .addModifiers(Modifier.PUBLIC)
                            .addStatement("return new " + tew.name() + "()")
                            .returns(tew.typeName());
                    graphClass.addMethod(graphBeanProviderFunction.build());

                }

            } else {

                StringJoiner stringJoiner = new StringJoiner(",");
                tew.getConstructorDependencies().forEach(vew -> stringJoiner.add(vew.name() + "()"));
                String commaSeparatedParams = stringJoiner.toString();

                if (singletonConfiguration) {

                    MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(lowerFirstLetter(tew.name()))
                            .addModifiers(Modifier.PUBLIC)
                            .addStatement("if ( this." + lowerFirstLetter(tew.name()) + " == null ) this." + lowerFirstLetter(tew.name()) + " = new " + tew.name() + "(" + commaSeparatedParams + ")")
                            .addStatement("return this." + lowerFirstLetter(tew.name()))
                            .returns(tew.typeName());
                    graphClass.addMethod(graphBeanProviderFunction.build());

                } else {

                    MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(lowerFirstLetter(tew.name()))
                            .addModifiers(Modifier.PUBLIC)
                            .addStatement("return new " + tew.name() + "(" + commaSeparatedParams + ")")
                            .returns(tew.typeName());
                    graphClass.addMethod(graphBeanProviderFunction.build());

                }

            }

            // add beans functions
            tew.methods(Bean.class).forEach(eew -> {

                // respect qualifier
                String beanName = lowerFirstLetter(eew.name());
                String beanQualifierName = eew.annotationString(Qualifier.class, "value");
                if (beanQualifierName != null)
                    beanName = beanQualifierName;

                // graph fields (constructorDependencies)
                FieldSpec.Builder dependencyField = FieldSpec.builder(eew.returnTypeName(), beanName)
                        .addModifiers(Modifier.PRIVATE);
                graphClass.addField(dependencyField.build());

                String beanScope = eew.annotationString(Scope.class, "value");
                boolean singletonBean = beanScope == null || beanScope.equalsIgnoreCase("singleton");

                if (eew.getParams().isEmpty()) {

                    if (singletonBean) {

                        MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(beanName)
                                .addModifiers(Modifier.PUBLIC)
                                .returns(eew.returnTypeName())
                                .addStatement("if ( this." + beanName + " == null ) this." + beanName + " = " + lowerFirstLetter(tew.name()) + "()." + eew.name() + "()")
                                .addStatement("return this." + beanName);
                        graphClass.addMethod(graphBeanProviderFunction.build());

                    } else {

                        MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(beanName)
                                .addModifiers(Modifier.PUBLIC)
                                .addStatement("return " + lowerFirstLetter(tew.name()) + "()." + eew.name() + "()")
                                .returns(eew.returnTypeName());
                        graphClass.addMethod(graphBeanProviderFunction.build());

                    }

                } else {

                    StringJoiner stringJoiner = new StringJoiner(",");
                    eew.getParams().forEach(vew -> {

                        // respect qualifier
                        String parameterName = vew.name();
                        String parameterQualifierName = vew.annotationString(Qualifier.class, "value");
                        if (parameterQualifierName != null)
                            parameterName = parameterQualifierName;

                        stringJoiner.add(parameterName + "()");

                    });

                    String commaSeparatedParams = stringJoiner.toString();

                    if (singletonBean) {

                        MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(beanName)
                                .addModifiers(Modifier.PUBLIC)
                                .addStatement("if ( this." + beanName + " == null ) this." + beanName + " = " + lowerFirstLetter(tew.name()) + "()." + eew.name() + "(" + commaSeparatedParams + ")")
                                .addStatement("return this." + beanName)
                                .returns(eew.returnTypeName());
                        graphClass.addMethod(graphBeanProviderFunction.build());

                    } else {

                        MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(beanName)
                                .addModifiers(Modifier.PUBLIC)
                                .addStatement("return " + lowerFirstLetter(tew.name()) + "()." + eew.name() + "(" + commaSeparatedParams + ")")
                                .returns(eew.returnTypeName());
                        graphClass.addMethod(graphBeanProviderFunction.build());

                    }

                }

            });

        });

        Map<TypeName, String> lazyBeans = new HashMap<>();

        extractor.classes(LazyBean.class, ElementKind.CLASS, ElementKind.INTERFACE).forEach(tew -> {

            TypeName typeName = tew.typeName();
            String name = tew.name();

            lazyBeans.put(typeName, name);

        });

        extractor.classes(EnableGraph.class, ElementKind.CLASS).forEach(tew -> {

            tew.annotationTypeMirrors(EnableGraph.class, "lazyBeans").forEach(typeMirror -> {

                TypeName typeName = TypeNames.CLASS(typeMirror);
                String name = types.asElement(typeMirror).getSimpleName().toString();

                lazyBeans.put(typeName, name);

            });

        });

        lazyBeans.forEach((typeName, name) -> {

            // graph fields (lazy beans)
            FieldSpec.Builder configurationField = FieldSpec.builder(typeName, lowerFirstLetter(name))
                    .addModifiers(Modifier.PRIVATE);
            graphClass.addField(configurationField.build());

            // lazy bean getter
            MethodSpec.Builder graphBeanGetterFunction = MethodSpec.methodBuilder(lowerFirstLetter(name))
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("return this." + lowerFirstLetter(name))
                    .returns(typeName);
            graphClass.addMethod(graphBeanGetterFunction.build());

            // lazy bean setter
            MethodSpec.Builder graphBeanSetterFunction = MethodSpec.methodBuilder(lowerFirstLetter(name))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(typeName, lowerFirstLetter(name))
                    .addStatement("this." + lowerFirstLetter(name) + "=" + lowerFirstLetter(name));
            graphClass.addMethod(graphBeanSetterFunction.build());

        });

        flush(PACKAGE_NAME, graphClass.build());

        return false;
    }


}
