package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Entities;

import lombok.*;

@Data
@Builder(setterPrefix = "with")
@With
@AllArgsConstructor
@NoArgsConstructor


public class Ingrediente {

    private long id;
    private String nombre;
    private UnidadMedia unidadMedia;

}
