package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.ShotRelease;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhuyin on 1/17/16.
 */
public interface ShotReleaseRepository extends JpaRepository<ShotRelease, Long> {
}
