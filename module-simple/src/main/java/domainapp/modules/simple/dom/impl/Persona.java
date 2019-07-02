package domainapp.modules.simple.dom.impl;

import java.util.Date;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
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
                        + "FROM domainapp.modules.simple.dom.impl.Persona "),
        @Query(
                name = "findByNombreContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.Persona "
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),
        @Query(
                name = "findByNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.Persona "
                        + "WHERE nombre == :nombre ")
})
@Unique(name = "Persona_nombre_UNQ", members = { "nombre" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor
public class Persona implements Comparable<Persona> {

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @Title(prepend = "Persona: ")
    private String nombre;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @Title()
    private String apellido;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String direccion;

    @javax.jdo.annotations.Column()
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String telefono;

    @javax.jdo.annotations.Column()
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String email;

    @javax.jdo.annotations.Column()
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String dni;
    //@javax.jdo.annotations.Column()
    //@lombok.NonNull
    // @Property(editing = Editing.ENABLED)
    // private Date fechaNacimiento;

    //listado de Jerarquias dropdown menu
    private ListaJerarquias jerarquia;

    @javax.jdo.annotations.Column(allowsNull="true")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    public ListaJerarquias getJerarquia() {
        return jerarquia;
    }
    public void setJerarquias(final ListaJerarquias jerarquia) {
        this.jerarquia = jerarquia;
    }

    Persona(String nombre,String apellido,String direccion,String telefono,String email,String dni,ListaJerarquias jerarquias){
        this.nombre=nombre;
        this.apellido=apellido;
        this.direccion=direccion;
        this.telefono=telefono;
        this.email=email;
        this.dni=dni;
        this.jerarquia=jerarquias;
    }

    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "nombre")
    public Persona updateNombre(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre")
            final String nombre) {
        setNombre(nombre);
        return this;
    }


    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "apellido")
    public Persona updateApellido(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Apellido")
            final String apellido) {
        setApellido(apellido);
        return this;
    }
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "direccion")
    public Persona updateDireccion(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Direccion")
            final String direccion) {
        setDireccion(direccion);
        return this;
    }
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "telefono")
    public Persona updateTelefono(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Telefono")
            final String telefono) {
        setTelefono(telefono);
        return this;
    }
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "email")
    public Persona updateEmail(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Email")
            final String email) {
        setEmail(email);
        return this;
    }
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "dni")
    public Persona updateDni(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Dni")
            final String dni) {
        setEmail(dni);
        return this;
    }
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "jerarquia")
    public ListaJerarquias updateJerarquia(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Jerarquias")
            final ListaJerarquias jerarquia) {
        setJerarquias(jerarquia);
        return jerarquia;
    }


    public String default0UpdateNombre() {
        return getNombre();
    }

    public TranslatableString validate0UpdateNombre(final String nombre) {
        return nombre != null && nombre.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    public String default0UpdateApellido() {
        return getApellido();
    }

    public TranslatableString validate0UpdateApellido(final String apellido) {
        return apellido != null && apellido.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }
    public TranslatableString validate0UpdateDireccion(final String direccion) {
        return direccion != null && direccion.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }
    public TranslatableString validate0UpdateTelefono(final String telefono) {
        return telefono != null && telefono.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
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


}
