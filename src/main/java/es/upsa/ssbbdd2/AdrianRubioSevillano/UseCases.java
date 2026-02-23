package es.upsa.ssbbdd2.AdrianRubioSevillano;

import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Dtos.CantidadIngrediente;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Entities.*;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions.AppException;

import java.time.LocalDate;
import java.util.List;

public interface UseCases {

    Ingrediente registrarIngrediente(String nombre, UnidadMedia unidadMedia) throws AppException;

    Plato registrarPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws AppException;

    Menu registrarMenu(String nombre, LocalDate desde, LocalDate hasta, List<String> platos) throws AppException;

    List<Menu> buscarMenu(LocalDate fecha) throws AppException;

    List<Plato> buscarPlatoConIntolerancias(Tipo tipo, List<String> ingredientesIntolerables) throws AppException;

    void subirPrecioPlato(String nombre, double porcentaje) throws AppException;
}
