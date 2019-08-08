package domainapp.modules.simple.dom.impl;

import java.util.Collection;
import java.util.Date;

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
        named = "Reservas",
        menuOrder = "10"
)

public class ReservaVehiculoRepository {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public java.util.List<ReservaVehiculo> listAll() {
        return container.allInstances(ReservaVehiculo.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public ReservaVehiculo findByFechaReserva(
            final String fechaReserva
    ) {
        return container.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        ReservaVehiculo.class,
                        "findByFechaReserva",
                        "fechaReserva", fechaReserva));
    }

    @Programmatic
    public Collection<Vehiculo> choices4Create() {
        return vehiculoRepository.listarVehiculosPorEstado("Disponible");
    }

    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    @Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    public ReservaVehiculo create(
            @ParameterLayout(named="Fecha Reserva") final Date fechaReserva,
            @ParameterLayout(named="Fecha Inicio")final Date fechaInicio,
            @ParameterLayout(named="Fecha Fin")final Date fechaFin,
            @ParameterLayout(named="Persona") final Persona persona,
            @ParameterLayout(named="Vehiculo") final Vehiculo vehiculo,
            @ParameterLayout(named="Estado")final String estado
    )
    {
        return repositoryService.persist(new ReservaVehiculo(fechaReserva,fechaInicio,fechaFin,persona,vehiculo,estado));
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;

    @javax.inject.Inject
    VehiculoRepository vehiculoRepository;
}
