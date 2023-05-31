package org.example;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import static net.bytebuddy.matcher.ElementMatchers.named;


public class MyAgent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        ByteBuddy byteBuddy = new ByteBuddy();

        AgentBuilder agentBuilder =
                new AgentBuilder.Default(byteBuddy);
        agentBuilder.type(named("org.graalvm.nativeimage.VMRuntime"))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, ProtectionDomain protectionDomain) {
                        return builder.visit(Advice.to(MyAdvice.class).on(named("initialize")));
                    }
                })
                .installOn(instrumentation);

        System.out.println("agent start complete -----------------");
    }
}
