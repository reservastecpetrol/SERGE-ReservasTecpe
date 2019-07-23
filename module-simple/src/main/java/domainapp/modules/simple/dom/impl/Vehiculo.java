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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
import org.apache.isis.schema.utils.jaxbadapters.PersistentEntityAdapter;

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
                        + "FROM domainapp.modules.simple.dom.impl.Vehiculo "),
        @Query(
                name = "findByMatriculaContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.Vehiculo "
                        + "WHERE matricula.indexOf(:matricula) >= 0 "),
        @Query(
                name = "findByMatricula", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.Vehiculo "
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
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
public class Vehiculo implements Comparable<Vehiculo> {

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @Title(prepend = "Vehiculo: ")
    private String matricula;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String marca;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String color;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String modelo;

    @javax.jdo.annotations.Column()
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private boolean combustible;

    @javax.jdo.annotations.Column()
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private boolean seguro;


    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String ubicacion;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String estado;

    public Vehiculo(String matricula,String marca,String color,String modelo,boolean combustible,boolean seguro,String ubicacion,String estado){
        this.matricula=matricula;
        this.marca=marca;
        this.color=color;
        this.modelo=modelo;
        this.combustible=combustible;
        this.seguro=seguro;
        this.ubicacion=ubicacion;
        this.estado=estado;
    }

    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "matricula")
    public Vehiculo updateMatricula(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Matricula")
            final String matricula) {
        setMatricula(matricula);
        return this;
    }


    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "marca")
    public Vehiculo updateMarca(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Marca")
            final String marca) {
        setMarca(marca);
        return this;
    }
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "modelo")
    public Vehiculo updateModelo(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Modelo")
            final String modelo) {
        setModelo(modelo);
        return this;
    }
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "color")
    public Vehiculo updateColor(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Color")
            final String color) {
        setColor(color);
        return this;
    }
    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "combustible")
    public Vehiculo updateCombustible(
            @ParameterLayout(named = "Combustible")
            final boolean combustible) {
        setCombustible(combustible);
        return this;
    }

    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "seguro")
    public Vehiculo updateSeguro(
            @ParameterLayout(named = "Seguro")
            final boolean seguro) {
        setSeguro(seguro);
        return this;
    }


    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith ="ubicacion")
    public Vehiculo updateUbicacion(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Ubicacion")
            final String ubicacion) {
        setUbicacion(ubicacion);
        return this;
    }

    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith ="estado")
    public Vehiculo updateEstado(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "estado")
            final String estado) {
        setEstado(estado);
        return this;
    }


    public String default0UpdateMatricula() {
        return getMatricula();
    }

    public TranslatableString validate0UpdateMatricula(final String matricula) {
        return matricula != null && matricula.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    public String default0UpdateMarca() {
        return getMarca();
    }

    public TranslatableString validate0UpdateMarca(final String marca) {
        return marca != null && marca.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }
    public TranslatableString validate0UpdateColor(final String color) {
        return color != null && color.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }
    public TranslatableString validate0UpdateModelo(final String modelo) {
        return modelo != null && modelo.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
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

}
