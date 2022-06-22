package com.zhengguoqiang.instrumentation.application;

import com.sun.tools.attach.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class AgentLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgentLoader.class);

    public static void run(String[] args) throws Exception {
        String agentFilePath = "/Users/zhengguoqiang/Projects/IdeaProjects/zhengguoqiang/tutorials/core-java-modules" +
                "/core-java-jvm/target/core-java-jvm-1.0-SNAPSHOT.jar";
        String applicationId = "13725";
        //iterate all jvms and get the first one that matches our application name
        /*Optional<String> jvmProcessOpt = Optional.ofNullable(VirtualMachine.list().stream().filter(jvm -> {
            LOGGER.info("jvm:{},id:{}", jvm.displayName(),jvm.id());
            return jvm.displayName().contains(applicationName);
        }).findFirst().get().id());

        if (!jvmProcessOpt.isPresent()){
            LOGGER.error("Target Application not found");
            return;
        }*/
        VirtualMachineDescriptor targetJvm =
                VirtualMachine.list().stream().filter(virtualMachineDescriptor ->
                        virtualMachineDescriptor.id().equals(applicationId)).findFirst().orElse(null);

        if (targetJvm == null) {
            throw new IllegalArgumentException("could not find the target jvm by process id:" + applicationId);
        }
        File agentFile = new File(agentFilePath);

        VirtualMachine jvm = null;
        try {
//            String jvmPid = jvmProcessOpt.get();
//            LOGGER.info("Attaching to target JVM with PID: " + jvmPid);
//            VirtualMachine jvm = VirtualMachine.attach(jvmPid);
            jvm = VirtualMachine.attach(targetJvm);
            jvm.loadAgent(agentFile.getAbsolutePath());
            jvm.detach();
            LOGGER.info("Attached to target JVM and loaded Java agent successfully");
        } catch (Exception e) {
            if (jvm != null) {
                jvm.detach();
            }
            throw new RuntimeException(e);
        }
    }
}
