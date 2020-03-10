/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.application.services.homepage;

import java.util.List;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Programmatic;

import domainapp.modules.simple.dom.impl.persona.Persona;
import domainapp.modules.simple.dom.impl.persona.PersonaRepository;
import domainapp.modules.simple.dom.impl.reservaHabitacion.ReservaHabitacion;
import domainapp.modules.simple.dom.impl.reservaHabitacion.ReservaHabitacionRepository;
import domainapp.modules.simple.dom.impl.reservaVehiculo.ReservaVehiculo;
import domainapp.modules.simple.dom.impl.reservaVehiculo.ReservaVehiculoRepository;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "domainapp.application.services.homepage.HomePageViewModel"
)
public class HomePageViewModel {

    public String title() {
        return "SISTEMA DE RESERVAS TECPE";
    }

    @HomePage()
    @CollectionLayout(named="Reservas de Vehiculos que Inician Hoy")
    public List<ReservaVehiculo> getReservasVehiculosQueInicianHoy() {
        return reservaVehiculoRepository.listarReservasQueInicianHoy();
    }

    @HomePage()
    @CollectionLayout(named="Reservas de Vehiculos que Finalizan Hoy")
    public List<ReservaVehiculo> getReservasVehiculosQueFinalizanHoy() {
        return reservaVehiculoRepository.listarReservasQueFinalizanHoy();
    }

    @HomePage()
    @CollectionLayout(named="Reservas de Habitaciones que Inician Hoy")
    public List<ReservaHabitacion> getReservasHabitacionesQueInicianHoy() {
        return reservaHabitacionRepository.listarReservasQueInicianHoy();
    }

    @HomePage()
    @CollectionLayout(named="Reservas de Habitaciones que Finalizan Hoy")
    public List<ReservaHabitacion> getReservasHabitacionesQueFinalizanHoy() {
        return reservaHabitacionRepository.listarReservasQueFinalizanHoy();
    }

    @HomePage()
    @CollectionLayout(named="Reservas Activas de Habitaciones")
    public List<ReservaHabitacion> getReservasHabitaciones() {
        return reservaHabitacionRepository.listarReservasDeHabitacionesActivas();
    }

    @HomePage()
    @CollectionLayout(named="Reservas Activas de Vehiculos")
    public List<ReservaVehiculo> getReservasVehiculos() {
        return reservaVehiculoRepository.listarReservasDeVehiculosActivas();
    }

    @Programmatic
    public List<Persona> getObjects() {
        return personaRepository.listarPersonas();
    }


    @javax.inject.Inject
    PersonaRepository personaRepository;

    @javax.inject.Inject
    ReservaHabitacionRepository reservaHabitacionRepository;

    @javax.inject.Inject
    ReservaVehiculoRepository reservaVehiculoRepository;
}