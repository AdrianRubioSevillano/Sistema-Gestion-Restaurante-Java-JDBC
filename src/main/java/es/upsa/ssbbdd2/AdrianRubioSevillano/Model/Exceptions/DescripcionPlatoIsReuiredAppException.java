package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class DescripcionPlatoIsReuiredAppException extends  AppException{

    public DescripcionPlatoIsReuiredAppException(Throwable cause) {
        super("La descripción del palto es dato obligatorio.", cause);
    }
}
