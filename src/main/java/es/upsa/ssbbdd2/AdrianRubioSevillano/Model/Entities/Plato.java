package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Entities;

import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Dtos.CantidadIngrediente;
import lombok.*;

import java.util.List;

@Data
@Builder(setterPrefix = "with")
@With
@AllArgsConstructor
@NoArgsConstructor

public class Plato {

    private long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Tipo tipo;
    private List<CantidadIngrediente> cantidadesIngredientes;

}
