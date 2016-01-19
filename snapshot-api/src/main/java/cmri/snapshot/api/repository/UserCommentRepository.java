package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.UserComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zhuyin on 12/3/15.
 */
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
    UserComment findByCommentatorId(long commentatorId);
    List<UserComment> findByObject(Long userId, Pageable pageable);
}
