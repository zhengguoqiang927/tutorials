package com.zhengguoqiang.instrumentation.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyAtm {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAtm.class);

    public static void withdrawMoney(int amount) throws InterruptedException, IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Thread.sleep(2000l); //processing going on here
        LOGGER.info("[Application] Successful Withdrawal of [{}] units!", amount);

//        Method xmove = MyAtm.class.getMethod("xmove");
//        xmove.invoke(200);

        throw new IOException("there is a io exception");
    }
}
