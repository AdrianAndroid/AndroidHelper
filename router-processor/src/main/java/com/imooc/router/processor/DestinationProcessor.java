package com.imooc.router.processor;

import com.google.auto.service.AutoService;
import com.imooc.router.annotations.Destination;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Time:2021/7/5 20:42
 * Author:
 * Description:
 */
@AutoService(Processor.class)
public class DestinationProcessor extends AbstractProcessor {


    private static final String TAG = "DestinationProcessor";

    /**
     * 告诉编译器，当前处理器支持的注解类型
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(
                Destination.class.getCanonicalName()
        );
    }


    /**
     * 编译器找到我们关心的注解后，会回调这个方法
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // 避免多次调用process
        if (roundEnv.processingOver()) {
            return false;
        }


        System.out.println(TAG + " >>> process start ... ");
        // 获取所有标记了@Destination注解的类的信息
        Set<? extends Element> allDestinationElements = roundEnv.getElementsAnnotatedWith(Destination.class);
        System.out.println(TAG + " >>> all Destination Element count = " + allDestinationElements.size());
        // 当未收集到@Destination注解的时候，跳过后续流程
        if (allDestinationElements.size() < 1) {
            return false;
        }
        // 遍历所有注解信息, 挨个获取详细信息
        for (Element element : allDestinationElements) {
            final TypeElement typeElement = (TypeElement) element;
            // 尝试在当前类上获取@Destination的信息
            final Destination destination = typeElement.getAnnotation(Destination.class);
            if (destination == null) {
                continue;
            }
            final String url = destination.url();
            final String description = destination.description();
            final String realPath = typeElement.getQualifiedName().toString();
            System.out.println(TAG + ">>> url = " + url);
            System.out.println(TAG + ">>> description = " + description);
            System.out.println(TAG + ">>> realPath = " + realPath);
        }
        return false;
    }
}
