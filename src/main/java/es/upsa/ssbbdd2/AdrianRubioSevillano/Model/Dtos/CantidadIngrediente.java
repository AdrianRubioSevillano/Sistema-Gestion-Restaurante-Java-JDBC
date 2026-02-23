package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Dtos;

import lombok.*;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@With


public class CantidadIngrediente {


    private String nombreIngrediente;
    private int cantidad;

}
