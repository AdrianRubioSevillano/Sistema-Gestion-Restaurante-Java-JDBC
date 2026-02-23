package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class PrecioPlatoIsRequiredAppException extends AppException{
    public PrecioPlatoIsRequiredAppException(Throwable cause) {
        super("El precio del plato es un dato obligatorio.", cause);
    }
}
