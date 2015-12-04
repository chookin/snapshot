package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Camera;
import cmri.snapshot.api.domain.GrapherPlan;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zhuyin on 12/3/15.
 */
public interface CameraRepository extends CrudRepository<Camera, Long>{
    Camera findByUserId(long id);
}
