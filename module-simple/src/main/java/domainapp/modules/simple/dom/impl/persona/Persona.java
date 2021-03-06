package domainapp.modules.simple.dom.impl.persona;

import java.util.List;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
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

import domainapp.modules.simple.dom.impl.enums.ListaJerarquias;
import domainapp.modules.simple.dom.impl.enums.TipoSexo;
import domainapp.modules.simple.dom.impl.reservaHabitacion.ReservaHabitacion;
import domainapp.modules.simple.dom.impl.reservaHabitacion.ReservaHabitacionRepository;
import domainapp.modules.simple.dom.impl.reservaVehiculo.ReservaVehiculo;
import domainapp.modules.simple.dom.impl.reservaVehiculo.ReservaVehiculoRepository;
import lombok.AccessLevel;
import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;


@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "simple",
        table = "Persona"
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
                        + "FROM domainapp.modules.simple.dom.impl.persona.Persona "
                        + "ORDER BY apellido ASC, nombre ASC"),
        @Query(
                name = "findByNombreContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.persona.Persona "
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),
        @Query(name = "findByNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.persona.Persona "
                        + "WHERE nombre == :nombre ")
})
//Se comenta de forma que permita crear personas con el mismo nombre
//@Unique(name = "Persona_nombre_UNQ", members = { "nombre" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@lombok.Getter @lombok.Setter
//@lombok.RequiredArgsConstructor
/**
 * Esta clase define la entidad de dominio Persona
 * con todas sus propiedades
 * Ademas de metodos que realizan
 * Validacion
 * Actualizacion
 * Eliminacion
 * Ademas de un metodo Constructor
 *
 * @author Francisco Bellani
 *
 */
public class Persona implements Comparable<Persona> {

    /**
     * Identificacion del nombre del icono que aparecera en la UI
     *
     * @return String
     */
    public String iconName() {
        return "Persona";
    }

    //Definicion de las propiedades de la entidad Persona

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @Title(prepend = "Persona: ")
    private String nombre; //esta variable hace referencia al nombre de la entidad Persona

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @Title()
    private String apellido; //esta variable hace referencia al apellido de la entidad Persona

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String direccion; //esta variable hace referencia a la dirección de la entidad Persona

    @javax.jdo.annotations.Column(allowsNull = "false", length =10)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String telefono; //esta variable hace referencia al telefono de la entidad Persona

    @javax.jdo.annotations.Column()
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String email; //esta variable hace referencia a la direccion de email de la entidad Persona

    @javax.jdo.annotations.Column(allowsNull ="false", length = 8)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String dni; //esta variable hace referencia al numero de documento (DNI) de la entidad Persona

