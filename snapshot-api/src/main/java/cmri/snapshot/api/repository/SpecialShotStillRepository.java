package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.SpecialShotStill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zhuyin on 1/15/16.
 */
public interface SpecialShotStillRepository extends CrudRepository<SpecialShotStill, Long> {
    List<SpecialShotStill> findByShotId(long shotId);
}
