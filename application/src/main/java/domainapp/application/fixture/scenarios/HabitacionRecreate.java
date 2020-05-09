package domainapp.application.fixture.scenarios;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dom.impl.enums.ListaHabitaciones;
import domainapp.modules.simple.dom.impl.habitacion.Habitacion;

public class HabitacionRecreate extends FixtureScript {

    public final List<String> nombres = Collections.unmodifiableList(Arrays.asList(
            "1A PABELLON 1",
            "1B PABELLON 1",
            "1C PABELLON 1",
            "1A PABELLON 2",
            "1B PABELLON 2",
            "1C PABELLON 2",
            "1A PABELLON 3",
            "1B PABELLON 3",
            "1C PABELLON 3",
            "1A PABELLON 4",
            "1B PABELLON 4",
            "1C PABELLON 4",
            "1A PABELLON 5",
            "1B PABELLON 5",
            "1C PABELLON 5",
            "1A PABELLON 6",
            "1B PABELLON 6",
            "1C PABELLON 6",
            "2A PABELLON 1",
            "2B PABELLON 1",
            "2C PABELLON 1",
            "2D PABELLON 2",
            "2E PABELLON 2",
            "2F PABELLON 2",
            "3G PABELLON 3",
            "3H PABELLON 3",
            "3I PABELLON 3",
            "4J PABELLON 4",
            "4K PABELLON 4",
            "4L PABELLON 4",
            "5M PABELLON 5",
            "5N PABELLON 5",
            "5O PABELLON 5",
            "6P PABELLON 6",
            "6Q PABELLON 6",
            "6R PABELLON 6",
            "2A PABELLON 7",
            "2B PABELLON 7",
            "2C PABELLON 7",
            "3D PABELLON 7"
            ));

    public final List<String> ubicaciones = Collections.unmodifiableList(Arrays.asList(
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA",
            "FORTIN DE PIEDRA"
            ));

    public final List<ListaHabitaciones> categorias = Collections.unmodifiableList(Arrays.asList(
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Estandar,
            ListaHabitaciones.Ejecutivas,
            ListaHabitaciones.Simple,
            ListaHabitaciones.Estandar
            ));

    public HabitacionRecreate() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    //region > number (optional input)
    private Integer number;

    /**
     * The number of objects to create, up to 10; optional, defaults to 3.
     */
    public Integer getNumber() {
        return number;
    }

    public HabitacionRecreate setNumber(final Integer number) {
        this.number = number;
        return this;
    }
    //endregion

    //region > simpleObjects (output)
    private final List<Habitacion> simpleObjects = Lists.newArrayList();

    /**
     * The simpleobjects created by this fixture (output).
     */
    @Programmatic
    public List<Habitacion> getSimpleObjects() {
        return simpleObjects;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final int number = defaultParam("number", ec, 1);

        // validate
        if(number < 0 || number > nombres.size()) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d)", nombres.size()));
        }

        //
        // execute
        //
        ec.executeChild(this, new HabitacionTearDown());

        for (int i = 0; i < number; i++) {
            final HabitacionCreate hc = new HabitacionCreate();
            hc.setNombre(nombres.get(i));
            hc.setUbicacion(ubicaciones.get(i));
            hc.setCategoria(categorias.get(i));

            ec.executeChild(this, hc.getNombre(), hc);
            simpleObjects.add(hc.getSimpleObject());
        }
    }
}
