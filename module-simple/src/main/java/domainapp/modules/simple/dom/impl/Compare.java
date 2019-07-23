package domainapp.modules.simple.dom.impl;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlAccessType;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.services.title.TitleService;

import lombok.Getter;
import lombok.Setter;

@javax.xml.bind.annotation.XmlRootElement(name = "compare")
@javax.xml.bind.annotation.XmlType(
        propOrder = {
                "left",
                "right"
        }
)
@javax.xml.bind.annotation.XmlAccessorType(XmlAccessType.PROPERTY)
@DomainObjectLayout()
@DomainObject(
        editing = Editing.DISABLED
)
public class Compare implements org.apache.isis.applib.services.dto.Dto {

    public String title(){
        return "Comparando "+titleService.titleOf(getLeft())+" con "+titleService.titleOf(getRight());
    }

    @Getter @Setter
    private Vehiculo left;

    public String getLeftMatricula(){
        return left.getMatricula();
    }

    public String getLeftMarca(){
        return left.getMarca();
    }

    public String getLeftModelo(){
        return left.getModelo();
    }

    public boolean getLeftCombustible(){
        return left.isCombustible();
    }

    public boolean getLeftSeguro(){
        return left.isSeguro();
    }

    public String getLeftUbicacion(){
        return left.getUbicacion();
    }

    public String getLeftEstado(){
        return left.getEstado();
    }

    @Getter @Setter
    private Vehiculo right;

    public String getRightMatricula(){
        return right.getMatricula();
    }

    public String getRightMarca(){
        return right.getMarca();
    }

    public String getRightModelo(){
        return right.getModelo();
    }

    public boolean getRightCombustible(){
        return right.isCombustible();
    }

    public boolean getRightSeguro(){
        return right.isSeguro();
    }

    public String getRightUbicacion(){
        return right.getUbicacion();
    }

    public String getRightEstado(){
        return right.getEstado();
    }

    @Inject
    TitleService titleService;

}