    //listado de Jerarquias dropdown menu
    @javax.jdo.annotations.Column(allowsNull="false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private ListaJerarquias jerarquia; //esta variable hace referencia al tipo de jerarquia de la entidad Persona

    public ListaJerarquias getJerarquia() {
        return jerarquia;
    }
    public void setJerarquias(final ListaJerarquias jerarquia) {
        this.jerarquia = jerarquia;
    }

    //Esta variable hace referencia al sexo de la Persona
    @javax.jdo.annotations.Column(allowsNull="false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private TipoSexo sexo;

    public Persona(){}

    /**
     * Este es un metodo constructor
     *
     * @param nombre
     * @param apellido
     * @param direccion
     * @param telefono
     * @param email
     * @param dni
     * @param jerarquias
     */
    public Persona(String nombre,String apellido,String direccion,String telefono,String email,String dni,ListaJerarquias jerarquias,TipoSexo sexo){
        this.nombre=nombre;
        this.apellido=apellido;
        this.direccion=direccion;
        this.telefono=telefono;
        this.email=email;
        this.dni=dni;
        this.jerarquia=jerarquias;
        this.sexo=sexo;
    }

    /**
     * Este metodo realiza la actualizacion de la variable nombre de la entidad Persona
     *
     * @param nombre
     * @return Persona
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "nombre")
    public Persona editarNombre(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre")
            final String nombre) {
        setNombre(nombre);
        return this;
    }

    /**
     * Este metodo realiza la actualizacion de la variable apellido de la entidad Persona
     *
     * @param apellido
     * @return Persona
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "apellido")
    public Persona editarApellido(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Apellido")
            final String apellido) {
        setApellido(apellido);
        return this;
    }

    /**
     * Este metodo realiza la actualizacion de la variable direccion de la entidad Persona
     *
     * @param direccion
     * @return Persona
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "direccion")
    public Persona editarDireccion(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Direccion")
            final String direccion) {
        setDireccion(direccion);
        return this;
    }

    /**
     * Este metodo realiza la actualizacion de la variable telefono de la entidad Persona
     *
     * @param telefono
     * @return Persona
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "telefono")
    public Persona editarTelefono(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Telefono")
            final String telefono) {
        setTelefono(telefono);
        return this;
    }

    /**
     * Este metodo realiza la actualizacion de la variable email de la entidad Persona
     *
     * @param email
     * @return Persona
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "email")
    public Persona editarEmail(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Email")
            final String email) {
        setEmail(email);
        return this;
    }

    
    /**
     * Este metodo realiza la actualizacion de la variable dni de la entidad Persona
     *
     * @param dni
     * @return Persona
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "dni")
    public Persona editarDni(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Dni")
            final String dni) {
        setEmail(dni);
        return this;
    }

    /**
     * Este metodo realiza la actualizacion de la variable jerarquia de la entidad Persona
     *
     * @param jerarquia
     * @return Persona
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "jerarquia")
    public ListaJerarquias editarJerarquia(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Jerarquias")
            final ListaJerarquias jerarquia) {
        setJerarquias(jerarquia);
        return jerarquia;
    }

    /**
     * Este metodo realiza la actualizacion de la variable sexo de la entidad Persona
     *
     * @param sexo
     * @return Persona
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "sexo")
    public TipoSexo editarSexo(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Sexo")
            final TipoSexo sexo) {
        setSexo(sexo);
        return sexo;
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


    public String default0EditarApellido() {
        return getApellido();
    }


    /**
     * Este metodo realiza la validacion de la variable apellido
     * de forma que el usuario no ingrese un signo de exclamacion
     *
     * @param apellido
     * @return TranslatableString
     */
    public TranslatableString validate0EditarApellido(final String apellido) {
        return apellido != null && apellido.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }


    /**
     * Este metodo realiza la validacion de la variable direccion
     * de forma que el usuario no ingrese un signo de exclamacion
     *
     * @param direccion
     * @return TranslatableString
     */
    public TranslatableString validate0EditarDireccion(final String direccion) {
        return direccion != null && direccion.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    /**
     * Este metodo realiza la validacion de la variable telefono
     * de forma que el usuario no ingrese un signo de exclamacion
     *
     * @param telefono
     * @return TranslatableString
     */
    public TranslatableString validate0EditarTelefono(final String telefono) {
        return telefono != null && telefono.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    /**
     * Este metodo permite generar el listado de reservas de habitaciones dado un dni
     *
     * @return List<ReservaHabitacion>
     */
    @NotPersistent
    @CollectionLayout(named = "Reservas Habitaciones Realizadas")
    public List<ReservaHabitacion> getReservasHabitacion(){
        return reservaHabitacionRepository.listarReservasPorDni(this.getDni());
    }

    /**
     * Este metodo permite generar el listado de reservas de vehiculos dado un dni
     *
     * @return List<ReservaVehiculo>
     */
    @NotPersistent
    @CollectionLayout(named = "Reservas Vehiculos Realizadas")
    public List<ReservaVehiculo> getReservasVehiculos(){
        return reservaVehiculoRepository.listarReservasPorDni(this.getDni());
    }


    /**
     * Este metodo permite eliminar la entidad de Persona
     */
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void eliminar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' FUE ELIMINADA", title));
        repositoryService.remove(this);
    }


    @Override
    public String toString() {
        return getNombre()+" "+getApellido();
    }

    public int compareTo(final Persona other) {
        return ComparisonChain.start()
                .compare(this.getNombre(), other.getNombre())
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
    ReservaVehiculoRepository reservaVehiculoRepository;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    ReservaHabitacionRepository reservaHabitacionRepository;

}
