package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class PrecioMenuIsRequiredAppException extends AppException{
    public PrecioMenuIsRequiredAppException(Throwable cause) {
        super("El precio del menu es un dato obligatorio.", cause);
    }
}
