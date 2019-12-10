package domainapp.modules.simple.dom.impl.reportes;

@lombok.Getter @lombok.Setter
public class ReservasHabitacionesActivasReporte {

    private String fechaReserva;
    private String fechaInicio;
    private String fechaFin;
    private String nombrePersona;
    private String apellidoPersona;
    private String nombreHabitacion;
    private String ubicacionHabitacion;

    public ReservasHabitacionesActivasReporte(String fechaReserva,String fechaInicio,String fechaFin,String nombrePersona,String apellidoPersona,String nombreHabitacion,String ubicacionHabitacion){
        this.fechaReserva=fechaReserva;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
        this.nombrePersona=nombrePersona;
        this.apellidoPersona=apellidoPersona;
        this.nombreHabitacion=nombreHabitacion;
        this.ubicacionHabitacion=ubicacionHabitacion;
    }

    public ReservasHabitacionesActivasReporte(){}

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

    public String getNombreHabitacion(){
        return this.nombreHabitacion;
    }

    public String getUbicacionHabitacion(){
        return this.ubicacionHabitacion;
    }
}
