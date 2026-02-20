package uz.qodirov.warehouse.error;

public class ByIdException extends RuntimeException {
    public ByIdException() {
        super();
    }

    public ByIdException(String message) {
        super(message);
    }

    public ByIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public ByIdException(Throwable cause) {
        super(cause);
    }

    protected ByIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
