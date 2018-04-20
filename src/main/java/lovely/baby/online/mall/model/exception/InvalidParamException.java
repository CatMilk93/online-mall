package lovely.baby.online.mall.model.exception;

public class InvalidParamException extends MallException {

    private static final long serialVersionUID = -1503935503623934883L;

    private static final String DEFAULT_MESSAGE = "提交的参数错误";

    public InvalidParamException(String messageTemplate, Object... messageArgs) {
        super(messageTemplate, messageArgs);
    }

    public InvalidParamException(Throwable cause, String messageTemplate, Object... messageArgs) {
        super(cause, messageTemplate, messageArgs);
    }

    public InvalidParamException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParamException(String message) {
        super(message);
    }

    public InvalidParamException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    @Override
    public boolean isExpectedException() {
        return true;
    }
}
