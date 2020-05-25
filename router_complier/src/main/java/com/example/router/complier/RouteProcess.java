package com.example.router.complier;

import com.example.router.annotation.Route;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

//注册，相当于activity/androidmanifest注册
// only care process annotation Route
//@SupportedAnnotationTypes("com.example.router.annotation.Route")
@AutoService(Processor.class)
public class RouteProcess extends AbstractProcessor {
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        Messager messager = processingEnv.getMessager();
//        messager.printMessage(Diagnostic.Kind.NOTE,"=======Route annotation processor");
//
//
//        TypeSpec autoClass = TypeSpec.classBuilder("AutoClass").build();
//
//        JavaFile javaFile = JavaFile.builder("com.apt.demo", autoClass).build();
//        Filer filer = processingEnv.getFiler();
//        try {
//            JavaFileObject fileObject = filer.createSourceFile("com.example.order.entry.OrderEntry");
//            OutputStream os = fileObject.openOutputStream();
////            os.write(c);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
    private Messager mMessager;
    private Elements mElementUtils;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv){
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        mMessager.printMessage(Diagnostic.Kind.WARNING, "initlizing...");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        mMessager.printMessage(Diagnostic.Kind.WARNING, "get supported types");
        HashSet<String> supportedTypes = new LinkedHashSet<>();
        supportedTypes.add(Route.class.getCanonicalName());
        return supportedTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Messager messager = processingEnv.getMessager();
        mMessager.printMessage(Diagnostic.Kind.WARNING,"=======Route annotation processor");
        HashMap<String,String> mapUrls = new HashMap<>();
        //获取注解
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Route.class);
        for (Element element:elements){
            TypeElement variableElement = (TypeElement) element;
            String fullClassName = variableElement.getQualifiedName().toString();//value
            mMessager.printMessage(Diagnostic.Kind.WARNING, "className ="+fullClassName);
            mapUrls.put(element.getAnnotation(Route.class).value(),fullClassName);
        }


        //成员变量path
        FieldSpec fieldPath = FieldSpec.builder(String.class,"path",Modifier.PUBLIC).build();

        //成员变量pathMap
        ClassName setName = ClassName.get("java.util","HashMap");
        ClassName stringName = ClassName.get("java.lang","String");
        TypeName setType = ParameterizedTypeName.get(setName,stringName,stringName);
        FieldSpec fieldPathMap = FieldSpec.builder(setType,"pathMap",Modifier.PUBLIC,Modifier.STATIC).build();
        //成员方法
        MethodSpec methodAdd = MethodSpec.methodBuilder("addPath")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .addParameter(String.class,"path")
                .addParameter(String.class,"value")
                .beginControlFlow("if(pathMap == null)")
                .addStatement("$N = new HashMap()",fieldPathMap)
                .endControlFlow()
                .addStatement("$N.put("+"path,value"+")",fieldPathMap)
                .returns(void.class).build();
        MethodSpec.Builder methodAddBuidler = MethodSpec.methodBuilder("addAllPath")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .beginControlFlow("if(pathMap == null)")
                .addStatement("$N = new HashMap()",fieldPathMap)
                .endControlFlow();

        for (Map.Entry<String,String> entry:mapUrls.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            methodAddBuidler.addStatement("pathMap.put($S,$S)",key,value);
        }
        methodAddBuidler.addStatement("return $N",fieldPathMap);
        MethodSpec methodAddAll = methodAddBuidler.returns(ParameterizedTypeName.get(ClassName.get(Map.class)
                ,ClassName.get(String.class),ClassName.get(String.class))).build();


        //构建类
        TypeSpec routerClass = TypeSpec.classBuilder("MyRouterClass")
                .addModifiers(Modifier.PUBLIC)
                .addField(fieldPath)
                .addField(fieldPathMap)
                .addMethod(methodAdd)
                .addMethod(methodAddAll)
                .build();

//        TypeSpec autoClass = TypeSpec.classBuilder("MyRouterClass").addModifiers(Modifier.PUBLIC).addField(String.class,"path",Modifier.PUBLIC).build();


        JavaFile javaFile = JavaFile.builder("com.apt.demo", routerClass).build();
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
