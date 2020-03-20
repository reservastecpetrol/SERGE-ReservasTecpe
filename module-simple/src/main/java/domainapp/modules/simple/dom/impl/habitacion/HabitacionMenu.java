package domainapp.modules.simple.dom.impl.habitacion;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import domainapp.modules.simple.dom.impl.enums.ListaHabitaciones;
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Habitacion",
        repositoryFor = Habitacion.class
)
@DomainServiceLayout(
        named = "Habitacion",
        menuOrder = "10"
)
/**
 *
 * Esta clase es el servicio de dominio Menu de la clase Habitacion
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 *@author Cintia Millacura
 *
 */
public class HabitacionMenu {

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Habitaciones"
    )
    @MemberOrder(sequence = "1")
    /**
     * Este metodo lista todos las Habitaciones que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    public List<Habitacion> listarHabitaciones() {
        return habitacionrepository.listarHabitaciones();
    }


    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Habitaciones Disponibles"
    )
    @MemberOrder(sequence = "2")
    /**
     * Este metodo lista todas las Habitaciones Disponibles que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    public List<Habitacion> listarHabitacionesDisponibles() {
        return habitacionrepository.listarHabitacionesDisponibles();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Habitaciones Ocupadas"
    )
    @MemberOrder(sequence = "3")
    /**
     * Este metodo lista todas las Habitaciones Ocupadas que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    public List<Habitacion> listarHabitacionesOcupadas() {
        return habitacionrepository.listarHabitacionesOcupadas();
    }


    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Habitaciones Simples"
    )
    @MemberOrder(sequence = "4")
    /**
     * Este metodo lista todas las Habitaciones Simples que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    public List<Habitacion> listarHabitacionesSimples() {
        return habitacionrepository.listarHabitacionesSimples();
    }


    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Habitaciones Ejecutivas"
    )
    @MemberOrder(sequence = "5")
    /**
     * Este metodo lista todas las Habitaciones Ejecutivas que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    public List<Habitacion> listarHabitacionesEjecutivas(){
        return habitacionrepository.listarHabitacionesEjecutivas();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Habitaciones Estandar"
    )
    @MemberOrder(sequence = "6")
    /**
     * Este metodo lista todas las Habitaciones Estandar que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    public List<Habitacion> listarHabitacionesEstandar(){
        return habitacionrepository.listarHabitacionesEstandar();
    }


    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Buscar Habitacion por Numero"
    )
    @MemberOrder(sequence = "7")
    /**
     * Este metodo permite encontrar una Habitacion en particular
     * dado un numero que identifica de manera
     * unica a cada Habitacion
     *
     * @param nombre
     * @return List<Habitacion>
     */
    public List<Habitacion> buscarHabitacionPorNombre(
            @ParameterLayout(named="Nombre")
            final String nombre){
        return habitacionrepository.buscarHabitacionPorNombre(nombre);
    }


    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Crear Habitacion"
    )
    @MemberOrder(sequence = "8")
    /**
     * Este metodo permite crear la entidad de dominio Habitacion
     * con los datos que va a ingresar el usuario
     *
     * @param nombre
     * @param ubicacion
     * @param categoria
     *
     * @return Habitacion
     *
     */
    public void crearHabitacion(
            @ParameterLayout(named="Numero") final String nombre,
            @ParameterLayout(named="Ubicacion")final String ubicacion,
            @ParameterLayout(named="Categoria") ListaHabitaciones categoria
    ){
        habitacionrepository.crearHabitacion(nombre,ubicacion,categoria);
    }


    @javax.inject.Inject
    HabitacionRepository habitacionrepository;
}
