package lovely.baby.online.mall.model.exception;

import org.apache.commons.lang3.StringUtils;

public abstract class MallException extends RuntimeException {

    private static final long serialVersionUID = -2616348620898830600L;

    public MallException(String messageTemplate, Object... messageArgs) {
        super(String.format(messageTemplate, messageArgs));
    }

    public MallException(Throwable cause, String messageTemplate, Object... messageArgs) {
        super(String.format(messageTemplate, messageArgs), cause);
    }

    public MallException() {
        super(StringUtils.EMPTY);
    }

    public MallException(String message, Throwable cause) {
        super(message, cause);
    }

    public MallException(String message) {
        super(message);
    }

    public MallException(Throwable cause) {
        super(cause);
    }

    public abstract boolean isExpectedException();
}
