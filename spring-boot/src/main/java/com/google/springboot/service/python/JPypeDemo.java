//package com.google.springboot.service.python;
//
///**
// * @Author kris
// * @Create 2024-06-14 21:29
// * @Description
// */
//import org.jpype.JClass;
//import org.jpype.JPyObject;
//import org.jpype.PyClassLoader;
//import org.jpype.PySystem;
//
//public class JPypeDemo {
//
//    public static void main(String[] args) throws Exception {
//        // Initialize JPype
//        PySystem.start("");
//
////        // Configure MyBatis
////        String resource = "mybatis-config.xml";
////        SqlSessionFactory sqlSessionFactory = new XMLConfigBuilder(resource).build();
////        SqlSession sqlSession = sqlSessionFactory.openSession();
////
////        // Read Python script content from MySQL using MyBatis
////        String scriptName = "myscript.py";
//        String scriptContent;
////        scriptContent = sqlSession.selectOne("com.example.mapper.ScriptMapper.getScriptContent", scriptName);
//
//        // Close MyBatis session
////        sqlSession.close();
//
//        // If script is found, execute it using JPype
//        if (scriptContent != null) {
//            // Execute Python script using scriptContent
//            PyClassLoader pyClassLoader = PyClassLoader.fromString(scriptContent);
//            JClass pyClass = pyClassLoader.loadClass("myscript");
//            JPyObject result = pyClass.call("__call__");
//
//            // Print the result
//            System.out.println("Python script output: " + result.toString());
//        } else {
//            System.out.println("Script not found.");
//        }
//    }
//}
//
////    public static void main(String[] args) {
////        callFromFile("math.py");
////
////    }
//
//    public static void callFromFile(String fileName){
//        // 初始化 JPype
//        PySystem.start("");
//
//        // 加载 Python 模块
//        JClass mathModule = PyClassLoader.fromFile(fileName).loadClass("math");
//
//        // 调用 Python 函数
//        JPyObject sqrtFunction = mathModule.get("sqrt");
//        double squareRoot = sqrtFunction.call(JPyObject.wrap(4)).doubleValue();
//
//        // 输出结果
//        System.out.println("Square root of 4 is: " + squareRoot);
//    }
//}
