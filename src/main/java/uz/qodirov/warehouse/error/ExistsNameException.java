package uz.qodirov.warehouse.error;

public class ExistsNameException extends RuntimeException {
    public ExistsNameException() {
        super();
    }

    public ExistsNameException(String message) {
        super(message);
    }

    public ExistsNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistsNameException(Throwable cause) {
        super(cause);
    }

    protected ExistsNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
