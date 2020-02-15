package com.ayg.tools.exec.timer.cmds;

/**
 * @Description: 命令接口
 * @Author: York.Hwang
 * @Date: 2020/2/16 01:01
 */
public interface IExeCmd {
    String COM = ",";
    String DOLLAR = "\\$";

    /**
     *  @Description:是否执行计算执行时长
     *  @Author: York.Hwang
     *  @Date: 2020/2/16 01:14
     */
     boolean execTime(ExecParam execParam);

     void printInit();

}
