package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.PhotoLike;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhuyin on 12/4/15.
 */
@Transactional
public interface PhotoLikeRepository extends CrudRepository<PhotoLike, Long>{
    @Modifying
    @Query("delete from PhotoLike as e where e.userId = ?1 and e.photoId = ?2")
    void delete(long uid, long photoId);
}
