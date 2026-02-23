package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class CantidadIngredientePlatoIsInvalidAppException extends AppException{
    public CantidadIngredientePlatoIsInvalidAppException(Throwable cause) {
        super("La cantidad de ingrediente en el plato, supera el maximo permitido.", cause);
    }
}
