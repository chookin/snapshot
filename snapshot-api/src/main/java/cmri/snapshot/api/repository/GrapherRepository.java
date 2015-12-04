package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Grapher;
import cmri.snapshot.api.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zhuyin on 12/3/15.
 */
public interface GrapherRepository extends CrudRepository<Grapher, Long> {
    Grapher findByUserId(long id);
}
