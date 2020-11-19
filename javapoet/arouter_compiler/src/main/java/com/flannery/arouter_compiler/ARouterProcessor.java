package com.flannery.arouter_compiler;

import com.flannery.arouter_annotation.ARouter;
import com.flannery.arouter_annotation.bean.RouterBean;
import com.flannery.arouter_compiler.utils.ProcessorConfig;
import com.flannery.arouter_compiler.utils.ProcessorUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

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
import javax.lang.model.element.Modifier;
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

    private void createGroupType(TypeElement groupType, TypeElement pathType) throws IOException {
        // 仓库二 缓存二 判断是否有需要生成的类文件
        if (ProcessorUtils.isEmpty(mAllGroupMap) || ProcessorUtils.isEmpty(mAllPathMap)) return;

        // 返回值 这一段 Map<String, Class<? extends ARouterPath>>
        TypeName methodReturns = ParameterizedTypeName.get(
                ClassName.get(Map.class),        // Map
                ClassName.get(String.class),    // Map<String,

                // Class<? extends ARouterPath>> 难度
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        // ? extends ARouterPath
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))) // ? extends ARouterLoadPath
                // WildcardTypeName.supertypeOf() 做实验 ? super

                // 最终的：Map<String, Class<? extends ARouterPath>>
        );

        // 1.方法 public Map<String, Class<? extends ARouterPath>> getGroupMap() {
        MethodSpec.Builder methodBuidler = MethodSpec.methodBuilder(ProcessorConfig.GROUP_METHOD_NAME) // 方法名
                .addAnnotation(Override.class) // 重写注解 @Override
                .addModifiers(Modifier.PUBLIC) // public修饰符
                .returns(methodReturns); // 方法返回值

        // Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
        methodBuidler.addStatement("$T<$T, $T> $N = new $T<>()",
                ClassName.get(Map.class),
                ClassName.get(String.class),

                // Class<? extends ARouterPath> 难度
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))), // ? extends ARouterPath
                ProcessorConfig.GROUP_VAR1,
                ClassName.get(HashMap.class));

        //  groupMap.put("personal", ARouter$$Path$$personal.class);
        //	groupMap.put("order", ARouter$$Path$$order.class);
        for (Map.Entry<String, String> entry : mAllGroupMap.entrySet()) {
            methodBuidler.addStatement("$N.put($S, $T.class)",
                    ProcessorConfig.GROUP_VAR1, // groupMap.put
                    entry.getKey(), // order, personal ,app
                    ClassName.get(aptPackage, entry.getValue()));
        }

        // return groupMap;
        methodBuidler.addStatement("return $N", ProcessorConfig.GROUP_VAR1);

        // 最终生成的类文件名 ARouter$$Group$$ + personal
        String finalClassName = ProcessorConfig.GROUP_FILE_NAME + options;

        messager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由组Group类文件：" +
                aptPackage + "." + finalClassName);

        // 生成类文件：ARouter$$Group$$app
        JavaFile.builder(aptPackage, // 包名
                TypeSpec.classBuilder(finalClassName) // 类名
                        .addSuperinterface(ClassName.get(groupType)) // 实现ARouterLoadGroup接口 implements ARouterGroup
                        .addModifiers(Modifier.PUBLIC) // public修饰符
                        .addMethod(methodBuidler.build()) // 方法的构建（方法参数 + 方法体）
                        .build()) // 类构建完成
                .build() // JavaFile构建完成
                .writeTo(filer); // 文件生成器开始生成类文件
    }

    private void createPathFile(TypeElement pathType) throws IOException {
        // 判断仓库中，是否需要生成需要生成的文件
        if (ProcessorUtils.isEmpty(mAllPathMap)) {
            return;
        }

        // 倒叙生成代码
        TypeName methodReturn = ParameterizedTypeName.get(
                ClassName.get(Map.class), // Map
                ClassName.get(String.class), //Map<String,
                ClassName.get(RouterBean.class)//Map<String, RouterBean>
        );

        // 遍历仓库 app,order,personal
        for (Map.Entry<String, List<RouterBean>> entry : mAllPathMap.entrySet()) {
            // 1.方法
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(ProcessorConfig.PATH_METHOD_NAME)
                    .addAnnotation(Override.class) // 给方法上添加注解@Override
                    .addModifiers(Modifier.PUBLIC) // public 修饰符
                    .returns(methodReturn); //把Map<String, RouterBean>加入方法返回

            // Map<String, RouterBean> pathMap = new HashMap<>();
            // $N == 变量 ，变量引用
            methodBuilder.addStatement("$T<$T, $T> $N = new $T<>()",
                    ClassName.get(Map.class),
                    ClassName.get(String.class),
                    ClassName.get(RouterBean.class),
                    ProcessorConfig.PATH_VAR1,
                    ClassName.get(HashMap.class)
            );

            // 必须要循环，因为由多个
            // pathMap.put("/personal/Personal_Main2Activity", RouterBean.create(RouterBean.TypeEnum.ACTIVITY,
            // Personal_Main2Activity.class);
            // pathMap.put("/personal/Personal_MainActivity", RouterBean.create(RouterBean.TypeEnum.ACTIVITY));
            List<RouterBean> pathList = entry.getValue();

            /*
                $N == 变量
                ¥L == TypeEnum.ACTIVITY
             */
            for (RouterBean routerBean : pathList) {
                methodBuilder.addStatement("$N.put($S, $T.create($T, $L, %T.class, $S, $S))",
                        ProcessorConfig.PATH_VAR1, //pathMap.put
                        routerBean.getPath(), // "/personal/Personal_Main2Activity"
                        ClassName.get(RouterBean.class),
                        ClassName.get(RouterBean.TypeEnum.class), //RouterBean
                        routerBean.getTypeEnum(),
                        ClassName.get((TypeElement) routerBean.getElement()), //MainActivity.class
                        routerBean.getPath(),
                        routerBean.getGroup()
                );
            }

            methodBuilder.addStatement("return $N", ProcessorConfig.PATH_VAR1);

            String finalClassName = ProcessorConfig.PATH_FILE_NAME + entry.getKey();

            messager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由Path类文件" + aptPackage + "." + finalClassName);


            // 生成类文件，ARouter$Path$personal
            JavaFile.builder(aptPackage, //包名 APT存放的路径
                    TypeSpec.classBuilder(finalClassName)
                            .addSuperinterface(ClassName.get(pathType)) //类名
                            .addModifiers(Modifier.PUBLIC) //public修饰符
                            .addMethod(methodBuilder.build()) //method build
                            .build() // build
            ).build().writeTo(filer);

            mAllGroupMap.put(entry.getKey(), finalClassName);
        }
    }

    private final boolean checkRouterPath(RouterBean bean) {
        String group = bean.getGroup(); // "app" "order" "personal"
        String path = bean.getPath(); // "/app/MainActivity"

        //校验
        if (ProcessorUtils.isEmpty(path) || !path.startsWith("/")) {
            messager.printMessage(Diagnostic.Kind.ERROR, "!ARouter注解中的path值，必须要以/开头");
            return false;
        }

        if (path.lastIndexOf("/") == 0) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如/app/MainActivity");
            return false;
        }

        String finalGroup = path.substring(1, path.indexOf("/", 1));

        if (!ProcessorUtils.isEmpty(group) && !group.equals(options)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的gropu值必须和子模块一致！");
            return false;
        } else {
            bean.setGroup(finalGroup);
        }

        return true;
    }
}
