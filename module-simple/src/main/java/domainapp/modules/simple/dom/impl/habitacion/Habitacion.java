package domainapp.modules.simple.dom.impl.habitacion;

import java.util.List;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import domainapp.modules.simple.dom.impl.enums.EstadoHabitacion;
import domainapp.modules.simple.dom.impl.enums.ListaHabitaciones;
import domainapp.modules.simple.dom.impl.reservaHabitacion.ReservaHabitacion;
import domainapp.modules.simple.dom.impl.reservaHabitacion.ReservaHabitacionRepository;
import lombok.AccessLevel;
import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "simple",
        table = "Habitacion"
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@Queries({
        @Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.habitacion.Habitacion "
                        + "ORDER BY nombre ASC"),
        @Query(
                name = "findBynombreContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.habitacion.Habitacion "
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),
        @Query(
                name = "findBynumerohabitacion", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.habitacion.Habitacion "
                        + "WHERE nombre == :nombre ")
})
@Unique(name = "Habitacion_nombre_UNQ", members = { "nombre" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@lombok.Getter @lombok.Setter

/**
 * Esta clase define la entidad de dominio Habitacion
 * con todas sus propiedades.
 * Ademas de metodos que realizan
 * Validacion de propiedades
 * Actualizacion de propiedades
 * Eliminar una entidad de Habitacion
 * Un metodo Constructor
 *Metodos para modificar los estados de la entidad Habitacion
 *
 *
 * @author Francisco Bellani
 *
 */
public class Habitacion implements Comparable<Habitacion> {

    /**
     * Identificacion del nombre del icono que aparecera en la UI
     *
     * @return String
     */
    public String iconName() {
        return "Habitacion";
    }

    //Definicion de las propiedades de la entidad Habitacion

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @Title(prepend = "Habitacion: ")
    private String nombre;  //esta variable hace referencia al numero que identifica a la entidad Habitacion

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String ubicacion;   //esta variable hace referencia a la ubicacion en que se encuentra la entidad Habitacion

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    // esta variable hace referencia al estado en el que se encuentra la entidad Habiatcion
    // los cuales pueden ser :
    // DISPONIBLE|| OCUPADA || MANTENIMIENTO|| INACTIVA
    private EstadoHabitacion estado;


    // esta variable hace referencia al tipo de categoria a la que pertenece la entidad Habitacion
    // Ejecutivas || Estandar || Simple
    @javax.jdo.annotations.Column(allowsNull="true")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private ListaHabitaciones categoria;
    public ListaHabitaciones getCategoria() {
        return categoria;
    }
    public void setCategoria(final ListaHabitaciones categoria) {
        this.categoria = categoria;
    }


    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String ocupante;   //esta variable hace referencia al ocupante de la entidad Habitacion

    @javax.jdo.annotations.Column()
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private int cantidadOcupante;   //esta variable hace referencia al ocupante de la entidad Habitacion


    public Habitacion(){}

    /**
     * Este es un metodo constructor
     *
     *
     * @param nombre -valor ingresado por el usuario
     * @param ubicacion -valor ingresado por el usuario
     * @param categoria -valor ingresado por el usuario
     * @param estado  -valor definido en el codigo
     * @param ocupante  -valor definido en el codigo
     * @param cantidadOcupante  -valor definido en el codigo
     */
    public Habitacion(String nombre,String ubicacion, ListaHabitaciones categoria,EstadoHabitacion estado,String ocupante,int cantidadOcupante){
        this.nombre=nombre;
        this.ubicacion=ubicacion;
        this.categoria=categoria;
        this.estado=estado;
        this.ocupante=ocupante;
        this.cantidadOcupante=cantidadOcupante;
        }


    /**
     * Este metodo realiza la actualizacion de la variable nombre
     *
      * @param nombre
     * @return Habitacion
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "nombre")
    public Habitacion editarNombre(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "nombre")
            final String nombre) {
        setNombre(nombre);
        return this;
    }


    /**
     * Este metodo realiza la actualizacion de la variable ubicacion
     *
     * @param ubicacion
     * @return Habitacion
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith ="ubicacion")
    public Habitacion editarUbicacion(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Ubicacion")
            final String ubicacion) {
        setUbicacion(ubicacion);
        return this;
    }


    /**
     * Este metodo realiza la actualizacion de la variable categoria
     *
     * @param categoria
     * @return Habitacion
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "categoria")
    public ListaHabitaciones editarCategoria(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Categorias")
            final ListaHabitaciones categoria) {
        setCategoria(categoria);
        return categoria;
    }


    /**
     * Este metodo realiza la actualizacion de la variable estado
     *
     * @param estado
     * @return Habitacion
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith ="estado")
    public Habitacion editarEstado(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "estado")
            final EstadoHabitacion estado) {
        setEstado(estado);
        return this;
    }

    /**
     * Este metodo realiza la actualizacion de la variable ocupante
     *
     * @param ocupante
     * @return Habitacion
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith ="ocupante")
    public Habitacion editarOcupante(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "ocupante")
            final String ocupante) {
        setOcupante(ocupante);
        return this;
    }

    /**
     * Este metodo realiza la actualizacion de la variable cantidadOcupante
     *
     * @param cantidadOcupante
     * @return Habitacion
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith ="cantidadOcupante")
    public Habitacion editarCantidadOcupante(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "cantidadOcupante")
            final int cantidadOcupante) {
        setCantidadOcupante(cantidadOcupante);
        return this;
    }
    
    public String default0EditarNombre() {
        return getNombre();
    }


    /**
     * Este metodo realiza la validacion de la variable nombre
     * de forma que el usuario no ingrese un signo de exclamacion
     *
     * @param nombre
     * @return TranslatableString
     */
    public TranslatableString validate0EditarNombre(final String nombre) {
        return nombre != null && nombre.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    public String default0EditarUbicacion() {
        return getUbicacion();
    }


    /**
     * Este metodo realiza la validacion de la variable ubicacion
     * de forma que el usuario no ingrese un signo de exclamacion
     *
     * @param ubicacion
     * @return TranslatableString
     */
    public TranslatableString validate0EditarUbicacion(final String ubicacion) {
        return ubicacion != null && ubicacion.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    /**
     * Este metodo permite generar el listado de reservas de habitaciones dado un nombre de habitacion
     *
     * @return List<ReservaHabitacion>
     */
    @NotPersistent
    @CollectionLayout(named = "Reservas Realizadas con esta Habitacion")
    public List<ReservaHabitacion> getReservasHabitacion(){
        return reservaHabitacionRepository.listarReservasPorNumeroDeHabitacion(this.getNombre());
    }

    /**
     * Este metodo permite eliminar la entidad de Habitacion
     */
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void eliminar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' FUE ELIMINADA", title));
        repositoryService.remove(this);
    }

    @Override
    public String toString() {
        return getNombre()+" "+getUbicacion();
    }

    public int compareTo(final Habitacion other) {
        return ComparisonChain.start()
                .compare(this.getNombre(), other.getUbicacion())
                .result();
    }

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    RepositoryService repositoryService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    TitleService titleService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    ReservaHabitacionRepository reservaHabitacionRepository;
}
