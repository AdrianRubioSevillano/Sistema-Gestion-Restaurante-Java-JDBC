package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class CantidadPlatoIngredienteIsRequiredAppException extends AppException{
    public CantidadPlatoIngredienteIsRequiredAppException(Throwable cause) {
        super("La cantidad de ingredientes en un plato es un dato necesario.", cause);
    }
}
