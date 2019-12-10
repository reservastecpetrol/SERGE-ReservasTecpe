package domainapp.modules.simple.dom.impl.reportes;

@lombok.Getter @lombok.Setter
public class PersonasReporte {

    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String email;
    private String dni;
    private String jerarquia;

    public PersonasReporte(String nombre,String apellido,String direccion,String telefono,String email,String dni,String jerarquia){
        this.nombre=nombre;
        this.apellido=apellido;
        this.direccion=direccion;
        this.telefono=telefono;
        this.email=email;
        this.dni=dni;
        this.jerarquia=jerarquia;
    }

    public PersonasReporte(){}

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getApellido(){
        return this.apellido;
    }

    public void setApellido(String apellido){
        this.apellido=apellido;
    }

    public String getDireccion(){
        return this.direccion;
    }

    public void setDireccion(String direccion){
        this.direccion=direccion;
    }

    public String getTelefono(){
        return this.telefono;
    }

    public void setTelefono(String telefono){
        this.telefono=telefono;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getDni(){
        return this.dni;
    }

    public void setDni(String dni){
        this.dni=dni;
    }

    public String getJerarquia(){
        return this.jerarquia;
    }

    public void setJerarquia(String jerarquia){
        this.jerarquia=jerarquia;
    }

}
