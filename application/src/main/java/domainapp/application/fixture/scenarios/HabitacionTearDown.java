package domainapp.application.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class HabitacionTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
     //   isisJdoSupport.executeUpdate("delete from \"simple\".\"Habitacion\"");
    }

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;
}
