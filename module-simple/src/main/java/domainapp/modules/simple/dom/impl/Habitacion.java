package domainapp.modules.simple.dom.impl;

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
                        + "FROM domainapp.modules.simple.dom.impl.Habitacion "),
        @Query(
                name = "findBynombreContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.Habitacion "
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),
        @Query(
                name = "findBynumerohabitacion", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.Habitacion "
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
public class Habitacion implements Comparable<Habitacion> {

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @Title(prepend = "Habitacion: ")
    private String nombre;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String ubicacion;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String estado;
    //listado de categorias de habitacion dropdown menu
    private ListaHabitaciones categoria;
    @javax.jdo.annotations.Column(allowsNull="true")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    public ListaHabitaciones getCategoria() {
        return categoria;
    }
    public void setCategoria(final ListaHabitaciones categoria) {
        this.categoria = categoria;
    }


    Habitacion(String nombre,String ubicacion, ListaHabitaciones categoria,String estado){
        this.nombre=nombre;
        this.ubicacion=ubicacion;
        this.categoria=categoria;
        this.estado=estado;
        }

    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "nombre")
    public Habitacion updateNombre(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "nombre")
            final String nombre) {
        setNombre(nombre);
        return this;
    }
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith ="ubicacion")
    public Habitacion updateUbicacion(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Ubicacion")
            final String ubicacion) {
        setUbicacion(ubicacion);
        return this;
    }

    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "categoria")
    public ListaHabitaciones updateCategoria(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Categorias")
            final ListaHabitaciones categoria) {
        setCategoria(categoria);
        return categoria;
    }

    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith ="estado")
    public Habitacion updateEstado(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "estado")
            final String estado) {
        setEstado(estado);
        return this;
    }


    public String default0UpdateNombre() {
        return getNombre();
    }

    public TranslatableString validate0UpdateNombre(final String nombre) {
        return nombre != null && nombre.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    public String default0UpdateUbicacion() {
        return getUbicacion();
    }

    public TranslatableString validate0UpdateUbicacion(final String ubicacion) {
        return ubicacion != null && ubicacion.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    public TranslatableString validate0UpdateEstado(final String estado) {
        return estado != null && estado.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
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

}
