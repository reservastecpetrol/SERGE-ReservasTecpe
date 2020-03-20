package domainapp.modules.simple.dom.impl.vehiculo;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Vehiculo",
        repositoryFor = Vehiculo.class
)
@DomainServiceLayout(
        named = "Vehiculo",
        menuOrder = "10"
)
/**
 *
 * Esta clase es el servicio de dominio Menu de la clase Vehiculo
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 *@author Cintia Millacura
 *
 */
public class VehiculoMenu {

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Vehiculos"
    )
    @MemberOrder(sequence = "1")
    /**
     * Este metodo lista todos los Vehiculos que hay cargados
     * en el sistema
     *
     * @return List<Vehiculo>
     */
    public List<Vehiculo> listarVehiculos() {
        return vehiculorepository.listarVehiculos();
    }


    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Vehiculos Disponibles"
    )
    @MemberOrder(sequence = "2")
    /**
     * Este metodo lista todos los Vehiculos Disponibles que hay cargados
     * en el sistema
     *
     * @return List<Vehiculo>
     */
    public List<Vehiculo> listarVehiculosDisponibles() {
        return vehiculorepository.listarVehiculosDisponibles();
    }


    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Vehiculos Ocupados"
    )
    @MemberOrder(sequence = "3")
    /**
     * Este metodo lista todos los Vehiculos Ocupados que hay cargados
     * en el sistema
     *
     * @return List<Vehiculo>
     */
    public List<Vehiculo> listarVehiculosOcupados() {
        return vehiculorepository.listarVehiculosOcupados();
    }


    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Buscar Vehiculo por Matricula"
    )
    @MemberOrder(sequence = "4")
    /**
     * Este metodo permite encontrar un Vehiculo en particular
     * dada una matricula que es la que identifica de manera
     * unica a cada Vehiculo
     *
     * @param matricula
     * @return List<Vehiculo>
     */
    public List<Vehiculo> buscarVehiculoPorMatricula(
            @ParameterLayout(named="Matricula")
            final String matricula
    ) {

        return vehiculorepository.buscarVehiculoPorMatricula(matricula);
    }


    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Crear Vehiculo"
    )
    @MemberOrder(sequence = "5")
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
    public void crearVehiculo(
            @Parameter(
                    regexPattern = "[a-z]{2} [0-9]{3} [a-z]{2}",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese formato AB 123 CD"
            )
            @ParameterLayout(named="Matricula") final String matricula,
            @ParameterLayout(named="Marca")final String marca,
            @Parameter(
                    regexPattern = "[A-Za-z ]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named="Color")final String color,
            @Parameter(
                    regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named="Modelo") final String modelo,
            @ParameterLayout(named="Combustible") final boolean combustible,
            @ParameterLayout(named="Seguro") final boolean seguro,
            @ParameterLayout(named="Ubicacion")final String ubicacion
    ) {
        vehiculorepository.crearVehiculo(matricula,marca,color,modelo,combustible,seguro,ubicacion);
    }


    @javax.inject.Inject
    VehiculoRepository vehiculorepository;
}
