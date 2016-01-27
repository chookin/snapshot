package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Grapher;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zhuyin on 12/3/15.
 */
public interface GrapherRepository extends PagingAndSortingRepository<Grapher, Long> {
    Grapher findByUserId(long id);
}
