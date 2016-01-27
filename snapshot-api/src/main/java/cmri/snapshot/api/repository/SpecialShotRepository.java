package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.SpecialShot;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhuyin on 1/15/16.
 */
public interface SpecialShotRepository extends JpaRepository<SpecialShot, Long> {
}
