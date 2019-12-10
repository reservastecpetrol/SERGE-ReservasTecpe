package domainapp.modules.simple.dom.impl.habitacion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.datanucleus.query.typesafe.TypesafeQuery;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.impl.SimpleObjects;
import domainapp.modules.simple.dom.impl.enums.EstadoHabitacion;
import domainapp.modules.simple.dom.impl.enums.ListaHabitaciones;
import domainapp.modules.simple.dom.impl.reportes.HabitacionesDisponiblesReporte;
import lombok.AccessLevel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@DomainService(
        nature = NatureOfService.DOMAIN,
      //  objectType = "simple.HabitacionMenu",
        repositoryFor = Habitacion.class
)
@DomainServiceLayout(
        named = "Habitaciones",
        menuOrder = "10"
)

/**
 *
 * Esta clase es el servicio de dominio de la clase Habitacion
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 *@author Francisco Bellani
 *
 */
public class HabitacionRepository {

    /**
     * Identificacion del nombre del icono que aparecera en la UI
     *
     * @return String
     */
    public String iconName() {
        return "Habitacion";
    }

    /**
     * Este metodo lista todos las Habitaciones que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "1")
    @Programmatic
    public List<Habitacion> listarHabitaciones() {
        return repositoryService.allInstances(Habitacion.class);
    }

    /**
     * Este metodo lista todas las Habitaciones Disponibles que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "2")
    @Programmatic
    public List<Habitacion> listarHabitacionesDisponibles() {

        return this.listarHabitacionesPorEstado(EstadoHabitacion.DISPONIBLE);
    }

    /**
     * Este metodo lista todas las Habitaciones Ocupadas que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "3")
    @Programmatic
    public List<Habitacion> listarHabitacionesOcupadas() {

        return this.listarHabitacionesPorEstado(EstadoHabitacion.OCUPADA);
    }

    /**
     * Este metodo lista todas las Habitaciones Simples que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "4")
    @Programmatic
    public List<Habitacion> listarHabitacionesSimples() {

        return this.listarHabitacionesPorCategoria(ListaHabitaciones.Simple);
    }

    /**
     * Este metodo lista todas las Habitaciones Ejecutivas que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "5")
    @Programmatic
    public List<Habitacion> listarHabitacionesEjecutivas() {

        return this.listarHabitacionesPorCategoria(ListaHabitaciones.Ejecutivas);
    }

    /**
     * Este metodo lista todas las Habitaciones Estandar que hay cargados
     * en el sistema
     *
     * @return List<Habitacion>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "6")
    @Programmatic
    public List<Habitacion> listarHabitacionesEstandar() {

        return this.listarHabitacionesPorCategoria(ListaHabitaciones.Estandar);
    }

    /**
     * Este metodo permite recuperar en una lista todos las Habitaciones
     * de tipo Simple que hay en el sistema
     *
     * @param categoria
     * @return List<Habitacion>
     */
    @Programmatic
    public List<Habitacion> listarHabitacionesPorCategoria(
            @ParameterLayout(named="Categoria")
            final ListaHabitaciones categoria
    ) {
        TypesafeQuery<Habitacion> tq = isisJdoSupport.newTypesafeQuery(Habitacion.class);
        final QHabitacion cand = QHabitacion.candidate();

        List<Habitacion> habitacion = tq.filter(
                cand.categoria.eq(tq.stringParameter("categoria")))
                .setParameter("categoria",categoria).executeList();

        return habitacion;
    }


    /**
     * Este metodo permite encontrar una Habitacion en particular
     * dado un numero que identifica de manera
     * unica a cada Habitacion
     *
     * @param nombre
     * @return List<Habitacion>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "7")
    @Programmatic
    public List<Habitacion> buscarHabitacionPorNombre(
         //   @ParameterLayout(named="Nombre")
            final String nombre
    ) {
        TypesafeQuery<Habitacion> q = isisJdoSupport.newTypesafeQuery(Habitacion.class);
        final QHabitacion cand = QHabitacion.candidate();
        q = q.filter(
                cand.nombre.indexOf(q.stringParameter("nombre")).ne(-1)
        );
        return q.setParameter("nombre", nombre)
                .executeList();
    }


    /**
     * Este metodo permite recuperar en una lista todos las Habitaciones
     * dado un estado en particular
     *
     * @param estado
     * @return List<Habitacion>
     */
    @Programmatic
    public List<Habitacion> listarHabitacionesPorEstado(
            @ParameterLayout(named="Estado")
            final EstadoHabitacion estado
    ) {
        TypesafeQuery<Habitacion> tq = isisJdoSupport.newTypesafeQuery(Habitacion.class);
        final QHabitacion cand = QHabitacion.candidate();

        List<Habitacion> habitacion = tq.filter(
                cand.estado.eq(tq.stringParameter("estado")))
                .setParameter("estado",estado).executeList();

        return habitacion;
    }

