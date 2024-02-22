package s.s.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmailSuccess() throws Exception {
        // Mock MimeMessage
        MimeMessage mockMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mockMessage);

        // Mock MultipartFile
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn("testbild.jpg");

        // Aufruf der zu testenden Methode
        boolean result = emailService.sendEmail("test@example.com", "Test Subject", "Test Message", mockFile);

        // Verifikation
        assertTrue(result);
        verify(javaMailSender, times(1)).send(mockMessage);
    }

    @Test
    public void testSendEmailFailure() throws Exception {
        // MimeMessage werfen, um einen Fehler zu simulieren
        when(javaMailSender.createMimeMessage()).thenThrow(new RuntimeException());

        // Aufruf der zu testenden Methode
        boolean result = emailService.sendEmail("fail@example.com", "Fail Subject", "Fail Message", null);

        // Verifikation
        assertFalse(result);
    }
}

