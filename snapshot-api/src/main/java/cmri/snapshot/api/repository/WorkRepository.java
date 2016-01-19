package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Work;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zhuyin on 15/12/6.
 */
public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByUserId(long userId, Pageable pageable);
}
