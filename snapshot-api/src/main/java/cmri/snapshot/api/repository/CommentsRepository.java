package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Comments;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhuyin on 1/19/16.
 */
public interface CommentsRepository extends JpaRepository<Comments, Long>{
    List<Comments> findByObjectId(long objectId, Pageable pageable);
}
