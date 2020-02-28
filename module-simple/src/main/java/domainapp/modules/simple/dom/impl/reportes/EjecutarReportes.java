package domainapp.modules.simple.dom.impl.reportes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dom.impl.enums.EstadoHabitacion;
import domainapp.modules.simple.dom.impl.enums.EstadoReserva;
import domainapp.modules.simple.dom.impl.enums.EstadoVehiculo;
import domainapp.modules.simple.dom.impl.habitacion.Habitacion;
import domainapp.modules.simple.dom.impl.persona.Persona;
import domainapp.modules.simple.dom.impl.reservaHabitacion.ReservaHabitacion;
import domainapp.modules.simple.dom.impl.reservaVehiculo.ReservaVehiculo;
import domainapp.modules.simple.dom.impl.vehiculo.Vehiculo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class EjecutarReportes {

    private Blob GenerarReporteTipoLista(String entrada, String salida, JRBeanCollectionDataSource ds) throws JRException, IOException{
        File rutaEntrada = new File(getClass().getResource(entrada).getPath());

        InputStream input = new FileInputStream(rutaEntrada);
        JasperDesign jasperDesign = JRXmlLoader.load(input);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ds", ds);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        return ExportarReporte(jasperPrint, salida);
    }

    private static Blob ExportarReporte(JasperPrint jasperPrint, String nombreArchivo) throws JRException, IOException {
        File pdf = File.createTempFile("output.", ".pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));

        byte[] fileContent = new byte[(int) pdf.length()];

        if (!(pdf.exists())) {
            try {
                pdf.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(pdf);
            fileInputStream.read(fileContent);
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return new Blob(nombreArchivo + ".pdf", "application/pdf", fileContent);

        } catch (Exception e) {
            byte[] result = new String("error en crear archivo").getBytes();
            return new Blob("error.txt", "text/plain", result);
        }
    }



    public Blob  ListadoHabitacionesPDF(List<Habitacion> habitaciones)throws JRException, IOException{

        List<HabitacionesDisponiblesReporte> habitacionesDatasource = new ArrayList<HabitacionesDisponiblesReporte>();

        habitacionesDatasource.add(new HabitacionesDisponiblesReporte());

        for (Habitacion habitacion: habitaciones) {

            if(habitacion.getEstado()== EstadoHabitacion.DISPONIBLE) {

                HabitacionesDisponiblesReporte habitacionesDisponiblesReporte = new HabitacionesDisponiblesReporte();

                habitacionesDisponiblesReporte.setNombre(habitacion.getNombre());
                habitacionesDisponiblesReporte.setUbicacion(habitacion.getUbicacion());
                habitacionesDisponiblesReporte.setEstado(habitacion.getEstado());
                habitacionesDisponiblesReporte.setCategoria(habitacion.getCategoria().toString());

                habitacionesDatasource.add(habitacionesDisponiblesReporte);

            }

        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(habitacionesDatasource);
        return GenerarReporteTipoLista("ListadoHabitaciones.jrxml","ListadoDeHabitaciones", ds);
    }

    public Blob  ListadoVehiculosPDF(List<Vehiculo> vehiculos)throws JRException, IOException{

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
        return GenerarReporteTipoLista("ListadoDeVehiculos.jrxml","ListadoDeVehiculos", ds);
    }

    public Blob  ListadoReservasVehiculosPDF(List<ReservaVehiculo> reservasVehiculos)throws JRException, IOException{

        List<ReservasVehiculosActivasReporte> reservasDatasource = new ArrayList<ReservasVehiculosActivasReporte>();

        reservasDatasource.add(new ReservasVehiculosActivasReporte());

        for (ReservaVehiculo reservaVehiculo: reservasVehiculos) {

            if(reservaVehiculo.getEstado()== EstadoReserva.ACTIVA) {

                ReservasVehiculosActivasReporte reservasVehiculosActivasReporte = new ReservasVehiculosActivasReporte();

                reservasVehiculosActivasReporte.setFechaReserva(reservaVehiculo.getFechaReserva().toString("dd-MM-yyyy"));
                reservasVehiculosActivasReporte.setFechaInicio(reservaVehiculo.getFechaInicio().toString("dd-MM-yyyy"));
                reservasVehiculosActivasReporte.setFechaFin(reservaVehiculo.getFechaFin().toString("dd-MM-yyyy"));
                reservasVehiculosActivasReporte.setNombrePersona(reservaVehiculo.getPersona().getNombre());
                reservasVehiculosActivasReporte.setApellidoPersona(reservaVehiculo.getPersona().getApellido());
                reservasVehiculosActivasReporte.setMatriculaVehiculo(reservaVehiculo.getVehiculo().getMatricula());
                reservasVehiculosActivasReporte.setUbicacionVehiculo(reservaVehiculo.getVehiculo().getUbicacion());

                reservasDatasource.add(reservasVehiculosActivasReporte);
            }

        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reservasDatasource);
        return GenerarReporteTipoLista("ListadoReservasVehiculos.jrxml","ListadoReservasVehiculos", ds);
    }


    public Blob  ListadoPersonasPDF(List<Persona> personas)throws JRException, IOException{

        List<PersonasReporte> personasDatasource = new ArrayList<PersonasReporte>();

        personasDatasource.add(new PersonasReporte());

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
        return GenerarReporteTipoLista("ListadoDePersonas.jrxml","ListadoDePersonas", ds);
    }

    public Blob  ListadoReservasHabitacionesPDF(List<ReservaHabitacion> reservaHabitaciones)throws JRException, IOException{

        List<ReservasHabitacionesActivasReporte> reservasDatasource = new ArrayList<ReservasHabitacionesActivasReporte>();

        reservasDatasource.add(new ReservasHabitacionesActivasReporte());

        for (ReservaHabitacion reservaHabitacion: reservaHabitaciones) {

            if(reservaHabitacion.getEstado()==EstadoReserva.ACTIVA) {

                ReservasHabitacionesActivasReporte reservasHabitacionesActivasReporte = new ReservasHabitacionesActivasReporte();

                reservasHabitacionesActivasReporte.setFechaReserva(reservaHabitacion.getFechaReserva().toString("dd-MM-yyyy"));
                reservasHabitacionesActivasReporte.setFechaInicio(reservaHabitacion.getFechaInicio().toString("dd-MM-yyyy"));
                reservasHabitacionesActivasReporte.setFechaFin(reservaHabitacion.getFechaFin().toString("dd-MM-yyyy"));
                reservasHabitacionesActivasReporte.setNombrePersona(reservaHabitacion.getPersona().getNombre());
                reservasHabitacionesActivasReporte.setApellidoPersona(reservaHabitacion.getPersona().getApellido());
                reservasHabitacionesActivasReporte.setNombreHabitacion(reservaHabitacion.getHabitacion().getNombre());
                reservasHabitacionesActivasReporte.setUbicacionHabitacion(reservaHabitacion.getHabitacion().getUbicacion());

                reservasDatasource.add(reservasHabitacionesActivasReporte);
            }

        }
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reservasDatasource);
        return GenerarReporteTipoLista("ListadoReservasHabitaciones.jrxml","ListadoReservasHabitaciones", ds);
    }

}
