package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Grapher;
import cmri.snapshot.api.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by zhuyin on 12/3/15.
 */
public interface GrapherRepository extends PagingAndSortingRepository<Grapher, Long> {
    Grapher findByUserId(long id);
}
