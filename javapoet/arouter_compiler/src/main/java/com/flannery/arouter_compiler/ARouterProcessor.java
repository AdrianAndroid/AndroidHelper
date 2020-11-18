package com.flannery.arouter_compiler;

import com.flannery.arouter_annotation.ARouter;
import com.flannery.arouter_annotation.bean.RouterBean;
import com.flannery.arouter_compiler.utils.ProcessorConfig;
import com.flannery.arouter_compiler.utils.ProcessorUtils;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
@SupportedAnnotationTypes({ProcessorConfig.AROUTER_PACKAGE})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions({ProcessorConfig.OPTIONS, ProcessorConfig.APT_PACKAGE})
public class ARouterProcessor extends AbstractProcessor {

    private Elements elementTool;// 操作Element的工具类（类，函数，属性，其实都是Element）
    private Types typeTool;// type(类信息)的工具类，包含用于操作TypeMirror的工具方法
    private Messager messager;// Message用来打印 日志相关信息
    private Filer filer;// 文件生成器， 类 资源 等，就是最终要生成的文件 是需要Filer来完成的
    private String options; // 各个模块传递过来的模块名 例如：app order personal
    private String aptPackage;// 各个模块传递过来的目录 用于统一存放 apt生成的文件

    private Map<String, List<RouterBean>> mAllPathMap = new HashMap<>();
    private Map<String, String> mAllGroupMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        elementTool = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        typeTool = processingEnvironment.getTypeUtils();

        // 只有接受APP壳传过来的数据，才能证明我们的APT环境搭建完成
        options = processingEnvironment.getOptions().get(ProcessorConfig.OPTIONS);
        aptPackage = processingEnvironment.getOptions().get(ProcessorConfig.APT_PACKAGE);
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>> options >" + options + "<\n");
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>> aptPackage:>" + aptPackage + "<\n");
        if (options != null && aptPackage != null) {
            messager.printMessage(Diagnostic.Kind.NOTE, "APT 环境搭建完成。。。\n");
        } else {
            messager.printMessage(Diagnostic.Kind.NOTE, "APT 环境有问题，请检查options与aptPackage为null。。。\n");
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "并没有发现@ARouter注解的地方啊");
            return false;
        }
        // 获取所有@ARouter注解的元素的集合
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ARouter.class);
        // 获取所有Element工具类， 获取Activity， Callback类型
        TypeElement activityType = elementTool.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE);
        // 显示类信息（获取被注解的节点，类节点）这也叫自描述Mirror
        TypeMirror activityMirror = activityType.asType();
        // 遍历所有的类节点
        for (Element element : elements) {
            // 获取类节点， 获取包节点
            // 获取简单类
            String className = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "被@ARouter注解的类有：" + className);

            // 拿到注解
            ARouter aRouter = element.getAnnotation(ARouter.class);

            // 一系列的检查工作
            // 在循环里面， 对"路有对象"进行封装
            RouterBean routerBean = new RouterBean.Builder()
                    .addGroup(aRouter.group())
                    .addPath(aRouter.path())
                    .addElement(element)
                    .build();

            // ARouter注解的类， 必须继承Activity
            TypeMirror elementMirror = element.asType();// XXXActivity的具体详情
            if (typeTool.isSubtype(elementMirror, activityMirror)) {
                routerBean.setTypeEnum(RouterBean.TypeEnum.ACTIVITY);
            } else {
                // 不匹配抛出异常
                throw new RuntimeException("@ARouter注解目前仅限于Activity类之上");
            }

            if (checkRouterPath(routerBean)) {
                messager.printMessage(Diagnostic.Kind.NOTE, "RouterBean Check Success:" + routerBean.toString());

                // 赋值mAllPathMap集合里面去
                List<RouterBean> routerBeans = mAllPathMap.get(routerBean.getGroup());

                // 如果从Map中找不到key为：bean.getGroup()的数据，就新建List集合再添加进Map
                if (ProcessorUtils.isEmpty(routerBeans)) {
                    routerBeans = new ArrayList<>();
                    routerBeans.add(routerBean);
                    mAllPathMap.put(routerBean.getGroup(), routerBeans);
                } else {
                    routerBeans.add(routerBean);
                }

            } else { //ERROR 编译期发生异常
                messager.printMessage(Diagnostic.Kind.NOTE, "@ARouter注解未按规范配置， 如：/app/MainActivity");
            }

            // mAllPathMap里面有值了
            TypeElement pathType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_PATH); // path
            TypeElement groupType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_GROUP);// group

            // 第一大步：系列PATH
            try {
                createPathFile(pathType);
            } catch (IOException e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE, "在生成PATH模板时，异常了 e:" + e.getMessage());
            }


            // 第二大步：组头（带头大哥）
            try {
                createGroupType(groupType, pathType);
            } catch (IOException e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE, "在生成GROUP模板时，异常了 e:" + e.getMessage());
            }

        }

        return true; // 必须写返回值， 表示处理@ARouter注解完成
    }

    private void createGroupType(TypeElement groupType, TypeElement pathType)throws IOException  {

    }

    private void createPathFile(TypeElement pathType) throws IOException {

    }

    private final boolean checkRouterPath(RouterBean routerBean) {



        return false;
    }
}
