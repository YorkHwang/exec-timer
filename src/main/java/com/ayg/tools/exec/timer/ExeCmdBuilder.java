package com.ayg.tools.exec.timer;

import com.ayg.tools.exec.timer.cmds.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 执行命令的建造者
 * @Author: York.Hwang
 * @Date: 2020/2/16 02:14
 */
public class ExeCmdBuilder {
    private static final String SP = "\\|\\|";
    private static final String NULL_STR = "";

    private static final Logger LOG = LoggerFactory.getLogger(ExeCmdBuilder.class);

    public static IExeCmd buildExeCmd(String cmdParam){
        if(cmdParam == null || NULL_STR.equals(cmdParam.trim())){
            LOG.error("执行时长统计参数参数为空");
            return null;
        }

        String[] cmdArray = cmdParam.trim().split(SP);
        List<IExeCmd> cmdList = new ArrayList<>();
        for(String cmdStr : cmdArray){
            //统一判空
            if(NULL_STR.equals(cmdStr.trim())){
                continue;
            }

            //类
            IExeCmd cmd = ClassExeCmd.makeCmd(cmdStr);
            if(cmd != null){
                cmdList.add(cmd);
                continue;
            }

            //包
            cmd = PackageExeCmd.makeCmd(cmdStr);
            if(cmd != null){
                cmdList.add(cmd);
                continue;
            }

            //方法
            cmd = MethodExeCmd.makeCmd(cmdStr);
            if(cmd != null){
                cmdList.add(cmd);
            }
        }

        if(cmdList.isEmpty()){
            LOG.warn("无法成功解析命令，请检查命令是否正确");
            return null;
        }

        return new CmdBroker(cmdList);
    }
}
