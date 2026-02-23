package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class AppRunTimeException extends RuntimeException{

    public AppRunTimeException() {
    }

    public AppRunTimeException(String message) {
        super(message);
    }

    public AppRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppRunTimeException(Throwable cause) {
        super(cause);
    }

    public AppRunTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
