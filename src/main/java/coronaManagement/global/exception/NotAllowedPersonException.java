package coronaManagement.global.exception;

public class NotAllowedPersonException extends RuntimeException {

    public NotAllowedPersonException() {
        super();
    }

    public NotAllowedPersonException(String message) {
        super(message);
    }

    public NotAllowedPersonException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAllowedPersonException(Throwable cause) {
        super(cause);
    }

    protected NotAllowedPersonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
