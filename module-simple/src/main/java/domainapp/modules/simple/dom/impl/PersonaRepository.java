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
        objectType = "simple.PersonaMenu",
        repositoryFor = Persona.class
)
@DomainServiceLayout(
        named = "Personas",
        menuOrder = "10"
)

public class PersonaRepository {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Persona> listarTodos() {
        return repositoryService.allInstances(Persona.class);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Persona> findPorNombre(
            @ParameterLayout(named="Nombre")
            final String nombre
    ) {
        TypesafeQuery<Persona> q = isisJdoSupport.newTypesafeQuery(Persona.class);
        final QPersona cand = QPersona.candidate();
        q = q.filter(
                cand.nombre.indexOf(q.stringParameter("nombre")).ne(-1)
        );
        return q.setParameter("nombre", nombre)
                .executeList();
    }
    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    @Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    public Persona create(
            @ParameterLayout(named="Nombre") final String nombre,
            @ParameterLayout(named="Apellido")final String apellido,
            @ParameterLayout(named="Direccion")final String direccion,
            @ParameterLayout(named="Telefono") final String telefono,
            @ParameterLayout(named="Email") final String email,
            @ParameterLayout(named="Dni") final String dni,
            @ParameterLayout(named="Jerarquia")ListaJerarquias jerarquias
                )
    {
        return repositoryService.persist(new Persona(nombre,apellido,direccion,telefono,email,dni,jerarquias));
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;
}
