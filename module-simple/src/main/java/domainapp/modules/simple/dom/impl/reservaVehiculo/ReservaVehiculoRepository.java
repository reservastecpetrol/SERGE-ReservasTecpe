package domainapp.modules.simple.dom.impl.reservaVehiculo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.datanucleus.query.typesafe.TypesafeQuery;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dom.impl.SimpleObjects;
import domainapp.modules.simple.dom.impl.enums.EstadoReserva;
import domainapp.modules.simple.dom.impl.enums.EstadoVehiculo;
import domainapp.modules.simple.dom.impl.persona.Persona;
import domainapp.modules.simple.dom.impl.persona.PersonaRepository;
import domainapp.modules.simple.dom.impl.reportes.EjecutarReportes;
import domainapp.modules.simple.dom.impl.vehiculo.Vehiculo;
import domainapp.modules.simple.dom.impl.vehiculo.VehiculoRepository;
import lombok.AccessLevel;
import net.sf.jasperreports.engine.JRException;

@DomainService(
        nature = NatureOfService.DOMAIN,
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

    /**
     * Identificacion del nombre del icono que aparecera en la UI
     *
     * @return String
     */
    public String iconName() {
        return "Reserva";
    }

    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "1")
    @Programmatic
    /**
     * Este metodo lista todas las reservas de vehiculos que hay cargados
     * en el sistema
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> listarReservasDeVehiculos() {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        ReservaVehiculo.class,
                        "find"));
    }

    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "2")
    @Programmatic
    /**
     * Este metodo lista todos las Reservas Activas que hay cargados
     * en el sistema
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> listarReservasDeVehiculosActivas() {
        return this.listarReservasPorEstado(EstadoReserva.ACTIVA);
    }

    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "3")
    @Programmatic
    /**
     * Este metodo lista todos las Reservas Canceladas que hay cargados
     * en el sistema
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> listarReservasDeVehiculosCanceladas() {
        return this.listarReservasPorEstado(EstadoReserva.CANCELADA);
    }

    /**
     * Este metodo permite recuperar en una lista todos las reservas realizadas
     * dado un estado en particular
     *
     * @param estado
     * @return List<ReservaVehiculo>
     */
    @Programmatic
    public List<ReservaVehiculo> listarReservasPorEstado(
            @ParameterLayout(named="Estado")
            final EstadoReserva estado
    ) {
        TypesafeQuery<ReservaVehiculo> tq = isisJdoSupport.newTypesafeQuery(ReservaVehiculo.class);
        final QReservaVehiculo cand = QReservaVehiculo.candidate();

        List<ReservaVehiculo> reservas = tq.filter(
                cand.estado.eq(tq.stringParameter("estado")))
                .setParameter("estado",estado).executeList();

        return reservas;
    }

    /**
     * Este metodo permite encontrar todas las reservas
     * realizadas por un usuario en particular
     *
     * @param persona
     * @return List<ReservaVehiculo>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "4")
    @Programmatic
    public List<ReservaVehiculo> listarReservasPorPersona(
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

    @Programmatic
    public List<ReservaVehiculo> listarReservasPorDni(
            final String dni
    ) {

        List<ReservaVehiculo> reservas;

        TypesafeQuery<ReservaVehiculo> q = isisJdoSupport.newTypesafeQuery(ReservaVehiculo.class);

        final QReservaVehiculo cand = QReservaVehiculo.candidate();

        reservas= q.filter(
                cand.persona.dni.eq(q.stringParameter("dniIngresado")))
                .setParameter("dniIngresado",dni)
                .executeList();

        return reservas;
    }


    @Programmatic
    public List<ReservaVehiculo> listarReservasPorMatricula(
            final String matricula
    ) {

        List<ReservaVehiculo> reservas;

        TypesafeQuery<ReservaVehiculo> q = isisJdoSupport.newTypesafeQuery(ReservaVehiculo.class);

        final QReservaVehiculo cand = QReservaVehiculo.candidate();

        reservas= q.filter(
                cand.vehiculo.matricula.eq(q.stringParameter("matriculaIngresado")))
                .setParameter("matriculaIngresado",matricula)
                .executeList();

        return reservas;
    }



    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "5")
    @Programmatic
    /**
     * Este metodo lista todas las reservas de vehiculos que hay cargados
     * en el sistema en el dia de la fecha
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> listarReservasQueInicianHoy() {

        List<ReservaVehiculo> reservas;

        TypesafeQuery<ReservaVehiculo> q = isisJdoSupport.newTypesafeQuery(ReservaVehiculo.class);

        final QReservaVehiculo cand = QReservaVehiculo.candidate();

        reservas = q.filter(
                cand.fechaInicio.eq(LocalDate.now()))
                .executeList();
        return reservas;
    }


    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "6")
    @Programmatic
    /**
     * Este metodo lista todas las reservas de vehiculos que hay cargados
     * en el sistema finalizan en el dia de la fecha
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> listarReservasQueFinalizanHoy() {

        List<ReservaVehiculo> reservas;

        TypesafeQuery<ReservaVehiculo> q = isisJdoSupport.newTypesafeQuery(ReservaVehiculo.class);

        final QReservaVehiculo cand = QReservaVehiculo.candidate();

        reservas = q.filter(
                cand.fechaFin.eq(LocalDate.now()))
                .executeList();
        return reservas;
    }


    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "7")
    @Programmatic
    /**
     * Este metodo permite listar todas las reservas de vehiculos
     * dada una fecha de reserva
     *
     * @param fechaReseva
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> buscarReservasPorFechaDeReserva(
            final LocalDate fechaReserva
    ) {
        List<ReservaVehiculo> reservas;

        TypesafeQuery<ReservaVehiculo> q = isisJdoSupport.newTypesafeQuery(ReservaVehiculo.class);

        final QReservaVehiculo cand = QReservaVehiculo.candidate();

        reservas = q.filter(
                cand.fechaReserva.eq(q.stringParameter("fecha")))
                .setParameter("fecha",fechaReserva)
                .executeList();
        return reservas;
    }

    @Programmatic
    public Persona recuperarPersonaPorEmail(String email){

        Persona persona=new Persona();

        boolean band=false;

        List<Persona> listaPersonas=new ArrayList<Persona>();

        listaPersonas=personaRepository.listarPersonas();

        int i=0;

        while(i<listaPersonas.size()&&(!band)){

            Persona aux=listaPersonas.get(i);

            if(aux.getEmail().equals(email)){
                persona=aux;
                band=true;
            }
            i++;
        }

        if(band==false){
            persona=null;
        }

        return persona;
    }

    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    //@Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    //@MemberOrder(sequence = "8")
    @Programmatic
    /**
     * Este metodo permite crear la entidad de dominio ReservaVehiculo
     * con los datos que va a ingresar el usuario
     *
     * @param fechaInicio
     * @param fechaFin
     * @param email
     *
     */
    public void crearReserva(
            final LocalDate fechaInicio,
            final LocalDate fechaFin,
            final String email
    )
    {
        Persona persona=new Persona();

        persona=recuperarPersonaPorEmail(email);

        int i=vehiculoRepository.listarVehiculosPorEstado(EstadoVehiculo.DISPONIBLE).size();

        if(i>=1) {

                 ReservaVehiculo reservaVehiculo=new ReservaVehiculo();

                 Vehiculo vehiculo = vehiculoRepository.listarVehiculosPorEstado(EstadoVehiculo.DISPONIBLE).get(0);

                 vehiculo.setEstado(EstadoVehiculo.OCUPADO);

                 reservaVehiculo.setFechaReserva(LocalDate.now());
                 reservaVehiculo.setFechaInicio(fechaInicio);
                 reservaVehiculo.setFechaFin(fechaFin);
                 reservaVehiculo.setPersona(persona);
                 reservaVehiculo.setVehiculo(vehiculo);
                 reservaVehiculo.setEstado(EstadoReserva.ACTIVA);

                 repositoryService.persist(reservaVehiculo);

               String mensaje="¡¡¡ LA OPERACIÓN DE LA RESERVA DEL VEHÍCULO FUE REALIZADA CON EXITO !!!";
               messageService.informUser(mensaje);

        }else {
            String mensaje="¡¡¡ NO HAY VEHÍCULOS DISPONIBLES EN EL SISTEMA PARA REALIZAR LA RESERVA !!!";
            messageService.warnUser(mensaje);
        }
    }


    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@ActionLayout(named = "Exportar PDF Lista de Resevas Activas")
    //@MemberOrder(sequence = "9")
    @Programmatic
    public Blob generarReporteReservasVehiculosActivas(
    ) throws JRException, IOException {

        List<ReservaVehiculo> reservasVehiculos = new ArrayList<ReservaVehiculo>();

        reservasVehiculos = repositoryService.allInstances(ReservaVehiculo.class);

        EjecutarReportes ejecutarReportes=new EjecutarReportes();

        return ejecutarReportes.ListadoReservasVehiculosPDF(reservasVehiculos);

    }

    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "10")
    @Programmatic
    /**
     * Este metodo lista todas las reservas de vehiculos que finalizaron
     * y de esta forma actualizar la disponibilidad de cada uno de los Vehiculos
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> actualizarVehiculosDisponibles() {

        List<ReservaVehiculo> reservas=new ArrayList<ReservaVehiculo>();

        LocalDate fechaAyer=LocalDate.now().minusDays(1);

        TypesafeQuery<ReservaVehiculo> q = isisJdoSupport.newTypesafeQuery(ReservaVehiculo.class);

        final QReservaVehiculo cand = QReservaVehiculo.candidate();

        reservas = q.filter(
                cand.fechaFin.eq((LocalDate)fechaAyer))
                .executeList();
        return reservas;
    }


    @javax.inject.Inject
    EjecutarReportes ejecutarReportes;

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

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

}
