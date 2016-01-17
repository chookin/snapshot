package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.ShotStill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhuyin on 1/17/16.
 */
public interface ShotStillRepository extends JpaRepository<ShotStill, Long> {
    List<ShotStill> findByShotId(long shotId);
}
