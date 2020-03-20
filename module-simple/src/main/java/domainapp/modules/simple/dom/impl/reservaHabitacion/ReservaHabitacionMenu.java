package domainapp.modules.simple.dom.impl.reservaHabitacion;

import java.util.Collection;
import java.util.List;

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

import domainapp.modules.simple.dom.impl.habitacion.HabitacionRepository;
import domainapp.modules.simple.dom.impl.persona.Persona;
import domainapp.modules.simple.dom.impl.persona.PersonaRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "ReservaHabitacion",
        repositoryFor = ReservaHabitacion.class
)
@DomainServiceLayout(
        named = "ReservaHabitacion",
        menuOrder = "10"
)
/**
 * Esta clase es el servicio de dominio de la clase ReservaHabitacion
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 * @author Francisco Bellani
 */

public class ReservaHabitacionMenu {

    @Action(
            semantics = SemanticsOf.SAFE
            //restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Habitaciones"
    )
    @MemberOrder(sequence = "1")
    /**
     * Este metodo lista todas las reservas de habitaciones que hay cargados
     * en el sistema
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> listarReservasDeHabitaciones() {
        return reservaHabitacionrepository.listarReservasDeHabitaciones();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Habitaciones Activas"
    )
    @MemberOrder(sequence = "2")
    /**
     * Este metodo lista todos las Reservas Activas que hay cargados
     * en el sistema
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> listarReservasDeHabitacionesActivas() {
        return reservaHabitacionrepository.listarReservasDeHabitacionesActivas();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Habitaciones Canceladas"
    )
    @MemberOrder(sequence = "3")
    /**
     * Este metodo lista todos las Reservas Canceladas que hay cargados
     * en el sistema
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> listarReservasDeHabitacionesCanceladas() {
        return reservaHabitacionrepository.listarReservasDeHabitacionesCanceladas();
    }


    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Habitaciones por Persona"
    )
    @MemberOrder(sequence = "4")
    /**
     * Este metodo permite encontrar todas las reservas
     * realizadas por un usuario en particular
     *
     * @param persona
     * @return List<ReservaVehiculo>
     */
    public List<ReservaHabitacion> listarReservasPorPersona(
            @ParameterLayout(named="Persona")
            final Persona persona
    ) {
        return reservaHabitacionrepository.listarReservasPorPersona(persona);
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
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Habitaciones que Inician Hoy"
    )
    @MemberOrder(sequence = "5")
    /**
     * Este metodo lista todas las reservas de habitaciones que hay cargados
     * en el sistema que inician en el dia de la fecha
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> listarReservasQueInicianHoy() {
        return reservaHabitacionrepository.listarReservasQueInicianHoy();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Reservas de Habitaciones que Finalizan Hoy"
    )
    @MemberOrder(sequence = "6")
    /**
     * Este metodo lista todas las reservas de habitaciones que hay cargados
     * en el sistema que finalizan en el dia de la fecha
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> listarReservasQueFinalizanHoy() {
        return reservaHabitacionrepository.listarReservasQueFinalizanHoy();
    }

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Buscar Reservas de Habitaciones por Fecha de Reserva"
    )
    @MemberOrder(sequence = "7")
    /**
     * Este metodo permite listar todas las reservas de habitaciones
     * dada una fecha de reserva
     *
     * @param fechaReseva
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> buscarReservasPorFechaDeReserva(
            @ParameterLayout(named="Fecha Reserva")
            final LocalDate fechaReserva
    ) {
        return reservaHabitacionrepository.buscarReservasPorFechaDeReserva(fechaReserva);
    }

    //@Programmatic
    /**
     * Este metodo lista todos los usuarios que hay en el sistema de
     * forma que el administrador seleccione a uno en especifico
     *
     * @return Collection<Persona>
     *
     */
    /*public Collection<Persona> choices2CrearReservaDeHabitacion() {
        return personaRepository.listarPersonas();
    }
*/
    @Programmatic
    /**
     * Este metodo realiza la validacion del ingreso de la fecha de inicio
     *
     * @param fechaInicio
     * @return String
     */
    public String validate0CrearReservaDeHabitacion(final LocalDate fechaInicio){

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
    public String validate1CrearReservaDeHabitacion(final LocalDate fechaInicio,final LocalDate fechaFin){

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
       //     semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
         //   bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Crear Reserva De Habitacion"
    )
    @MemberOrder(sequence = "8")
    /**
     * Este metodo permite crear la entidad de dominio ReservaHabitacion
     * con los datos que va a ingresar el usuario
     *
     * @param fechaInicio
     * @param fechaFin
     * @param email
     *
     */
    public void crearReservaDeHabitacion(
            @ParameterLayout(named="Fecha Inicio")final LocalDate fechaInicio,
            @ParameterLayout(named="Fecha Fin")final LocalDate fechaFin,
            @ParameterLayout(named="Email")final String email
    ) {
        reservaHabitacionrepository.crearReservaDeHabitacion(fechaInicio,fechaFin,email);
    }

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Actualizar Habitaciones Disponibles"
    )
    @MemberOrder(sequence = "9")
    /**
     * Este metodo lista todas las reservas de habitaciones que finalizaron
     * y de esta forma actualizar la disponibilidad de cada uno de las Habitaciones.
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> actualizarHabitacionesDisponibles() {

        return reservaHabitacionrepository.actualizarHabitacionesDisponibles();
    }


    @javax.inject.Inject
    HabitacionRepository habitacionRepository;

    @javax.inject.Inject
    PersonaRepository personaRepository;

    @javax.inject.Inject
    ReservaHabitacionRepository reservaHabitacionrepository;
}
