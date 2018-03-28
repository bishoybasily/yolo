package com.yolo.processor.graph;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;
import com.yolo.annotations.Bean;
import com.yolo.annotations.Component;
import com.yolo.annotations.Configuration;
import com.yolo.processor.Annotations;
import com.yolo.processor.Extractor;
import com.yolo.processor.ProcessorBase;

import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.inject.Provider;
import javax.lang.model.element.Modifier;
import java.util.StringJoiner;

@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(Annotations.ENABLE_GRAPH)
public class ProcessorGraph extends ProcessorBase {

	@Override
	public boolean process(Extractor extractor) {

		TypeSpec.Builder graphClass = TypeSpec.classBuilder("Graph")
				.addModifiers(Modifier.PUBLIC);

		extractor.classesAnnotatedWith(Configuration.class)
				.forEach(tew -> {

					// graph fields (configurations)
					FieldSpec.Builder configurationField = FieldSpec.builder(tew.typeName(), lowerFirstLetter(tew.name()))
							.addModifiers(Modifier.PRIVATE);
					graphClass.addField(configurationField.build());

					if (tew.getDependencies().isEmpty()) {

						MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(lowerFirstLetter(tew.name()))
								.addModifiers(Modifier.PUBLIC)
								.returns(tew.typeName())
								.addStatement("if ( this." + lowerFirstLetter(tew.name()) + " == null ) this." + lowerFirstLetter(tew.name()) + " = new " + tew.name() + "()")
								.addStatement("return this." + lowerFirstLetter(tew.name()));
						graphClass.addMethod(graphBeanProviderFunction.build());

					} else {

						StringJoiner stringJoiner = new StringJoiner(",");
						tew.getDependencies().forEach(vew -> stringJoiner.add(vew.name() + "()"));
						String commaSeparatedParams = stringJoiner.toString();

						MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(lowerFirstLetter(tew.name()))
								.addModifiers(Modifier.PUBLIC)
								.returns(tew.typeName())
								.addStatement("if ( this." + lowerFirstLetter(tew.name()) + " == null ) this." + lowerFirstLetter(tew.name()) + " = new " + tew.name() + "(" + commaSeparatedParams + ")")
								.addStatement("return this." + lowerFirstLetter(tew.name()));
						graphClass.addMethod(graphBeanProviderFunction.build());

					}

					// add beans functions
					tew.methodsAnnotatedWith(Bean.class)
							.forEach(eew -> {

								// graph fields (dependencies)
								FieldSpec.Builder dependencyField = FieldSpec.builder(eew.returnTypeName(), lowerFirstLetter(eew.name()))
										.addModifiers(Modifier.PRIVATE);
								graphClass.addField(dependencyField.build());

								if (eew.getParams().isEmpty()) {

									MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(eew.name())
											.addModifiers(Modifier.PUBLIC)
											.returns(eew.returnTypeName())
											.addStatement("if ( this." + lowerFirstLetter(eew.name()) + " == null ) this." + lowerFirstLetter(eew.name()) + " = " + lowerFirstLetter(tew.name()) + "()." + eew.name() + "()")
											.addStatement("return this." + lowerFirstLetter(eew.name()));
									graphClass.addMethod(graphBeanProviderFunction.build());

								} else {

									StringJoiner stringJoiner = new StringJoiner(",");
									eew.getParams().forEach(vew -> stringJoiner.add(vew.name() + "()"));
									String commaSeparatedParams = stringJoiner.toString();

									MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(eew.name())
											.addModifiers(Modifier.PUBLIC)
											.returns(eew.returnTypeName())
											.addStatement("if ( this." + lowerFirstLetter(eew.name()) + " == null ) this." + lowerFirstLetter(eew.name()) + " = " + lowerFirstLetter(tew.name()) + "()." + eew.name() + "(" + commaSeparatedParams + ")")
											.addStatement("return this." + lowerFirstLetter(eew.name()));

									graphClass.addMethod(graphBeanProviderFunction.build());

								}

							});

				});

		flush("com.yolo.generated", graphClass.build());

		return false;
	}

	private void temporary(Extractor extractor) {
		extractor.classesAnnotatedWith(Component.class)
				.forEach(tew -> {

					// create provider foreach bean
					tew.methodsAnnotatedWith(Bean.class)
							.forEach(eew -> {

								TypeSpec.Builder elementFactoryClass = TypeSpec.classBuilder("Provider" + upperFirstLetter(eew.name()))
										.addModifiers(Modifier.PUBLIC)
										.addSuperinterface(ParameterizedTypeName.get(ClassName.get(Provider.class), eew.returnTypeName()));

								FieldSpec.Builder factoryElementField = FieldSpec.builder(tew.typeName(), lowerFirstLetter(tew.name()), Modifier.PRIVATE);
								elementFactoryClass.addField(factoryElementField.build());

								MethodSpec.Builder factoryConstructor = MethodSpec.constructorBuilder()
										.addModifiers(Modifier.PUBLIC)
										.addParameter(tew.typeName(), lowerFirstLetter(tew.name()))
										.addStatement("this." + lowerFirstLetter(tew.name() + " = " + lowerFirstLetter(tew.name() + "")));
								elementFactoryClass.addMethod(factoryConstructor.build());

								MethodSpec.Builder factoryGetFunction = MethodSpec.methodBuilder("get")
										.addAnnotation(Override.class)
										.addModifiers(Modifier.PUBLIC)
										.returns(eew.returnTypeName())
										.addStatement("return " + lowerFirstLetter(tew.name()) + "." + eew.name() + "()");
								elementFactoryClass.addMethod(factoryGetFunction.build());

								flush(tew.packageReference(), elementFactoryClass.build());

							});

				});
	}

}
