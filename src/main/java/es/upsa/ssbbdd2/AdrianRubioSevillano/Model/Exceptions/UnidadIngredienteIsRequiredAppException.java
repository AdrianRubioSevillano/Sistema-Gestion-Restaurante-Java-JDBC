package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class UnidadIngredienteIsRequiredAppException extends AppException{

    public UnidadIngredienteIsRequiredAppException(Throwable cause) {
        super("La unidad del ingrediente es un dato necesario.", cause);
    }
}
