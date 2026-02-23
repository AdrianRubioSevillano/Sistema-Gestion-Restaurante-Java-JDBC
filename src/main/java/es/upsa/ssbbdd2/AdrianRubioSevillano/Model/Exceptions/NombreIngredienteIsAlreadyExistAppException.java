package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class NombreIngredienteIsAlreadyExistAppException extends  AppException{

    public NombreIngredienteIsAlreadyExistAppException(Throwable cause) {
        super("El nombre del ingrediente ya esta registrado.", cause);
    }
}
