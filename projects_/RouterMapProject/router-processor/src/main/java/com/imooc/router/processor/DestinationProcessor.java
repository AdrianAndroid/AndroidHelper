package com.imooc.router.processor;

import com.google.auto.service.AutoService;
import com.imooc.router.annotations.Destination;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class DestinationProcessor extends AbstractProcessor {

    private static final String TAG = "DestinationProcessor";

    /**
     * 编译器找到我们关心的注解后，会回调这个方法
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set,
                           RoundEnvironment roundEnvironment) {

        // 避免多次调用 process
        if (roundEnvironment.processingOver()) {
            return false;
        }

        System.out.println(TAG + " >>> process start ...");

        String rootDir = processingEnv.getOptions().get("root_project_dir"); // 在buildSrc中传入
        String module_name = processingEnv.getOptions().get("module_name"); // 在buildSrc中传入
        System.out.println("[root_project_dir] root_project_dir=" + rootDir);
        System.out.println("[module_name] module_name=" + module_name);

        // 获取所有标记了 @Destination 注解的 类的信息
        Set<Element> allDestinationElements =
                (Set<Element>) roundEnvironment.getElementsAnnotatedWith(Destination.class);

        System.out.println(TAG + " >>> all Destination elements count = "
                + allDestinationElements.size());

        // 当未收集到 @Destination 注解的时候，跳过后续流程
        if (allDestinationElements.size() < 1) {
            return false;
        }


        // 需要校验
        // 先保存所有的
        Map<String, DestinationImpl> map = new HashMap<>();

        for (Element element : allDestinationElements) {
            final TypeElement typeElement = (TypeElement) element;
            // 尝试在当前类上，获取 @Destination 的信息
            final Destination destination = typeElement.getAnnotation(Destination.class);
            if (destination != null) {
                String url = destination.url();
                DestinationImpl value = new DestinationImpl(url, destination.description(),
                        typeElement.getQualifiedName().toString());
                if (!(map.put(url, value) == null)) {
                    String message = value.toString();
                    for (int i = 0; i < 50; i++) {
                        System.out.println("[ERROR]  异常抛不出文字，多打几遍！！！！！[ERROR]!!!![ERROR]");
                        System.out.println(message);
                        System.out.println();
                    }
                    throw new AssertionError(message);
                }
            }
        }


        String packageName = "com.imooc.router.mapping";
        long time = System.currentTimeMillis();

        RouterMappingFactory2 factory = new RouterMappingFactory2(map);
        
        // 生成RouterMap文件， 添加时间戳为了各个模块
        factory.generateClass_get(processingEnv.getFiler(), packageName, "RouterMapping_" + time);
        // 生成RouterPath文件， 便于每个模块中调用，方便
        factory.generateClass_field_only(processingEnv.getFiler(), packageName, module_name); // 每个模块都有自己独有的KEY

        // 写入json
        factory.generateJsonAndWrite(rootDir);


        System.out.println(TAG + " >>> process finish.");

        return false;
    }

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

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }
}
