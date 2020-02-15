package com.ayg.tools.exec.timer.cmds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:扫包命令 @P-com.ayg.service
 * @Author: York.Hwang
 * @Date: 2020/2/16 01:15
 */
public class PackageExeCmd implements IExeCmd {
    private static final Logger LOG = LoggerFactory.getLogger(ClassExeCmd.class);

    private static final int CMD_MIN_LEN = 4;
    private static final String CMD_PREFIX = "@P-";


    private String packageName;


    private PackageExeCmd(String packageName){
        this.packageName = packageName;
        printInit();
    }

    @Override
    public void printInit() {
        try{
            LOG.info("包:{}其下所有类的方法将计算执行时长", packageName);
        } catch (Exception e){
            LOG.error("", e);
        }
    }

    public static PackageExeCmd makeCmd(String cmdStr){
        String cmdTrim = cmdStr.trim();

        if(cmdTrim.length() < CMD_MIN_LEN){
            return null;
        }

        if(!cmdTrim.startsWith(CMD_PREFIX)){
            return null;
        }

        return new PackageExeCmd(cmdTrim.substring(3).concat("."));
    }


    @Override
    public boolean execTime(ExecParam execParam) {
        try {
            return execParam.getClassName().startsWith(packageName);
        } catch (Exception e){
            LOG.error("", e);
        }
        return false;
    }


}
