package s.s.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import s.s.Entitiy.KeyRequest;
import s.s.Repository.KeyRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class KeyService {

    @Autowired
    private KeyRepository keyRepository;

    @Transactional
    public KeyRequest createKeyRequest(KeyRequest keyRequest) {
        return keyRepository.save(keyRequest);
    }

    public boolean updateStatus(Long id, KeyRequest.keyStatus keyStatus) {
        Optional<KeyRequest> keyRequestOptional = keyRepository.findById(id);
        if (keyRequestOptional.isPresent()) {
            KeyRequest keyRequest = keyRequestOptional.get();
            keyRequest.setStatus(keyStatus);
            keyRepository.save(keyRequest);
            return true;
        }
        return false;
    }
    @Transactional
    public KeyRequest updateKeyRequest(Long id, KeyRequest keyRequest) throws IOException {
        Optional<KeyRequest> optionalKeyRequest = keyRepository.findById(id);
        if (optionalKeyRequest.isPresent()) {
            KeyRequest existingKeyRequest = optionalKeyRequest.get();
            existingKeyRequest.setMessage(keyRequest.getMessage());
            existingKeyRequest.setPhoneNumber(keyRequest.getPhoneNumber());
            existingKeyRequest.setFirstname(keyRequest.getFirstname());
            existingKeyRequest.setLastname(keyRequest.getLastname());
            existingKeyRequest.setStatus(keyRequest.getStatus());

            return keyRepository.save(existingKeyRequest);
        } else {
            throw new EntityNotFoundException("Key request with id: " + id + " not found!");
        }
    }

    public Iterable<KeyRequest> getAllKeyRequest(){
        return keyRepository.findAll();
    }

    public KeyRequest getKeyRequestById(Long id){
        Optional<KeyRequest> optionalKeyRequest = keyRepository.findById(id);
        if(optionalKeyRequest.isPresent()) {
            return optionalKeyRequest.get();
        }else{
            throw new EntityNotFoundException("Key request with id: " + id + " not found!");
        }
    }

    public void deleteKeyRequest(Long id){
        keyRepository.deleteById(id);
    }



}
