package domainapp.modules.simple.dom.impl.mail;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;

import domainapp.modules.simple.dom.impl.persona.Persona;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "simple",
        table = "Mail"
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@Queries({
        @Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.mail.Mail "),
        @Query(
                name = "findByMailContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.mail.Mail "
                        + "WHERE mail.indexOf(:mail) >= 0 "),
        @Query(
                name = "findByMail", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.mail.Mail "
                        + "WHERE mail == :mail ")
})
@Unique(name = "Mail_mail_UNQ", members = { "mail" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class Mail implements Comparable<Mail> {

    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getMail());
    }

    public Mail() {
    }

    public Mail(boolean mailAuth, boolean starttlsEnable, String smtphost, int smtpPort, String nombre, String mail,
            String contraseña) {
        setMailAuth(mailAuth);
        setStarttlsEnable(starttlsEnable);
        setSmtphost(smtphost);
        setSmtpPort(smtpPort);
        setNombre(nombre);
        setMail(mail);
        setContraseña(contraseña);
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Auth", describedAs = "Dejar true para usar autenticación mediante usuario y clave")
    private boolean mailAuth;

    public boolean getMailAuth() {
        return mailAuth;
    }

    public void setMailAuth(boolean mailAuth) {
        this.mailAuth = mailAuth;
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Starttls Enable", describedAs = "Dejar true para conectar de manera segura al servidor SMTP")
    private boolean starttlsEnable;

    public boolean getStarttlsEnable() {
        return starttlsEnable;
    }

    public void setStarttlsEnable(boolean starttlsEnable) {
        this.starttlsEnable = starttlsEnable;
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Smtp Host", describedAs = "Servidor SMTP que vas a usar")
    private String smtphost;

    public String getSmtphost() {
        return smtphost;
    }

    public void setSmtphost(String smtphost) {
        this.smtphost = smtphost;
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Smtp Port", describedAs = "Puerto SMTP que vas a usar")
    private int smtpPort;

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Nombre del remitente", describedAs = "Ingrese el nombre del remitente")
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Mail", describedAs = "Ingrese su direccion de mail")
    private String mail;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Contraseña", describedAs = "Ingrese la contraseña de su mail")
    private String contraseña;

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    // acciones
    private static Mail obtenerMailEmisor() {
        List<Mail> lista = mailRepository.listar();
        Mail mail = lista.get(0);
        return mail;
    }

    private static Properties conectarse(Mail mail) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", mail.getMailAuth());
        props.put("mail.smtp.starttls.enable", mail.getStarttlsEnable());
        props.put("mail.smtp.host", mail.getSmtphost());
        props.put("mail.smtp.port", mail.getSmtpPort());
        return props;
    }

    private static Session autentificar(Mail mail, Properties props) {
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail.getMail(), mail.getContraseña());
            }
        });
        return session;
    }

    public static void enviarMailReserva(Persona persona) {
        if (persona.getEmail() != null) {
            Mail mail = obtenerMailEmisor();
            Properties props = conectarse(mail);
            Session session = autentificar(mail, props);

            String asunto = "Proximo Inicio de Reserva";

            String mensaje = "Buenos días " + persona.toString()
                    + ", \r\nEn los proximos dias dara Inicio su Reserva.\r\nSaludos cordiales.\r\n"
                    + "Sistema de Reservas TECPE";

            try {
                BodyPart texto = new MimeBodyPart();

                // Texto del mensaje
                texto.setText(mensaje);

                MimeMultipart multiParte = new MimeMultipart();
                multiParte.addBodyPart(texto);

                MimeMessage message = new MimeMessage(session);

                // Se rellena el From
                InternetAddress emisor = new InternetAddress(mail.getNombre() + " <" + mail.getMail() + ">");
                message.setFrom(emisor);

                // Se rellenan los destinatarios
                InternetAddress receptor = new InternetAddress();
                receptor.setAddress(persona.getEmail());
                message.addRecipient(Message.RecipientType.TO, receptor);

                // Se rellena el subject
                message.setSubject(asunto);

                // Se mete el texto y la foto adjunta.
                message.setContent(multiParte);

                Transport.send(message);

            } catch (MessagingException e) {
                messageService.informUser("Falló envío de mail");
            }
        }
    }

    public static void enviarMailCancelacion(Persona persona) {
        if (persona.getEmail() != null) {
            Mail mail = obtenerMailEmisor();
            Properties props = conectarse(mail);
            Session session = autentificar(mail, props);

            String asunto = "Cancelacion de Reserva";

            String mensaje = "Buenos días " + persona.toString()
                    + ", \r\nSe le informa que la Reserva realizada fue CANCELADA por falta de Autorizacion.\r\nSaludos cordiales.\r\n"
                    + "Sistema de Reservas TECPE";

            try {
                BodyPart texto = new MimeBodyPart();

                // Texto del mensaje
                texto.setText(mensaje);

                MimeMultipart multiParte = new MimeMultipart();
                multiParte.addBodyPart(texto);

                MimeMessage message = new MimeMessage(session);

                // Se rellena el From
                InternetAddress emisor = new InternetAddress(mail.getNombre() + " <" + mail.getMail() + ">");
                message.setFrom(emisor);

                // Se rellenan los destinatarios
                InternetAddress receptor = new InternetAddress();
                receptor.setAddress(persona.getEmail());
                message.addRecipient(Message.RecipientType.TO, receptor);

                // Se rellena el subject
                message.setSubject(asunto);

                // Se mete el texto y la foto adjunta.
                message.setContent(multiParte);

                Transport.send(message);

            } catch (MessagingException e) {
                messageService.informUser("Falló envío de mail");
            }
        }
    }

    public static void enviarMail(Persona persona, String asunto, String mensaje) {
        if (persona.getEmail() != null) {
            Mail mail = obtenerMailEmisor();
            Properties props = conectarse(mail);
            Session session = autentificar(mail, props);

            try {
                BodyPart texto = new MimeBodyPart();

                // Texto del mensaje
                texto.setText(mensaje);

                MimeMultipart multiParte = new MimeMultipart();
                multiParte.addBodyPart(texto);

                MimeMessage message = new MimeMessage(session);

                // Se rellena el From
                InternetAddress emisor = new InternetAddress(mail.getNombre() + " <" + mail.getMail() + ">");
                message.setFrom(emisor);

                // Se rellenan los destinatarios
                InternetAddress receptor = new InternetAddress();
                receptor.setAddress(persona.getEmail());
                message.addRecipient(Message.RecipientType.TO, receptor);

                // Se rellena el subject
                message.setSubject(asunto);

                // Se mete el texto y la foto adjunta.
                message.setContent(multiParte);

                Transport.send(message);

            } catch (MessagingException e) {
                messageService.informUser("Falló envío de mail");
            }

        }
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Mail other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "mail");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "mail");
    }
    //endregion

    @Inject
    static MailRepository mailRepository;
    @Inject
    static MessageService messageService;

}
