package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class idIngredientesIsRequiredAppException extends AppException{
    public idIngredientesIsRequiredAppException(Throwable cause) {
        super("El id del plato es un dato necesario.", cause);
    }
}
