//package com.google.springboot.service.groovy;
//import groovy.lang.GroovyClassLoader;
//import java.lang.reflect.Method;
///**
// * @Author kris
// * @Create 2024-06-13 23:48
// * @Description
// */
//public class JavaCallGroovyWithClassLoader {
//
//    public static void main(String[] args) throws Exception {
//        // 创建 GroovyClassLoader 实例
//
//
//
//        GroovyClassLoader classLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader());
//
//        // 加载 Groovy 类
//        Class<?> groovyClass = classLoader.parseClass("JavaCallGroovyWithClassLoader$GroovyClass");
//
//        // 创建 Groovy 对象
//        Object groovyObject = groovyClass.newInstance();
//
//        // 获取 sayHello 方法
//        Method sayHelloMethod = groovyClass.getMethod("sayHello", String.class);
//
//        // 调用 sayHello 方法
//        sayHelloMethod.invoke(groovyObject, "Bob");
//    }
//
//    public static class GroovyClass {
//        public void sayHello(String name) {
//            System.out.println("Hello from Groovy, " + name + "!");
//        }
//    }
//}
