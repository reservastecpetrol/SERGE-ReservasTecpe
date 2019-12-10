package domainapp.modules.simple.dom.impl.reportes;

@lombok.Getter @lombok.Setter
public class VehiculosDisponiblesReporte {

    private String matricula;
    private String marca;
    private String modelo;
    private String ubicacion;
    private String estado;

    public VehiculosDisponiblesReporte(String matricula,String marca,String modelo,String ubicacion,String estado){
         this.matricula=matricula;
         this.marca=marca;
         this.modelo=modelo;
         this.ubicacion=ubicacion;
         this.estado=estado;
    }

    public VehiculosDisponiblesReporte(){}

    public String getMatricula(){
        return this.matricula;
    }

    public String getMarca(){
        return this.marca;
    }

    public String getModelo(){
        return this.modelo;
    }

    public String getUbicacacion(){
        return this.ubicacion;
    }

    public String getEstado(){
        return this.estado;
    }

}
