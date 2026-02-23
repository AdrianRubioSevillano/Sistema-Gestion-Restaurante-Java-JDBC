package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class IdMenuIsRequiredAppException extends  AppException {

    public IdMenuIsRequiredAppException(Throwable cause) {
        super("El id del menu es un dato obligatorio.", cause);
    }
}
