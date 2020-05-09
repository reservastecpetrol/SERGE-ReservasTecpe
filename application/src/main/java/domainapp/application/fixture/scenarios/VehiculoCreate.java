package domainapp.application.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dom.impl.vehiculo.Vehiculo;
import domainapp.modules.simple.dom.impl.vehiculo.VehiculoMenu;

public class VehiculoCreate extends FixtureScript {

    private String matricula;

    private String marca;

    private String color;

    private String modelo;

    private boolean combustible;

    private boolean seguro;

    private String ubicacion;


    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(final String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(final String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(final String modelo) {
        this.modelo = modelo;
    }

    public boolean getCombustible() {
        return combustible;
    }

    public void setCombustible(final boolean combustible) {
        this.combustible = combustible;
    }

    public boolean getSeguro() {
        return seguro;
    }

    public void setSeguro(final boolean seguro) {
        this.seguro = seguro;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(final String ubicacion) {
        this.ubicacion = ubicacion;
    }


    private Vehiculo vehiculo;

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    //region > simpleObject (output)
    private Vehiculo simpleObject;

    /**
     * The created simple object (output).
     * @return
     */
    public Vehiculo getSimpleObject() {
        return simpleObject;
    }
    //endregion



    @Override
    protected void execute(final ExecutionContext ec) {

        String matricula = checkParam("matricula", ec, String.class);
        String marca = checkParam("marca", ec, String.class);
        String color = checkParam("color", ec, String.class);
        String modelo = checkParam("modelo", ec, String.class);
        boolean combustible = checkParam("combustible", ec, boolean.class);
        boolean seguro = checkParam("seguro", ec, boolean.class);
        String ubicacion = checkParam("ubicacion", ec, String.class);

        this.vehiculo = wrap(menu).crearVehiculo(matricula,marca,color,modelo,combustible,seguro,ubicacion);

        // also make available to UI
        ec.addResult(this, vehiculo);
    }

    @javax.inject.Inject
    VehiculoMenu menu;
}
