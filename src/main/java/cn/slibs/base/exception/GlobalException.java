package cn.slibs.base.exception;


import cn.slibs.base.rs.IStatusCode;
import cn.slibs.base.rs.StatusCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 全局统一异常
 */
@Getter
@Setter
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 5658766541661L;

    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;
    /**
     * 导致异常的参数对象
     */
    protected Object causeParams;

    public GlobalException() {
        super();
    }

    public GlobalException(IStatusCode statusCode) {
        super(statusCode.getMsg());
        this.errorCode = statusCode.getCode();
        this.errorMsg = statusCode.getMsg();
    }

    public GlobalException(String errorMsg) {
        super(errorMsg);
        this.errorCode = StatusCode.INTERNAL_SERVER_ERROR.code;
        this.errorMsg = errorMsg;
    }

    public GlobalException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public GlobalException(String errorCode, String errorMsg, Object causeParams) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.causeParams = causeParams;
    }

    public GlobalException(Throwable cause, String errorMsg, Object causeParams) {
        this(cause, StatusCode.INTERNAL_SERVER_ERROR.code, errorMsg, causeParams);
    }

    public GlobalException(Throwable cause, String errorCode, String errorMsg, Object causeParams) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.causeParams = causeParams;
    }

    public GlobalException(Throwable cause, IStatusCode statusCode, Object causeParams) {
        super(statusCode.getMsg(), cause);
        this.errorCode = statusCode.getCode();
        this.errorMsg = statusCode.getMsg();
        this.causeParams = causeParams;
    }

    public String getMessage() {
        return errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}