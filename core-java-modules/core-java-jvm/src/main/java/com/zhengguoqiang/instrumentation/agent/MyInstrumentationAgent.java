package com.zhengguoqiang.instrumentation.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class MyInstrumentationAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyInstrumentationAgent.class);

    public static void premain(String agentArgs, Instrumentation inst){
        LOGGER.info("[Agent] In premain method");

        String className = "com.zhengguoqiang.instrumentation.application.MyAtm";
        transformClass(className,inst);
    }

    private static void transformClass(String className, Instrumentation inst) {
        Class<?> targetCls = null;
        ClassLoader targetClassLoader = null;
        // see if we can get the class using forName
        try {
            targetCls = Class.forName(className);
            targetClassLoader = targetCls.getClassLoader();
            transform(targetCls, targetClassLoader, inst);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class [{}] not found with Class.forName",className);
        }
        // otherwise iterate all loaded classes and find what we want
        for (Class<?> clazz:inst.getAllLoadedClasses()){
            if (clazz.getName().equals(className)){
                targetCls = clazz;
                targetClassLoader = clazz.getClassLoader();
                transform(targetCls, targetClassLoader, inst);
                return;
            }
        }
        throw new RuntimeException("Failed to find class [" + className + "]");
    }

    private static void transform(Class<?> targetCls, ClassLoader targetClassLoader, Instrumentation inst) {

    }


}
