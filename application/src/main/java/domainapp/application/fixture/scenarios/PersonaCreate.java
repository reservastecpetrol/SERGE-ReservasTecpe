package domainapp.application.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dom.impl.enums.ListaJerarquias;
import domainapp.modules.simple.dom.impl.enums.TipoSexo;
import domainapp.modules.simple.dom.impl.persona.Persona;
import domainapp.modules.simple.dom.impl.persona.PersonaMenu;

public class PersonaCreate extends FixtureScript {

    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String email;
    private String dni;
    private ListaJerarquias jerarquia;
    private TipoSexo sexo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(final String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(final String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(final String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }


    public String getDni() {
        return dni;
    }

    public void setDni(final String dni) {
        this.dni = dni;
    }


    public ListaJerarquias getJerarquia() {
        return jerarquia;
    }

    public void setJerarquia(final ListaJerarquias jerarquia) {
        this.jerarquia = jerarquia;
    }

    public TipoSexo getSexo() {
        return sexo;
    }

    public void setSexo(final TipoSexo sexo) {
        this.sexo = sexo;
    }


    private Persona persona;


    public Persona getPersonaObject() {
        return persona;
    }

    //region > simpleObject (output)
    private Persona simpleObject;

    /**
     * The created simple object (output).
     * @return
     */
    public Persona getSimpleObject() {
        return simpleObject;
    }
    //endregion



    @Override
    protected void execute(final ExecutionContext ec) {

        String nombre = checkParam("nombre", ec, String.class);
        String apellido = checkParam("apellido", ec, String.class);
        String direccion = checkParam("direccion", ec, String.class);
        String telefono = checkParam("telefono", ec, String.class);
        String email = checkParam("email", ec, String.class);
        String dni = checkParam("dni", ec, String.class);
        ListaJerarquias jerarquia = checkParam("jerarquia", ec,ListaJerarquias.class);
        TipoSexo sexo = checkParam("sexo", ec, TipoSexo.class);

        this.persona = wrap(menu).crearPersona(nombre,apellido,direccion,telefono,email,dni,jerarquia,sexo);

        // also make available to UI
        ec.addResult(this, persona);
    }

    @javax.inject.Inject
    PersonaMenu menu;
}
