package com.example.router.complier;

import com.example.router.annotation.RouterAppService;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * 【说明】：
 *
 * @author daijun
 * @version 2.0
 * @date 2020/5/27 10:16
 */
@SupportedAnnotationTypes("com.example.router.annotation.RouterAppService")
@AutoService(Processor.class)
public class RouterAppServiceProcess extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(set.size() <=0){
            return false;
        }
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.WARNING,"========RouterAppService annotation process start");
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(RouterAppService.class);
        if (elements.size() <=0){
            return false;
        }
        List<String> fullClassNameList = new ArrayList<>();
        String groupName = "";
        for(Element element:elements){
            TypeElement variableElement = (TypeElement) element;
            String fullClassName = variableElement.getQualifiedName().toString();
            messager.printMessage(Diagnostic.Kind.WARNING,"className="+fullClassName+" elemntSize="+elements.size());
            fullClassNameList.add(fullClassName);
            groupName = variableElement.getAnnotation(RouterAppService.class).value();
        }
        //构建方法
        MethodSpec.Builder methodAppBuilder = MethodSpec.methodBuilder("getAppServices")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("List<String> serviceList = new $T<>()",ArrayList.class);
        for (String className : fullClassNameList){
            methodAppBuilder.addStatement("serviceList.add($S)",className);
        }
        methodAppBuilder.addStatement("return serviceList");
        MethodSpec methodGetService = methodAppBuilder.returns(ParameterizedTypeName.get(ClassName.get(List.class),ClassName.get(String.class))).build();
        if (groupName == null || groupName.isEmpty()){
            groupName = System.currentTimeMillis()%100+"";
        }
        //构建类
        TypeSpec serviceClass = TypeSpec.classBuilder("DAppServiceClass_"+groupName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodGetService)
                .build();
        JavaFile javaFile = JavaFile.builder("com.dj.apt.service",serviceClass).build();
        try{
            Filer filer = processingEnv.getFiler();
            javaFile.writeTo(filer);
        }catch (IOException e){
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.WARNING,"generate apt class file fail");
        }
        return true;
    }
}
