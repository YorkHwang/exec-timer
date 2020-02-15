package com.ayg.tools.exec.timer;

import com.ayg.tools.exec.timer.cmds.ExecParam;
import com.ayg.tools.exec.timer.cmds.IExeCmd;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 *  @Description: 定义计数器扫描待修改类的visitor，本质就是访问者模式
 *  @Author: York.Hwang
 *  @Date: 2020/2/16 00:00
 */
public class TimerClassVisitor extends ClassVisitor {
    private String className;
    private String annotationDesc;
    private IExeCmd exeCmd;

    public TimerClassVisitor(ClassVisitor cv, String className, IExeCmd exeCmd) {
        super(Opcodes.ASM5, cv);
        this.className = className;
        this.exeCmd = exeCmd;
    }


    /**
     * 访问注解：将访问一个个类的注解
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        this.annotationDesc = desc;
        return super.visitAnnotation(desc, visible);
    }



    /**
     * 访问方法：将访问一个个方法
     */
    @Override
    public MethodVisitor visitMethod(int access, final String name, final String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (mv == null || name.equals("<init>") ||name.equals("<clinit>")) {
            return mv;
        }

        if(className == null){
            return mv;
        }

        //判断无需执行
        if(!exeCmd.execTime(ExecParam.Vf(className.replace("/", "."), annotationDesc, name, ""))){
            return mv;
        }

        final String key = className + name + desc;
        return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
            //方法进入时获取开始时间
            @Override public void onMethodEnter() {
                //相当于com.blueware.agent.TimeUtil.setStartTime("key");
                this.visitLdcInsn(key);
                this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ayg/tools/exec/timer/ExecTime", "start", "(Ljava/lang/String;)V", false);
            }

            //方法退出时获取结束时间并计算执行时间
            @Override public void onMethodExit(int opcode) {
                //相当于com.blueware.agent.TimeUtil.setEndTime("key");
                this.visitLdcInsn(key);
                this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ayg/tools/exec/timer/ExecTime", "end", "(Ljava/lang/String;)V", false);
                //向栈中压入类名称
                this.visitLdcInsn(className);
                //向栈中压入方法名
                this.visitLdcInsn(name);
                //向栈中压入方法描述
                this.visitLdcInsn(desc);
                //相当于com.blueware.agent.TimeUtil.getExclusiveTime("com/blueware/agent/TestTime","testTime");
                this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/ayg/tools/exec/timer/ExecTime", "execTime", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                        false);
            }
        };
    }
}
