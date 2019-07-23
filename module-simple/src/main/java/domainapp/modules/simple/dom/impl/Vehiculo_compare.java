package domainapp.modules.simple.dom.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.services.factory.FactoryService;

@Mixin
public class Vehiculo_compare {

    private final Vehiculo vehiculo;

    public Vehiculo_compare(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    @Action()
    public Compare $$(final Vehiculo other) {
        final Compare compare=factoryService.instantiate(Compare.class);
        compare.setLeft(this.vehiculo);
        compare.setRight(other);
        return compare;
    }

    public List<Vehiculo> choices0$$() {
        return vehiculoRepository.listarTodos()
                .stream()
                .filter(x -> x!=this.vehiculo).
                collect(Collectors.toList());
    }

    @Inject
    FactoryService factoryService;
    @javax.inject.Inject
    VehiculoRepository vehiculoRepository;

}
