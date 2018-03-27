package com.springandroid.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;

@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(Annotations.ENABLE_GRAPH)
public class ProcessorGraph extends ProcessorBase {

	@Override
	public boolean process(Extractor extractor) {

		return false;
	}
}
