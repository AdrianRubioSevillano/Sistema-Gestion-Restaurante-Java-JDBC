package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class TipoPlatoIsRequiredAppException extends  AppException{

    public TipoPlatoIsRequiredAppException(Throwable cause) {
        super("El tipo de plato es un dato obligatorio.", cause);
    }
}
