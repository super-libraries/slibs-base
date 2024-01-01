package cn.slibs.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 返回数据对象（Result Or Response）
 *
 * @since 0.0.1
 */
@Getter
public class RS<T> implements Serializable {
    private static final long serialVersionUID = 99876587956866169L;
    /**
     * 默认成功状态码
     */
    @Getter
    @Setter
    private static IStatusCode defaultSuccessStatusCode = StatusCode.OK;
    /**
     * 默认错误状态码
     */
    @Getter
    @Setter
    private static IStatusCode defaultErrorStatusCode = StatusCode.INTERNAL_SERVER_ERROR;
    /**
     * IStatusCode 默认是否显示英文消息
     */
    @Getter
    @Setter
    private static boolean isDefaultEnglish = false;

    /**
     * 状态码
     */
    private String code;
    /**
     * 请求信息
     */
    private String msg;
    /**
     * 完整的错误信息
     */
    private String error;
    /**
     * 请求的数据
     */
    private T data;

    public RS() {
        this(defaultSuccessStatusCode, null);
    }

    public RS(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RS(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RS(IStatusCode statusCode) {
        this(statusCode, null);
    }

    public RS(IStatusCode statusCode, T data) {
        this.code = statusCode.getCode();
        this.msg = isDefaultEnglish ? statusCode.getMsgEn() : statusCode.getMsg();
        this.data = data;
    }

    public static <D> RS<D> ok() {
        return new RS<>();
    }

    public static <D> RS<D> ok(D data) {
        return new RS<>(defaultSuccessStatusCode, data);
    }

    public static <D> RS<D> success(String msg) {
        return new RS<>(defaultSuccessStatusCode.getCode(), msg);
    }

    public static <D> RS<D> ok(String msg, D data) {
        return new RS<>(defaultSuccessStatusCode.getCode(), msg, data);
    }

    public static <D> RS<D> error() {
        return new RS<>(defaultErrorStatusCode.getCode(), defaultErrorStatusCode.getMsg());
    }

    public static <D> RS<D> error(String msg) {
        return new RS<>(defaultErrorStatusCode.getCode(), msg);
    }

    public static <D> RS<D> error(String code, String msg) {
        return new RS<>(code, msg);
    }

    public static <D> RS<D> error(String code, String msg, String error) {
        return new RS<D>(code, msg).setError(error);
    }

    public static <D> RS<D> error(IStatusCode statusCode) {
        return new RS<D>(statusCode);
    }

    public static <D> RS<D> error(IStatusCode statusCode, String error) {
        return new RS<D>(statusCode).setError(error);
    }

    public RS<T> setError(String error) {
        this.error = error;
        return this;
    }

    public RS<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean success() {
        return defaultSuccessStatusCode.getCode().equals(code);
    }

    public boolean failed() {
        return !defaultSuccessStatusCode.getCode().equals(code);
    }

    @Override
    public String toString() {
        String err = error == null ? null : "'" + error + "'";
        return "RS{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", error=" + err +
                ", data=" + data +
                '}';
    }

}
