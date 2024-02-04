package s.s.ServiceTest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import s.s.Entitiy.KeyRequest;
import s.s.Repository.KeyRepository;
import s.s.Service.KeyService;

import jakarta.persistence.EntityNotFoundException;

import java.io.IOException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class KeyServiceTest {

    @InjectMocks
    private KeyService keyService;

    @Mock
    private KeyRepository keyRepository;

    @Test
    public void testCreateKeyRequest() {
        // Arrange
        KeyRequest mockKeyRequest = new KeyRequest();
        Mockito.when(keyRepository.save(Mockito.any(KeyRequest.class))).thenReturn(mockKeyRequest);

        // Act
        KeyRequest createdKeyRequest = keyService.createKeyRequest(new KeyRequest());

        // Assert
        assert createdKeyRequest != null;
        Mockito.verify(keyRepository, Mockito.times(1)).save(Mockito.any(KeyRequest.class));
    }

    @Test
    public void testGetAllKeyRequest() {
        // Arrange
        List<KeyRequest> mockKeyRequests = new ArrayList<>();
        Mockito.when(keyRepository.findAll()).thenReturn(mockKeyRequests);

        // Act
        Iterable<KeyRequest> keyRequests = keyService.getAllKeyRequest();

        // Assert
        assert keyRequests != null;
        Mockito.verify(keyRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetKeyRequestById() {
        // Arrange
        Long keyRequestId = 1L;
        KeyRequest mockKeyRequest = new KeyRequest();
        Mockito.when(keyRepository.findById(keyRequestId)).thenReturn(Optional.of(mockKeyRequest));

        // Act
        KeyRequest keyRequest = keyService.getKeyRequestById(keyRequestId);

        // Assert
        assert keyRequest != null;
        Mockito.verify(keyRepository, Mockito.times(1)).findById(keyRequestId);
    }

    @Test
    public void testGetKeyRequestById_NotFound() {
        // Arrange
        Long keyRequestId = 1L;
        Mockito.when(keyRepository.findById(keyRequestId)).thenReturn(Optional.empty());

        // Act/Assert
        assertThrows(EntityNotFoundException.class, () -> keyService.getKeyRequestById(keyRequestId));
        Mockito.verify(keyRepository, Mockito.times(1)).findById(keyRequestId);
    }

    @Test
    public void testDeleteKeyRequest() {
        // Arrange
        Long keyRequestId = 1L;

        // Act
        keyService.deleteKeyRequest(keyRequestId);

        // Assert
        Mockito.verify(keyRepository, Mockito.times(1)).deleteById(keyRequestId);
    }

    @Test
    public void testUpdateKeyRequest() throws IOException {
        // Arrange
        Long keyRequestId = 1L;
        KeyRequest existingKeyRequest = new KeyRequest();
        KeyRequest updatedKeyRequest = new KeyRequest();
        updatedKeyRequest.setMessage("Updated Message");

        Mockito.when(keyRepository.findById(keyRequestId)).thenReturn(Optional.of(existingKeyRequest));
        Mockito.when(keyRepository.save(existingKeyRequest)).thenReturn(existingKeyRequest);

        // Act
        KeyRequest result = keyService.updateKeyRequest(keyRequestId, updatedKeyRequest);

        // Assert
        assert result != null;
        assert result.getMessage().equals("Updated Message");
        Mockito.verify(keyRepository, Mockito.times(1)).findById(keyRequestId);
        Mockito.verify(keyRepository, Mockito.times(1)).save(existingKeyRequest);
    }

    @Test
    public void testUpdateKeyRequest_NotFound() {
        // Arrange
        Long keyRequestId = 1L;
        KeyRequest updatedKeyRequest = new KeyRequest();
        updatedKeyRequest.setMessage("Updated Message");

        Mockito.when(keyRepository.findById(keyRequestId)).thenReturn(Optional.empty());

        // Act/Assert
        assertThrows(EntityNotFoundException.class, () -> keyService.updateKeyRequest(keyRequestId, updatedKeyRequest));
        Mockito.verify(keyRepository, Mockito.times(1)).findById(keyRequestId);
    }
}
