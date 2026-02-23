package es.upsa.ssbbdd2.AdrianRubioSevillano;

import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Dtos.CantidadIngrediente;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Entities.*;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions.AppException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface Dao extends AutoCloseable{

    Ingrediente insertarIngrediente(String nombre, UnidadMedia unidadMedia) throws AppException;
    Plato insertarPlato(String nombre, String descripcion, double precio, Tipo tipo) throws AppException;
    List<CantidadIngrediente> insertarIngredientePlato(long idPlato, List<CantidadIngrediente> listaIngredientes) throws AppException;
    Menu registrarMenu(String nombre, double precio, LocalDate desde, LocalDate hasta) throws AppException;
    void insertarPlatosMenu(long idMenu, long idPlato) throws AppException;
    Optional<Plato> buscarPlatoByNombre(String nombre) throws AppException;
    List<Menu> buscarMenu(LocalDate fecha) throws AppException;
    List<Plato> buscarPlatosByMenu(long idMenu) throws AppException;
    List<Long> buscarPlatoConIntolerancias(List<String> ingredientesIntolerables) throws AppException;
    List<Plato> buscarTipoPlatos(Tipo tipo) throws AppException;
    void subirPrecioPlato(String nombre, double porcentaje) throws AppException;
    List<Long> ObtenerMenuByPlato(String nombre) throws AppException;
    void actualizarPrecioMenu(long idMenu, double precio) throws AppException;

}
