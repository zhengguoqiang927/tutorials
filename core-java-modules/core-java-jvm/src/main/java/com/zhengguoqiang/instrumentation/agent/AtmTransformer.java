package com.zhengguoqiang.instrumentation.agent;

import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
        LOGGER.info("premain load Class:{}",className);
        byte[] byteCode = classfileBuffer;

        String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/");//replace . with /
        if (!className.equals(finalTargetClassName)){
            return byteCode;
        }

        if (loader.equals(targetClassLoader)){
            LOGGER.info("[Agent] Transforming class MyAtm");
            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.get(targetClassName);
                CtMethod ctMethod = ctClass.getDeclaredMethod(WITHDRAW_MONEY_METHOD);
                ctMethod.addLocalVariable("startTime",CtClass.longType);
                ctMethod.insertBefore("startTime = System.currentTimeMillis();");

                StringBuilder endBlock = new StringBuilder();

                ctMethod.addLocalVariable("endTime",CtClass.longType);
                ctMethod.addLocalVariable("opTime",CtClass.longType);
                endBlock.append("endTime = System.currentTimeMillis();");
                endBlock.append("opTime = (endTime-startTime)/1000;");

                endBlock.append("LOGGER.info(\"[Application] Withdrawal operation completed in:\" + opTime + \" seconds!\");");

                ctMethod.insertAfter(endBlock.toString());

                byteCode = ctClass.toBytecode();
                ctClass.detach();
            } catch (NotFoundException | CannotCompileException | IOException e) {
                LOGGER.error("Exception",e);
            }
        }


        return byteCode;
    }
}
