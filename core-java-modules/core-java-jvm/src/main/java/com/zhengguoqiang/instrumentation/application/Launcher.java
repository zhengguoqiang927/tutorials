package com.zhengguoqiang.instrumentation.application;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args[0].equals("StartMyAtmApplication")){
            MyAtmApplication.run(args);
        }else if (args[0].equals("LoadAgent")){

        }
    }
}
