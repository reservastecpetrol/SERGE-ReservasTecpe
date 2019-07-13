package domainapp.modules.simple.dom.impl;

import java.util.List;

import org.datanucleus.query.typesafe.TypesafeQuery;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.HabitacionMenu",
        repositoryFor = Habitacion.class
)
@DomainServiceLayout(
        named = "Habitaciones",
        menuOrder = "10"
)

public class HabitacionRepository {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Habitacion> listarTodos() {
        return repositoryService.allInstances(Habitacion.class);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Habitacion> Buscar(
            @ParameterLayout(named="Nombre")
            final String nombre
    ) {
        TypesafeQuery<Habitacion> q = isisJdoSupport.newTypesafeQuery(Habitacion.class);
        final QHabitacion cand = QHabitacion.candidate();
        q = q.filter(
                cand.nombre.indexOf(q.stringParameter("nombre")).ne(-1)
        );
        return q.setParameter("nombre", nombre)
                .executeList();
    }
    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    @Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    public Habitacion create(
            @ParameterLayout(named="Nombre") final String nombre,
            @ParameterLayout(named="Ubicacion")final String ubicacion,
            @ParameterLayout(named="Estado")final String estado,
            @ParameterLayout(named="Categoria")ListaHabitaciones categoria
    )
    {
        return repositoryService.persist(new Habitacion(nombre,ubicacion,categoria,estado));
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;
}
