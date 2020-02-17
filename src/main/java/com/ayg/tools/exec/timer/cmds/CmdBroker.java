package com.ayg.tools.exec.timer.cmds;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Description:命名服务
 * @Author: York.Hwang
 * @Date: 2020/2/16 02:00
 */
public class CmdBroker implements IExeCmd {
    private static final Logger LOG = LoggerFactory.getLogger(ClassExeCmd.class);

    private List<IExeCmd> cmdList;

    public CmdBroker(List<IExeCmd> cmdList) {
        this.cmdList = cmdList;
        printInit();
    }

    @Override
    public boolean execTime(ExecParam execParam) {
        for (IExeCmd cmd : cmdList) {
            if (cmd.execTime(execParam)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void printInit() {
        try {
            LOG.info("共有{}条规则", cmdList.size());
        } catch (Exception e) {
            LOG.error("", e);
        }
    }

}
