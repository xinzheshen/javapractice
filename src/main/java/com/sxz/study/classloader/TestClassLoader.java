package com.sxz.study.classloader;

// refer : https://www.cnblogs.com/doit8791/p/5820037.html

public class TestClassLoader {

    public static void main(String[] arg) {

        ClassLoader c = TestClassLoader.class.getClassLoader();  //获取Test类的类加载器

        System.out.println(c);

        ClassLoader c1 = c.getParent();  //获取c这个类加载器的父类加载器

        System.out.println(c1);

        ClassLoader c2 = c1.getParent();//获取c1这个类加载器的父类加载器

        System.out.println(c2);

    }

}
