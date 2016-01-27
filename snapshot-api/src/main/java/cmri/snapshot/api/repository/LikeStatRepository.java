package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.LikeStat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhuyin on 1/27/16.
 */
public interface LikeStatRepository extends JpaRepository<LikeStat, Long> {
    LikeStat findByObjectIdAndType(long objectId, byte type);
}
