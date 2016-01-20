package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.SpecialShot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zhuyin on 1/15/16.
 */
public interface SpecialShotRepository extends JpaRepository<SpecialShot, Long> {
}
