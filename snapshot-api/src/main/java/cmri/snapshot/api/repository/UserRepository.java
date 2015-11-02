package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zhuyin on 10/29/15.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
    User findByMobile(long mobile);
    User findByEmail(String email);
}
