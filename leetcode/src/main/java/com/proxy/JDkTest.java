package com.proxy;

//https://www.jianshu.com/p/820645faff4f
public class JDkTest {

//    public static void main(String[] args) {
//        new JDkTest().test();
//    }

//    @Test
    public void test() {
        UserServiceImpl userService = new UserServiceImpl();
        JdkCacheHandler jdkCacheHandler = new JdkCacheHandler(userService);
        UserService proxy = (UserService) jdkCacheHandler.createJDKProxy();
        System.out.println("==========================");
        proxy.getUserByName("bugpool");
        System.out.println("==========================");

        System.out.println(proxy.getClass());
    }

}