    @Programmatic
    public Habitacion verificarHabitacion(String nombre){

        TypesafeQuery<Habitacion> q = isisJdoSupport.newTypesafeQuery(Habitacion.class);
        final QHabitacion cand = QHabitacion.candidate();

        q= q.filter(
                cand.nombre.eq(q.stringParameter("nombreIngresado"))
        );
        return  q.setParameter("nombreIngresado",nombre)
                .executeUnique();
    }


    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    @Programmatic
    //@Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    //@MemberOrder(sequence = "8")
    /**
     * Este metodo permite crear la entidad de dominio Habitacion
     * con los datos que va a ingresar el usuario
     *
     * @param nombre
     * @param ubicacion
     * @param categoris
     *
     * @return Habitacion
     *
     */
    public Habitacion crearHabitacion(
           // @ParameterLayout(named="Nombre")
            final String nombre,
           // @ParameterLayout(named="Ubicacion")
            final String ubicacion,
          //  @ParameterLayout(named="Categoria")
                    ListaHabitaciones categoria
    )
    {

        Habitacion habitacion=new Habitacion();

        if (verificarHabitacion(nombre.toUpperCase())==null) {
            EstadoHabitacion estado=EstadoHabitacion.DISPONIBLE;

            String ocupante="DESOCUPADA";

            int cantidadOcupante=0;

            repositoryService.persist(habitacion=new Habitacion(nombre.toUpperCase(),ubicacion.toUpperCase(),categoria,estado,ocupante,cantidadOcupante));

        }else{
            String mensaje="Esta Habitacion ya se encuentra cargada en el sistema!";
            messageService.informUser(mensaje);
        }

        return habitacion;
    }


    private File Entrada(String nombre){
        return new File(getClass().getResource(nombre).getPath());
    }

    private String Salida(String nombre){
        String ruta = System.getProperty("user.home") + File.separatorChar + "ReservasPdf" + File.separatorChar + nombre;
        String adicion = "";
        int x = 0;
        while (ExisteArchivo(ruta, adicion)) {
            x++;
            adicion = "-" + x;
        }
        return ruta + adicion + ".pdf";
    }

    private boolean ExisteArchivo(String ruta, String adicion){
        File archivo = new File(ruta + adicion + ".pdf");
        return archivo.exists();
    }


    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@ActionLayout(named = "Exportar PDF Lista de Habitaciones Disponibles")
    //@MemberOrder(sequence = "9")
    @Programmatic
    public void generarReporteHabitacionesDisponibles(
    ) {

        List<Habitacion> habitaciones = new ArrayList<Habitacion>();

        habitaciones = repositoryService.allInstances(Habitacion.class);

        List<HabitacionesDisponiblesReporte> habitacionesDatasource = new ArrayList<HabitacionesDisponiblesReporte>();

        habitacionesDatasource.add(new HabitacionesDisponiblesReporte());


        for (Habitacion habitacion: habitaciones) {

            if(habitacion.getEstado()==EstadoHabitacion.DISPONIBLE) {

                HabitacionesDisponiblesReporte habitacionesDisponiblesReporte = new HabitacionesDisponiblesReporte();

                habitacionesDisponiblesReporte.setNombre(habitacion.getNombre());
                habitacionesDisponiblesReporte.setUbicacion(habitacion.getUbicacion());
                habitacionesDisponiblesReporte.setEstado(habitacion.getEstado());
                habitacionesDisponiblesReporte.setCategoria(habitacion.getCategoria().toString());

                habitacionesDatasource.add(habitacionesDisponiblesReporte);

            }

        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(habitacionesDatasource);

        String entrada="ListadoHabitaciones.jrxml";

        String salida="ListadoHabitaciones";

        try {

            File rutaEntrada = Entrada(entrada);
            String rutaSalida = Salida(salida);

            InputStream input = new FileInputStream(rutaEntrada);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ds", ds);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
            JasperExportManager.exportReportToPdfFile(jasperPrint,rutaSalida);

            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
            pdfExporter.exportReport();

            pdfReportStream.close();

        } catch (Exception e) {
            TranslatableString.tr("Error al mostrar el reporte: "+e);
        }
    }


    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;
}
