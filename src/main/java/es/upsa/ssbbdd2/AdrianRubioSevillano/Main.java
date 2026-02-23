package es.upsa.ssbbdd2.AdrianRubioSevillano;

import es.upsa.ssbbdd2.AdrianRubioSevillano.Impl.DaoImpl;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Impl.UseCasesImpl;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Dtos.CantidadIngrediente;
import es.upsa.ssbbdd2.AdrianRubioSevillano.Model.Entities.*;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        try(Dao dao = new DaoImpl()){

            UseCases useCases = UseCasesImpl.builder()
                    .withDao(dao)
                    .build();

            //REGISTRAR INGREDIENTES

            Ingrediente gambas = useCases.registrarIngrediente("Gambas", UnidadMedia.GRAMO);
            Ingrediente jamon = useCases.registrarIngrediente("Jamon", UnidadMedia.GRAMO);
            Ingrediente solomillo = useCases.registrarIngrediente("Solomillo", UnidadMedia.GRAMO);
            Ingrediente merluza = useCases.registrarIngrediente("Merluza", UnidadMedia.GRAMO);
            Ingrediente turron = useCases.registrarIngrediente("Turron", UnidadMedia.GRAMO);
            Ingrediente aceite = useCases.registrarIngrediente("Aceite", UnidadMedia.CENTILITRO);
            Ingrediente sal = useCases.registrarIngrediente("Sal", UnidadMedia.GRAMO);
            Ingrediente ajo = useCases.registrarIngrediente("Ajo", UnidadMedia.UNIDAD);
            Ingrediente huevo = useCases.registrarIngrediente("Huevo", UnidadMedia.UNIDAD);
            Ingrediente patata = useCases.registrarIngrediente("Patata", UnidadMedia.GRAMO);
            Ingrediente nata = useCases.registrarIngrediente("Nata", UnidadMedia.CENTILITRO);
            Ingrediente azucar = useCases.registrarIngrediente("Azucar", UnidadMedia.GRAMO);


            //REGISTRAR PLATOS

            Plato coctelGambas = useCases.registrarPlato("Coctel de Gambas", "Entrante clasico", 12.0, Tipo.ENTRANTE, List.of(
                    new CantidadIngrediente(gambas.getNombre(), 200),
                    new CantidadIngrediente(nata.getNombre(), 50),
                    new CantidadIngrediente(sal.getNombre(), 5)
            ));

            Plato huevosRotos = useCases.registrarPlato("Huevos Rotos", "Con jamon iberico", 14.0, Tipo.ENTRANTE, List.of(
                    new CantidadIngrediente(patata.getNombre(), 200),
                    new CantidadIngrediente(huevo.getNombre(), 2),
                    new CantidadIngrediente(jamon.getNombre(), 50),
                    new CantidadIngrediente(aceite.getNombre(), 30)
            ));

            Plato solomilloPimienta = useCases.registrarPlato("Solomillo a la Pimienta", "Carne tierna con salsa", 25.0, Tipo.PRINCIPAL, List.of(
                    new CantidadIngrediente(solomillo.getNombre(), 300),
                    new CantidadIngrediente(nata.getNombre(), 40),
                    new CantidadIngrediente(aceite.getNombre(), 20),
                    new CantidadIngrediente(sal.getNombre(), 5)
            ));

            Plato merluzaSalsa = useCases.registrarPlato("Merluza en Salsa Verde", "Pescado fresco del dia", 22.0, Tipo.PRINCIPAL, List.of(
                    new CantidadIngrediente(merluza.getNombre(), 250),
                    new CantidadIngrediente(ajo.getNombre(), 2),
                    new CantidadIngrediente(aceite.getNombre(), 25),
                    new CantidadIngrediente(sal.getNombre(), 5)
            ));

            Plato mousseTurron = useCases.registrarPlato("Mousse de Turron", "Postre navideño", 8.0, Tipo.POSTRE, List.of(
                    new CantidadIngrediente(turron.getNombre(), 150),
                    new CantidadIngrediente(nata.getNombre(), 50),
                    new CantidadIngrediente(azucar.getNombre(), 20),
                    new CantidadIngrediente(huevo.getNombre(), 1)
            ));


            //REGISTRAR MENUS

            LocalDate inicio = LocalDate.of(2025, 12, 20);
            LocalDate fin = LocalDate.of(2026, 1, 10);

            Menu menuNochebuena = useCases.registrarMenu("Menu Nochebuena", inicio, fin, List.of(
                    coctelGambas.getNombre(),
                    merluzaSalsa.getNombre(),
                    mousseTurron.getNombre()
            ));

            Menu menuNavidad = useCases.registrarMenu("Menu Navidad", inicio, fin, List.of(
                    huevosRotos.getNombre(),
                    solomilloPimienta.getNombre(),
                    mousseTurron.getNombre()
            ));

            Menu menuReyes = useCases.registrarMenu("Menu Reyes", inicio, fin, List.of(
                    coctelGambas.getNombre(),
                    solomilloPimienta.getNombre(),
                    mousseTurron.getNombre()
            ));



            //BUSCAR MENUS FECHA

            List<Menu> menus = useCases.buscarMenu(LocalDate.of(2025, 12, 28));

            for (Menu menu : menus) {
                System.out.println("Menu: " + menu.getNombre());
                System.out.println("\t Precio: " + menu.getPrecio());
                System.out.println("\t Platos: ");

                System.out.println("\t\tENTRANTE: ");
                List<Plato> entrantes = menu.getPlatos().get(Tipo.ENTRANTE);
                for (Plato p : entrantes){
                    System.out.println("\t\t\t" + p.getNombre());
                }

                System.out.println("\t\tPRINCIPAL: ");
                List<Plato> principales = menu.getPlatos().get(Tipo.PRINCIPAL);
                for (Plato p : principales){
                    System.out.println("\t\t\t" + p.getNombre());
                }

                System.out.println("\t\tPOSTRE: ");
                List<Plato> postres = menu.getPlatos().get(Tipo.POSTRE);
                for (Plato p : postres){
                    System.out.println("\t\t\t" + p.getNombre());
                }
            }



            //BUSCAR PLATO INTOLERANCIAS

            List<Plato> entrantesSinAceiteJamon = useCases.buscarPlatoConIntolerancias(Tipo.ENTRANTE, List.of(
                    aceite.getNombre(),
                    jamon.getNombre()
            ));
            System.out.println(entrantesSinAceiteJamon);

            List<Plato> postresSinAzucar = useCases.buscarPlatoConIntolerancias(Tipo.POSTRE, List.of(
                    azucar.getNombre()
            ));
            System.out.println(postresSinAzucar);

            List<Plato> princiapalSinPatata = useCases.buscarPlatoConIntolerancias(Tipo.PRINCIPAL, List.of(
                    patata.getNombre()
            ));
            System.out.println(princiapalSinPatata);


            //SUBIR PRECIO MENU Y PLATOS

            useCases.subirPrecioPlato(merluzaSalsa.getNombre(), 0.5);
            useCases.subirPrecioPlato(mousseTurron.getNombre(), 0.3);


        }
    }
}