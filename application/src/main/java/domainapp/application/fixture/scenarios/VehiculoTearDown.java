package domainapp.application.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class VehiculoTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
      //  isisJdoSupport.executeUpdate("delete from \"simple\".\"Vehiculo\"");
    }

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;
}
