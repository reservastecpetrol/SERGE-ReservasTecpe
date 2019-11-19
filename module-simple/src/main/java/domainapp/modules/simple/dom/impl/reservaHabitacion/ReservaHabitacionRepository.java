package domainapp.modules.simple.dom.impl.reservaHabitacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.datanucleus.query.typesafe.TypesafeQuery;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.impl.SimpleObjects;
import domainapp.modules.simple.dom.impl.enums.EstadoHabitacion;
import domainapp.modules.simple.dom.impl.enums.EstadoReserva;
import domainapp.modules.simple.dom.impl.enums.ListaHabitaciones;
import domainapp.modules.simple.dom.impl.enums.TipoSexo;
import domainapp.modules.simple.dom.impl.habitacion.Habitacion;
import domainapp.modules.simple.dom.impl.habitacion.HabitacionRepository;
import domainapp.modules.simple.dom.impl.persona.Persona;
import domainapp.modules.simple.dom.impl.persona.PersonaRepository;
import domainapp.modules.simple.dom.impl.reservaVehiculo.QReservaVehiculo;
import lombok.AccessLevel;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.ReservaHabitacionMenu",
        repositoryFor = ReservaHabitacion.class
)
@DomainServiceLayout(
        named = "Reservas Habitacion",
        menuOrder = "10"
)

/**
 * Esta clase es el servicio de dominio de la clase ReservaHabitacion
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 * @author Francisco Bellani
 */
public class ReservaHabitacionRepository {

