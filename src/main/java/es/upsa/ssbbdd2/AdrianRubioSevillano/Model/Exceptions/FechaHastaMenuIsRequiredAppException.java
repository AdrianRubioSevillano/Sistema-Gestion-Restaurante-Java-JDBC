package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class FechaHastaMenuIsRequiredAppException extends AppException{

    public FechaHastaMenuIsRequiredAppException(Throwable cause) {
        super("La fecha del fin del menu es un dato obligatorio.", cause);
    }
}
