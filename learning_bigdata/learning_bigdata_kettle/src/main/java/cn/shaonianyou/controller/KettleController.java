package cn.shaonianyou.controller;

import cn.shaonianyou.res.RestResponse;
import cn.shaonianyou.res.RestStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.logging.LoggingBuffer;
import org.pentaho.di.core.plugins.PluginFolder;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wei.zhang
 * @className KettleController
 * @description Kettle
 * @date 2021/7/6
 **/
@RestController
@RequestMapping("/kettle")
@Slf4j
public class KettleController {

    @Value("${kettle.plugins.path}")
    private String kettlePluginsPath;

    /**
     * 执行作业
     *
     * @param jobPath 作业文件绝对路径
     * @return 执行成功返回"success",执行错误返回错误信息
     * @throws KettleException
     */
    @GetMapping("/job")
    public RestResponse<String> runJob(String jobPath) throws KettleException {
        if (StringUtils.isBlank(jobPath)) {
            return new RestResponse<>(RestStatusCode.INVALID_PARAMS_CONVERSION.code(), "作业文件绝对路径不能为空!");
        }
        // 指定kettle插件目录，否在kettle环境初始化时可能会报错
        StepPluginType.getInstance().getPluginFolders().add(new PluginFolder(kettlePluginsPath, false, true, true));
        // 初始化kettle环境
        KettleEnvironment.init();
        // 构建作业对象
        JobMeta jobMeta = new JobMeta(jobPath, null);
        Job job = new Job(null, jobMeta);
        // 设置日志级别
        job.setLogLevel(LogLevel.ERROR);
        // 开始执行
        job.start();
        // 执行(阻塞到执行完毕或出错)
        job.waitUntilFinished();
        // 如果出错记录错误
        if (job.getErrors() > 0) {
            LoggingBuffer appender = KettleLogStore.getAppender();
            String logText = appender.getBuffer(job.getLogChannelId(), false).toString();
            log.error("KettleController.runJob e:{}", logText);
            return new RestResponse("logText");
        }
        return new RestResponse("success");
    }

    /**
     * 执行转换
     *
     * @param transPath 转换文件绝对路径
     * @return 执行成功返回"success",执行错误返回错误信息
     * @throws KettleException
     */
    @GetMapping("/trans")
    public RestResponse<String> runTrans(String transPath) throws KettleException {
        if (StringUtils.isBlank(transPath)) {
            return new RestResponse<>(RestStatusCode.INVALID_PARAMS_CONVERSION.code(), "转换文件绝对路径不能为空!");
        }
        // 指定kettle插件目录，否在kettle环境初始化时可能会报错
        StepPluginType.getInstance().getPluginFolders().add(new PluginFolder(kettlePluginsPath, false, true, true));
        // 初始化kettle环境
        KettleEnvironment.init();
        // 构建转换对象
        TransMeta transMeta = new TransMeta(transPath, (Repository) null);
        Trans trans = new Trans(transMeta);
        // 设置日志级别
        trans.setLogLevel(LogLevel.ERROR);
        // 执行转换
        trans.execute(null);
        // 执行(阻塞到执行完毕或出错)
        trans.waitUntilFinished();
        // 如果出错记录错误
        if (trans.getErrors() > 0) {
            LoggingBuffer appender = KettleLogStore.getAppender();
            String logText = appender.getBuffer(trans.getLogChannelId(), false).toString();
            log.error("KettleController.runTrans e:{}", logText);
            return new RestResponse("logText");
        }
        return new RestResponse<>("success");
    }
}
