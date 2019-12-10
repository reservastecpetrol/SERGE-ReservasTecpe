package domainapp.modules.simple.dom.impl.vehiculo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.datanucleus.query.typesafe.TypesafeQuery;

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
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.impl.SimpleObjects;
import domainapp.modules.simple.dom.impl.enums.EstadoVehiculo;
import domainapp.modules.simple.dom.impl.reportes.VehiculosDisponiblesReporte;
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
        repositoryFor = Vehiculo.class
)
@DomainServiceLayout(
        named = "Vehiculos",
        menuOrder = "10"
)

/**
 *
 * Esta clase es el servicio de dominio de la clase Vehiculo
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 *@author Cintia Millacura
 *
 */
public class VehiculoRepository {

    /**
     * Identificacion del nombre del icono que aparecera en la UI
     *
     * @return String
     */
    public String iconName() {
        return "Vehiculo";
    }

    /**
     * Este metodo lista todos los Vehiculos que hay cargados
     * en el sistema
     *
     * @return List<Vehiculo>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "1")
    @Programmatic
    public List<Vehiculo> listarVehiculos() {
        return repositoryService.allInstances(Vehiculo.class);
    }


    /**
     * Este metodo lista todos los Vehiculos Disponibles que hay cargados
     * en el sistema
     *
     * @return List<Vehiculo>
     */
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Vehiculo> listarVehiculosDisponibles() {

        return this.listarVehiculosPorEstado(EstadoVehiculo.DISPONIBLE);
    }

    /**
     * Este metodo lista todos los Vehiculos Ocupados que hay cargados
     * en el sistema
     *
     * @return List<Vehiculo>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "3")
    @Programmatic
    public List<Vehiculo> listarVehiculosOcupados() {

        return this.listarVehiculosPorEstado(EstadoVehiculo.OCUPADO);
    }


    /**
     * Este metodo permite encontrar un Vehiculo en particular
     * dada una matricula que es la que identifica de manera
     * unica a cada Vehiculo
     *
     * @param matricula
     * @return List<Vehiculo>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "4")
    @Programmatic
    public List<Vehiculo> buscarVehiculoPorMatricula(
            final String matricula
    ) {
        TypesafeQuery<Vehiculo> q = isisJdoSupport.newTypesafeQuery(Vehiculo.class);
        final QVehiculo cand = QVehiculo.candidate();
        q = q.filter(
                cand.matricula.indexOf(q.stringParameter("matricula")).ne(-1)
        );
        return q.setParameter("matricula", matricula)
                .executeList();
    }


    /**
     * Este metodo permite recuperar en una lista todos los Vehiculos
     * dado un estado en particular
     *
     * @param estado
     * @return List<Vehiculo>
     */
    @Programmatic
    public List<Vehiculo> listarVehiculosPorEstado(
            @ParameterLayout(named="Estado")
            final EstadoVehiculo estado
    ) {
        TypesafeQuery<Vehiculo> tq = isisJdoSupport.newTypesafeQuery(Vehiculo.class);
        final QVehiculo cand = QVehiculo.candidate();

        List<Vehiculo> vehiculos = tq.filter(
                cand.estado.eq(tq.stringParameter("estado")))
                .setParameter("estado",estado).executeList();

        return vehiculos;
    }

    @Programmatic
    public Vehiculo verificarVehiculo(String matricula){

        TypesafeQuery<Vehiculo> q = isisJdoSupport.newTypesafeQuery(Vehiculo.class);
        final QVehiculo cand = QVehiculo.candidate();

        q= q.filter(
                cand.matricula.eq(q.stringParameter("matriculaIngresada"))
        );
        return  q.setParameter("matriculaIngresada",matricula)
                .executeUnique();
    }

    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {}
    //@Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    //@MemberOrder(sequence = "5")
    @Programmatic
    /**
     * Este metodo permite crear la entidad de dominio Vehiculo
     * con los datos que va a ingresar el usuario
     *
     *
     * @param matricula
     * @param marca
     * @param color
     * @param modelo
     * @param combustible
     * @param seguro
     * @param ubicacion
     *
     * @return Vehiculo
     */
    public Vehiculo crearVehiculo(
            final String matricula,
            final String marca,
            final String color,
            final String modelo,
            final boolean combustible,
            final boolean seguro,
            final String ubicacion
    )
    {
        Vehiculo vehiculo=new Vehiculo();

        if (verificarVehiculo(matricula.toUpperCase())==null) {
            EstadoVehiculo estado=EstadoVehiculo.DISPONIBLE;

            repositoryService.persist(vehiculo=new Vehiculo(matricula.toUpperCase(),marca.toUpperCase(),color.toUpperCase(),modelo.toUpperCase(),combustible,seguro,ubicacion.toUpperCase(),estado));

        }else{
            String mensaje="Este Vehiculo ya se encuentra cargado en el sistema!";
            messageService.informUser(mensaje);
        }

        return vehiculo;
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
    //@ActionLayout(named = "Exportar PDF Lista de Vehiculos Disponibles")
    //@MemberOrder(sequence = "6")
    @Programmatic
    public void generarReporteVehiculosDisponibles(
    ) {

        List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();

        vehiculos = repositoryService.allInstances(Vehiculo.class);

        List<VehiculosDisponiblesReporte> vehiculosDatasource = new ArrayList<VehiculosDisponiblesReporte>();

        vehiculosDatasource.add(new VehiculosDisponiblesReporte());


        for (Vehiculo vehiculo: vehiculos) {

            if(vehiculo.getEstado()== EstadoVehiculo.DISPONIBLE) {

                VehiculosDisponiblesReporte vehiculosDisponiblesReporte = new VehiculosDisponiblesReporte();

                vehiculosDisponiblesReporte.setMatricula(vehiculo.getMatricula());
                vehiculosDisponiblesReporte.setMarca(vehiculo.getMarca());
                vehiculosDisponiblesReporte.setModelo(vehiculo.getModelo());
                vehiculosDisponiblesReporte.setUbicacion(vehiculo.getUbicacion());
                vehiculosDisponiblesReporte.setEstado(vehiculo.getEstado().toString());

                vehiculosDatasource.add(vehiculosDisponiblesReporte);

            }

        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(vehiculosDatasource);

        String entrada="ListadoDeVehiculos.jrxml";

        String salida="ListadoVehiculos";

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
