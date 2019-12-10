package domainapp.modules.simple.dom.impl.reportes;

import domainapp.modules.simple.dom.impl.enums.EstadoHabitacion;

@lombok.Getter @lombok.Setter
public class HabitacionesDisponiblesReporte {

    private String nombre;

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    private String ubicacion;

    public String getUbicacion(){
        return ubicacion;
    }

    public void setUbicacion(String ubicacion){
        this.ubicacion=ubicacion;
    }

    private EstadoHabitacion estado;

    public String getEstado(){
        return estado.toString();
    }

    public void setEstado(EstadoHabitacion estado){
        this.estado=estado;
    }


    private String categoria;

    public String getCategoria(){
        return categoria.toString();
    }

    public void setCategoria(String categoria){
        this.categoria=categoria;
    }

}
