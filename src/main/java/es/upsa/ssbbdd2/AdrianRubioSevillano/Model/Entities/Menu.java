package es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Entities;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder(setterPrefix = "with")
@With
@AllArgsConstructor
@NoArgsConstructor


public class Menu {

    private long id;
    private String nombre;
    private double precio;
    private LocalDate desde;
    private LocalDate hasta;
    private Map<Tipo, List<Plato>> platos;



}
