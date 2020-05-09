package domainapp.application.fixture.scenarios;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dom.impl.vehiculo.Vehiculo;

public class VehiculoRecreate extends FixtureScript {

    public final List<String> matriculas = Collections.unmodifiableList(Arrays.asList(
            "AB 222 TY",
            "CD 111 WE",
            "EF 888 JK",
            "GH 234 AS",
            "IJ 456 FG",
            "KL 333 DF",
            "MN 222 TY",
            "OP 111 WE",
            "QR 888 JK",
            "ST 234 AS",
            "UV 456 FG",
            "WX 333 DF",
            "YZ 222 TY",
            "AA 111 WE",
            "BB 888 JK",
            "CC 234 AS",
            "DD 456 FG",
            "EE 333 DF",
            "FF 222 TY",
            "GG 111 WE",
            "HH 888 JK",
            "II 234 AS",
            "JJ 456 FG",
            "KK 333 DF",
            "LL 222 TY",
            "MM 111 WE",
            "NN 888 JK",
            "OO 234 AS",
            "PP 456 FG",
            "QQ 333 DF",
            "RR 222 TY",
            "SS 111 WE",
            "TT 888 JK",
            "UU 234 AS",
            "VV 456 FG",
            "XX 333 DF",
            "YY 222 TY",
            "ZZ 111 WE",
            "AF 888 JK",
            "AK 888 JK"
            ));


    public final List<String> marcas = Collections.unmodifiableList(Arrays.asList(
            "FORD",
            "PEUGEOT",
            "CHEVROLET",
            "TOYOTA",
            "FORD",
            "FORD",
            "PEUGEOT",
            "CHEVROLET",
            "TOYOTA",
            "FORD",
            "FORD",
            "PEUGEOT",
            "CHEVROLET",
            "TOYOTA",
            "FORD",
            "FORD",
            "PEUGEOT",
            "CHEVROLET",
            "TOYOTA",
            "FORD",
            "FORD",
            "PEUGEOT",
            "CHEVROLET",
            "TOYOTA",
            "FORD",
            "FORD",
            "PEUGEOT",
            "CHEVROLET",
            "TOYOTA",
            "FORD",
            "FORD",
            "PEUGEOT",
            "CHEVROLET",
            "TOYOTA",
            "FORD",
            "FORD",
            "PEUGEOT",
            "CHEVROLET",
            "TOYOTA",
            "FORD"
            ));


    public final List<String> colores = Collections.unmodifiableList(Arrays.asList(
            "BLANCO",
            "GRIS",
            "GRIS",
            "BLANCO",
            "BLANCO",
            "BLANCO",
            "GRIS",
            "GRIS",
            "BLANCO",
            "BLANCO",
            "BLANCO",
            "GRIS",
            "GRIS",
            "BLANCO",
            "BLANCO",
            "BLANCO",
            "GRIS",
            "GRIS",
            "BLANCO",
            "BLANCO",
            "BLANCO",
            "GRIS",
            "GRIS",
            "BLANCO",
            "BLANCO",
            "BLANCO",
            "GRIS",
            "GRIS",
            "BLANCO",
            "BLANCO",
            "BLANCO",
            "GRIS",
            "GRIS",
            "BLANCO",
            "BLANCO",
            "BLANCO",
            "GRIS",
            "GRIS",
            "BLANCO",
            "BLANCO"
    ));


    public final List<String> modelos = Collections.unmodifiableList(Arrays.asList(
            "S10",
            "RANGER",
            "HILUX",
            "HILUX",
            "RANGER",
            "S10",
            "RANGER",
            "HILUX",
            "HILUX",
            "S10",
            "S10",
            "RANGER",
            "HILUX",
            "HILUX",
            "RANGER",
            "S10",
            "RANGER",
            "HILUX",
            "HILUX",
            "S10",
            "S10",
            "RANGER",
            "HILUX",
            "HILUX",
            "RANGER",
            "S10",
            "RANGER",
            "HILUX",
            "HILUX",
            "S10",
            "S10",
            "RANGER",
            "HILUX",
            "HILUX",
            "RANGER",
            "S10",
            "RANGER",
            "HILUX",
            "HILUX",
            "S10"
            ));


    public final List<Boolean> combustibles = Collections.unmodifiableList(Arrays.asList(
            true,
            true,
            false,
            true,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            false,
            true,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true,
            false,
            true,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true,
            false,
            true,
            false,
            true,
            true,
            false,
            true,
            true,
            true
            ));


    public final List<Boolean> seguros = Collections.unmodifiableList(Arrays.asList(
            true,
            true,
            false,
            true,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            false,
            true,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true,
            false,
            true,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            true,
            false,
            true,
            false,
            true,
            true,
            false,
            true,
            true,
            true
            ));


    public final List<String> ubicaciones = Collections.unmodifiableList(Arrays.asList(
            "AÑELO",
            "AÑELO",
            "NEUQUEN",
            "NEUQUEN",
            "AÑELO",
            "AÑELO",
            "AÑELO",
            "NEUQUEN",
            "NEUQUEN",
            "AÑELO",
            "AÑELO",
            "NEUQUEN",
            "NEUQUEN",
            "AÑELO",
            "AÑELO",
            "AÑELO",
            "NEUQUEN",
            "NEUQUEN",
            "NEUQUEN",
            "AÑELO",
            "AÑELO",
            "NEUQUEN",
            "NEUQUEN",
            "AÑELO",
            "AÑELO",
            "AÑELO",
            "NEUQUEN",
            "NEUQUEN",
            "NEUQUEN",
            "AÑELO",
            "AÑELO",
            "NEUQUEN",
            "NEUQUEN",
            "AÑELO",
            "AÑELO",
            "AÑELO",
            "NEUQUEN",
            "NEUQUEN",
            "NEUQUEN",
            "NEUQUEN"
            ));

    public VehiculoRecreate() {
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

    public VehiculoRecreate setNumber(final Integer number) {
        this.number = number;
        return this;
    }
    //endregion

    //region > simpleObjects (output)
    private final List<Vehiculo> simpleObjects = Lists.newArrayList();

    /**
     * The simpleobjects created by this fixture (output).
     */
    @Programmatic
    public List<Vehiculo> getSimpleObjects() {
        return simpleObjects;
    }
    //endregion


    @Override
    protected void execute(final FixtureScript.ExecutionContext ec) {

        // defaults
        final int number = defaultParam("number", ec, 1);

        // validate
        if(number < 0 || number > matriculas.size()) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d)", matriculas.size()));
        }

        //
        // execute
        //
        ec.executeChild(this, new VehiculoTearDown());

        for (int i = 0; i < number; i++) {
            final VehiculoCreate vc = new VehiculoCreate();
            vc.setMatricula(matriculas.get(i));
            vc.setMarca(marcas.get(i));
            vc.setColor(colores.get(i));
            vc.setModelo(modelos.get(i));
            vc.setCombustible(combustibles.get(i));
            vc.setSeguro(seguros.get(i));
            vc.setUbicacion(ubicaciones.get(i));



            ec.executeChild(this, vc.getMatricula(), vc);
            simpleObjects.add(vc.getSimpleObject());
        }

    }
}
