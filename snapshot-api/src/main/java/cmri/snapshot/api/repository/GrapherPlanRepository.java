package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.GrapherPlan;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zhuyin on 12/3/15.
 */
public interface GrapherPlanRepository extends CrudRepository<GrapherPlan, Long>{
    GrapherPlan findByUserId(long id);
}
