package cn.slibs.base;

import com.iofairy.falcon.time.DateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import static com.iofairy.falcon.misc.Preconditions.args;
import static com.iofairy.falcon.misc.Preconditions.checkHasBlank;

/**
 * 响应的数据对象（Result Or Response）<br>
 * <b>注：状态码为{@code int}类型</b>
 *
 * @since 0.1.1
 */
@Getter
@Schema(description = "〖响应体〗")
public class RI<T> implements Serializable {
    private static final long serialVersionUID = 358895623876566356L;

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
     * 响应体中是否带时间
     */
    @Getter
    @Setter
    private static boolean showTime = false;
    /**
     * IStatusCode 默认是否显示英文消息
     */
    @Getter
    @Setter
    private static boolean isDefaultEnglish = false;

    /**
     * 时间格式化
     */
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("y-MM-dd HH:mm:ss.SSS '['VV xxx']'");

    /*###################################################################################
     ------------------------------------------------------------------------------------
     *******************************        成员变量        ******************************
     ------------------------------------------------------------------------------------
     ###################################################################################*/
    /**
     * 状态码
     */
    @Schema(description = "〖状态码〗")
    public final int code;
    /**
     * 响应信息
     */
    @Schema(description = "〖响应信息〗")
    public final String msg;
    /**
     * 是否成功（有些极个别的情况可能导致此值不正确，具体参照 {@link #adjust()} 方法）
     */
    @Schema(description = "〖是否成功〗")
    public final boolean success;
    /**
     * 响应的数据
     */
    @Schema(description = "〖数据〗")
    public final T data;
    /**
     * 响应的时间
     */
    @Schema(description = "〖响应的时间〗")
    private String time;
    /**
     * 完整的错误信息
     */
    @Setter
    @Accessors(chain = true)
    @Schema(description = "〖详细错误信息〗")
    private String error;


    /*==================================
     ******       RI构造函数       ******
     ==================================*/
    public RI() {
        this(defaultSuccessStatusCode, null);
    }

    public RI(int code, String msg) {
        this(code, msg, null, null);
    }

    public RI(int code, String msg, T data) {
        this(code, msg, data, null);
    }

    public RI(int code, String msg, T data, String error) {
        checkHasBlank(args(msg), args("msg"));

        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = defaultSuccessStatusCode.getCodeNo() == this.code;
        this.error = error;
        if (showTime) {
            this.time = DateTime.nowDate().format(DTF);
        }
    }

    public RI(IStatusCode statusCode) {
        this(statusCode, null, null);
    }

    public RI(IStatusCode statusCode, T data) {
        this(statusCode, data, null);
    }

    public RI(IStatusCode statusCode, T data, String error) {
        this(statusCode.getCodeNo(), isDefaultEnglish ? statusCode.getMsgEn() : statusCode.getMsg(), data, error);
    }

    /*==================================
     ******     返回请求成功结果     ******
     ==================================*/
    public static <D> RI<D> ok() {
        return new RI<>();
    }

    public static <D> RI<D> ok(D data) {
        return new RI<>(defaultSuccessStatusCode, data);
    }

    public static <D> RI<D> ok(String msg, D data) {
        return new RI<>(defaultSuccessStatusCode.getCodeNo(), msg, data);
    }

    public static <D> RI<D> success(String msg) {
        return new RI<>(defaultSuccessStatusCode.getCodeNo(), msg);
    }

    /*=======================================
     ******  返回“不带数据”的请求失败结果  ******
     =======================================*/
    public static <D> RI<D> fail() {
        return new RI<>(defaultErrorStatusCode.getCodeNo(), defaultErrorStatusCode.getMsg());
    }

    public static <D> RI<D> fail(String msg) {
        return new RI<>(defaultErrorStatusCode.getCodeNo(), msg);
    }

    public static <D> RI<D> fail(int code, String msg) {
        return new RI<>(code, msg);
    }

    public static <D> RI<D> fail(int code, String msg, String error) {
        return new RI<>(code, msg, null, error);
    }

    public static <D> RI<D> fail(IStatusCode statusCode) {
        return new RI<>(statusCode);
    }

    public static <D> RI<D> fail(IStatusCode statusCode, String error) {
        return new RI<>(statusCode, null, error);
    }

    /*=======================================
     ******   返回“带数据”的请求失败结果   ******
     =======================================*/
    public static <D> RI<D> error(D data) {
        return new RI<>(defaultErrorStatusCode.getCodeNo(), defaultErrorStatusCode.getMsg(), data);
    }

    public static <D> RI<D> error(String msg, D data) {
        return new RI<>(defaultErrorStatusCode.getCodeNo(), msg, data);
    }

    public static <D> RI<D> error(int code, String msg, D data) {
        return new RI<>(code, msg, data);
    }

    public static <D> RI<D> error(int code, String msg, D data, String error) {
        return new RI<>(code, msg, data, error);
    }

    public static <D> RI<D> error(IStatusCode statusCode, D data) {
        return new RI<>(statusCode, data);
    }

    public static <D> RI<D> error(IStatusCode statusCode, D data, String error) {
        return new RI<>(statusCode, data, error);
    }

    public static <D> RI<D> error(RI<?> ri, D data) {
        return new RI<>(ri.code, ri.msg, data, ri.error);
    }


    /*===========================================
     ******   校正 RI 中的 success 字段的值   ******
     ===========================================*/

    /**
     * 校正（调节）字段 {@link #success} 的值<br><br>
     * <b>注：</b><br>
     * <b>以下情况可能导致 {@link #success} 的值不正确：</b><br>
     * <ul>
     *     <li> Json串 <b>反序列化</b>成 RI对象，但Json串 <b>缺失 {@code success} 字段</b>，导致转换的 {@link #success} 总为 {@code true}；
     *     <li> {@link #defaultSuccessStatusCode} 的值被改变，导致原先设置的 {@code success} 不正确。
     * </ul>
     *
     * @return 校正（调节）后的新的 RI实例
     */
    public RI<T> adjust() {
        return new RI<>(code, msg, data, error);
    }

    /**
     * 校正（调节）字段 {@link #success} 的值<br><br>
     * <b>注：</b><br>
     * <b>以下情况可能导致 {@link #success} 的值不正确：</b><br>
     * <ul>
     *     <li> Json串 <b>反序列化</b>成 RI对象，但Json串 <b>缺失 {@code success} 字段</b>，导致转换的 {@link #success} 总为 {@code true}；
     *     <li> {@link #defaultSuccessStatusCode} 的值被改变，导致原先设置的 {@code success} 不正确。
     * </ul>
     *
     * @param ri  RI实例
     * @param <D> 数据类型
     * @return 校正（调节）后的新的 RI实例
     */
    public static <D> RI<D> adjust(RI<D> ri) {
        return ri.adjust();
    }

    @Override
    public String toString() {
        String err = error == null ? null : "'" + error + "'";

        return "RI{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                ", time='" + time + '\'' +
                ", error=" + err +
                '}';
    }

}
