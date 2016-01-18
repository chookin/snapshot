package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.GrapherPriceHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhuyin on 1/18/16.
 */
public interface GrapherPriceHistoryRepository extends JpaRepository<GrapherPriceHistory, Long> {
    List<GrapherPriceHistory> findByGrapherId(long grapherId, Pageable pageable);
}
