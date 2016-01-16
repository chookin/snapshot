package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.SpecialShotGrapher;
import cmri.snapshot.api.domain.SpecialShotStills;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zhuyin on 1/15/16.
 */
public interface SpecialShotGrapherRepository extends CrudRepository<SpecialShotGrapher, Long> {
    List<SpecialShotGrapher> findByShotId(long shotId);
}
