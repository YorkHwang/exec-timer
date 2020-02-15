package com.ayg.tools.exec.timer.cmds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:扫方法命令 @M-com.ayg.service.OrderClass$foo,bar
 * @Author: York.Hwang
 * @Date: 2020/2/16 01:15
 */
public class MethodExeCmd implements IExeCmd {

    private static final Logger LOG = LoggerFactory.getLogger(MethodExeCmd.class);

    private static final int CMD_MIN_LEN = 7;
    private static final String CMD_PREFIX = "@M-";

    private String className;
    private Set<String> methodNameSet;



    private MethodExeCmd(String className, Set<String> methodNameSet){
        this.className = className;
        this.methodNameSet= methodNameSet;
        printInit();
    }

    @Override
    public void printInit() {
        try{
            StringBuilder methods = new StringBuilder();
            methodNameSet.forEach(m-> methods.append(",").append(m));
            LOG.info("类:{}的方法{}将计算执行时长", className, methods.substring(1));
        } catch (Exception e){
           LOG.error("", e);
        }
    }


    public static MethodExeCmd makeCmd(String cmdStr){
        try {
            String trimCmd = cmdStr.trim();
            if(trimCmd.length() < CMD_MIN_LEN){
                return null;
            }

            if(!trimCmd.startsWith(CMD_PREFIX)){
                return null;
            }

            String classMethod = trimCmd.trim().substring(3);

            String[] classMethods = classMethod.split(DOLLAR);
            if(classMethods.length != 2){
                return null;
            }

            String[] methodArr = classMethods[1].split(COM);
            return new MethodExeCmd(classMethods[0], new HashSet<>(Arrays.asList(methodArr)));
        } catch (Exception e) {
            LOG.error("",e);
        }

        return null;
    }


    @Override
    public boolean execTime(ExecParam execParam) {
        try {
            return className.equals(execParam.getClassName()) && methodNameSet.contains(execParam.getMethodName());
        } catch (Exception e){
            LOG.error("",e);
        }
        return false;
    }


}
