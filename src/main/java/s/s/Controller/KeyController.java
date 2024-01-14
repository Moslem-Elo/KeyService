package s.s.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s.s.Entitiy.KeyRequest;
import s.s.Service.EmailService;
import s.s.Service.KeyService;

@RestController
public class KeyController {


@Autowired
    KeyService keyService;
    EmailService emailService;

@RequestMapping("/")
public String sysOut(){
    return "Hello world";
}

@GetMapping("/get-all")
public Iterable<KeyRequest> getAllKeyRequest(){
    return keyService.getAllKeyRequest();
}

@GetMapping("/{id}/get")
public KeyRequest getKeyRequestById(@PathVariable Long id){
    return keyService.getKeyRequestById(id);
}

@PostMapping("/create")
public ResponseEntity<KeyRequest> createQuestion(@RequestBody KeyRequest keyRequest) {
    KeyRequest createdKeyRequest = keyService.createKeyRequest(keyRequest);
    emailService.sendEmail(keyRequest.getEmail(), "Betreff", "Nachrichtentext");
    return new ResponseEntity<>(createdKeyRequest, HttpStatus.CREATED);
}

@PutMapping("/{id}/update")
public KeyRequest getKeyRequest(@PathVariable Long id, @RequestBody KeyRequest keyRequest) {
    KeyRequest updatedKeyRequest = keyService.updateKeyRequest(id, keyRequest);
    if (updatedKeyRequest != null) {
        return updatedKeyRequest;
    } else {
        throw new RuntimeException("false input");
    }
}

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteKeyRequest(@PathVariable Long id) {
        try {
            keyService.deleteKeyRequest(id);
            return ResponseEntity.ok("Schlüsselanfrage erfolgreich gelöscht.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein Fehler ist aufgetreten.");
        }
    }

}


