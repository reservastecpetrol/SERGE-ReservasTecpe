package domainapp.modules.simple.dom.impl.reservaVehiculo;

import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;

import domainapp.modules.simple.dom.impl.persona.Persona;
import domainapp.modules.simple.dom.impl.persona.PersonaRepository;
import domainapp.modules.simple.dom.impl.vehiculo.VehiculoRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "ReservaVehiculo",
        repositoryFor = ReservaVehiculo.class
)
@DomainServiceLayout(
        named = "ReservaVehiculo",
        menuOrder = "10"
)
/**
 * Esta clase es el servicio de dominio de la clase ReservaVehiculo
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 * @author Cintia Millacura
 */
public class ReservaVehiculoMenu {

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Vehiculos"
    )
    @MemberOrder(sequence = "1")
    /**
     * Este metodo lista todas las reservas de vehiculos que hay cargados
     * en el sistema
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> listarReservasDeVehiculos() {
        return reservaVehiculorepository.listarReservasDeVehiculos();
    }

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Vehiculos Activas"
    )
    @MemberOrder(sequence = "2")
    /**
     * Este metodo lista todos las Reservas Activas que hay cargados
     * en el sistema
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaVehiculo> listarReservasDeVehiculosActivas() {
        return reservaVehiculorepository.listarReservasDeVehiculosActivas();
    }

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Vehiculos Canceladas"
    )
    @MemberOrder(sequence = "3")
    /**
     * Este metodo lista todos las Reservas Canceladas que hay cargados
     * en el sistema
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> listarReservasDeVehiculosCanceladas() {
        return reservaVehiculorepository.listarReservasDeVehiculosCanceladas();
    }


    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Vehiculos por Persona"
    )
    @MemberOrder(sequence = "4")
    /**
     * Este metodo permite encontrar todas las reservas
     * realizadas por un usuario en particular
     *
     * @param persona
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> listarReservasPorPersona(
            @ParameterLayout(named="Persona")
            final Persona persona
    ) {
        return reservaVehiculorepository.listarReservasPorPersona(persona);
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
        return personaRepository.listarPersonas();
    }


    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Vehiculos que Inician Hoy"
    )
    @MemberOrder(sequence = "5")
    /**
     * Este metodo lista todas las reservas de vehiculos que hay cargados
     * en el sistema en el dia de la fecha
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> listarReservasQueInicianHoy() {
        return reservaVehiculorepository.listarReservasQueInicianHoy();
    }

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Vehiculos que Finalizan Hoy"
    )
    @MemberOrder(sequence = "6")
    /**
     * Este metodo lista todas las reservas de vehiculos que hay cargados
     * en el sistema finalizan en el dia de la fecha
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> listarReservasQueFinalizanHoy() {
        return reservaVehiculorepository.listarReservasQueFinalizanHoy();
    }

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Buscar Reservas de Vehiculos por Fecha de Reserva"
    )
    @MemberOrder(sequence = "7")
    /**
     * Este metodo permite listar todas las reservas de vehiculos
     * dada una fecha de reserva
     *
     * @param fechaReseva
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> buscarReservasPorFechaDeReserva(
            @ParameterLayout(named="Fecha Reserva")
            final LocalDate fechaReserva
    ) {
        return reservaVehiculorepository.buscarReservasPorFechaDeReserva(fechaReserva);
    }

    @Programmatic
    /**
     * Este metodo lista todos los usuarios que hay en el sistema de
     * forma que el administrador seleccione a uno en especifico
     *
     * @return Collection<Persona>
     *
     */
    public Collection<Persona> choices2CrearReserva() {
        return personaRepository.listarPersonas();
    }



    @Programmatic
    /**
     * Este metodo realiza la validacion del ingreso de la fecha de inicio
     *
     * @param fechaInicio
     * @return String
     */
    public String validate0CrearReserva(final LocalDate fechaInicio){

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
    public String validate1CrearReserva(final LocalDate fechaInicio,final LocalDate fechaFin){

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


    @Action(
          //  semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
       //     bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Crear Reserva De Vehiculo"
    )
    @MemberOrder(sequence = "8")
    /**
     * Este metodo permite crear la entidad de dominio ReservaVehiculo
     * con los datos que va a ingresar el usuario
     *
     * @param fechaInicio
     * @param fechaFin
     * @param persona
     *
     */
    public ReservaVehiculo crearReserva(
            @ParameterLayout(named="Fecha Inicio")final LocalDate fechaInicio,
            @ParameterLayout(named="Fecha Fin")final LocalDate fechaFin,
            @ParameterLayout(named="Persona")final Persona persona
    ) {
        return reservaVehiculorepository.crearReserva(fechaInicio,fechaFin,persona);
    }

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Actualizar Vehiculos Disponibles"
    )
    @MemberOrder(sequence = "9")
    /**
     * Este metodo lista todas las reservas de Vehiculos que finalizaron
     * y de esta forma actualizar la disponibilidad de cada uno de los Vehiculos.
     *
     * @return List<ReservaVehiculo>
     */
    public List<ReservaVehiculo> actualizarVehiculosDisponibles() {

        return reservaVehiculorepository.actualizarVehiculosDisponibles();
    }


    @javax.inject.Inject
    VehiculoRepository vehiculoRepository;

    @javax.inject.Inject
    PersonaRepository personaRepository;

    @javax.inject.Inject
    ReservaVehiculoRepository reservaVehiculorepository;
}
