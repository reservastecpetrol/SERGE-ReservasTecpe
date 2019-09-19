package domainapp.modules.simple.dom.impl;

import java.util.Collection;
import java.util.List;

import org.datanucleus.query.typesafe.TypesafeQuery;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.ReservaVehiculoMenu",
        repositoryFor = ReservaVehiculo.class
)
@DomainServiceLayout(
        named = "Reserva Vehiculos",
        menuOrder = "10"
)

/**
 * Esta clase es el servicio de dominio de la clase ReservaVehiculo
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 * @author Cintia Millacura
 */
public class ReservaVehiculoRepository {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    /**
     * Este metodo lista todas las reservas de vehiculos que hay cargados
     * en el sistema
     *
     * @return List<ReservaVehiculo>
     */
    public java.util.List<ReservaVehiculo> listarTodos() {
        return container.allInstances(ReservaVehiculo.class);
    }

    @Programmatic
    /**
     * Este metodo lista todos los usuarios que hay en el sistema de
     * forma que el administrador seleccione a uno en especifico
     *
     * @return Collection<Persona>
     *
     */
    public Collection<Persona> choices0ListarReservasPorPersona() {
        return personaRepository.listarTodos();
    }

    /**
     * Este metodo permite encontrar todas las reservas
     * realizadas por un usuario en particular
     *
     * @param persona
     * @return List<ReservaVehiculo>
     */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<ReservaVehiculo> listarReservasPorPersona(
            @ParameterLayout(named="Persona")
            final Persona persona
    ) {

        List<ReservaVehiculo> reservas;

        TypesafeQuery<ReservaVehiculo> q = isisJdoSupport.newTypesafeQuery(ReservaVehiculo.class);

        final QReservaVehiculo cand = QReservaVehiculo.candidate();

        reservas= q.filter(
                cand.persona.dni.eq(q.stringParameter("dniIngresado")))
                .setParameter("dniIngresado",persona.getDni())
                .executeList();

        return reservas;
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
    /**
     * Este metodo permite encontrar una reserva de vehiculo en particular
     * dada una fecha de reserva
     *
     * @param fechaReseva
     * @return ReservaVehiculo
     */
    public ReservaVehiculo findPorFechaReserva(
            final LocalDate fechaReserva
    ) {
        return container.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        ReservaVehiculo.class,
                        "findByFechaReserva",
                        "fechaReserva", fechaReserva));
    }



    @Programmatic
    /**
     * Este metodo lista todos los usuarios que hay en el sistema de
     * forma que el administrador seleccione a uno en especifico
     *
     * @return Collection<Persona>
     *
     */
    public Collection<Persona> choices2Create() {
        return personaRepository.listarTodos();
    }



    @Programmatic
    /**
     * Este metodo realiza la validacion del ingreso de la fecha de inicio
     *
     * @param fechaInicio
     * @return String
     */
    public String validate0Create(final LocalDate fechaInicio){

        String validacion="";

        if (fechaInicio.isBefore(LocalDate.now())) {
              validacion="Una Reserva no puede empezar en el pasado";
        }

        return validacion;
    }


    @Programmatic
    /**
     *Este metodo realiza la validacion del ingreso de la fecha en que finalizaria la reserva
     *
     * @param fechaInicio
     * @param fechaFin
     *
     * @return String
     *
     */
    public String validate1Create(final LocalDate fechaInicio,final LocalDate fechaFin){

        String validacion="";

        if (fechaFin.isBefore(LocalDate.now())) {
            validacion="Una Reserva no puede finalizar en el pasado";
        }else {
            if (fechaFin.isBefore(fechaInicio)) {
                validacion = "Una Reserva no puede finalizar antes de la fecha de Inicio";
            }
        }

        return validacion;
    }



    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    @Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    @MemberOrder(sequence = "4")
    /**
     * Este metodo permite crear la entidad de dominio ReservaVehiculo
     * con los datos que va a ingresar el usuario
     *
     * @param fechaInicio
     * @param fechaFin
     * @param persona
     *
     * @return ReservaVehiculo
     */
    public ReservaVehiculo create(

            @ParameterLayout(named="Fecha Inicio")final LocalDate fechaInicio,
            @ParameterLayout(named="Fecha Fin")final LocalDate fechaFin,
            @ParameterLayout(named="Persona")final Persona persona
    )
    {
        ReservaVehiculo reservaVehiculo=new ReservaVehiculo();

        Vehiculo vehiculo=vehiculoRepository.listarVehiculosPorEstado("DISPONIBLE").get(0);

        vehiculo.ocupado();

        reservaVehiculo.setFechaReserva(LocalDate.now());
        reservaVehiculo.setFechaInicio(fechaInicio);
        reservaVehiculo.setFechaFin(fechaFin);
        reservaVehiculo.setPersona(persona);
        reservaVehiculo.setVehiculo(vehiculo);
        reservaVehiculo.setEstado("ACTIVA");

        return repositoryService.persist(reservaVehiculo);
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;

    @javax.inject.Inject
    PersonaRepository personaRepository;

    @javax.inject.Inject
    VehiculoRepository vehiculoRepository;

}
