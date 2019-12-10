package domainapp.modules.simple.dom.impl.persona;

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
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.impl.SimpleObjects;
import domainapp.modules.simple.dom.impl.enums.ListaJerarquias;
import domainapp.modules.simple.dom.impl.enums.TipoSexo;
import domainapp.modules.simple.dom.impl.reportes.PersonasReporte;
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
     //   objectType = "Persona",
        repositoryFor = Persona.class
)
//@DomainServiceLayout(
//        named = "Personas",
//        menuOrder = "10"
//)

/**
 * Esta clase es el servicio de dominio de la clase Persona
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 * @author Francisco Bellani
 *
 */
public class PersonaRepository {

    /**
     * Identificacion del nombre del icono que aparecera en la UI
     *
     * @return String
     */
    public String iconName() {
        return "Persona";
    }

    /**
     * Este metodo lista todos las personas que hay registradas
     * en el sistema
     *
     * @return List<Persona>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "1")
    @Programmatic
    public List<Persona> listarPersonas() {
        return repositoryService.allInstances(Persona.class);
    }

    /**
     * Este metodo lista todas las personas con jerarquia de Ejecutivos que hay cargados
     * en el sistema
     *
     * @return List<Persona>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "2")
    @Programmatic
    public List<Persona> listarPersonasEjecutivas() {

        return this.listarPersonasPorJerarquia(ListaJerarquias.Ejecutivos);
    }

    /**
     * Este metodo lista todas las personas con jerarquia de Supervisores que hay cargados
     * en el sistema
     *
     * @return List<Persona>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "3")
    @Programmatic
    public List<Persona> listarPersonasSupervisores() {

        return this.listarPersonasPorJerarquia(ListaJerarquias.Supervisores);
    }

    /**
     * Este metodo lista todas las personas con jerarquia de Operadores que hay cargados
     * en el sistema
     *
     * @return List<Persona>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "4")
    @Programmatic
    public List<Persona> listarPersonasOperadores() {

        return this.listarPersonasPorJerarquia(ListaJerarquias.Operadores);
    }

    /**
     * Este metodo permite recuperar en una lista todas las Personas
     * dado una jerarquia en particular
     *
     * @param jerarquia
     * @return List<Persona>
     */
    @Programmatic
    public List<Persona> listarPersonasPorJerarquia(
            @ParameterLayout(named = "Jerarquia") final ListaJerarquias jerarquia
    ) {
        TypesafeQuery<Persona> tq = isisJdoSupport.newTypesafeQuery(Persona.class);
        final QPersona cand = QPersona.candidate();

        List<Persona> personas = tq.filter(
                cand.jerarquia.eq(tq.stringParameter("jerarquia")))
                .setParameter("jerarquia", jerarquia).executeList();

        return personas;
    }

    /**
     * Este metodo permite encontrar una Persona en particular
     * dado un nombre
     *
     * @param nombre
     * @return List<Persona>
     */
    //@Action(semantics = SemanticsOf.SAFE)
    //@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    //@MemberOrder(sequence = "5")
    @Programmatic
    public List<Persona> buscarPersonaPorNombre(
            //@ParameterLayout(named = "Nombre")
            final String nombre
    ) {
        TypesafeQuery<Persona> q = isisJdoSupport.newTypesafeQuery(Persona.class);
        final QPersona cand = QPersona.candidate();
        q = q.filter(
                cand.nombre.indexOf(q.stringParameter("nombre")).ne(-1)
        );
        return q.setParameter("nombre", nombre.toUpperCase())
                .executeList();
    }

    @Programmatic
    public Persona verificarUsuario(String dni) {

        TypesafeQuery<Persona> q = isisJdoSupport.newTypesafeQuery(Persona.class);
        final QPersona cand = QPersona.candidate();

        q = q.filter(
                cand.dni.eq(q.stringParameter("dniIngresado"))
        );
        return q.setParameter("dniIngresado", dni)
                .executeUnique();
    }

    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {
    }
    @Programmatic
    //@Action(domainEvent = SimpleObjects.CreateDomainEvent.class)
    //@MemberOrder(sequence = "6")
    /**
     * Este metodo permite crear la entidad de dominio Persona
     * con los datos que va a ingresar el usuario
     *
     *
     * @param nombre
     * @param apellido
     * @param direccion
     * @param telefono
     * @param email
     * @param dni
     * @param jerarquias
     *
     *
     * @return Persona
     */
    public Persona crearPersona(
            //@Parameter(
            //        regexPattern = "[A-Za-z ]+",
            //        regexPatternFlags = Pattern.CASE_INSENSITIVE,
            //        regexPatternReplacement = "Ingrese dato correcto"
           // )
           // @ParameterLayout(named = "Nombre")
            final String nombre,
          /*  @Parameter(
                    regexPattern = "[A-Za-z ]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named = "Apellido") */
            final String apellido,
            /*@Parameter(
                    regexPattern = "[A-Za-z ]+[0-9]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named = "Direccion")*/
            final String direccion,
            /*@Parameter(
                    regexPattern = "[0-9]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named = "Telefono")*/
            final String telefono,
            /*@Parameter(
                    regexPattern = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese una dirección de correo electrónico válida (contienen un símbolo '@') -"
            )
            @ParameterLayout(named = "Email")*/
            final String email,
            /*@Parameter(
                    regexPattern = "[0-9]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named = "Dni")*/
            final String dni,
            //@ParameterLayout(named = "Jerarquia")
                    ListaJerarquias jerarquias,
            //@ParameterLayout(named = "Sexo")
                    TipoSexo sexo

    ) {
        Persona persona=new Persona();

        if (verificarUsuario(dni) == null) {

             persona=new Persona(nombre.toUpperCase(), apellido.toUpperCase(), direccion.toUpperCase(), telefono, email,
                    dni, jerarquias, sexo);

            repositoryService.persist(persona);

        } else {
            String mensaje = "Este Usuario ya se encuentra cargado en el sistema!";
            messageService.informUser(mensaje);
        }

        return persona;
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


    @Programmatic
    public void generarReportePersonas(
    ) {

        List<Persona> personas = new ArrayList<Persona>();

        List<PersonasReporte> personasDatasource = new ArrayList<PersonasReporte>();

        personasDatasource.add(new PersonasReporte());

        personas = repositoryService.allInstances(Persona.class);

        for (Persona per : personas) {

            PersonasReporte personasReporte = new PersonasReporte();

            personasReporte.setNombre(per.getNombre());
            personasReporte.setApellido(per.getApellido());
            personasReporte.setDireccion(per.getDireccion());
            personasReporte.setTelefono(per.getTelefono());
            personasReporte.setEmail(per.getEmail());
            personasReporte.setDni(per.getDni());
            personasReporte.setJerarquia(per.getJerarquia().toString());

            personasDatasource.add(personasReporte);

        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(personasDatasource);

        String entrada="ListadoPersonas.jrxml";

        String salida="ListadoPersonas";

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
