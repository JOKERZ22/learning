package cn.shaonianyou.res;

/**
 * @author wei.zhang
 * @className RestStatus
 * @description TODO
 * @date 2021/7/6
 **/
public interface RestStatus {

    int code();

    /**
     * 枚举名字
     *
     * @return status enum name
     */
    String name();

    /**
     * 枚举对应消息
     *
     * @return 消息描述
     */
    String message();

    /**
     * 多语言消息
     *
     * @param lang 语言参数
     * @return
     */
    String langMessage(String lang);

}
