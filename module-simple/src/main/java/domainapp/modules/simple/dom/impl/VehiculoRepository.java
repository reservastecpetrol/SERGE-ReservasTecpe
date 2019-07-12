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
        objectType = "simple.VehiculoMenu",
        repositoryFor = Vehiculo.class
)
@DomainServiceLayout(
        named = "Vehiculos",
        menuOrder = "10"
)

public class VehiculoRepository {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Vehiculo> listarTodos() {
        return repositoryService.allInstances(Vehiculo.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Vehiculo> findPorMatricula(
            @ParameterLayout(named="Matricula")
            final String matricula
    ) {
        TypesafeQuery<Vehiculo> q = isisJdoSupport.newTypesafeQuery(Vehiculo.class);
        final QVehiculo cand = QVehiculo.candidate();
        q = q.filter(
                cand.matricula.indexOf(q.stringParameter("matricula")).ne(-1)
        );
        return q.setParameter("matricula", matricula)
                .executeList();
    }

    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    @Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    public Vehiculo create(
            @ParameterLayout(named="Matricula") final String matricula,
            @ParameterLayout(named="Marca")final String marca,
            @ParameterLayout(named="Color")final String color,
            @ParameterLayout(named="Modelo") final String modelo,
            @ParameterLayout(named="Combustible") final boolean combustible,
            @ParameterLayout(named="Seguro") final boolean seguro,
            @ParameterLayout(named="Ubicacion")final String ubicacion,
            @ParameterLayout(named="Estado")final String estado
    )
    {
        return repositoryService.persist(new Vehiculo(matricula,marca,color,modelo,combustible,seguro,ubicacion,estado));
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;
}
