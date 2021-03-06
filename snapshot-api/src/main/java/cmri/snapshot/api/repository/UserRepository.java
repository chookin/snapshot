package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhuyin on 10/29/15.
 */
// mark @Transactional to avoid exception: No transactional EntityManager available.
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    default User find(User user){
        Validate.notNull(user, "arg user is null");
        return find(user.getName(), user.getPhone(), user.getEmail());
    }
    default User find(String name, Long phone, String email){
        if(StringUtils.isNoneEmpty(name)){
            return findByName(name);
        }else if(phone != null){
            return findByPhone(phone);
        }else if(StringUtils.isNoneEmpty(email)) {
            return findByEmail(email);
        }
        throw new IllegalArgumentException("请指定用户");
    }
    User findById(long id);
    User findByName(String name);
    User findByPhone(long phone);
    User findByEmail(String email);
    void deleteByPhone(long phone);
}
