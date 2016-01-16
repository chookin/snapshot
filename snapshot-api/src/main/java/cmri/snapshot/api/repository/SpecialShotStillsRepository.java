package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.SpecialShotStills;
import cmri.snapshot.api.domain.Works;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zhuyin on 1/15/16.
 */
public interface SpecialShotStillsRepository extends CrudRepository<SpecialShotStills, Long> {
    List<SpecialShotStills> findByShotId(long shotId);
}
