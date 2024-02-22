package s.s.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import s.s.Entitiy.KeyRequest;
import s.s.Service.EmailService;
import s.s.Service.KeyService;

import java.io.IOException;

import static s.s.Entitiy.KeyRequest.keyStatus.AUFTRAG_ANGENOMMEN;

@RestController
public class KeyController {

    @Autowired
    KeyService keyService;

    @Autowired
    EmailService emailService;

    @RequestMapping("/")
    public String sysOut() {
        return "Hello world";
    }

    @GetMapping("/get-all")
    public Iterable<KeyRequest> getAllKeyRequest() {
        return keyService.getAllKeyRequest();
    }

    @GetMapping("/{id}/get")
    public KeyRequest getKeyRequestById(@PathVariable Long id) {
        return keyService.getKeyRequestById(id);
    }



    @PostMapping("/create")
    public ResponseEntity<KeyRequest> createKeyRequest(@RequestParam("bild") MultipartFile bild, // Stellen Sie sicher, dass der Name "bild" mit dem Namen des Formularfelds übereinstimmt
                                                       @RequestParam("message") String message,
                                                       @RequestParam("phoneNumber") String phoneNumber,
                                                       @RequestParam("firstname") String firstname,
                                                       @RequestParam("lastname") String lastname,
                                                       @RequestParam("email") String email) {
        try {

            KeyRequest keyRequest = new KeyRequest();
            keyRequest.setMessage(message);
            keyRequest.setPhoneNumber(phoneNumber);
            keyRequest.setFirstname(firstname);
            keyRequest.setLastname(lastname);
            keyRequest.setEmail(email);
            keyRequest.setStatus(AUFTRAG_ANGENOMMEN);
            keyService.createKeyRequest(keyRequest);
            System.out.println("KeyRequest wurde erstellt");
            emailService.sendEmail(email, "Ihre Anfrage mit der mit der Auftragsnummer " + keyRequest.getId() + " ist eingegangen.", emailService.emailTextCustomer(keyRequest), bild);
            return new ResponseEntity<>(keyRequest, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}/update")
    public KeyRequest updateKeyRequest(@PathVariable Long id, @RequestBody KeyRequest keyRequest) {
        try {
            return keyService.updateKeyRequest(id, keyRequest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Aktualisieren der Schlüsselanfrage");
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteKeyRequest(@PathVariable Long id) {
        try {
            keyService.deleteKeyRequest(id);
            return ResponseEntity.ok("Schlüsselanfrage erfolgreich gelöscht.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein Fehler ist aufgetreten.");
        }
    }
}



