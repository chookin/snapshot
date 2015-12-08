package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Works;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zhuyin on 15/12/6.
 */
public interface WorksRepository extends CrudRepository<Works, Long> {
    List<Works> findByUserId(long userId);
}
