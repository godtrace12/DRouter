package com.example.router.complier;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

//注册，相当于activity/androidmanifest注册
@AutoService(Processor.class)
// only care process annotation Route
@SupportedAnnotationTypes("com.example.router.annotation.Route")
public class RouteProcess extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE,"=======Route annotation processor");


        TypeSpec autoClass = TypeSpec.classBuilder("AutoClass").build();

        JavaFile javaFile = JavaFile.builder("com.apt.demo", autoClass).build();
        Filer filer = processingEnv.getFiler();
        try {
            JavaFileObject fileObject = filer.createSourceFile("com.example.order.entry.OrderEntry");
            OutputStream os = fileObject.openOutputStream();
//            os.write(c);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
