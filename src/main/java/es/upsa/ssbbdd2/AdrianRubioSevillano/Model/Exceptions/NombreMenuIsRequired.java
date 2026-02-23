package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions;

public class NombreMenuIsRequired extends AppException{
    public NombreMenuIsRequired(Throwable cause) {
        super("El nombre del menu es un dato obligatorio.", cause);
    }
}
