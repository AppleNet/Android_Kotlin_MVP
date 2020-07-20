package com.example.llcgs.kotlin.plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ASMClassHack {


    public static void hackClass(String classPath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(classPath);
            // 交给 classReader
            ClassReader classReader = new ClassReader(fileInputStream);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            // 开始插桩
            classReader.accept(new MyClassVisitor(Opcodes.ASM5, classWriter), ClassReader.EXPAND_FRAMES);
            // 结果获取
            byte[] bytes = classWriter.toByteArray();
            // 结果写入一个文件中
            FileOutputStream fileOutputStream = new FileOutputStream(classPath);
            fileOutputStream.write(bytes);
            // 关闭输出 输出流
            fileInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *  用来访问类信息
     *
     * */
    private static class MyClassVisitor extends ClassVisitor {

        public MyClassVisitor(int api) {
            super(api);
        }

        MyClassVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        /**
         *  每读取到一个方法的时候，都会执行这个方法
         *
         * */
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
            methodVisitor = new MyMethodVisitor(api, methodVisitor, access, name, desc);
            return methodVisitor;
        }
    }

    /**
     *  用来访问方法信息
     *
     * */
    private static class MyMethodVisitor extends AdviceAdapter {

        private String methodName;
        private int enterLocal, exitLocal;
        private boolean inject = false;

        MyMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv, access, name, desc);
            methodName = name;
        }

        @Override
        public void visitInsn(int opcode) {
            // 方法签名中 字节码 <init> 表示构造方法
            if ("<init>".equals(methodName) && opcode == Opcodes.RETURN) {
                // invokeConstructor(Type.getType("Lcom/example/koltin/hack/AntilazyLoad;"),new Method("<init>","()V"));
                super.visitLdcInsn(Type.getType("Lcom/example/koltin/hack/AntilazyLoad;"));
            }
            super.visitInsn(opcode);
        }

        /**
         *  每读到一个注解 就执行依次
         *
         * */
        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            if ("Lcom/example/kotlin/plugin/ASMClassHack;".equals(desc)) {
                // 获取到注解标注的方法
                inject = true;
            }
            return super.visitAnnotation(desc, visible);
        }

        /**
         *  方法进入的时候 执行
         *
         * */
        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            if (inject) {
                invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
                // 局部变量表 开启一块空间来存储返回值 long
                enterLocal = newLocal(Type.LONG_TYPE);
                storeLocal(enterLocal);
            }
        }

        /**
         *  方法退出的时候 执行
         *
         * */
        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            if (inject) {
                invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
                exitLocal = newLocal(Type.LONG_TYPE);
                storeLocal(exitLocal);

                getStatic(Type.getType("Ljava/lang/System;"),"out", Type.getType("Ljava/io/PrintStream;"));
                newInstance(Type.getType("Ljava/lang/StringBuilder;"));
                dup();
                invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"), new Method("<init>", "()V"));
                visitLdcInsn("execute time=");
                invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
                loadLocal(exitLocal);
                loadLocal(enterLocal);
                math(SUB, Type.LONG_TYPE);
                invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(J)Ljava/lang/StringBuilder;"));
                invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("toString", "()Ljava/lang/String;"));
                invokeVirtual(Type.getType("Ljava/io/PrintStream;"), new Method("println", "(Ljava/lang/String;)V"));
            }

        }
    }

}
