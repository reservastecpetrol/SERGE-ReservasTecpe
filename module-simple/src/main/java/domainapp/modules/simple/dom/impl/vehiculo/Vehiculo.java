package domainapp.modules.simple.dom.impl.vehiculo;

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

import domainapp.modules.simple.dom.impl.enums.EstadoVehiculo;
import domainapp.modules.simple.dom.impl.reservaVehiculo.ReservaVehiculo;
import domainapp.modules.simple.dom.impl.reservaVehiculo.ReservaVehiculoRepository;
import lombok.AccessLevel;
import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;


@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "simple",
        table = "Vehiculo"
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
                        + "FROM domainapp.modules.simple.dom.impl.vehiculo.Vehiculo "
                        + "ORDER BY matricula ASC"),
        @Query(
                name = "findByMatriculaContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.vehiculo.Vehiculo "
                        + "WHERE matricula.indexOf(:matricula) >= 0 "),
        @Query(
                name = "findByMatricula", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.vehiculo.Vehiculo "
                        + "WHERE matricula == :matricula ")
})
@Unique(name = "Vehiculo_matricula_UNQ", members = { "matricula" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@lombok.Getter @lombok.Setter
/**
 * Esta clase define la entidad de dominio Vehiculo
 * con todas sus propiedades.
 * Ademas de metodos que realizan
 * Validacion de propiedades
 * Actualizacion de propiedades
 * Eliminar una entidad de Vehiculo
 * Un metodo Constructor
 *Metodos para modificar los estados de la entidad Vehiculo
 *
 *
 * @author Cintia Millacura
 *
 */
public class Vehiculo implements Comparable<Vehiculo> {

    /**
     * Identificacion del nombre del icono que aparecera en la UI
     *
     * @return String
     */
    public String iconName() {
        return "Vehiculo";
    }

    //Definicion de las propiedades de la entidad Vehiculo

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @Title(prepend = "Vehiculo: ")
    private String matricula; //esta variable hace referencia a la matricula de la entidad Vehiculo

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String marca; // esta variable hace referencia a la marca de la entidad Vehiculo

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String color; // esta variable hace referencia al color de la entidad Vehiculo

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String modelo; // esta variable hace referencia al modelo de la entidad Vehiculo

    @javax.jdo.annotations.Column()
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private boolean combustible; // esta variable booleana hace referencia a si la entidad Vehiculo cuenta con combustible

    @javax.jdo.annotations.Column()
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private boolean seguro; // esta variable booleana hace referencia a si la entidad Vehiculo cuenta con seguro


    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String ubicacion; //esta variable hace referencia a la ubicacion de la entidad Vehiculo


    @javax.jdo.annotations.Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    // esta variable hace referencia al estado en el que se encuentra la entidad Vehiculo
    // los cuales pueden ser :
    // DISPONIBLE|| OCUPADO || REPARACION || INACTIVO
    private EstadoVehiculo estado;


    public Vehiculo(){}

    /**
    *Este es un metodo constructor
    *
    *@param matricula -valor ingresado por el usuario
     *@param marca -valor ingresado por el usuario
     *@param color  -valor ingresado por el usuario
     *@param modelo  -valor ingresado por el usuario
     *@param combustible -valor ingresado por el usuario
     *@param seguro -valor ingresado por el usuario
     *@param ubicacion -valor ingresado por el usuario
     *@param estado  -valor definido en el codigo
    *
    */
    public Vehiculo(String matricula,String marca,String color,String modelo,boolean combustible,boolean seguro,String ubicacion,EstadoVehiculo estado){
        this.matricula=matricula;
        this.marca=marca;
        this.color=color;
        this.modelo=modelo;
        this.combustible=combustible;
        this.seguro=seguro;
        this.ubicacion=ubicacion;
        this.estado=estado;
    }


    /**
     *
     * Este metodo realiza la actualizacion de la variable matricula
     *
     * @param matricula
     * @return Vehiculo
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "matricula")
    public Vehiculo editarMatricula(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Matricula")
            final String matricula) {
        setMatricula(matricula);
        return this;
    }


    /**
     *
     * Este metodo realiza la actualizacion de la variable marca
     *
     * @param marca
     * @return Vehiculo
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "marca")
    public Vehiculo editarMarca(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Marca")
            final String marca) {
        setMarca(marca);
        return this;
    }


    /**
     *
     * Este metodo realiza la actualizacion de la variable modelo
     *
     * @param modelo
     * @return Vehiculo
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "modelo")
    public Vehiculo editarModelo(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Modelo")
            final String modelo) {
        setModelo(modelo);
        return this;
    }


    /**
     *
     * Este metodo realiza la actualizacion de la variable color
     *
     * @param color
     * @return Vehiculo
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "color")
    public Vehiculo editarColor(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Color")
            final String color) {
        setColor(color);
        return this;
    }


    /**
     *
     * Este metodo realiza la actualizacion de la variable combustible
     *
     * @param combustible
     * @return Vehiculo
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "combustible")
    public Vehiculo editarCombustible(
            @ParameterLayout(named = "Combustible")
            final boolean combustible) {
        setCombustible(combustible);
        return this;
    }


    /**
     *
     * Este metodo realiza la actualizacion de la variable seguro
     *
     * @param seguro
     * @return Vehiculo
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "seguro")
    public Vehiculo editarSeguro(
            @ParameterLayout(named = "Seguro")
            final boolean seguro) {
        setSeguro(seguro);
        return this;
    }


    /**
     *
     * Este metodo realiza la actualizacion de la variable ubicacion
     *
     * @param ubicacion
     * @return Vehiculo
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith ="ubicacion")
    public Vehiculo editarUbicacion(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Ubicacion")
            final String ubicacion) {
        setUbicacion(ubicacion);
        return this;
    }


    /**
     * Este metodo realiza la actualizacion de la variable estado
     *
     * @param estado
     * @return Vehiculo
     */
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith ="estado")
    public Vehiculo editarEstado(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "estado")
            final EstadoVehiculo estado) {
        setEstado(estado);
        return this;
    }


    public String default0EditarMatricula() {
        return getMatricula();
    }


    /**
     * Este metodo realiza la validacion de la variable matricula
     * de forma que el usuario no ingrese un signo de exclamacion
     *
     * @param matricula
     * @return TranslatableString
     */
    public TranslatableString validate0EditarMatricula(final String matricula) {
        return matricula != null && matricula.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    public String default0EditarMarca() {
        return getMarca();
    }


    /**
     * Este metodo realiza la validacion de la variable marca
     * de forma que el usuario no ingrese un signo de exclamacion
     *
     * @param marca
     * @return TranslatableString
     */
    public TranslatableString validate0EditarMarca(final String marca) {
        return marca != null && marca.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }


    /**
     * Este metodo realiza la validacion de la variable color
     * de forma que el usuario no ingrese un signo de exclamacion
     *
     * @param color
     * @return TranslatableString
     */
    public TranslatableString validate0EditarColor(final String color) {
        return color != null && color.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }


    /**
     * Este metodo realiza la validacion de la variable modelo
     * de forma que el usuario no ingrese un signo de exclamacion
     *
     * @param modelo
     * @return TranslatableString
     */
    public TranslatableString validate0EditarModelo(final String modelo) {
        return modelo != null && modelo.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
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
     * Este metodo permite generar el listado de reservas de vehiculos dada una matricula
     *
     * @return List<ReservaVehiculo>
     */
    @NotPersistent
    @CollectionLayout(named = "Reservas Realizadas con este Vehiculo")
    public List<ReservaVehiculo> getReservasVehiculos(){
        return reservaVehiculoRepository.listarReservasPorMatricula(this.getMatricula());
    }

    /**
     * Este metodo permite eliminar la entidad de Vehiculo
     */
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void eliminar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' FUE ELIMINADO", title));
        repositoryService.remove(this);
    }


    @Override
    public String toString() {
        return getMatricula()+" "+getMarca();
    }

    public int compareTo(final Vehiculo other) {
        return ComparisonChain.start()
                .compare(this.getMatricula(), other.getMatricula())
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
}
