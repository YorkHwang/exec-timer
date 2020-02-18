## Java Agent实现无代码侵入方法执行时长打印方案

### 一、背景

项目运行发现接口执行慢，排查主要在于找出哪些方法执行慢，一般需要打印方法执行时长的日志。通过对执行时长的日志观察对比，最终可找到慢的原因。

### 二、方案对比
|方案|优势|劣势|
|---|---|---|
|System.currentTimeMillis()相减|简单 |代码侵入且繁琐 |
|StopWatch|简单且更加方便|代码侵入且繁琐|
|SpringAOP|实现统一按切面计算，较少侵入|私有和静态方法无法拦截|
|Java Agent|实现统一计算所有方法，零侵入|技术难度较高|

从开发一个开发测试和排查问题工具角度出发，使用Java Agent技术实现，能够不修改任何业务代码的前提下（零侵入），对所有的业务代码里的任意方法做时长统计。
代码地址：https://github.com/YorkHwang/exec-timer

### 三、使用方法

- 获取agent包
      方式一：直接下载jar：[exec-timer.jar](https://github.com/YorkHwang/exec-timer/blob/master/jar/exec-timer.jar)
	
	方式二：自己打包: mvn clean package
	在target目录下将生成对应的exec-timer.jar
- 测试用例

VM加上如下参数

-javaagent:target/exec-timer.jar=@M-com.ayg.tools.test.AppTest$testApp||@C-com.ayg.tools.test.AppTest

执行测试用例 com.ayg.tools.test.AppTest.testApp()

- Jar启动方式

javar -javaaget:[exec-timer.jar全路径]=@M|C|P-包全名|类全名$方法1,方法2...方法N -jar [可执行Jar的全路径]

示例：
java -javaagent:/code/open/exec-timer/target/exec-timer.jar=@P-com.ayg.contract.service -jar contract-web.jar

- 命令说明

a.指定类方法打印执行时长： __@M-类全名$方法1,方法2__ 
示例：@M-com.ayg.contract.service.ContractService$addContract,updateContract

b.指定类所有方法打印执行时长： __@C-类全名__ 
示例：@C-com.ayg.contract.service.ContractService

c.指定包下所有类所有方法打印执行时长： __@P-包名__ 
示例：@M-com.ayg.contract.service

 __多个命令用双竖线||间隔__ 




