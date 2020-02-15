package com.ayg.tools.exec.timer.cmds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:扫类命令 @C-com.ayg.service.OrderClass
 * @Author: York.Hwang
 * @Date: 2020/2/16 01:15
 */
public class ClassExeCmd implements IExeCmd {

    private static final Logger LOG = LoggerFactory.getLogger(ClassExeCmd.class);

    private static final int CMD_MIN_LEN = 4;
    private static final String CMD_PREFIX = "@C-";


    private String className;


    private ClassExeCmd(String className) {
        this.className = className;
        printInit();
    }


    public static ClassExeCmd makeCmd(String cmdStr) {
        try {
            String cmdTrim = cmdStr.trim();

            if (cmdTrim.length() < CMD_MIN_LEN) {
                return null;
            }

            if (!cmdTrim.startsWith(CMD_PREFIX)) {
                return null;
            }

            return new ClassExeCmd(cmdTrim.substring(3));
        } catch (Exception e) {
            LOG.error("", e);
            return null;
        }
    }


    @Override
    public boolean execTime(ExecParam execParam) {
        try {
            return className.equals(execParam.getClassName());
        } catch (Exception e) {
            LOG.error("", e);
            return false;
        }

    }

    @Override
    public void printInit() {
        try {
            LOG.info("类:{}所有的方法将计算执行时长", className);
        } catch (Exception e) {
            LOG.error("", e);
        }
    }


}
