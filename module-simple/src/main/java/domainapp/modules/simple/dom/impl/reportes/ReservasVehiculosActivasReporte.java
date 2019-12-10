package domainapp.modules.simple.dom.impl.reportes;

@lombok.Getter @lombok.Setter
public class ReservasVehiculosActivasReporte {

    private String fechaReserva;
    private String fechaInicio;
    private String fechaFin;
    private String nombrePersona;
    private String apellidoPersona;
    private String matriculaVehiculo;
    private String ubicacionVehiculo;

    public ReservasVehiculosActivasReporte(String fechaReserva,String fechaInicio,String fechaFin,String nombrePersona,String apellidoPersona,String matriculaVehiculo,String ubicacionVehiculo){
        this.fechaReserva=fechaReserva;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
        this.nombrePersona=nombrePersona;
        this.apellidoPersona=apellidoPersona;
        this.matriculaVehiculo=matriculaVehiculo;
        this.ubicacionVehiculo=ubicacionVehiculo;
    }

    public ReservasVehiculosActivasReporte(){}


    public String getFechaReserva(){
        return this.fechaReserva;
    }

    public String getFechaInicio(){
        return this.fechaInicio;
    }

    public String getFechaFin(){
        return this.fechaFin;
    }

    public String getNombrePersona(){
        return this.nombrePersona;
    }

    public String getApellidoPersona(){
        return this.apellidoPersona;
    }


    public String getMatriculaVehiculo(){
        return this.matriculaVehiculo;
    }

    public String getUbicacionVehiculo(){
        return this.ubicacionVehiculo;
    }
}
