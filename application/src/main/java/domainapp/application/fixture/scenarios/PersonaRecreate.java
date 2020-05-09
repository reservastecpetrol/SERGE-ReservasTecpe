package domainapp.application.fixture.scenarios;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dom.impl.enums.ListaJerarquias;
import domainapp.modules.simple.dom.impl.enums.TipoSexo;
import domainapp.modules.simple.dom.impl.persona.Persona;

public class PersonaRecreate extends FixtureScript {

    public final List<String> nombres = Collections.unmodifiableList(Arrays.asList(
            "JOSE",
            "JUAN",
            "PEDRO",
            "MARIA",
            "ANA",
            "LUIS",
            "MARIANA",
            "MIGUEL",
            "SANTIAGO",
            "JULIO",
            "JUANA",
            "LUCIA",
            "LUCAS",
            "LUCIANO",
            "MARCOS",
            "MARIANO",
            "VICTORIA",
            "BELEN",
            "AGUSTIN",
            "VALENTIN",
            "JULIAN",
            "BRENDA",
            "LEYLA",
            "BARBARA",
            "NICOLAS",
            "TOMAS",
            "LUCIANA",
            "BRIAN",
            "IAN",
            "SEBASTIAN",
            "OMAR",
            "GASTON",
            "EMILIA",
            "ENZO",
            "SERGIO",
            "LUIS",
            "JUAN",
            "PEDRO",
            "SANTIAGO",
            "MATIAS"
             ));


    public final List<String> apellidos = Collections.unmodifiableList(Arrays.asList(
            "PEREZ",
            "GONZALES",
            "GARCIA",
            "LOPEZ",
            "RODRIGUEZ",
            "PEREYRA",
            "GOMEZ",
            "MORENO",
            "CASEROS",
            "URRUTIA",
            "MORENO",
            "SIMONS",
            "MOREIRA",
            "GARCIA",
            "LODEIRO",
            "IRIARTE",
            "GONZALES",
            "HENRIQUEZ",
            "GOMEZ",
            "PEREZ",
            "PEREIRA",
            "CASEROS",
            "LOPEZ",
            "GARCIA",
            "RODRIGUEZ",
            "LOPEZ",
            "URRUTIA",
            "GONZALES",
            "GOMEZ",
            "LOPEZ",
            "ENRIQUEZ",
            "MORENO",
            "CASEROS",
            "LOPEZ",
            "GARCIA",
            "GOMEZ",
            "ROMARIO",
            "LOPEZ",
            "RODRIGUEZ",
            "ROMERO"
    ));


    public final List<String> direcciones = Collections.unmodifiableList(Arrays.asList(
            "SAN LUIS 123",
            "SANTA FE 345",
            "JUJUY 567",
            "SALTA 453",
            "TIERRA DEL FUEGO 123",
            "BUENOS AIRES 234",
            "SAN JUAN 123",
            "PERU 4444",
            "BOLIVIA 111",
            "PARAGUAY 222",
            "MEXICO 554",
            "BUENOS AIRES 111",
            "SANTA FE 121",
            "SAN JUAN 322",
            "BOLIVIA 343",
            "JUJUY 223",
            "SALTA 787",
            "TIERRA DEL FUEGO 344",
            "SANTIAGO DEL ESTERO 344",
            "CASTELLI 213",
            "ELORDI 111",
            "SAN MARTIN 114",
            "RIVADAVIA 234",
            "MENDOZA 455",
            "BROWN 789",
            "BUENOS AIRES 389",
            "RIVADAVIA 544",
            "JUJUY 389",
            "SANTIAGO DEL ESTERO 565",
            "MENDOZA 233",
            "RIVADAVIA 115",
            "SALTA 145",
            "ELORDI 233",
            "BUENOS AIRES 134",
            "CASTELLI 345",
            "JUJUY 223",
            "AV ARGENTINA 1113",
            "MITRE 118",
            "INDEPENDENCIA 389",
            "SAN JUSTO 832"
            ));


    public final List<String> telefonos = Collections.unmodifiableList(Arrays.asList(
            "4421212",
            "156234534",
            "154232323",
            "155343434",
            "156898989",
            "155121234",
            "156898989",
            "154898989",
            "156898989",
            "155343434",
            "154343434",
            "155343434",
            "156343434",
            "154343434",
            "155343434",
            "156343434",
            "155343434",
            "154343434",
            "155343434",
            "156343434",
            "155343434",
            "154343434",
            "156343434",
            "155343434",
            "156343434",
            "155343434",
            "154343434",
            "156343434",
            "155343434",
            "154343434",
            "155343434",
            "155343434",
            "156343434",
            "156343434",
            "155343434",
            "154343434",
            "155343434",
            "155343434",
            "154343434",
            "155891234"
             ));


