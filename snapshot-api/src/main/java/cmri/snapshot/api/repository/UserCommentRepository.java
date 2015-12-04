package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.UserComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zhuyin on 12/3/15.
 */
public interface UserCommentRepository extends CrudRepository<UserComment, Long>{
    UserComment findByUserId(Long userId);
    @Query("select c from UserComment as c where c.object=?")
    List<UserComment> findCommentsAboutUser(Long userId);

}
