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
import org.apache.isis.applib.annotation.Programmatic;
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

/**
 *
 * Esta clase es el servicio de dominio de la clase Habitacion
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 *@author Francisco Bellani
 *
 */
public class HabitacionRepository {

    /**
     * Este metodo lista todos las Habitaciones que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Habitacion> listarTodos() {
        return repositoryService.allInstances(Habitacion.class);
    }


    /**
     * Este metodo permite encontrar una Habitacion en particular
     * dado un numero que identifica de manera
     * unica a cada Habitacion
     *
     * @param nombre
     * @return List<Habitacion>
     */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Habitacion> findPorNombre(
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


    /**
     * Este metodo permite recuperar en una lista todos las Habitaciones
     * dado un estado en particular
     *
     * @param estado
     * @return List<Habitacion>
     */
    @Programmatic
    public List<Habitacion> listarHabitacionesPorEstado(
            @ParameterLayout(named="Estado")
            final String estado
    ) {
        TypesafeQuery<Habitacion> tq = isisJdoSupport.newTypesafeQuery(Habitacion.class);
        final QHabitacion cand = QHabitacion.candidate();

        List<Habitacion> habitacion = tq.filter(
                cand.estado.startsWith(tq.stringParameter("estado")))
                .setParameter("estado",estado).executeList();

        return habitacion;
    }


    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    @Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    /**
     * Este metodo permite crear la entidad de dominio Habitacion
     * con los datos que va a ingresar el usuario
     *
     * @param nombre
     * @param ubicacion
     * @param categoris
     *
     * @return Habitacion
     *
     */
    public Habitacion create(
            @ParameterLayout(named="Nombre") final String nombre,
            @ParameterLayout(named="Ubicacion")final String ubicacion,
            @ParameterLayout(named="Categoria")ListaHabitaciones categoria
    )
    {
        String estado="DISPONIBLE";

        return repositoryService.persist(new Habitacion(nombre,ubicacion,categoria,estado));
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;
}
