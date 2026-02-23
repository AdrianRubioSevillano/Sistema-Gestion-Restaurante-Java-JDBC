package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class FechaDesdeMenuIsRequiredAppException extends  AppException{
    public FechaDesdeMenuIsRequiredAppException(Throwable cause) {
        super("Lafecha de inicio del menu es un dato obligatorio", cause);
    }
}
