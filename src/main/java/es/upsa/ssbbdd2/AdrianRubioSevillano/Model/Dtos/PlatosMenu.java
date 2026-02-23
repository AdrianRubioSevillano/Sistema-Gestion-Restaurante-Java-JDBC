package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Dtos;

import lombok.*;

@Data
@Builder(setterPrefix = "with")
@With
@AllArgsConstructor
@NoArgsConstructor

public class PlatosMenu {

    private String nombreMenu;
    private String nombrePlato;

}