    /**
     * Identificacion del nombre del icono que aparecera en la UI
     *
     * @return String
     */
    public String iconName() {
        return "Reserva";
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    /**
     * Este metodo lista todas las reservas de habitaciones que hay cargados
     * en el sistema
     *
     * @return List<ReservaHabitacion>
     */
    public java.util.List<ReservaHabitacion> listarReservasDeHabitaciones() {
        return container.allInstances(ReservaHabitacion.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    /**
     * Este metodo lista todos las Reservas Activas que hay cargados
     * en el sistema
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> listarReservasDeHabitacionesActivas()  {
        return this.listarReservasPorEstado(EstadoReserva.ACTIVA);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
    /**
     * Este metodo lista todos las Reservas Canceladas que hay cargados
     * en el sistema
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> listarReservasDeHabitacionesCanceladas() {
        return this.listarReservasPorEstado(EstadoReserva.CANCELADA);
    }

    /**
     * Este metodo permite recuperar en una lista todos las reservas realizadas
     * dado un estado en particular
     *
     * @param estado
     * @return List<ReservaHabitacion>
     */
    @Programmatic
    public List<ReservaHabitacion> listarReservasPorEstado(
            @ParameterLayout(named="Estado")
            final EstadoReserva estado
    ) {
        TypesafeQuery<ReservaHabitacion> tq = isisJdoSupport.newTypesafeQuery(ReservaHabitacion.class);
        final QReservaVehiculo cand = QReservaVehiculo.candidate();

        List<ReservaHabitacion> reservas = tq.filter(
                cand.estado.eq(tq.stringParameter("estado")))
                .setParameter("estado",estado).executeList();

        return reservas;
    }


    @Programmatic
    /**
     * Este metodo lista todos los usuarios que hay en el sistema de
     * forma que el administrador seleccione a uno en especifico
     *
     * @return Collection<Persona>
     *
     */
    public Collection<Persona> choices0ListarReservasPorPersona() {
        return personaRepository.listarPersonas();
    }

    /**
     * Este metodo permite encontrar todas las reservas
     * realizadas por un usuario en particular
     *
     * @param persona
     * @return List<ReservaVehiculo>
     */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "4")
    public List<ReservaHabitacion> listarReservasPorPersona(
            @ParameterLayout(named="Persona")
            final Persona persona
    ) {

        List<ReservaHabitacion> reservas;

        TypesafeQuery<ReservaHabitacion> q = isisJdoSupport.newTypesafeQuery(ReservaHabitacion.class);

        final QReservaVehiculo cand = QReservaVehiculo.candidate();

        reservas= q.filter(
                cand.persona.dni.eq(q.stringParameter("dniIngresado")))
                .setParameter("dniIngresado",persona.getDni())
                .executeList();

        return reservas;
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "5")
    /**
     * Este metodo lista todas las reservas de habitaciones que hay cargados
     * en el sistema en el dia de la fecha
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> listarReservasQueInicianHoy() {

        List<ReservaHabitacion> reservas;

        TypesafeQuery<ReservaHabitacion> q = isisJdoSupport.newTypesafeQuery(ReservaHabitacion.class);

        final QReservaHabitacion cand = QReservaHabitacion.candidate();

        reservas = q.filter(
                cand.fechaInicio.eq(LocalDate.now()))
                .executeList();
        return reservas;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "6")
    /**
     * Este metodo lista todas las reservas de habitaciones que hay cargados
     * en el sistema que finalizan en el dia de la fecha
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> listarReservasQueFinalizanHoy() {

        List<ReservaHabitacion> reservas;

        TypesafeQuery<ReservaHabitacion> q = isisJdoSupport.newTypesafeQuery(ReservaHabitacion.class);

        final QReservaHabitacion cand = QReservaHabitacion.candidate();

        reservas = q.filter(
                cand.fechaFin.eq(LocalDate.now()))
                .executeList();
        return reservas;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "7")
    /**
     * Este metodo permite listar todas las reservas de habitaciones
     * dada una fecha de reserva
     *
     * @param fechaReseva
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> buscarReservasPorFechaDeReserva(
            @ParameterLayout(named="Fecha Reserva")
            final LocalDate fechaReserva
    ) {
        List<ReservaHabitacion> reservas;

        TypesafeQuery<ReservaHabitacion> q = isisJdoSupport.newTypesafeQuery(ReservaHabitacion.class);

        final QReservaHabitacion cand = QReservaHabitacion.candidate();

        reservas = q.filter(
                cand.fechaReserva.eq(q.stringParameter("fecha")))
                .setParameter("fecha",fechaReserva)
                .executeList();
        return reservas;
    }



    @Programmatic
    /**
     * Este metodo lista todos los usuarios que hay en el sistema de
     * forma que el administrador seleccione a uno en especifico
     *
     * @return Collection<Persona>
     *
     */
    public Collection<Persona> choices2CreateCrearReservaDeHabitacion() {
        return personaRepository.listarPersonas();
    }



    @Programmatic
    /**
     * Este metodo realiza la validacion del ingreso de la fecha de inicio
     *
     * @param fechaInicio
     * @return String
     */
    public String validate0CrearReservaDeHabitacion(final LocalDate fechaInicio){

        String validacion="";

        if (fechaInicio.isBefore(LocalDate.now())) {
            validacion="Una Reserva no puede empezar en el pasado";
        }

        return validacion;
    }


    @Programmatic
    /**
     *Este metodo realiza la validacion del ingreso de la fecha en que finalizaria la reserva
     *
     * @param fechaInicio
     * @param fechaFin
     *
     * @return String
     *
     */
    public String validate1CrearReservaDeHabitacion(final LocalDate fechaInicio,final LocalDate fechaFin){

        String validacion="";

        if (fechaFin.isBefore(LocalDate.now())) {
            validacion="Una Reserva no puede finalizar en el pasado";
        }else {
            if (fechaFin.isBefore(fechaInicio)) {
                validacion = "Una Reserva no puede finalizar antes de la fecha de Inicio";
            }
        }

        return validacion;
    }

    @Programmatic
    /**
     *Este metodo genera el listado de las Habitaciones dada una Jerarquia en particular
     *
     * @param jerarquia
     *
     * @return List<Habitacion>
     *
     */
    public List<Habitacion> listaHabitacionesPorJerarquia(String jerarquia){

        List<Habitacion> lista=habitacionRepository.listarHabitacionesPorEstado(EstadoHabitacion.DISPONIBLE);

        List<Habitacion> listaEjecutivos=new ArrayList<Habitacion>();

        List<Habitacion> listaSuperOper=new ArrayList<Habitacion>();

        List<Habitacion> listaHabitacionesPorJerarquia=new ArrayList<Habitacion>();

        int i=0;

        if(jerarquia=="Ejecutivos"){

            while (i<lista.size()){

                Habitacion habitacion=new Habitacion();

                habitacion=(Habitacion) lista.get(i);

                if(habitacion.getCategoria()== ListaHabitaciones.Ejecutivas){
                    listaEjecutivos.add(habitacion);
                }
                i++;
            }
            listaHabitacionesPorJerarquia=listaEjecutivos;

        }else{

            if((jerarquia=="Supervisores")||(jerarquia=="Operadores")){

                while (i<lista.size()){

                    Habitacion habitacion=new Habitacion();

                    habitacion=(Habitacion) lista.get(i);

                    if ((habitacion.getCategoria()== ListaHabitaciones.Estandar)|| (habitacion.getCategoria()== ListaHabitaciones.Simple)){
                        listaSuperOper.add(habitacion);
                    }

                    i++;
                }
                listaHabitacionesPorJerarquia=listaSuperOper;
            }
        }
        return listaHabitacionesPorJerarquia;
    }

    @Programmatic
    /**
     * Este metodo permite generar el listado de habitaciones simples que hay registradas en el sistema
     *
     * @return List<Habitacion>
     */
    public List<Habitacion> listaHabitacionSimple(List<Habitacion> lista){

        List<Habitacion> listaSimples=new ArrayList<Habitacion>();

        int i=0;

        while (i<lista.size()){

            Habitacion habitacion=new Habitacion();

            habitacion=(Habitacion) lista.get(i);

            if(habitacion.getCategoria()== ListaHabitaciones.Simple){
                listaSimples.add(habitacion);
            }

            i++;
        }

        return listaSimples;
    }

    @Programmatic
    /**
     * Este metodo permite generar el listado de habitaciones Estandar que hay registradas en el sistema
     *
     * @return List<Habitacion>
     */
    public List<Habitacion> listaHabitacionEstandar(List<Habitacion> lista){

        List<Habitacion> listaEstandar=new ArrayList<Habitacion>();

        int i=0;

        while (i<lista.size()){

            Habitacion habitacion=new Habitacion();

            habitacion=(Habitacion) lista.get(i);

            if(habitacion.getCategoria()== ListaHabitaciones.Estandar){
                listaEstandar.add(habitacion);
            }

            i++;
        }

        return listaEstandar;
    }

    @Programmatic
    /**
     *Este metodo asigna la Habitacion Estandar correspondiente a una persona dependiendo su sexo
     *
     * @param listaEstandares
     * @param sexo
     *
     * @return Habitacion
     *
     */
    public Habitacion asignaHabitacionEstandarPersona(List<Habitacion> listaEstandares, TipoSexo sexo){

        int i=0,pos=0;

        Habitacion habitacion=new Habitacion();

        boolean reserva=false;

        String ocupante;

        while (i<listaEstandares.size()&&(!reserva)){

            habitacion=(Habitacion)listaEstandares.get(i);

            ocupante=habitacion.getOcupante();

            if (ocupante.equals("DESOCUPADA")){
                habitacion.setOcupante(sexo.toString());
                habitacion.setCantidadOcupante(1);
                reserva=true;
                pos=i;
            }else{
                if(ocupante.equals(sexo.toString())){
                    habitacion.setCantidadOcupante(2);
                    reserva=true;
                    pos=i;
                }
            }
            i++;
        }
        if(reserva==false){
            habitacion=null;
        }else{
            habitacion=(Habitacion)listaEstandares.get(pos);
        }

        return habitacion;
    }


    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    @Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    @MemberOrder(sequence = "8")
    /**
     * Este metodo permite crear la entidad de dominio ReservaHabitacion
     * con los datos que va a ingresar el usuario
     *
     * @param fechaInicio
     * @param fechaFin
     * @param persona
     *
     */
    public void crearReservaDeHabitacion(

            @ParameterLayout(named="Fecha Inicio")final LocalDate fechaInicio,
            @ParameterLayout(named="Fecha Fin")final LocalDate fechaFin,
            @ParameterLayout(named="Persona")final Persona persona
    )
    {
        ReservaHabitacion reservaHabitacion=new ReservaHabitacion();

        int i=habitacionRepository.listarHabitacionesPorEstado(EstadoHabitacion.DISPONIBLE).size();

        if(i>=1) {

            Habitacion habitacion=habitacionRepository.listarHabitacionesPorEstado(EstadoHabitacion.DISPONIBLE).get(0);

            habitacion.setEstado(EstadoHabitacion.OCUPADA);

            reservaHabitacion.setFechaReserva(LocalDate.now());
            reservaHabitacion.setFechaInicio(fechaInicio);
            reservaHabitacion.setFechaFin(fechaFin);
            reservaHabitacion.setPersona(persona);
            reservaHabitacion.setHabitacion(habitacion);
            reservaHabitacion.setEstado(EstadoReserva.ACTIVA);

            repositoryService.persist(reservaHabitacion);


        }else {
            String mensaje="No hay Habitaciones Disponibles";
            messageService.informUser(mensaje);
        }

    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;

    @javax.inject.Inject
    PersonaRepository personaRepository;

    @javax.inject.Inject
    HabitacionRepository habitacionRepository;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

}