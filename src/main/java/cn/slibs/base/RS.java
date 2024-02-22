package cn.slibs.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static com.iofairy.falcon.misc.Preconditions.*;

/**
 * 响应的数据对象（Result Or Response）
 *
 * @since 0.0.1
 */
@Getter
public class RS<T> implements Serializable {
    private static final long serialVersionUID = 358876587956866169L;
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
    public final String code;
    /**
     * 请求信息
     */
    public final String msg;
    /**
     * 是否成功（有些极个别的情况可能导致此值不正确，具体参照 {@link #adjust()} 方法）
     */
    public final boolean success;
    /**
     * 请求的数据
     */
    public final T data;
    /**
     * 完整的错误信息
     */
    @Setter
    @Accessors(chain = true)
    private String error;


    /*==================================
     ******       RS构造函数       ******
     ==================================*/
    public RS() {
        this(defaultSuccessStatusCode, null);
    }

    public RS(String code, String msg) {
        this(code, msg, null, null);
    }

    public RS(String code, String msg, T data) {
        this(code, msg, data, null);
    }

    public RS(String code, String msg, T data, String error) {
        checkHasBlank(args(code, msg), args("code", "msg"));
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = defaultSuccessStatusCode.getCode().equals(this.code);
        this.error = error;
    }

    public RS(IStatusCode statusCode) {
        this(statusCode, null, null);
    }

    public RS(IStatusCode statusCode, T data) {
        this(statusCode, data, null);
    }

    public RS(IStatusCode statusCode, T data, String error) {
        this(statusCode.getCode(), isDefaultEnglish ? statusCode.getMsgEn() : statusCode.getMsg(), data, error);
    }

    /*==================================
     ******     返回请求成功结果     ******
     ==================================*/
    public static RS<?> ok() {
        return new RS<>();
    }

    public static <D> RS<D> ok(D data) {
        return new RS<>(defaultSuccessStatusCode, data);
    }

    public static <D> RS<D> ok(String msg, D data) {
        return new RS<>(defaultSuccessStatusCode.getCode(), msg, data);
    }

    public static RS<?> success(String msg) {
        return new RS<>(defaultSuccessStatusCode.getCode(), msg);
    }

    /*=======================================
     ******  返回“不带数据”的请求失败结果  ******
     =======================================*/
    public static RS<?> fail() {
        return new RS<>(defaultErrorStatusCode.getCode(), defaultErrorStatusCode.getMsg());
    }

    public static RS<?> fail(String msg) {
        return new RS<>(defaultErrorStatusCode.getCode(), msg);
    }

    public static RS<?> fail(String code, String msg) {
        return new RS<>(code, msg);
    }

    public static RS<?> fail(String code, String msg, String error) {
        return new RS<>(code, msg, error);
    }

    public static RS<?> fail(IStatusCode statusCode) {
        return new RS<>(statusCode);
    }

    public static RS<?> fail(IStatusCode statusCode, String error) {
        return new RS<>(statusCode, error);
    }

    /*=======================================
     ******   返回“带数据”的请求失败结果   ******
     =======================================*/
    public static <D> RS<D> error(D data) {
        return new RS<>(defaultErrorStatusCode.getCode(), defaultErrorStatusCode.getMsg(), data);
    }

    public static <D> RS<D> error(String msg, D data) {
        return new RS<>(defaultErrorStatusCode.getCode(), msg, data);
    }

    public static <D> RS<D> error(String code, String msg, D data) {
        return new RS<>(code, msg, data);
    }

    public static <D> RS<D> error(String code, String msg, D data, String error) {
        return new RS<>(code, msg, data, error);
    }

    public static <D> RS<D> error(IStatusCode statusCode, D data) {
        return new RS<>(statusCode, data);
    }

    public static <D> RS<D> error(IStatusCode statusCode, D data, String error) {
        return new RS<>(statusCode, data, error);
    }

    public static <D> RS<D> error(RS<?> rs, D data) {
        return new RS<>(rs.code, rs.msg, data, rs.error);
    }


    /*===========================================
     ******   校正 RS 中的 success 字段的值   ******
     ===========================================*/

    /**
     * 校正（调节）字段 {@link #success} 的值<br><br>
     * <b>注：</b><br>
     * <b>以下情况可能导致 {@link #success} 的值不正确：</b><br>
     * <ul>
     *     <li> Json串 <b>反序列化</b>成 RS对象，但Json串 <b>缺失 {@code success} 字段</b>，导致转换的 {@link #success} 总为 {@code true}；
     *     <li> {@link #defaultSuccessStatusCode} 的值被改变，导致原先设置的 {@code success} 不正确。
     * </ul>
     *
     * @return 校正（调节）后的新的 RS实例
     */
    public RS<T> adjust() {
        return new RS<>(code, msg, data, error);
    }

    /**
     * 校正（调节）字段 {@link #success} 的值<br><br>
     * <b>注：</b><br>
     * <b>以下情况可能导致 {@link #success} 的值不正确：</b><br>
     * <ul>
     *     <li> Json串 <b>反序列化</b>成 RS对象，但Json串 <b>缺失 {@code success} 字段</b>，导致转换的 {@link #success} 总为 {@code true}；
     *     <li> {@link #defaultSuccessStatusCode} 的值被改变，导致原先设置的 {@code success} 不正确。
     * </ul>
     *
     * @param rs  RS实例
     * @param <D> 数据类型
     * @return 校正（调节）后的新的 RS实例
     */
    public static <D> RS<D> adjust(RS<D> rs) {
        return rs.adjust();
    }


    @Override
    public String toString() {
        String err = error == null ? null : "'" + error + "'";
        return "RS{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", error=" + err +
                ", data=" + data +
                '}';
    }

}
