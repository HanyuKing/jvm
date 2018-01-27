package com.why.asm;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class PreMainAgent {
    public static void agentmain(String args, Instrumentation inst) {
        System.out.println("Agent Main called...");
        System.out.println("agentArgs: " + args);
        premain(args, inst);
    }

    public static void premain(String args, Instrumentation inst) {
        System.out.println("premain agentArgs: " + args);

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if(className.equals("com/why/asm/po/Account")) {
                    System.out.println("meet com.why.asm.po.Account");

                }
                return new byte[0];
            }
        });
    }
}
