package s.s.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import s.s.Entitiy.KeyRequest;


public interface KeyRepository extends CrudRepository<KeyRequest, Long> {
}
