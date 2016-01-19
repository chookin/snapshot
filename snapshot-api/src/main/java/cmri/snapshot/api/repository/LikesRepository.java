package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhuyin on 1/19/16.
 */
@Transactional
public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Modifying
    @Query("delete from Likes as e where e.commentatorId = ?1 and e.objectId = ?2")
    void delete(long commentatorId, long objectId);
}
