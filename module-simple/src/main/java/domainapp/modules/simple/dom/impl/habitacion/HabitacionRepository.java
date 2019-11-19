package domainapp.modules.simple.dom.impl.habitacion;

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
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.impl.SimpleObjects;
import domainapp.modules.simple.dom.impl.enums.EstadoHabitacion;
import domainapp.modules.simple.dom.impl.enums.ListaHabitaciones;
import lombok.AccessLevel;

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
     * Identificacion del nombre del icono que aparecera en la UI
     *
     * @return String
     */
    public String iconName() {
        return "Habitacion";
    }

    /**
     * Este metodo lista todos las Habitaciones que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Habitacion> listarHabitaciones() {
        return repositoryService.allInstances(Habitacion.class);
    }

    /**
     * Este metodo lista todas las Habitaciones Disponibles que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Habitacion> listarHabitacionesDisponibles() {

        return this.listarHabitacionesPorEstado(EstadoHabitacion.DISPONIBLE);
    }

    /**
     * Este metodo lista todas las Habitaciones Ocupadas que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
    public List<Habitacion> listarHabitacionesOcupadas() {

        return this.listarHabitacionesPorEstado(EstadoHabitacion.OCUPADA);
    }

    /**
     * Este metodo lista todas las Habitaciones Simples que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "4")
    public List<Habitacion> listarHabitacionesSimples() {

        return this.listarHabitacionesPorCategoria(ListaHabitaciones.Simple);
    }

    /**
     * Este metodo lista todas las Habitaciones Ejecutivas que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "5")
    public List<Habitacion> listarHabitacionesEjecutivas() {

        return this.listarHabitacionesPorCategoria(ListaHabitaciones.Ejecutivas);
    }

    /**
     * Este metodo permite recuperar en una lista todos las Habitaciones
     * de tipo Simple que hay en el sistema
     *
     * @param categoria
     * @return List<Habitacion>
     */
    @Programmatic
    public List<Habitacion> listarHabitacionesPorCategoria(
            @ParameterLayout(named="Categoria")
            final ListaHabitaciones categoria
    ) {
        TypesafeQuery<Habitacion> tq = isisJdoSupport.newTypesafeQuery(Habitacion.class);
        final QHabitacion cand = QHabitacion.candidate();

        List<Habitacion> habitacion = tq.filter(
                cand.categoria.eq(tq.stringParameter("categoria")))
                .setParameter("categoria",categoria).executeList();

        return habitacion;
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
    @MemberOrder(sequence = "4")
    public List<Habitacion> buscarHabitacionPorNombre(
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
            final EstadoHabitacion estado
    ) {
        TypesafeQuery<Habitacion> tq = isisJdoSupport.newTypesafeQuery(Habitacion.class);
        final QHabitacion cand = QHabitacion.candidate();

        List<Habitacion> habitacion = tq.filter(
                cand.estado.eq(tq.stringParameter("estado")))
                .setParameter("estado",estado).executeList();

        return habitacion;
    }

    @Programmatic
    public Habitacion verificarHabitacion(String nombre){

        TypesafeQuery<Habitacion> q = isisJdoSupport.newTypesafeQuery(Habitacion.class);
        final QHabitacion cand = QHabitacion.candidate();

        q= q.filter(
                cand.nombre.eq(q.stringParameter("nombreIngresado"))
        );
        return  q.setParameter("nombreIngresado",nombre)
                .executeUnique();
    }


    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    @Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    @MemberOrder(sequence = "5")
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
    public void crearHabitacion(
            @ParameterLayout(named="Nombre") final String nombre,
            @ParameterLayout(named="Ubicacion")final String ubicacion,
            @ParameterLayout(named="Categoria") ListaHabitaciones categoria
    )
    {

        if (verificarHabitacion(nombre.toUpperCase())==null) {
            EstadoHabitacion estado=EstadoHabitacion.DISPONIBLE;

            String ocupante="DESOCUPADA";

            int cantidadOcupante=0;

            repositoryService.persist(new Habitacion(nombre.toUpperCase(),ubicacion.toUpperCase(),categoria,estado,ocupante,cantidadOcupante));

        }else{
            String mensaje="Esta Habitacion ya se encuentra cargada en el sistema!";
            messageService.informUser(mensaje);
        }
    }

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;
}
