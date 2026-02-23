package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class IdPlatoIsRequiredAppException extends AppException{
    public IdPlatoIsRequiredAppException(Throwable cause) {
        super("El id del plato es un dato necesario.", cause);
    }
}
