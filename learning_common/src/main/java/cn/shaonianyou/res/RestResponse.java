package cn.shaonianyou.res;

import java.io.Serializable;

/**
 * @author wei.zhang
 * @className RestResponse
 * @description TODO
 * @date 2021/7/6
 **/
public class RestResponse<T> implements Serializable {

    /**
     * 描述 : serialVersionUID
     */
    private static final long serialVersionUID = 1L;


    /**
     * 描述 : 状态码(业务定义)
     */
    private Integer code = RestStatusCode.OK.code();

    /**
     * 描述 : 状态码描述(业务定义)
     */
    private String message = RestStatusCode.OK.message();

    /**
     * 描述 : 结果集(泛型)
     */
    private T result = null;


    /**
     * 描述 : 构造函数
     */
    public RestResponse() {
        super();
    }

    /**
     * 描述 : 构造函数
     *
     * @param result 结果集(泛型)
     */
    public RestResponse(T result) {
        this.result = result;
    }

    /**
     * 描述 : 构造函数
     *
     * @param httpStatus http状态
     */
    public RestResponse(RestStatus httpStatus) {
        this.code = httpStatus.code();
        this.message = httpStatus.message();
    }

    /**
     * 描述 : 构造函数
     *
     * @param httpStatus http状态
     * @param result     结果集
     */
    public RestResponse(RestStatus httpStatus, T result) {
        this.code = httpStatus.code();
        this.message = httpStatus.message();
        this.result = result;
    }

    /**
     * 描述 : 构造函数
     *
     * @param httpStatus http状态
     * @param result     结果集
     */
    public RestResponse(RestStatus httpStatus, String lang, T result) {
        this.code = httpStatus.code();
        this.message = httpStatus.langMessage(lang);
        this.result = result;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     */
    public RestResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     * @param result  结果集(泛型)
     */
    public RestResponse(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
