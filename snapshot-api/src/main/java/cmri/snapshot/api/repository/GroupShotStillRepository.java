package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.GroupShotStill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhuyin on 1/21/16.
 */
public interface GroupShotStillRepository extends JpaRepository<GroupShotStill, Long> {
    List<GroupShotStill> findByShotId(long shotId);
}
