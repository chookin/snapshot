package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.CommentStat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhuyin on 1/27/16.
 */
public interface CommentStatRepository extends JpaRepository<CommentStat, Long>{
    CommentStat findByObjectIdAndType(long objectId, byte type);
}
