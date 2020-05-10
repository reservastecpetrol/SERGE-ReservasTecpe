package domainapp.modules.simple.dom.impl.persona;

import java.util.List;
import java.util.regex.Pattern;

import org.datanucleus.query.typesafe.TypesafeQuery;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import domainapp.modules.simple.dom.impl.enums.ListaJerarquias;
import domainapp.modules.simple.dom.impl.enums.TipoSexo;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "Persona",
        repositoryFor = Persona.class
)
@DomainServiceLayout(
        named = "Personas",
        menuOrder = "10"
)
/**
 *
 * Esta clase es el servicio de dominio Menu de la clase Persona
 * que define los metodos
 * que van a aparecer en el menu del sistema
 *
 *@author Cintia Millacura
 *
 */
public class PersonaMenu {

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Personas"
    )
    @MemberOrder(sequence = "1")
    /**
     * Este metodo lista todos las personas que hay registradas
     * en el sistema
     *
     * @return List<Persona>
     */
    public List<Persona> listarPersonas() {
        return personarepository.listarPersonas();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Personas Ejecutivas"
    )
    @MemberOrder(sequence = "2")
    /**
     * Este metodo lista todas las personas con jerarquia de Ejecutivos que hay cargados
     * en el sistema
     *
     * @return List<Persona>
     */
    public List<Persona> listarPersonasEjecutivas() {
        return personarepository.listarPersonasEjecutivas();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Personas Supervisores"
    )
    @MemberOrder(sequence = "3")
    /**
     * Este metodo lista todas las personas con jerarquia de Supervisores que hay cargados
     * en el sistema
     *
     * @return List<Persona>
     */
    public List<Persona> listarPersonasSupervisores() {
        return personarepository.listarPersonasSupervisores();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Personas Operadores"
    )
    @MemberOrder(sequence = "4")
    /**
     * Este metodo lista todas las personas con jerarquia de Operadores que hay cargados
     * en el sistema
     *
     * @return List<Persona>
     */
    public List<Persona> listarPersonasOperadores() {
        return personarepository.listarPersonasOperadores();
    }

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Buscar Persona por Nombre"
    )
    @MemberOrder(sequence = "5")
    /**
     * Este metodo permite encontrar una Persona en particular
     * dado un nombre
     *
     * @param nombre
     * @return List<Persona>
     */
    public List<Persona> buscarPersonaPorNombre(
            @ParameterLayout(named = "Nombre") final String nombre
    ) {
        return personarepository.buscarPersonaPorNombre(nombre);
    }

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Buscar Persona por DNI"
    )
    @MemberOrder(sequence = "6")
    /**
     * Este metodo permite encontrar una Persona en particular
     * dado un DNI
     *
     * @param dni
     * @return List<Persona>
     */
    public Persona buscarPersonaPorDni(
            @ParameterLayout(named = "DNI") final String dni
    ) {
        return this.verificarUsuario(dni);
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

    @Programmatic
    public String validate5CrearPersona(final String dni) {

        Persona persona=verificarUsuario(dni);

        String validar="";

        if(persona!=null){
            validar="EXISTE DNI";
        }

        return validar;
    }

    @Action(
            //semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            //bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Crear Persona"
    )
    @MemberOrder(sequence = "7")
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

            @Parameter(
                    regexPattern = "[A-Za-z ]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named = "Nombre") final String nombre,
            @Parameter(
                    regexPattern = "[A-Za-z ]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named = "Apellido") final String apellido,
            @Parameter(
                    regexPattern = "[A-Za-z ]+[0-9]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named = "Direccion") final String direccion,
            @Parameter(
                    regexPattern = "[0-9]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named = "Telefono") final String telefono,
            @Parameter(
                    regexPattern = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese una dirección de correo electrónico válida (contienen un símbolo '@') -"
            )
            @ParameterLayout(named = "Email") final String email,
            @Parameter(
                    regexPattern = "[0-9]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese dato correcto"
            )
            @ParameterLayout(named = "Dni") final String dni,
            @ParameterLayout(named = "Jerarquia") ListaJerarquias jerarquias,
            @ParameterLayout(named = "Sexo") TipoSexo sexo


    ) {
        return personarepository.crearPersona(nombre,apellido,direccion,telefono,email,dni,jerarquias,sexo);
    }

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    PersonaRepository personarepository;
}
