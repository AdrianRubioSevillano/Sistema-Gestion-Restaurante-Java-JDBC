package es.upsa.ssbbdd2.AdrianRubioSevillano.Impl;

import es.upsa.ssbbdd2.AdrianRubioSevillano.Dao;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Dtos.CantidadIngrediente;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Entities.*;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Exceptions.*;
import org.postgresql.Driver;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoImpl implements Dao {

    Connection connection;

    public DaoImpl() throws AppException {

        try{

            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/upsa", "system", "manager");

        }catch (SQLException sqlException){

            throw asAppException(sqlException);

        }
    }

    @Override
    public Ingrediente insertarIngrediente(String nombre, UnidadMedia unidadMedia) throws AppException {

        final String SQL= """
                          INSERT INTO ingredientes(nombre, unidad)
                                            VAlUES(  ?,       ?);
                          """;
        final String[] GENERATED_KEYS =  {"id"};

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL, GENERATED_KEYS)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, unidadMedia.name());
            preparedStatement.executeUpdate();

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {

                generatedKeys.next();
                return Ingrediente.builder()
                        .withId(generatedKeys.getLong(1))
                        .withNombre(nombre)
                        .withUnidadMedia(unidadMedia)
                        .build();

            }

        }catch (SQLException sqlException){

            throw asAppException(sqlException);

        }
    }

    @Override
    public Plato insertarPlato(String nombre, String descripcion, double precio, Tipo tipo) throws AppException {

        final String SQL = """
                           INSERT INTO platos(nombre, descripcion, precio, tipo)
                                       VAlUES( ?,          ?,         ?,    ?);
                           """;
        final String[] GENERATED_KEYS =  {"id"};

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL, GENERATED_KEYS)){

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, descripcion);
            preparedStatement.setDouble(3, precio);
            preparedStatement.setString(4, tipo.name());
            preparedStatement.executeUpdate();

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {

                generatedKeys.next();

                return Plato.builder()
                        .withId(generatedKeys.getLong(1))
                        .withNombre(nombre)
                        .withDescripcion(descripcion)
                        .withPrecio(precio)
                        .withTipo(tipo)
                        .build();

            }

        }catch (SQLException sqlException){
            throw asAppException(sqlException);
        }
    }

    @Override
    public List<CantidadIngrediente> insertarIngredientePlato(long idPlato, List<CantidadIngrediente> listaIngredientes) throws AppException {


        final String SQL = """
                           
                           INSERT INTO plato_ingredientes(id_platos, id_ingrediente, cantidad)
                                                   VAlUES(  ?,       (SELECT i.id
                                                                      FROM ingredientes i
                                                                      WHERE nombre = ?),    ?);
                           """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            for (CantidadIngrediente listaIngrediente : listaIngredientes) {

                preparedStatement.clearParameters();

                preparedStatement.setLong(1, idPlato);
                preparedStatement.setString(2, listaIngrediente.getNombreIngrediente());
                preparedStatement.setInt(3, listaIngrediente.getCantidad());
                preparedStatement.executeUpdate();

            }

            return listaIngredientes;


        }catch (SQLException sqlException){
            throw asAppException(sqlException);
        }

    }

    @Override
    public Menu registrarMenu(String nombre, double precio, LocalDate desde, LocalDate hasta) throws AppException {

        final String SQL = """
                           INSERT INTO menus(nombre, precio, desde, hasta)
                                      VAlUES(  ?,      ?,      ?,     ?);
                           """;
        final String[] GENERATED_KEYS =  {"id"};

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL, GENERATED_KEYS)){

            preparedStatement.setString(1, nombre);
            preparedStatement.setDouble(2, precio);
            preparedStatement.setObject(3, desde);
            preparedStatement.setObject(4, hasta);
            preparedStatement.executeUpdate();

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {

                generatedKeys.next();
                return Menu.builder()
                        .withId(generatedKeys.getLong(1))
                        .withNombre(nombre)
                        .withPrecio(precio)
                        .withDesde(desde)
                        .withHasta(hasta)
                        .build();
            }

        }catch (SQLException sqlException){
            throw  asAppException(sqlException);
        }

    }

    @Override
    public void insertarPlatosMenu(long idMenu, long idPlato) throws AppException {

        final String SQL = """
                           INSERT INTO menu_platos(id_menu, id_platos)
                                            VAlUES( ?,          ?);
                           """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){

            preparedStatement.setLong(1, idMenu);
            preparedStatement.setLong(2, idPlato);
            preparedStatement.executeUpdate();


        }catch (SQLException sqlException){
            throw asAppException(sqlException);
        }
    }

    @Override
    public Optional<Plato> buscarPlatoByNombre(String nombre) throws AppException {
        final String SQL = """
                           SELECT p.id, p.nombre, p.descripcion, p.precio, p.tipo
                           FROM platos p
                           WHERE p.nombre = ?
                           """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){

            preparedStatement.setString(1, nombre);
            preparedStatement.executeQuery();

            try(ResultSet resultSet = preparedStatement.getResultSet()){

                if (!resultSet.next()) return Optional.empty();

                return Optional.of(Plato.builder()
                        .withId(resultSet.getLong(1))
                        .withNombre(resultSet.getString(2))
                        .withDescripcion(resultSet.getString(3))
                        .withPrecio(resultSet.getDouble(4))
                        .withTipo(Tipo.valueOf(resultSet.getString(5)))
                        .build());

            }catch (SQLException sqlException){
                throw asAppException(sqlException);
            }


        }catch (SQLException sqlException){
            throw  asAppException(sqlException);
        }
    }

    @Override
    public List<Menu> buscarMenu(LocalDate fecha) throws AppException {

        List<Menu> listaMenu = new ArrayList<>();

        final String SQL = """
                                SELECT DISTINCT m.id, m.nombre, m.precio, m.desde, m.hasta
                                FROM menus m
                                JOIN menu_platos mp ON m.id = mp.id_menu
                                WHERE ? BETWEEN m.desde AND m.hasta
                           """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){

            preparedStatement.setObject(1, fecha);
            preparedStatement.executeQuery();

            try(ResultSet resultSet = preparedStatement.getResultSet()){
                while (resultSet.next()){
                    Menu menu = Menu.builder()
                            .withId(resultSet.getLong(1))
                            .withNombre(resultSet.getString(2))
                            .withPrecio(resultSet.getDouble(3))
                            .withDesde(LocalDate.parse(resultSet.getString(4)))
                            .withHasta(LocalDate.parse(resultSet.getString(5)))
                            .build();
                    listaMenu.add(menu);
                }

                return listaMenu;

            }

        }catch (SQLException sqlException){
            throw asAppException(sqlException);
        }


    }

    @Override
    public List<Plato> buscarPlatosByMenu(long idMenu) throws AppException {
        List<Plato> listaPlato = new ArrayList<>();

        final String SQL= """
                              SELECT p.id, p.nombre, p.descripcion, p.precio, p.tipo
                              FROM platos p
                              JOIN menu_platos mp ON p.id = mp.id_platos
                              WHERE mp.id_menu = ?
                          """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){

            preparedStatement.setLong(1, idMenu);
            preparedStatement.executeQuery();

            try(ResultSet resultSet = preparedStatement.getResultSet()){
                while (resultSet.next()){
                    Plato plato = Plato.builder()
                            .withId(resultSet.getLong(1))
                            .withNombre(resultSet.getString(2))
                            .withDescripcion(resultSet.getString(3))
                            .withPrecio(resultSet.getDouble(4))
                            .withTipo(Tipo.valueOf(resultSet.getString(5)))
                            .build();
                    listaPlato.add(plato);
                }

                return listaPlato;
            }

        }catch (SQLException sqlException){
            throw asAppException(sqlException);
        }

    }

    @Override
    public List<Long> buscarPlatoConIntolerancias(List<String> ingredientesIntolerables) throws AppException {

        List<Long> idingrediente = new ArrayList<>();

        final String SQL = """
                            SELECT pi.id_platos
                            FROM plato_ingredientes pi
                            JOIN ingredientes i ON pi.id_ingrediente = i.id
                            WHERE i.nombre = ?
                           """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){

            for (String ingredientesIntolerable : ingredientesIntolerables) {

                preparedStatement.clearParameters();
                preparedStatement.setString(1, ingredientesIntolerable);
                preparedStatement.executeQuery();

                try(ResultSet resultSet = preparedStatement.getResultSet()){

                    while (resultSet.next()){

                        idingrediente.add(resultSet.getLong(1));

                    }

                }

            }


        }catch (SQLException sqlException){
            throw asAppException(sqlException);
        }

        return idingrediente;

    }

    @Override
    public List<Plato> buscarTipoPlatos(Tipo tipo) throws AppException {

        List<Plato> listaPlato = new ArrayList<>();

        final String SQL = """
                           SELECT p.id, p.nombre, p.descripcion, p.precio, p.tipo
                           FROM platos p
                           WHERE p.tipo = ?
                           """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){

            preparedStatement.setString(1, tipo.name());
            preparedStatement.executeQuery();

            try(ResultSet resultSet = preparedStatement.getResultSet()){

                while (resultSet.next()){

                    Plato plato = Plato.builder()
                            .withId(resultSet.getLong(1))
                            .withNombre(resultSet.getString(2))
                            .withDescripcion(resultSet.getString(3))
                            .withPrecio(resultSet.getDouble(4))
                            .withTipo(Tipo.valueOf(resultSet.getString(5)))
                            .build();
                    listaPlato.add(plato);
                }

            }

            return listaPlato;

        }catch (SQLException sqlException){

            throw asAppException(sqlException);

        }

    }

    @Override
    public void subirPrecioPlato(String nombre, double porcentaje) throws AppException {

        final String SQL = """
                           UPDATE platos
                           SET precio = precio * (1 + ?)
                           WHERE id = (SELECT p.id
                                       FROM platos p
                                       WHERE p.nombre = ?)
                           """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){

            preparedStatement.setDouble(1, porcentaje);
            preparedStatement.setString(2, nombre);
            preparedStatement.executeUpdate();

        }catch (SQLException sqlException){
            throw asAppException(sqlException);
        }

    }

    @Override
    public List<Long> ObtenerMenuByPlato(String nombre) throws AppException {

        List<Long> listaMenu = new ArrayList<>();

        final String SQL = """
                            SELECT mp.id_menu
                            FROM menu_platos mp
                            JOIN platos p ON mp.id_platos = p.id
                            WHERE p.nombre = ?
                           """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){

            preparedStatement.setString(1, nombre);
            preparedStatement.executeQuery();

            try (ResultSet resultSet = preparedStatement.getResultSet()){

                while (resultSet.next()){
                    listaMenu.add(resultSet.getLong(1));
                }

            }

            return listaMenu;
        }catch (SQLException sqlException){

            throw asAppException(sqlException);

        }
    }

    @Override
    public void actualizarPrecioMenu(long idMenu, double precio) throws AppException {

        final  String SQL = """
                            UPDATE menus
                            SET precio = ?
                            WHERE id = ?
                            """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){

            preparedStatement.setDouble(1, precio);
            preparedStatement.setLong(2, idMenu);
            preparedStatement.executeUpdate();

        }catch (SQLException sqlException){
            throw asAppException(sqlException);
        }

    }


    public AppException asAppException(SQLException sqlException){

        String message = sqlException.getMessage();

        if (message.contains("NN_INGREDIENTES.NOMBRE")) return new NombreIngredienteIsRequiredAppException(sqlException);
        if (message.contains("UK_INGREDIENTES.NOMBRE")) return new NombreIngredienteIsAlreadyExistAppException(sqlException);
        if (message.contains("NN_INGREDIENTES.UNIDAD")) return new UnidadIngredienteIsRequiredAppException(sqlException);
        if (message.contains("CH_INGREDIENTES.UNIDAD")) return new UnidadIngredienteIsNotFoundAppException(sqlException);
        if (message.contains("FK_PLATO_INGREDIENTES.ID_INGREDIENTES")) return new idIngredientesIsRequiredAppException(sqlException);
        if (message.contains("FK_PLATO_INGREDIENTES.ID_PLATOS")) return new IdPlatoIsRequiredAppException(sqlException);
        if(message.contains("CH_PLATO_INGRADIENTES.CANTIDAD")) return new CantidadIngredientePlatoIsInvalidAppException(sqlException);
        if (message.contains("NN_PLATO_INGREDIENTES.CANTIDAD")) return new CantidadPlatoIngredienteIsRequiredAppException(sqlException);
        if (message.contains("FK_MENUS_PLATOS.ID_MENUS")) return new IdMenuIsRequiredAppException(sqlException);
        if (message.contains("FK_MENUS_PLATOS.ID_PLATOS")) return new IdPlatoIsRequiredAppException(sqlException);
        if (message.contains("NN_PLATOS.TIPO")) return new TipoPlatoIsRequiredAppException(sqlException);
        if (message.contains("CH_PLATOS.TIPO")) return new TipoPlatoIsNotFoundAppException(sqlException);
        if (message.contains("NN_PLATOS.DESCRIPCION")) return new DescripcionPlatoIsReuiredAppException(sqlException);
        if (message.contains("NN_PLATOS.PRECIO")) return new PrecioPlatoIsRequiredAppException(sqlException);
        if (message.contains("UK_PLATOS.NOMBRE")) return new NombrePlatoIsAlreadyExistAppException(sqlException);
        if (message.contains("NN_PLATOS.NOMBRE")) return new NombrePlatoIsRequiredAppException(sqlException);
        if (message.contains("NN_MENUS.HASTA")) return new FechaHastaMenuIsRequiredAppException(sqlException);
        if (message.contains("NN_MENUS.DESDE")) return new FechaDesdeMenuIsRequiredAppException(sqlException);
        if (message.contains("NN_MENUS.PRECIO")) return new PrecioMenuIsRequiredAppException(sqlException);
        if (message.contains("NN_MENUS.NOMBRE")) return new NombreMenuIsRequired(sqlException);



        throw new RuntimeException(sqlException.getMessage(), sqlException);
    }

    @Override
    public void close() throws Exception {

        connection.close();

    }
}
