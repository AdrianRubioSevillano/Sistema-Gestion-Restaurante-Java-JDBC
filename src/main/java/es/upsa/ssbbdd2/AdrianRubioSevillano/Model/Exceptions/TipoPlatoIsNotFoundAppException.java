package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class TipoPlatoIsNotFoundAppException extends AppException{

    public TipoPlatoIsNotFoundAppException(Throwable cause) {
        super("EL tipo de plato no se encuentra.", cause);
    }
}
