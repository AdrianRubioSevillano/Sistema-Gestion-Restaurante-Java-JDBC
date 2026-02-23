package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class NombrePlatoIsRequiredAppException extends AppException{
    public NombrePlatoIsRequiredAppException(Throwable cause) {
        super("El nombre del plato es un dato obligatorio.", cause);
    }
}
