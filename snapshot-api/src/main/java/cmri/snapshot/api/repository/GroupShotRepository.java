package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.GroupShot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zhuyin on 12/8/15.
 */
public interface GroupShotRepository extends JpaRepository<GroupShot, Long> {
}
