package com.zhengguoqiang.instrumentation.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class AtmTransformer implements ClassFileTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AtmTransformer.class);

    private static final String WITHDRAW_MONEY_METHOD = "withdrawMoney";

    /** The internal form class name of the class to transform */
    private String targetClassName;

    /** The class loader of the class we want to transform */
    private ClassLoader targetClassLoader;

    public AtmTransformer(String targetClassName, ClassLoader targetClassLoader) {
        this.targetClassName = targetClassName;
        this.targetClassLoader = targetClassLoader;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;

        String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/");//replace . with /
        if (!className.equals(finalTargetClassName)){
            return byteCode;
        }

        if (loader.equals(targetClassLoader)){
            LOGGER.info("[Agent] Transforming class MyAtm");

        }


        return new byte[0];
    }
}
