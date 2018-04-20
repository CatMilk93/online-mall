package lovely.baby.online.mall.model.exception;

public class UnexpectedException extends MallException {

    private static final long serialVersionUID = 44296579167425752L;

    private static final String DEFAULT_MESSAGE = "未知异常";

    public UnexpectedException(String messageTemplate, Object... messageArgs) {
        super(messageTemplate, messageArgs);
    }

    public UnexpectedException(Throwable cause, String messageTemplate, Object... messageArgs) {
        super(cause, messageTemplate, messageArgs);
    }

    public UnexpectedException() {
        super(DEFAULT_MESSAGE);
    }

    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    @Override
    public boolean isExpectedException() {
        return false;
    }
}
