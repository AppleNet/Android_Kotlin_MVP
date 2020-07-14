package com.example.llc.annotation_compiler;

import com.example.llc.annotation.BindPath;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;


@AutoService(Processor.class) // 标记注解处理器
public class AnnotationCompiler extends AbstractProcessor {

    // 通过注解的方式获取注解处理器要找的注解，等价于重写getSupportedAnnotationTypes()
    // @SupportedAnnotationTypes({"com.example.llc.annotation.BindPath"})

    // 获取生成 java 的工具
    private Filer filer;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 得到模块中标记了 bindPath 注解的内容
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        HashMap<String, String> map = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            // 获取类节点
            TypeElement typeElement = (TypeElement) element;
            // 获取 BindPath 的值
            String value = typeElement.getAnnotation(BindPath.class).value();
            // 获取注解标记的类的全类名
            String activityName = typeElement.getQualifiedName().toString();
            map.put(value,activityName + ".class");
        }
        if (map.size() > 0) {
            createClass(map);
        }
        return false;
    }

    private void createClass(HashMap<String, String> map) {
        try {
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("putActivity")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(Void.class);
            for (String key : map.keySet()) {
                String activityName = map.get(key);
                methodBuilder.addStatement("com.example.llc.arouter.ARouter.getInstance().addActivity(\"" + key + "\"," + activityName + ");");
            }
            MethodSpec methodSpec = methodBuilder.build();
            // 获取到接口的类
            ClassName iRouter = ClassName.get("com.example.llc.arouter", "IRouter");

            // 创建工具类
            TypeSpec typeSpec = TypeSpec.classBuilder("ActivityUtil" + System.currentTimeMillis())
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(iRouter)
                    .addMethod(methodSpec)
                    .build();

            // 构建目录对象
            JavaFile javaFile = JavaFile.builder("com.example.llcgs.util", typeSpec).build();
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     *  声明支持的 java 版本
     *
     * */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     *  声明注解处理器要找的注解是谁
     *
     * */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }
}
