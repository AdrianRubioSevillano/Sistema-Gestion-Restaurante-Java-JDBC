package es.upsa.ssbbdd2.AdrianRubioSevillano.Impl;

import es.upsa.ssbbdd2.AdrianRubioSevillano.Dao;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Dtos.CantidadIngrediente;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Entities.*;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions.AppException;
import es.upsa.ssbbdd2.AdrianRubioSevillano.UseCases;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@With

public class UseCasesImpl implements UseCases {

    public Dao dao;

    @Override
    public Ingrediente registrarIngrediente(String nombre, UnidadMedia unidadMedia) throws AppException {
        return dao.insertarIngrediente(nombre, unidadMedia);
    }

    @Override
    public Plato registrarPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws AppException {


        Plato plato = dao.insertarPlato(nombre, descripcion, precio, tipo);
        dao.insertarIngredientePlato(plato.getId(), cantidadesIngredientes);
        plato.setCantidadesIngredientes(cantidadesIngredientes);
        return plato;
    }

    @Override
    public Menu registrarMenu(String nombre, LocalDate desde, LocalDate hasta, List<String> platos) throws AppException {

        double precioMenu = 0;
        List<Plato> platosEncontrados = new ArrayList<>();

        for (String plato : platos) {
            Plato p = dao.buscarPlatoByNombre(plato).get();
            precioMenu = precioMenu + p.getPrecio();
            platosEncontrados.add(p);
        }

        precioMenu = precioMenu * 0.75;

        Menu menu = dao.registrarMenu(nombre, precioMenu, desde, hasta);

        Map<Tipo, List<Plato>> mapaPlatos = new HashMap<>();

        mapaPlatos.put(Tipo.ENTRANTE, new ArrayList<>());
        mapaPlatos.put(Tipo.PRINCIPAL, new ArrayList<>());
        mapaPlatos.put(Tipo.POSTRE, new ArrayList<>());
        mapaPlatos.put(Tipo.INFANTIL, new ArrayList<>());

        for (Plato platosEncontrado : platosEncontrados) {

            dao.insertarPlatosMenu(menu.getId(), platosEncontrado.getId());
            mapaPlatos.get(platosEncontrado.getTipo()).add(platosEncontrado);
        }

        menu.setPlatos(mapaPlatos);

        return menu;
    }

    @Override
    public List<Menu> buscarMenu(LocalDate fecha) throws AppException {

        List<Menu> menus = dao.buscarMenu(fecha);

        for (Menu menu : menus) {

            List<Plato> listaPlatos = dao.buscarPlatosByMenu(menu.getId());

            Map<Tipo, List<Plato>> mapPlatos = new HashMap<>();
            mapPlatos.put(Tipo.ENTRANTE, new ArrayList<>());
            mapPlatos.put(Tipo.PRINCIPAL, new ArrayList<>());
            mapPlatos.put(Tipo.POSTRE, new ArrayList<>());
            mapPlatos.put(Tipo.INFANTIL, new ArrayList<>());

            for (Plato plato : listaPlatos) {
                if (mapPlatos.containsKey(plato.getTipo())) {
                    mapPlatos.get(plato.getTipo()).add(plato);
                }
            }

            menu.setPlatos(mapPlatos);
        }

        return menus;
    }

    @Override
    public List<Plato> buscarPlatoConIntolerancias(Tipo tipo, List<String> ingredientesIntolerables) throws AppException {

        List<Plato> ListaPlatosTipos = dao.buscarTipoPlatos(tipo);
        List<Long> idPlatosIntolerantes = dao.buscarPlatoConIntolerancias(ingredientesIntolerables);

        List<Plato> ListaPlatos = new ArrayList<>();

        for (Plato plato : ListaPlatosTipos) {

            if(!idPlatosIntolerantes.contains(plato.getId())){

                ListaPlatos.add(plato);

            }

        }

        return ListaPlatos;
    }

    @Override
    public void subirPrecioPlato(String nombre, double porcentaje) throws AppException {


        dao.subirPrecioPlato(nombre, porcentaje);
        List<Long> menus = dao.ObtenerMenuByPlato(nombre);

        for (Long menu : menus) {

            List<Plato> platosMenu = dao.buscarPlatosByMenu(menu);
            double precioMenu = 0;

            for (Plato platoMenu : platosMenu) {
                precioMenu = precioMenu + platoMenu.getPrecio();
            }

            precioMenu = precioMenu * 0.75;

            dao.actualizarPrecioMenu(menu, precioMenu);

        }


    }
}
