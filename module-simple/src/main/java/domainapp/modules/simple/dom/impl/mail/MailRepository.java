package domainapp.modules.simple.dom.impl.mail;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Mail.class
)
public class MailRepository {

    public List<Mail> listar() {
        return repositoryService.allInstances(Mail.class);
    }

    public Mail crear(boolean mailAuth, boolean starttlsEnable, String smtphost, int smtpPort, String nombre,
            String mail, String contraseña) {
        final Mail object = new Mail(mailAuth, starttlsEnable, smtphost, smtpPort, nombre, mail, contraseña);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
