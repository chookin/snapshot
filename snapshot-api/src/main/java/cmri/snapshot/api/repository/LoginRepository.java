package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Login;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zhuyin on 11/15/15.
 */
public interface LoginRepository extends CrudRepository<Login, Long> {
}
