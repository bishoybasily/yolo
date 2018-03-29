package com.yolo.processor.graph;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.yolo.annotations.*;
import com.yolo.processor.Annotations;
import com.yolo.processor.Extractor;
import com.yolo.processor.ProcessorBase;
import com.yolo.processor.TypeNames;

import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Modifier;
import java.util.StringJoiner;

@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(Annotations.ENABLE_GRAPH)
public class ProcessorGraph extends ProcessorBase {

	private final String packageName = "com.yolo.generated";

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

		extractor.classes(LazyBean.class)
				.forEach(tew -> {

					// graph fields (lazy beans)
					FieldSpec.Builder configurationField = FieldSpec.builder(tew.typeName(), lowerFirstLetter(tew.name()))
							.addModifiers(Modifier.PRIVATE);
					graphClass.addField(configurationField.build());

					// lazy bean getter
					MethodSpec.Builder graphBeanGetterFunction = MethodSpec.methodBuilder(lowerFirstLetter(tew.name()))
							.addModifiers(Modifier.PUBLIC)
							.addStatement("return this." + lowerFirstLetter(tew.name()))
							.returns(tew.typeName());
					graphClass.addMethod(graphBeanGetterFunction.build());

					// lazy bean setter
					MethodSpec.Builder graphBeanSetterFunction = MethodSpec.methodBuilder(lowerFirstLetter(tew.name()))
							.addModifiers(Modifier.PUBLIC)
							.addParameter(tew.typeName(), lowerFirstLetter(tew.name()))
							.addStatement("this." + lowerFirstLetter(tew.name()) + "=" + lowerFirstLetter(tew.name()));
					graphClass.addMethod(graphBeanSetterFunction.build());

				});

		extractor.classes(InjectMembers.class)
				.forEach(tew -> {

					// injector class
					TypeSpec.Builder injectorClass = TypeSpec.classBuilder("Injector" + tew.name())
							.addModifiers(Modifier.PUBLIC);

					// inject function
					MethodSpec.Builder injectFunction = MethodSpec.methodBuilder("inject")
							.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
							.addParameter(tew.typeName(), lowerFirstLetter(tew.name()));

					tew.fields(Autowired.class)
							.forEach(vew -> {
								injectFunction.addStatement("" + lowerFirstLetter(tew.name()) + "." + lowerFirstLetter(vew.name()) + " = " + packageName + ".Graph.getInstance()." + lowerFirstLetter(vew.name()) + "()");
							});

					injectorClass.addMethod(injectFunction.build());

					flush(tew.packageReference(), injectorClass.build());

				});

		extractor.classes(Configuration.class)
				.forEach(tew -> {


					// graph fields (configurations)
					FieldSpec.Builder configurationField = FieldSpec.builder(tew.typeName(), lowerFirstLetter(tew.name()))
							.addModifiers(Modifier.PRIVATE);
					graphClass.addField(configurationField.build());

					if (tew.getConstructorDependencies().isEmpty()) {

						MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(lowerFirstLetter(tew.name()))
								.addModifiers(Modifier.PUBLIC)
								.addStatement("if ( this." + lowerFirstLetter(tew.name()) + " == null ) this." + lowerFirstLetter(tew.name()) + " = new " + tew.name() + "()")
								.addStatement("return this." + lowerFirstLetter(tew.name()))
								.returns(tew.typeName());
						graphClass.addMethod(graphBeanProviderFunction.build());

					} else {

						StringJoiner stringJoiner = new StringJoiner(",");
						tew.getConstructorDependencies().forEach(vew -> stringJoiner.add(vew.name() + "()"));
						String commaSeparatedParams = stringJoiner.toString();

						MethodSpec.Builder graphBeanProviderFunction = MethodSpec.methodBuilder(lowerFirstLetter(tew.name()))
								.addModifiers(Modifier.PUBLIC)
								.addStatement("if ( this." + lowerFirstLetter(tew.name()) + " == null ) this." + lowerFirstLetter(tew.name()) + " = new " + tew.name() + "(" + commaSeparatedParams + ")")
								.addStatement("return this." + lowerFirstLetter(tew.name()))
								.returns(tew.typeName());
						graphClass.addMethod(graphBeanProviderFunction.build());

					}

					// add beans functions
					tew.methods(Bean.class)
							.forEach(eew -> {

								// graph fields (constructorDependencies)
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
											.addStatement("if ( this." + lowerFirstLetter(eew.name()) + " == null ) this." + lowerFirstLetter(eew.name()) + " = " + lowerFirstLetter(tew.name()) + "()." + eew.name() + "(" + commaSeparatedParams + ")")
											.addStatement("return this." + lowerFirstLetter(eew.name()))
											.returns(eew.returnTypeName());

									graphClass.addMethod(graphBeanProviderFunction.build());

								}

							});

				});

		flush(packageName, graphClass.build());

		return false;
	}

}
