package org.example;

import net.bytebuddy.asm.Advice;

public class MyAdvice {
    @Advice.OnMethodEnter(suppress = Throwable.class)
    public static void onEnter() {
        System.out.println("native image start:---------------------------------------------------");
    }
}
