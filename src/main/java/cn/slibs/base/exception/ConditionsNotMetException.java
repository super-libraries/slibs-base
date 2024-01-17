package cn.slibs.base.exception;


import com.iofairy.top.S;
import lombok.Getter;
import lombok.Setter;

/**
 * 条件未满足时抛出此异常
 *
 * @since 0.0.5
 */
@Getter
@Setter
public class ConditionsNotMetException extends RuntimeException {

    private static final long serialVersionUID = 9658567356866L;

    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误标签（可以用来标识这个错误是由什么参数引发的）
     */
    protected String errorTag;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public ConditionsNotMetException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public ConditionsNotMetException(String errorTag, String errorMsg) {
        super(errorMsg);
        this.errorTag = errorTag;
        this.errorMsg = errorMsg;
    }

    public ConditionsNotMetException(String errorCode, String errorTag, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorTag = errorTag;
        this.errorMsg = errorMsg;
    }

    public String getMessage() {
        return S.isBlank(errorTag) ? errorMsg : errorTag + "。" + errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}