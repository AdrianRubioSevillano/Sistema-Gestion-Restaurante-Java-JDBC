package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class NombreIngredienteIsRequiredAppException extends AppException{

    public NombreIngredienteIsRequiredAppException(Throwable cause) {
        super("El nombre de ingrediente es un dato necesario.", cause);
    }
}
