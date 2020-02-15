package com.ayg.tools.exec.timer.cmds;

/**
 * @Description:判断是否执行的参数
 * @Author: York.Hwang
 * @Date: 2020/2/16 01:18
 */
public class ExecParam {

    private String className;
    private String classAno;
    private String methodName;
    private String methodAno;

    public ExecParam(String className, String classAno, String methodName, String methodAno) {
        this.className=className;
        this.classAno=classAno;
        this.methodName=methodName;
        this.methodAno=methodAno;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassAno() {
        return classAno;
    }

    public void setClassAno(String classAno) {
        this.classAno = classAno;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodAno() {
        return methodAno;
    }

    public void setMethodAno(String methodAno) {
        this.methodAno = methodAno;
    }

    public static ExecParam Vf(String className, String classAno, String methodName, String methodAno){
        return new ExecParam( className,  classAno,  methodName, methodAno);
    }
}
