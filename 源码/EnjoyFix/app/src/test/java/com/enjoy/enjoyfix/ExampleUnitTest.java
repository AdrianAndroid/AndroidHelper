package com.enjoy.enjoyfix;


import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    class A<T> {

    }

    class B extends A<String> {

    }

    @Test
    public void name() {
        new A<String>();

        new A<String>() {
        };
    }


    class ArtMethod {
        String clazz; //方法属于哪个类
        int code_offset; //代码的地址
        //......
    }


    @Test
    public void showAndfix() throws IOException, ClassNotFoundException {
        assertEquals(4, 2 + 2);
        ArtMethod bugMethod = null;
        ArtMethod fixMethod = null;

        bugMethod.clazz = fixMethod.clazz;
        bugMethod.code_offset = fixMethod.code_offset;
        //......
    }

    public interface ChangeQuickRedirect {
        Object accessDispatch();
    }

    public static class StatePatch implements ChangeQuickRedirect {

        @Override
        public Object accessDispatch() {
            return 106L;
        }

    }

    public static class State {
        public static ChangeQuickRedirect changeQuickRedirect;

        public static long getIndex() {
            if (changeQuickRedirect != null) {
                //....
                return (Long) changeQuickRedirect.accessDispatch();
            }
            return 100L;
        }


    }


    @Test
    public void showRoubust() throws InstantiationException, IllegalAccessException {
        System.out.println(State.getIndex());
        Class<StatePatch> clzz = StatePatch.class;
        State.changeQuickRedirect = clzz.newInstance();

        System.out.println(State.getIndex());


    }


}