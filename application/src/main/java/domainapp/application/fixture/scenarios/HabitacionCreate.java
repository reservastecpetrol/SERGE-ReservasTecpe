package domainapp.application.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dom.impl.enums.ListaHabitaciones;
import domainapp.modules.simple.dom.impl.habitacion.Habitacion;
import domainapp.modules.simple.dom.impl.habitacion.HabitacionMenu;

public class HabitacionCreate extends FixtureScript {

    private String nombre;

    private String ubicacion;

    private ListaHabitaciones categoria;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(final String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public ListaHabitaciones getCategoria (){
        return categoria;
    }

    public void setCategoria(final ListaHabitaciones categoria) {
        this.categoria = categoria;
    }


    private Habitacion habitacion;

    public Habitacion getHabitacion() {
        return habitacion;
    }


    //region > simpleObject (output)
    private Habitacion simpleObject;

    /**
     * The created simple object (output).
     * @return
     */
    public Habitacion getSimpleObject() {
        return simpleObject;
    }
    //endregion


    @Override
    protected void execute(final ExecutionContext ec) {

        String nombre = checkParam("nombre", ec, String.class);

        String ubicacion = checkParam("ubicacion", ec, String.class);

        ListaHabitaciones categoria = checkParam("categoria", ec, ListaHabitaciones.class);


        this.habitacion = wrap(menu).crearHabitacion(nombre,ubicacion,categoria);

        // also make available to UI
        ec.addResult(this, habitacion);
    }

    @javax.inject.Inject
    HabitacionMenu menu;
}
