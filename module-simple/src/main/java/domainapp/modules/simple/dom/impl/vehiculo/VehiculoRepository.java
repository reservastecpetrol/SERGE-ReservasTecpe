package domainapp.modules.simple.dom.impl.vehiculo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.datanucleus.query.typesafe.TypesafeQuery;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dom.impl.SimpleObjects;
import domainapp.modules.simple.dom.impl.enums.EstadoVehiculo;
import domainapp.modules.simple.dom.impl.reportes.EjecutarReportes;
import lombok.AccessLevel;
import net.sf.jasperreports.engine.JRException;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Vehiculo.class
)
@DomainServiceLayout(
        named = "Vehiculos",
        menuOrder = "10"
)

/**
 *
 * Esta clase es el servicio de dominio de la clase Vehiculo
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 *@author Cintia Millacura
 *
 */
public class VehiculoRepository {

    /**
     * Identificacion del nombre del icono que aparecera en la UI
     *
     * @return String
     */
    public String iconName() {
        return "Vehiculo";
    }

    /**
     * Este metodo lista todos los Vehiculos que hay cargados
     * en el sistema
     *
     * @return List<Vehiculo>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "1")
    @Programmatic
    public List<Vehiculo> listarVehiculos() {
        return repositoryService.allInstances(Vehiculo.class);
    }


    /**
     * Este metodo lista todos los Vehiculos Disponibles que hay cargados
     * en el sistema
     *
     * @return List<Vehiculo>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "2")
    @Programmatic
    public List<Vehiculo> listarVehiculosDisponibles() {

        return this.listarVehiculosPorEstado(EstadoVehiculo.DISPONIBLE);
    }

    /**
     * Este metodo lista todos los Vehiculos Ocupados que hay cargados
     * en el sistema
     *
     * @return List<Vehiculo>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "3")
    @Programmatic
    public List<Vehiculo> listarVehiculosOcupados() {

        return this.listarVehiculosPorEstado(EstadoVehiculo.OCUPADO);
    }


    /**
     * Este metodo permite encontrar un Vehiculo en particular
     * dada una matricula que es la que identifica de manera
     * unica a cada Vehiculo
     *
     * @param matricula
     * @return List<Vehiculo>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "4")
    @Programmatic
    public List<Vehiculo> buscarVehiculoPorMatricula(
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


    /**
     * Este metodo permite recuperar en una lista todos los Vehiculos
     * dado un estado en particular
     *
     * @param estado
     * @return List<Vehiculo>
     */
    @Programmatic
    public List<Vehiculo> listarVehiculosPorEstado(
            @ParameterLayout(named="Estado")
            final EstadoVehiculo estado
    ) {
        TypesafeQuery<Vehiculo> tq = isisJdoSupport.newTypesafeQuery(Vehiculo.class);
        final QVehiculo cand = QVehiculo.candidate();

        List<Vehiculo> vehiculos = tq.filter(
                cand.estado.eq(tq.stringParameter("estado")))
                .setParameter("estado",estado).executeList();

        return vehiculos;
    }

    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    //@Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    //@MemberOrder(sequence = "5")
    @Programmatic
    /**
     * Este metodo permite crear la entidad de dominio Vehiculo
     * con los datos que va a ingresar el usuario
     *
     *
     * @param matricula
     * @param marca
     * @param color
     * @param modelo
     * @param combustible
     * @param seguro
     * @param ubicacion
     *
     * @return Vehiculo
     */
    public Vehiculo crearVehiculo(
            final String matricula,
            final String marca,
            final String color,
            final String modelo,
            final boolean combustible,
            final boolean seguro,
            final String ubicacion
    )
    {

            EstadoVehiculo estado=EstadoVehiculo.DISPONIBLE;

            final Vehiculo vehiculo=new Vehiculo(matricula,marca,color,modelo,combustible,seguro,ubicacion,estado);

            repositoryService.persist(vehiculo);

            return vehiculo;
    }

    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@ActionLayout(named = "Exportar PDF Lista de Vehiculos Disponibles")
    //@MemberOrder(sequence = "6")
    @Programmatic
    public Blob generarReporteVehiculosDisponibles(
    ) throws JRException, IOException {

        List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();

        vehiculos = repositoryService.allInstances(Vehiculo.class);

        EjecutarReportes ejecutarReportes=new EjecutarReportes();

        return ejecutarReportes.ListadoVehiculosPDF(vehiculos);

    }

    @javax.inject.Inject
    EjecutarReportes ejecutarReportes;

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
