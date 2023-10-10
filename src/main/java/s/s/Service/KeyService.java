package s.s.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s.s.Entitiy.KeyRequest;
import s.s.Repository.KeyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class KeyService {

    @Autowired
    private KeyRepository keyRepository;

    public KeyRequest createKeyRequest(KeyRequest keyRequest){
        return keyRepository.save(keyRequest);
    }

    public Iterable<KeyRequest> getAllKeyRequest(){
        return keyRepository.findAll();
    }

    public KeyRequest getKeyRequestById(Long id){
        Optional<KeyRequest> optionalKeyRequest = keyRepository.findById(id);
        if(optionalKeyRequest.isPresent()) {
            return optionalKeyRequest.get();
        }else {
            throw new EntityNotFoundException("Key request with id: " + id + " not found!");
        }
    }

    public void deleteKeyRequest(Long id, KeyRequest keyRequest){
        keyRepository.deleteById(id);
    }

    public void updateKeyRequest(Long id, KeyRequest keyRequest){
        Optional<KeyRequest> optionalKeyRequest = keyRepository.findById(id);
        if(optionalKeyRequest.isPresent()){
            KeyRequest existingKeyRequest = optionalKeyRequest.get();
            existingKeyRequest.setMessage(keyRequest.getMessage());
            keyRepository.save(existingKeyRequest);
        } else{
            throw new EntityNotFoundException("Key request with id: " + id + " not found!");
        }
    }
}
