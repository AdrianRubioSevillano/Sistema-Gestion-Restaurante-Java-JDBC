package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class UnidadIngredienteIsNotFoundAppException extends  AppException{

    public UnidadIngredienteIsNotFoundAppException(Throwable cause) {
        super("La unidad del ingrediente no se encuentra.", cause);
    }
}