    public final List<String> emails = Collections.unmodifiableList(Arrays.asList(
            "josePerez@gmail.com",
            "juanGonzales@gmail.com",
            "pedroGarcia@hotmail.com.ar",
            "mariaLopez@gmail.com",
            "anaRodriguez@gmail.com",
            "luisPereyra@gmail.com",
            "marianaGomez@gmail.com",
            "miguelMoreno@gmail.com",
            "santiagoCaseros@gmail.com",
            "julioUrrutia@gmail.com",
            "juanaMoreno@gmail.com",
            "luciaSimons@gmail.com",
            "lucasMoreira@gmail.com",
            "lucianoGarcia@gmail.com",
            "marcosLodeiro@gmail.com",
            "marianoIriarte@gmail.com",
            "victoriaGonzales@gmail.com",
            "belenHenriquez@gmail.com",
            "agustinGomez@gmail.com",
            "valentinPerez@gmail.com",
            "julianPereira@gmail.com",
            "brendaCaseros@gmail.com",
            "leylaLopez@gmail.com",
            "barbaraGarcia@gmail.com",
            "nicolasRodriguez@gmail.com",
            "tomasLopez@gmail.com",
            "lucianaUrrutia@gmail.com",
            "brianGonzales@gmail.com",
            "ianGomez@gmail.com",
            "sebastianLopez@gmail.com",
            "omarEnriquez@gmail.com",
            "gastonMoreno@gmail.com",
            "emiliaCaseros@gmail.com",
            "enzoLopez@gmail.com",
            "sergioGarcia@gmail.com",
            "luisGomez@gmail.com",
            "juanRomario@gmail.com",
            "pedroLopez@gmail.com",
            "santiagoRodriguez@gmail.com",
            "matiasRomero@gmail.com"
             ));


    public final List<String> dnies = Collections.unmodifiableList(Arrays.asList(
            "15345345",
            "16123123",
            "17456456",
            "18789789",
            "19345345",
            "20234234",
            "21222111",
            "22345789",
            "23123123",
            "24123123",
            "25123123",
            "26123123",
            "27123123",
            "28123123",
            "29123123",
            "30123123",
            "31123123",
            "32123123",
            "33123123",
            "34123123",
            "35123123",
            "36123123",
            "37123123",
            "38123123",
            "39123123",
            "40123123",
            "13123123",
            "14123123",
            "20123123",
            "21123123",
            "22123123",
            "25451234",
            "26789789",
            "27333444",
            "28777888",
            "29555222",
            "35222888",
            "36999111",
            "37233244",
            "21444555"
            ));


    public final List<ListaJerarquias> jerarquias = Collections.unmodifiableList(Arrays.asList(
            ListaJerarquias.Operadores,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Operadores,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Operadores,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Operadores,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Operadores,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Operadores,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Operadores,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Supervisores,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Ejecutivos,
            ListaJerarquias.Operadores,
            ListaJerarquias.Supervisores
            ));


    public final List<TipoSexo> sexos = Collections.unmodifiableList(Arrays.asList(
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.FEMENINO,
            TipoSexo.FEMENINO,
            TipoSexo.MASCULINO,
            TipoSexo.FEMENINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.FEMENINO,
            TipoSexo.FEMENINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.FEMENINO,
            TipoSexo.FEMENINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.FEMENINO,
            TipoSexo.FEMENINO,
            TipoSexo.FEMENINO,
            TipoSexo.FEMENINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.FEMENINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.FEMENINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO,
            TipoSexo.MASCULINO
            ));

    public PersonaRecreate() {
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

    public PersonaRecreate setNumber(final Integer number) {
        this.number = number;
        return this;
    }
    //endregion

    //region > simpleObjects (output)
    private final List<Persona> simpleObjects = Lists.newArrayList();

    /**
     * The simpleobjects created by this fixture (output).
     */
    @Programmatic
    public List<Persona> getSimpleObjects() {
        return simpleObjects;
    }
    //endregion


    @Override
    protected void execute(final FixtureScript.ExecutionContext ec) {

        // defaults
        final int number = defaultParam("number", ec, 1);

        // validate
        if(number < 0 || number > nombres.size()) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d)", nombres.size()));
        }

        //
        // execute
        //
        ec.executeChild(this, new PersonaTearDown());

        for (int i = 0; i < number; i++) {
            final PersonaCreate pc = new PersonaCreate();
            pc.setNombre(nombres.get(i));
            pc.setApellido(apellidos.get(i));
            pc.setDireccion(direcciones.get(i));
            pc.setTelefono(telefonos.get(i));
            pc.setEmail(emails.get(i));
            pc.setDni(dnies.get(i));
            pc.setJerarquia(jerarquias.get(i));
            pc.setSexo(sexos.get(i));

            ec.executeChild(this, pc.getNombre(), pc);
            simpleObjects.add(pc.getSimpleObject());
        }


    }
}
