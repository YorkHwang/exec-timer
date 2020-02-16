package com.ayg.tools.exec.timer;

import com.ayg.tools.exec.timer.cmds.IExeCmd;
import com.ayg.tools.exec.timer.cmds.MethodExeCmd;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @Description:方法执行时长字节码转换器
 * @Author: York.Hwang
 * @Date: 2020/2/15 23:49
 */
public class ExecTimerTransformer implements ClassFileTransformer {

    private static final Logger LOG = LoggerFactory.getLogger(MethodExeCmd.class);


    private IExeCmd exeCmd;

    public ExecTimerTransformer(IExeCmd exeCmd) {
        super();
        this.exeCmd = exeCmd;
    }


    /**
     * @Description:覆写转换方法
     * @Author: York.Hwang
     * 参数说明
     * loader: 定义要转换的类加载器，如果是引导加载器，则为null
     * className:完全限定类内部形式的类名称和中定义的接口名称，例如"java.lang.instrument.ClassFileTransformer"
     * classBeingRedefined:如果是被重定义或重转换触发，则为重定义或重转换的类；如果是类加载，则为 null
     * protectionDomain:要定义或重定义的类的保护域
     * classfileBuffer:类文件格式的输入字节缓冲区（不得修改，一个格式良好的类文件缓冲区（转换的结果），如果未执行转换,则返回 null。
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        try {
            //第一步：读取类的字节码流
            ClassReader reader = new ClassReader(classfileBuffer);
            //第二步：创建操作字节流值对象，ClassWriter.COMPUTE_MAXS:表示自动计算栈大小
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
            //第三步：接受一个ClassVisitor子类进行字节码修改
            reader.accept(new TimerClassVisitor(writer, className, exeCmd), ClassReader.EXPAND_FRAMES);
            //第四步：返回修改后的字节码流
            return writer.toByteArray();
        } catch (Throwable e) {
            LOG.error("", e);
            throw e;
        }
    }


}  
