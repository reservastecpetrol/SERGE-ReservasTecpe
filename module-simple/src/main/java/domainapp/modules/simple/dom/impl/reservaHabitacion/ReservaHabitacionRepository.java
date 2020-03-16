package domainapp.modules.simple.dom.impl.reservaHabitacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.datanucleus.query.typesafe.TypesafeQuery;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dom.impl.SimpleObjects;
import domainapp.modules.simple.dom.impl.enums.EstadoHabitacion;
import domainapp.modules.simple.dom.impl.enums.EstadoReserva;
import domainapp.modules.simple.dom.impl.enums.ListaHabitaciones;
import domainapp.modules.simple.dom.impl.enums.TipoSexo;
import domainapp.modules.simple.dom.impl.habitacion.Habitacion;
import domainapp.modules.simple.dom.impl.habitacion.HabitacionRepository;
import domainapp.modules.simple.dom.impl.persona.Persona;
import domainapp.modules.simple.dom.impl.persona.PersonaRepository;
import domainapp.modules.simple.dom.impl.reportes.EjecutarReportes;
import domainapp.modules.simple.dom.impl.reservaVehiculo.QReservaVehiculo;
import lombok.AccessLevel;
import net.sf.jasperreports.engine.JRException;

@DomainService(
        nature = NatureOfService.DOMAIN,
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


    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "1")
    @Programmatic
    /**
     * Este metodo lista todas las reservas de habitaciones que hay cargados
     * en el sistema
     *
     * @return List<ReservaHabitacion>
     */
    public java.util.List<ReservaHabitacion> listarReservasDeHabitaciones() {
        return container.allInstances(ReservaHabitacion.class);
    }

    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "2")
    @Programmatic
    /**
     * Este metodo lista todos las Reservas Activas que hay cargados
     * en el sistema
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> listarReservasDeHabitacionesActivas()  {
        return this.listarReservasPorEstado(EstadoReserva.ACTIVA);
    }

    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "3")
    @Programmatic
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

    /**
     * Este metodo permite encontrar todas las reservas
     * realizadas por un usuario en particular
     *
     * @param persona
     * @return List<ReservaHabitacion>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "4")
    @Programmatic
    public List<ReservaHabitacion> listarReservasPorPersona(
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


    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "5")
    @Programmatic
    /**
     * Este metodo lista todas las reservas de habitaciones que hay cargados
     * en el sistema que inician en el dia de la fecha
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

    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "6")
    @Programmatic
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

    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "7")
    @Programmatic
    /**
     * Este metodo permite listar todas las reservas de habitaciones
     * dada una fecha de reserva
     *
     * @param fechaReseva
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> buscarReservasPorFechaDeReserva(
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

    @Programmatic
    public Persona recuperarPersonaPorEmail(String email){

        Persona persona=new Persona();

        boolean band=false;

        List<Persona> listaPersonas=new ArrayList<Persona>();

        listaPersonas=personaRepository.listarPersonas();

        int i=0;

        while(i<listaPersonas.size()&&(!band)){

            Persona aux=listaPersonas.get(i);

            if(aux.getEmail().equals(email)){
                persona=aux;
                band=true;
            }
            i++;
        }

        if(band==false){
            persona=null;
        }

        return persona;
    }

    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    //@Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    //@MemberOrder(sequence = "8")
    @Programmatic
    /**
     * Este metodo permite crear la entidad de dominio ReservaHabitacion
     * con los datos que va a ingresar el usuario
     *
     * @param fechaInicio
     * @param fechaFin
     * @param email
     *
     */
    public void crearReservaDeHabitacion(

            final LocalDate fechaInicio,
            final LocalDate fechaFin,
            final String email
    )
    {
        Persona persona=new Persona();

        persona=recuperarPersonaPorEmail(email);

        if(persona!=null) {

            ReservaHabitacion reservaHabitacion=new ReservaHabitacion();

            //Se obtiene la jerarquia que posse la persona ingresada
            String jerarquia = persona.getJerarquia().toString();

            //Se obtiene el sexo de la Persona ingresada
            TipoSexo sexo = persona.getSexo();
            //Se crea una lista de tipo habitacion
            List<Habitacion> lista = new ArrayList<Habitacion>();

            List<Habitacion> listaSimples = new ArrayList<Habitacion>();

            List<Habitacion> listaEstandares = new ArrayList<Habitacion>();

            Habitacion habitacion = new Habitacion();

            //Se recupera la lista de habitaciones correspondientes a una jerarquia en particular
            lista = this.listaHabitacionesPorJerarquia(jerarquia);

            int dimension = lista.size();

            if (dimension >= 1) {

                if ((jerarquia == "Supervisores") || (jerarquia == "Operadores")) {

                    listaSimples = this.listaHabitacionSimple(lista);

                    listaEstandares = this.listaHabitacionEstandar(lista);

                    if (listaSimples.size() >= 1) {

                        habitacion = (Habitacion) listaSimples.get(0);

                        habitacion.setEstado(EstadoHabitacion.OCUPADA);

                        habitacion.setOcupante(sexo.toString());

                        habitacion.setCantidadOcupante(1);

                        reservaHabitacion.setFechaReserva(LocalDate.now());
                        reservaHabitacion.setFechaInicio(fechaInicio);
                        reservaHabitacion.setFechaFin(fechaFin);
                        reservaHabitacion.setPersona(persona);
                        reservaHabitacion.setHabitacion(habitacion);
                        reservaHabitacion.setEstado(EstadoReserva.ACTIVA);

                        repositoryService.persist(reservaHabitacion);

                    } else {
                        if (listaEstandares.size() >= 1) {

                            habitacion = this.asignaHabitacionEstandarPersona(listaEstandares, sexo);

                            if (habitacion != null) {
                                if (habitacion.getCantidadOcupante() == 1) {
                                    habitacion.setEstado(EstadoHabitacion.DISPONIBLE);

                                } else {
                                    habitacion.setEstado(EstadoHabitacion.OCUPADA);

                                }

                                reservaHabitacion.setFechaReserva(LocalDate.now());
                                reservaHabitacion.setFechaInicio(fechaInicio);
                                reservaHabitacion.setFechaFin(fechaFin);
                                reservaHabitacion.setPersona(persona);
                                reservaHabitacion.setHabitacion(habitacion);
                                reservaHabitacion.setEstado(EstadoReserva.ACTIVA);

                                repositoryService.persist(reservaHabitacion);

                            } else {
                                String mensaje = "No hay Habitaciones Disponibles para realizar reservas";
                                messageService.informUser(mensaje);
                            }
                        } else {
                            String mensaje = "No hay Habitaciones Disponibles para realizar reservas";
                            messageService.informUser(mensaje);
                        }
                    }

                } else {
                    habitacion = (Habitacion) lista.get(0);

                    habitacion.setEstado(EstadoHabitacion.OCUPADA);

                    habitacion.setOcupante(sexo.toString());

                    habitacion.setCantidadOcupante(1);

                    reservaHabitacion.setFechaReserva(LocalDate.now());
                    reservaHabitacion.setFechaInicio(fechaInicio);
                    reservaHabitacion.setFechaFin(fechaFin);
                    reservaHabitacion.setPersona(persona);
                    reservaHabitacion.setHabitacion(habitacion);
                    reservaHabitacion.setEstado(EstadoReserva.ACTIVA);

                    repositoryService.persist(reservaHabitacion);
                }
            } else {
                String mensaje = "No hay Habitaciones Disponibles";
                messageService.informUser(mensaje);
            }

        }else{
            String mensaje = "No hay usuario para el correo Ingresado";
            messageService.informUser(mensaje);
        }
    }


    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@ActionLayout(named = "Exportar PDF Lista de Resevas Activas")
    //@MemberOrder(sequence = "9")
    @Programmatic
    public Blob generarReporteReservasHabitacionesActivas (
    ) throws JRException, IOException {

        List<ReservaHabitacion> reservaHabitaciones = new ArrayList<ReservaHabitacion>();

        reservaHabitaciones = repositoryService.allInstances(ReservaHabitacion.class);

        EjecutarReportes ejecutarReportes=new EjecutarReportes();

        return ejecutarReportes.ListadoReservasHabitacionesPDF(reservaHabitaciones);

    }

    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "10")
    @Programmatic
    /**
     * Este metodo lista todas las reservas de habitaciones que finalizaron
     * y de esta forma actualizar la disponibilidad de cada uno de las Habitaciones.
     *
     * @return List<ReservaHabitacion>
     */
    public List<ReservaHabitacion> actualizarHabitacionesDisponibles() {

        List<ReservaHabitacion> reservas=new ArrayList<ReservaHabitacion>();

        LocalDate fechaAyer=LocalDate.now().minusDays(1);

        TypesafeQuery<ReservaHabitacion> q = isisJdoSupport.newTypesafeQuery(ReservaHabitacion.class);

        final QReservaHabitacion cand = QReservaHabitacion.candidate();

        reservas = q.filter(
                cand.fechaFin.eq((LocalDate)fechaAyer))
                .executeList();
        return reservas;
    }

    @javax.inject.Inject
    EjecutarReportes ejecutarReportes;

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