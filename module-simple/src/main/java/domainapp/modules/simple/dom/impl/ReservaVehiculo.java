package domainapp.modules.simple.dom.impl;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import lombok.AccessLevel;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "simple",
        table = "ReservaVehiculo",
        detachable="true"
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
                        + "FROM domainapp.modules.simple.dom.impl.ReservaVehiculo "),
        @Query(
                name = "findByFechaReservaContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.ReservaVehiculo "
                        + "WHERE fechaReserva.indexOf(:fechaReserva) >= 0 "),
        @Query(
                name = "findByFechaReserva", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.ReservaVehiculo "
                        + "WHERE fechaReserva == :fechaReserva ")
})
//@Unique(name = "ReservaVehiculo_fechaReserva_UNQ", members = { "fechaReserva" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@lombok.Getter @lombok.Setter
public class ReservaVehiculo implements Comparable<ReservaVehiculo> {

    //, CalendarEventable

    @javax.jdo.annotations.Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    @Title(prepend = "Reserva: ")
    private LocalDate fechaReserva;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private LocalDate fechaInicio;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private LocalDate fechaFin;

    @javax.jdo.annotations.Column(allowsNull = "false",name="PERSONA_ID")
    @Persistent(defaultFetchGroup="true")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private Persona persona;

    @javax.jdo.annotations.Column(allowsNull = "false",name="VEHICULO_ID")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private Vehiculo vehiculo;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @lombok.NonNull
    @Property(editing = Editing.ENABLED)
    private String estado;//ACTIVA | CANCELADA | ARRIBADA


    public ReservaVehiculo(){}

    public ReservaVehiculo(LocalDate fechaReserva,LocalDate fechaInicio,LocalDate fechaFin,Persona persona,Vehiculo vehiculo,String estado){
        this.fechaReserva=fechaReserva;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
        this.persona=persona;
        this.vehiculo=vehiculo;
        this.estado=estado;
    }

    public void cancelar()
    {
        estado = "CANCELADA";
    }

    public void arribar()
    {
        estado = "ARRIBADA";
    }

    /*
    @Programmatic
    @Override
    public String getCalendarName() {

        return  getPersona().getJerarquia().name();
    }

    @Programmatic
    public String getNotes() {

        return persona.getNombre() + persona.getApellido();

    }

    @Programmatic
    @Override
    public CalendarEvent toCalendarEvent() {

        return new CalendarEvent(getFechaInicio().toDateTimeAtStartOfDay(), getCalendarName(), getNotes());
    }
   */

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final ReservaVehiculo other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "fechaReserva");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "fechaReserva");
    }
    //endregion

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
