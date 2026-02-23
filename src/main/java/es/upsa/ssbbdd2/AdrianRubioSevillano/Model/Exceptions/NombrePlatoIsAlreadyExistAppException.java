package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class NombrePlatoIsAlreadyExistAppException extends  AppException{
    public NombrePlatoIsAlreadyExistAppException(Throwable cause) {
        super("El nombre del plato ya existe.", cause);
    }
}
