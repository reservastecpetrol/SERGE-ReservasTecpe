package domainapp.modules.simple.dom.impl.reportes;

import java.io.IOException;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dom.impl.habitacion.HabitacionRepository;
import domainapp.modules.simple.dom.impl.persona.PersonaRepository;
import domainapp.modules.simple.dom.impl.reservaHabitacion.ReservaHabitacionRepository;
import domainapp.modules.simple.dom.impl.reservaVehiculo.ReservaVehiculoRepository;
import domainapp.modules.simple.dom.impl.vehiculo.VehiculoRepository;
import net.sf.jasperreports.engine.JRException;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "ReporteMenu"
)
@DomainServiceLayout(
        named = "Reportes",
        menuOrder = "10"
)

public class ReporteMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Reporte de Personas"
    )
    @MemberOrder(sequence = "1")
    /**
     * Este metodo genera el reporte de todos las personas que hay registradas
     * en el sistema.
     *
     */
    public Blob  generarReportePersonas(
    ) throws JRException, IOException {
        return personarepository.generarReportePersonas();
    }


    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Reporte de Habitaciones Disponibles"
    )
    @MemberOrder(sequence = "2")
    /**
     * Este metodo permite generar un reporte en formato PDF del listado de las Habitaciones Disponibles
     * en total que hay registrados en el sistema
     *
     */
    public Blob generarReporteHabitacionesDisponibles(
    ) throws JRException, IOException {
        return habitacionrepository.generarReporteHabitacionesDisponibles();

    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Reporte de Vehiculos Disponibles"
    )
    @MemberOrder(sequence = "3")
    /**
     * Este metodo permite generar un reporte en formato PDF del listado de Vehiculos Disponibles
     * en total que hay registrados en el sistema
     *
     */
    public Blob generarReporteVehiculosDisponibles()throws JRException, IOException {

        return vehiculorepository.generarReporteVehiculosDisponibles();

    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Reporte Listado de Reservas Activas Habitaciones"
    )
    @MemberOrder(sequence = "4")
    /**
     * Este metodo permite generar un reporte en formato PDF del listado de Reservas de Habitaciones Activas
     * en total que hay registrados en el sistema
     *
     */
    public Blob generarReporteReservasHabitacionesActivas() throws JRException, IOException {

        return reservaHabitacionrepository.generarReporteReservasHabitacionesActivas();

    }



    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Reporte Listado de Reservas Activas Vehiculos"
    )
    @MemberOrder(sequence = "5")
    /**
     * Este metodo permite generar un reporte en formato PDF del listado de Reservas de Vehiculos Activas
     * en total que hay registrados en el sistema
     *
     */
    public Blob generarReporteReservasVehiculosActivas() throws JRException, IOException {

        return reservaVehiculorepository.generarReporteReservasVehiculosActivas();

    }

    @javax.inject.Inject
    ReservaVehiculoRepository reservaVehiculorepository;

    @javax.inject.Inject
    ReservaHabitacionRepository reservaHabitacionrepository;

    @javax.inject.Inject
    VehiculoRepository vehiculorepository;

    @javax.inject.Inject
    HabitacionRepository habitacionrepository;

    @javax.inject.Inject
    PersonaRepository personarepository;
}
