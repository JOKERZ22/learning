# Java调用kettle


## 流程


## 坑

| 描述 | 异常栈信息 | 原因 | 解决方法 |
| ---- | ---------- | -------- | ---- |
| 初始化kettle环境报错 | org.pentaho.di.core.exception.KettleException: <br/>Unable to find plugin with ID 'Kettle'.  If this is a test, make sure kettle-core tests jar is a dependency.  If this is live make sure a kettle-password-encoder-plugins.xml exits in the classpath | 9.1.0.0-324版本中没有'kettle-password-encoder-plugins.xml'配置文件 | 在项目中添加'kettle-password-encoder-plugins.xml'配置文件或使用老版本 |
| 初始化kettle环境报错 |            |          |  |
|      |            |          |  |

