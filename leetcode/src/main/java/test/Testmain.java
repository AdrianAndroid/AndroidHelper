package test;

import java.lang.reflect.InvocationTargetException;

/**
 * Time:2021/7/23 11:06
 * Author:
 * Description:
 */
public class Testmain {

    public static void main(String[] args) {
        try {
            Class<?> testTypeForName = Class.forName("test.Test");
            //Test test = (Test) testTypeForName.newInstance();
            Test test = (Test) testTypeForName.getDeclaredConstructor().newInstance();
            test.sayHello();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

//    public static void main2(String[] args) {
//        try {
//            // 测试class
//            Class<Test> testTypeClass = Test.class;
//            System.out.println("testTypeClass---" + testTypeClass);
//            // 测试Class.forName()
//            Class<?> testTypeForName = Class.forName("test.Test");
////            testTypeClass.getDeclaredConstructor().newInstance(null);
//            System.out.println("testTypeForName-----" + testTypeClass);
//            // 测试Object.getClass()
//            Test test = new Test();
//            System.out.println("testTypeClass----" + test.getClass());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

}
