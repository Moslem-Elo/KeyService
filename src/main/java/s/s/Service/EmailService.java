package s.s.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import s.s.Entitiy.KeyRequest;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendEmail(String to, String subject, String text, MultipartFile bild) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            // Fügen Sie den Dateianhang hinzu, wenn ein Bild vorhanden ist
            if (bild != null && !bild.isEmpty()) {
                helper.addAttachment(bild.getOriginalFilename(), bild);
            }

            javaMailSender.send(message);

            // Das Senden war erfolgreich, da keine Ausnahme ausgelöst wurde
            return true;
        } catch (MessagingException | MailException e) {
            // Fehler beim Senden der E-Mail
            e.printStackTrace();

            // Hier können Sie die Ausnahmebehandlung anpassen,
            // z.B. durch Protokollierung, Benachrichtigung oder alternative Verarbeitung
            // Beispiel: logger.error("Fehler beim Senden der E-Mail", e);

            return false;
        }
    }

    public String emailTextCustomer(KeyRequest keyRequest){
        String text ="\n\n\nDiese E-Mail wird automatisch generiert. Bitte nicht antworten." +
                "\n\nGuten Tag " + keyRequest.getFirstname() + " " + keyRequest.getLastname() + "," +
                "\n\nIhre Anfrage wird bearbeitet. Bitte behalten Sie Ihre Auftragsnummer, wenn Sie den Status der Anfrage überprüfen möchten." +
                "\n\nIhre Auftragsnummer: " + keyRequest.getId() +
                "\nSie können unter dem Bereich \"Auftrag suchen\" gehen und dort erhalten Sie dann Updates zu Ihrem Auftrag." +
                "\n\n\n\nKontakt:\n\nAnsprechpartner: Fatih Gümus\nTelefonnummer: 0151991915\nAdresse: Tempelhoferhafen 23, 12342 Berlin" + "\n\nIhr beigefügtes Bild: ";

        return text;
    }

}
