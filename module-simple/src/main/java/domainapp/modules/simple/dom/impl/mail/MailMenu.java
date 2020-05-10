package domainapp.modules.simple.dom.impl.mail;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "MailMenu",
        repositoryFor = Mail.class
)
@DomainServiceLayout(
        named = "Mails",
        menuOrder = "10"
)
public class MailMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(cssClassFa="fa-search",bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Mail")
    @MemberOrder(sequence = "1")
    public Mail verMail() {
        List<Mail> lista = mailRepository.listar();
        return lista.get(0);
    }

    public String validateVerMail() {
        List<Mail> listaMail = mailRepository.listar();
        if (listaMail.size() == 0) {
            return "No hay mail creado ingrese por Crear Mail";
        }
        return "";
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Crear Mail")
    @MemberOrder(sequence = "2")
    public Mail crear(
            @ParameterLayout(named = "Auth", describedAs = "Dejar true para usar autenticación mediante usuario y clave") boolean mailAuth,
            @ParameterLayout(named = "Starttls Enable", describedAs = "Dejar true para conectar de manera segura al servidor SMTP") boolean starttlsEnable,
            @ParameterLayout(named = "Smtp Host", describedAs = "Servidor SMTP que vas a usar") String smtphost,
            @ParameterLayout(named = "Smtp Port", describedAs = "Puerto SMTP que vas a usar") int smtpPort,
            @ParameterLayout(named = "Nombre", describedAs = "Ingrese el nombre del remitente") String nombre,
            @ParameterLayout(named = "Mail", describedAs = "Ingrese su direccion de mail") String mail,
            @ParameterLayout(named = "Contraseña", describedAs = "Ingrese la contraseña de su mail") String contraseña) {
        return mailRepository.crear(mailAuth, starttlsEnable, smtphost, smtpPort, nombre, mail, contraseña);
    }

    public String validateCrear(boolean mailAuth, boolean starttlsEnable, String smtphost, int smtpPort, String nombre,
            String mail, String contraseña) {
        List<Mail> listaMail = mailRepository.listar();
        if (listaMail.size() != 0) {
            return "Ya hay creado un mail, si quiere modificarlo ingrese por Listar Mail";
        }
        return "";
    }

    @javax.inject.Inject
    MailRepository mailRepository;
}