package com.gmail.bishoybasily.yolo.annotations.worker;

import com.gmail.bishoybasily.yolo.annotations.Annotations;
import com.gmail.bishoybasily.yolo.annotations.Extractor;
import com.gmail.bishoybasily.yolo.annotations.ProcessorBase;
import com.gmail.bishoybasily.yolo.annotations.TypeNames;
import com.gmail.bishoybasily.yolo.annottions.Job;
import com.gmail.bishoybasily.yolo.annottions.Worker;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(Annotations.ENABLE_WORKER)
public class ProcessorWorker extends ProcessorBase {

    @Override
    public boolean process(Extractor extractor) {

        extractor.classes(Worker.class, ElementKind.CLASS)
                .forEach(tew -> {

                    String elementClassName = "Worker" + tew.name();
                    TypeSpec.Builder elementWorkerClass = TypeSpec.classBuilder(elementClassName)
                            .addModifiers(Modifier.PUBLIC)
                            .superclass(TypeNames.INTENT_SERVICE_CLASS);

                    MethodSpec.Builder elementHandlerConstructor = MethodSpec.constructorBuilder()
                            .addModifiers(Modifier.PUBLIC)
                            .addStatement("super(\"" + elementClassName + "\")");
                    elementWorkerClass.addMethod(elementHandlerConstructor.build());

                    Set<String> actions = new HashSet<>();

                    tew.methods(Job.class)
                            .forEach(eew -> {

                                MethodSpec.Builder elementJobFunction = MethodSpec.methodBuilder(eew.name())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addParameter(TypeNames.INTENT_CLASS, "intent");
                                elementWorkerClass.addMethod(elementJobFunction.build());

                                MethodSpec.Builder elementExecuteJobFunction = MethodSpec.methodBuilder("execute" + upperFirstLetter(eew.name()) + "Job")
                                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                        .addParameter(TypeNames.CONTEXT_CLASS, "context")
                                        .addParameter(TypeNames.INTENT_CLASS, "intent")
                                        .addStatement("context.startService(intent.setClass(context, " + tew.packageReference() + "." + tew.name() + ".class).setAction(\"" + eew.name() + "\"))");
                                elementWorkerClass.addMethod(elementExecuteJobFunction.build());

                                actions.add(eew.name());

                            });

                    MethodSpec.Builder workerOnHandleIntentFunction = MethodSpec.methodBuilder("onHandleIntent")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                            .addParameter(TypeNames.INTENT_CLASS, "intent");

                    CodeBlock.Builder elementSwitchBlock = CodeBlock.builder();
                    elementSwitchBlock.add("switch (intent.getAction()){");
                    for (String action : actions)
                        elementSwitchBlock.add("case \"" + action + "\": " + action + "(intent); break;");
                    elementSwitchBlock.add("}");
                    workerOnHandleIntentFunction.addCode(elementSwitchBlock.build());

                    elementWorkerClass.addMethod(workerOnHandleIntentFunction.build());

                    flush(tew.packageReference(), elementWorkerClass.build());

                });

        return false;

    }

}
